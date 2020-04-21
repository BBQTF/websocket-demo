package com.bbq.websocketserver.dto;

import lombok.Data;

/**
 * @author liutf
 * @date 2020-04-21
 */
@Data
public class QueryMsgRecordDto {
    private String customerId;
    private String serviceId;
    private Long startTime;
    private Long endTime;
}
