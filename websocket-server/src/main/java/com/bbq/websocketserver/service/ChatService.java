package com.bbq.websocketserver.service;

import com.bbq.websocketserver.dto.MessageDto;
import com.bbq.websocketserver.entity.LeaveMsg;

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
    List<LeaveMsg> getLeaveMsg(String userId);

    /**
     * 确认客户端收到消息
     *
     * @param userId
     * @param count
     */
    void confirmMsgCount(String userId, Integer count);

    /**
     * 留言信息入库
     *
     * @param leaveMsg
     */
    void saveLeaveMsg(LeaveMsg leaveMsg);

    /**
     * 更新留言状态
     *
     * @param status
     */
    void updateStatus(String status);
}
