package com.bbq.websocketserver.entity;

import lombok.Data;


/**
 * 消息记录实体类
 *
 * @author liutf
 * @date 2020-04-20
 */
@Data
public class MsgRecord {
    private String id;
    private String ownId;
    private String otherId;
    private String message;
    private Long recordTime;
    private Integer status;

    public MsgRecord() {
        super();
    }

    public MsgRecord(String id, String ownId, String otherId, String message, Long recordTime, Integer status) {
        this.id = id;
        this.ownId = ownId;
        this.otherId = otherId;
        this.message = message;
        this.recordTime = recordTime;
        this.status = status == null ? 0 : status;
    }
}
