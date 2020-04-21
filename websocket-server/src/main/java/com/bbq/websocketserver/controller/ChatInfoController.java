package com.bbq.websocketserver.controller;

import com.bbq.websocketserver.common.ApiResponse;
import com.bbq.websocketserver.dto.QueryMsgRecordDto;
import com.bbq.websocketserver.entity.LeaveMsg;
import com.bbq.websocketserver.entity.MsgRecord;
import com.bbq.websocketserver.service.ChatService;
import com.bbq.websocketserver.service.MsgRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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
    @Resource
    MsgRecordService msgRecordService;

    @GetMapping("/getLeaveMsg")
    @ApiOperation("获取留言")
    public ApiResponse<List<LeaveMsg>> getLeaveMsg(@RequestParam String userId){
        return new ApiResponse<List<LeaveMsg>>(chatService.getLeaveMsg(userId));
    }

    @GetMapping("/confirmMsgCount")
    @ApiOperation("确认客户端收到留言") // TODO 未做完整性验证
    public void confirmMsgCount(@RequestParam String userId, @RequestParam Integer count){
        chatService.confirmMsgCount(userId, count);
    }

    @PostMapping("/queryMsgRecord")
    @ApiOperation("查询聊天记录")
    public ApiResponse<List<MsgRecord>> queryMsgRecord(@RequestBody QueryMsgRecordDto dto){
        return new ApiResponse<List<MsgRecord>>(msgRecordService.queryMsgRecord(dto));
    }
}
