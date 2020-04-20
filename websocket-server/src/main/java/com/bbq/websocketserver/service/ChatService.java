package com.bbq.websocketserver.service;

import com.bbq.websocketserver.common.dto.MessageDto;

import java.util.List;

/**
 * @author liutf
 * @date 2020-04-20
 */
public interface ChatService {
    /**
     * 获取留言记录
     *
     * @param userId
     * @return
     */
    List<MessageDto> getLeaveMsg(String userId);

    /**
     * 确认客户端收到消息
     *
     * @param userId
     * @param count
     */
    void confirmMsgCount(String userId, Integer count);
}
