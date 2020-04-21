package com.bbq.websocketserver.service.impl;

import com.bbq.websocketserver.common.utils.RedisUtils;
import com.bbq.websocketserver.entity.LeaveMsg;
import com.bbq.websocketserver.mapper.ChatMapper;
import com.bbq.websocketserver.service.ChatService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liutf
 * @date 2020-04-20
 */
@Service
public class ChatServiceImpl implements ChatService {
    @Resource
    private ChatMapper chatMapper;

    /**
     * 获取留言记录
     *
     * @param userId
     * @return
     */
    @Override
    public List<LeaveMsg> getLeaveMsg(String userId) {
        // 组装redis中的list"容器"的key值
        String lUserId = "l" + userId;
        List<Object> objectMsg = RedisUtils.lGet(lUserId, 0, -1);
        if (!CollectionUtils.isEmpty(objectMsg)) {
            List<LeaveMsg> leaveMsg = (List<LeaveMsg>) (List) objectMsg;
            return leaveMsg;
        }
        return null;
    }

    /**
     * 确认客户端收到消息
     *
     * @param userId
     * @param count
     */
    @Override
    public void confirmMsgCount(String userId, Integer count) {
        // 组装redis中的list"容器"的key值
        String lUserId = "l" + userId;
        List<Object> objectMsg = RedisUtils.lGet(lUserId, 0, -1);
        if (objectMsg.size() == count) {
            // 删除redis中的留言记录&& TODO 更改留言表中留言处理状态
            RedisUtils.delete(lUserId);
            // TODO 级联更新聊天记录表
        }
    }

    /**
     * 留言信息入库
     *
     * @param leaveMsg
     */
    @Override
    public void saveLeaveMsg(LeaveMsg leaveMsg) {
        chatMapper.saveLeaveMsg(leaveMsg);
    }

    /**
     * 更新留言状态
     *
     * @param status
     */
    @Override
    public void updateStatus(String status) {
        chatMapper.updateStatus(status);
    }
}
