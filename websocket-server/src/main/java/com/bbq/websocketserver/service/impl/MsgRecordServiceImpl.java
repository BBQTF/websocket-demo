package com.bbq.websocketserver.service.impl;

import com.bbq.websocketserver.dto.QueryMsgRecordDto;
import com.bbq.websocketserver.entity.MsgRecord;
import com.bbq.websocketserver.mapper.MsgRecordMapper;
import com.bbq.websocketserver.service.MsgRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liutf
 * @date 2020-04-21
 */
@Service
public class MsgRecordServiceImpl implements MsgRecordService {
    @Resource
    private MsgRecordMapper msgRecordMapper;

    /**
     * 插入聊天记录
     * @param msgRecord
     * @return
     */
    @Override
    public int saveMsgRecord(MsgRecord msgRecord) {
        return saveMsgRecord(msgRecord);
    }

    /**
     * 查询聊天记录
     * @param dto
     * @return
     */
    @Override
    public List<MsgRecord> queryMsgRecord(QueryMsgRecordDto dto) {
        return msgRecordMapper.queryMsgRecord(dto);
    }
}
