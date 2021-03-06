package com.bbq.websocketserver.service.impl;

import com.bbq.websocketserver.common.RedisKeyPrefixEnum;
import com.bbq.websocketserver.common.utils.RedisUtils;
import com.bbq.websocketserver.dto.QueryMsgRecordDto;
import com.bbq.websocketserver.entity.MsgRecord;
import com.bbq.websocketserver.mapper.MsgRecordMapper;
import com.bbq.websocketserver.service.MsgRecordService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
     *
     * @param msgRecord
     * @return
     */
    @Override
    public int saveMsgRecord(MsgRecord msgRecord) {
        return msgRecordMapper.saveMsgRecord(msgRecord);
    }

    /**
     * 查询聊天记录
     *
     * @param dto
     * @return
     */
    @Override
    public List<MsgRecord> queryMsgRecord(QueryMsgRecordDto dto) {
        return msgRecordMapper.queryMsgRecord(dto);
    }

    /**
     * 获取redis中的会话列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<String> getChats(String userId) {
        Object objects = RedisUtils.zsGet(RedisKeyPrefixEnum.SORTED_SET.getKeyName() + userId);
        if (objects == null || objects instanceof Set) {
            return null;
        }
        Set<String> chats = (Set<String>) (Set) objects;
        List<String> chatList = chats.stream().collect(Collectors.toList());
        return chatList;
    }

    /**
     * 获取redis中的离线消息
     *
     * @param ownId
     * @param otherId
     * @return
     */
    @Override
    public List<MsgRecord> getLeaveMsg(String ownId, String otherId) {
        // 组装redis中的list"容器"的key值
        String lLey = RedisKeyPrefixEnum.LIST.getKeyName() + ownId + otherId;
        List<Object> objectMsg = RedisUtils.lGet(lLey, 0, -1);
        if (CollectionUtils.isEmpty(objectMsg)) {
            List<MsgRecord> msgRecordList = (List<MsgRecord>) (List) objectMsg;
            return msgRecordList;
        }
        return null;
    }
}
