package com.bbq.websocketserver.common.dto;

import lombok.Data;

/**
 * @author liutf
 * @date 2020-04-20
 */
@Data
public class MessageDto {
    private String userId;
    private String message;

    public MessageDto(){
        super();
    }
    public MessageDto(String message) {
        this.message = message;
    }

    public MessageDto(String userId, String message) {
        this.userId = userId;
        this.message = message;
    }
}
