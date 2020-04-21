package com.bbq.websocketserver.mapper;

import com.bbq.websocketserver.entity.LeaveMsg;

/**
 * @author liutf
 * @date 2020-04-21
 */
public interface ChatMapper {
    /**
     * 插入留言记录
     *
     * @param leaveMsg
     * @return
     */
    int saveLeaveMsg(LeaveMsg leaveMsg);

    /**
     * 更新留言状态
     *
     * @param status
     * @return
     */
    int updateStatus(String status);
}
