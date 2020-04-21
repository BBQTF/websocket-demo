package com.bbq.websocketserver.service;

import com.bbq.websocketserver.dto.QueryMsgRecordDto;
import com.bbq.websocketserver.entity.MsgRecord;

import java.util.List;

/**
 * @author liutf
 * @date 2020-04-21
 */
public interface MsgRecordService {
    /**
     * 插入聊天记录
     * @param msgRecord
     * @return
     */
    int saveMsgRecord(MsgRecord msgRecord);

    /**
     * 查询聊天记录
     * @param dto
     * @return
     */
    List<MsgRecord> queryMsgRecord(QueryMsgRecordDto dto);

    /**
     * 获取redis中的离线消息
     * @param ownId
     * @param otherId
     * @return
     */
    List<MsgRecord> getLeaveMsg(String ownId, String otherId);
}
