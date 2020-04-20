package com.bbq.websocketclient.controller;

import com.bbq.websocketclient.service.WebSocketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author liutf
 * @date 2020-04-17
 */
@RestController
@Api("websocket测试客户端")
@RequestMapping("/client")
public class WebSocketClientController {

    @Resource
    private WebSocketService service;

    @ApiOperation("发送信息")
    @PostMapping("/send")
    public void send(@RequestBody String message){
        service.appointSending(message);
    }

}
