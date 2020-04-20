package com.bbq.websocketserver.entity;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author liutf
 * @date 2020-04-20
 */
@Data
public class MsgRecord {
    private String id;
    private String customerId;
    private String serviceId;
    private Date   recordTime;

    public static void main(String[] args) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }
}
