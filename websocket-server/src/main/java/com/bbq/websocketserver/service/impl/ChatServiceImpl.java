package com.bbq.websocketserver.service.impl;

import com.bbq.websocketserver.common.dto.MessageDto;
import com.bbq.websocketserver.common.utils.RedisUtils;
import com.bbq.websocketserver.service.ChatService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author liutf
 * @date 2020-04-20
 */
@Service
public class ChatServiceImpl implements ChatService {

    @Override
    public List<MessageDto> getLeaveMsg(String userId) {
        // 组装redis中的list"容器"的key值
        String lUserId = "l" + userId;
        List<Object> objectMsg = RedisUtils.lGet(lUserId, 0, -1);
        if (!CollectionUtils.isEmpty(objectMsg)) {
            List<MessageDto> leaveMsg = (List<MessageDto>) (List) objectMsg;
            return leaveMsg;
        }
        return null;
    }

    @Override
    public void confirmMsgCount(String userId, Integer count) {
        // 组装redis中的list"容器"的key值
        String lUserId = "l" + userId;
        List<Object> objectMsg = RedisUtils.lGet(lUserId, 0, -1);
        if(objectMsg.size() == count){
            // 删除redis中的留言记录&&更改留言表中留言处理状态
            RedisUtils.delete(lUserId);
            // 级联更新聊天记录表
        }
    }
}
