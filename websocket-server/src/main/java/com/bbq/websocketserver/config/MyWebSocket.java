package com.bbq.websocketserver.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bbq.websocketserver.common.utils.RedisUtils;
import com.bbq.websocketserver.entity.MsgRecord;
import com.bbq.websocketserver.service.MsgRecordService;
import com.bbq.websocketserver.service.UserService;
import com.bbq.websocketserver.dto.MessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liutf
 * @date 2020-04-17
 */
@Component
@ServerEndpoint(value = "/websocket/{userId}", configurator = GetHttpSessionConfigurator.class)
// 该注解用来指定一个URI，客户端可以通过这个URI来连接到WebSocket
public class MyWebSocket {

    //静态变量，用来记录当前在线连接数
    private static int onlineCount = 0;

    //根据用户标识存放每个客户端对应的MyWebSocket对象
    private static ConcurrentHashMap<String, MyWebSocket> websocketMap = new ConcurrentHashMap<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private String userId;
    private UserService userService;
    private MsgRecordService msgRecordService;
    private HttpSession httpSession;
    private final static Logger logger = LoggerFactory.getLogger(MyWebSocket.class);

    /**
     * 连接建立成功调用的方法
     *
     * @param session 为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") String userId, EndpointConfig config) {
        this.session = session;
        this.userId = userId;
        websocketMap.put(userId, this); //加入map中
        addOnlineCount(); //在线数加1
        logger.info("客户端----：" + userId + "加入连接");
        logger.info("有新连接加入！当前在线人数为" + getOnlineCount());
        // 获取httpSession
        this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        if (httpSession != null) {
            ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(httpSession.getServletContext());
            // 获取service
            userService = context.getBean(UserService.class);
            msgRecordService = context.getBean(MsgRecordService.class);
        }
        // 验证当前用户身份
        boolean customer = "5".equals(userId); //如果是客户身份 TODO 获取用户session中的角色标识
        if (customer) {
            // 验证当前用户会话是否已有客服处理
            // 根据客户id获取客服id
            Object serviceId = RedisUtils.get(userId);
            MessageDto messageDto = new MessageDto();
            if (serviceId == null) {
                // 查询当前处理会话数最低的客服userId
                String lowestServiceId = "test"; // TODO 暂时写死，应从redis或数据库中获取
                // 为客户分配对应的客服
                messageDto.setUserId(lowestServiceId);
                this.AppointSending(userId, JSON.toJSONString(messageDto));
                // 记录客户沟通对象
                RedisUtils.set(userId, lowestServiceId);
                // 缓存客服沟通对象-选择sorted set 存储
                RedisUtils.zsSet("zs" + lowestServiceId, userId);
            } else {
                messageDto.setUserId((String) serviceId);
                this.AppointSending(userId, JSON.toJSONString(messageDto));
            }
        }
        /**替代请求接口获取离线时消息的方案，更新为客户端连接时自动推送*/
        // 获取当前用户的离线消息
    }

    /**
     * 连接关闭时调用的方法
     */
    @OnClose
    public void onClose() {
        websocketMap.remove(this);  //从map中删除
        subOnlineCount();  //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 客户端消息分发处理
     *
     * @param message
     */
    @OnMessage
    public void sending(String message) {
        // 解析消息
        MsgRecord msgRecord = transToMsgRecord(message);
        try {
            logger.info("收到客户端传来的消息---：" + message);
            // 指定userId发送消息&&也将自己的userId保存在messageDto中发送给对方
            MessageDto messageDto = new MessageDto();
            messageDto.setUserId(userId);
            messageDto.setMessage(msgRecord.getMessage());
            AppointSending(msgRecord.getOtherId(), JSON.toJSONString(messageDto));
            // 消息数据入库
            msgRecordService.saveMsgRecord(msgRecord);
        } /**防止接收方下线，无法正常推送消息，将消息定义为留言*/ catch (NullPointerException e) {
            logger.info("你好，对方已离线，请留言");
            setLeaveMsg(msgRecord);
            AppointSending(userId, JSON.toJSONString(new MessageDto("你好，对方已离线，请留言")));
        } catch (IllegalStateException e) {
            logger.error("你好，对方已离线，请留言");
            setLeaveMsg(msgRecord);
            AppointSending(userId, JSON.toJSONString(new MessageDto("你好，对方已离线，请留言")));
        }
    }

    /**
     * 指定客户端发送消息
     *
     * @param userId  接收方用户客户端标识
     * @param message 消息
     */
    public void AppointSending(String userId, String message) {
        try {
            websocketMap.get(userId).session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析消息
     *
     * @param message
     * @return
     */
    private MsgRecord transToMsgRecord(String message) {
        try {
            MessageDto messageDto = JSONObject.parseObject(message, MessageDto.class);
            MsgRecord msgRecord = new MsgRecord();
            msgRecord.setId(UUID.randomUUID().toString());
            msgRecord.setOwnId(userId);
            msgRecord.setOtherId(messageDto.getUserId());
            msgRecord.setMessage(messageDto.getMessage());
            msgRecord.setRecordTime(new Date().getTime());
            return msgRecord;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 设置留言
     *
     * @param msgRecord
     */
    private void setLeaveMsg(MsgRecord msgRecord) {
        // 组装redis中的list"容器"的key值 l + 接收方id + 自己的id
        String lLey = "l" + msgRecord.getOtherId() + userId;
        List<Object> objectMsg = RedisUtils.lGet(lLey, 0, -1);
        if (CollectionUtils.isEmpty(objectMsg)) {
            List<MsgRecord> msgRecordList = new ArrayList<>();
            msgRecordList.add(msgRecord);
            // 缓存留言
            RedisUtils.llSet(lLey, msgRecordList);
        }
    }

    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        MyWebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        MyWebSocket.onlineCount--;
    }
}
