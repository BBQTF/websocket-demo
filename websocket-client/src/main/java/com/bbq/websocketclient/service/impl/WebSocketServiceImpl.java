package com.bbq.websocketclient.service.impl;

import com.bbq.websocketclient.service.WebSocketService;
import org.java_websocket.client.WebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liutf
 * @date 2020-04-17
 */
@Service
public class WebSocketServiceImpl implements WebSocketService {
    @Autowired
    private WebSocketClient webSocketClient;
    @Override
    public void groupSending(String message) {
        webSocketClient.send(message);
    }

    @Override
    public void appointSending(String message) {
        webSocketClient.send(message);
    }
}
