package com.bbq.websocketserver.entity;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

/**
 * 留言表实体类
 *
 * @author liutf
 * @date 2020-04-21
 */
@Data
public class LeaveMsg {
    private String id;
    private String userId;
    private String message;
    private Long recordTime;
    private Integer status;

    public LeaveMsg() {
        super();
    }

    public LeaveMsg(String id, String userId, String message, Long recordTime, Integer status) {
        this.id = id;
        this.userId = userId;
        this.message = message;
        this.recordTime = recordTime;
        this.status = status == null ? 0 : status;
    }
}
