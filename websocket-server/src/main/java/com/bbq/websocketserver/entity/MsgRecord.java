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
    private String customerId;
    private String serviceId;
    private Long recordTime;
}
