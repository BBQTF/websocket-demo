package com.bbq.websocketserver.mapper;

import com.bbq.websocketserver.dto.QueryMsgRecordDto;
import com.bbq.websocketserver.entity.MsgRecord;

import java.util.List;

/**
 * @author liutf
 * @date 2020-04-21
 */
public interface MsgRecordMapper {
    /**
     * 插入聊天记录
     * @param msgRecord
     * @return
     */
    int saveMsgRecord(MsgRecord msgRecord);

    /**
     * 查询聊天记录
     * @param queryMsgRecordDto 查询参数对象
     * @return
     */
    List<MsgRecord> queryMsgRecord(QueryMsgRecordDto queryMsgRecordDto);
}
