package com.example.demo.service;

/**
 * @author liutf
 * @date 2020-04-17
 */
public interface WebSocketService {

    /**
     * 群发
     * @param message
     */
    void groupSending(String message);

    /**
     * 指定发送
     * @param message
     */
    void appointSending(String message);
}


