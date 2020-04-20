package com.bbq.websocketserver.controller;

import com.bbq.websocketserver.common.ApiResponse;
import com.bbq.websocketserver.common.dto.MessageDto;
import com.bbq.websocketserver.common.utils.RedisUtils;
import com.bbq.websocketserver.service.ChatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 聊天室控制层
 * @author liutf
 * @date 2020-04-20
 */
@RestController
@Api("聊天室控制层")
@RequestMapping("/chat")
public class ChatInfoController {
    @Resource
    private ChatService chatService;

    @GetMapping("/getLeaveMsg")
    @ApiOperation("获取留言")
    public ApiResponse<List<MessageDto>> getLeaveMsg(@RequestParam String userId){
        return new ApiResponse<List<MessageDto>>(chatService.getLeaveMsg(userId));
    }

    @GetMapping("/confirmMsgCount")
    @ApiOperation("确认客户端收到留言") // TODO 未做完整性验证
    public void confirmMsgCount(@RequestParam String userId, @RequestParam Integer count){

    }
}
