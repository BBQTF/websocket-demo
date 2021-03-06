package com.bbq.websocketclient.config;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.URI;


@Component
public class WebSocketClientConfig {

    private static Logger log = LoggerFactory.getLogger(WebSocketClientConfig.class);

    // 获取当前用户Id
    String userId = "test";

    @Bean
    public WebSocketClient WebSocketClient() {
        try {
            //WebSocketClient webSocketClient = new WebSocketClient(new URI("ws://192.168.1.153:8084/productWebSocket/001"), new Draft_6455()) {
            WebSocketClient webSocketClient = new WebSocketClient(new URI("ws://172.20.10.3:9093/pushServer/" + userId), new Draft_6455()) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    log.info("[websocket] 连接成功");
                }

                @Override
                public void onMessage(String message) {
                    log.info("[websocket] 收到消息={}", message);

                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    log.info(code + "");
                    log.info(reason);
                    log.info("[websocket] 退出连接");
                }

                @Override
                public void onError(Exception ex) {
                    log.info("[websocket] 连接错误={}", ex.getMessage());
                }
            };
            webSocketClient.connect();
            return webSocketClient;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

