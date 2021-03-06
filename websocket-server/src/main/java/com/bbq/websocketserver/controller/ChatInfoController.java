package com.bbq.websocketserver.controller;

import com.bbq.websocketserver.common.ApiResponse;
import com.bbq.websocketserver.common.ResultStatusEnum;
import com.bbq.websocketserver.dto.QueryMsgRecordDto;
import com.bbq.websocketserver.entity.MsgRecord;
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
    MsgRecordService msgRecordService;

    /**
     * 获取redis中的会话列表
     * @param userId
     * @return
     */
    @GetMapping("/getChats")
    @ApiOperation("获取会话列表")
    public ApiResponse  getChats(@RequestParam String userId){
        try{
            return new ApiResponse<List<String>>(msgRecordService.getChats(userId));
        }catch (Exception e){
            e.printStackTrace();
            return new ApiResponse<>(ResultStatusEnum.SYSTEM_ERROR);
        }
    }

    @GetMapping("/getLeaveMsg")
    @ApiOperation("获取redis中的离线消息")
    public ApiResponse  getLeaveMsg(@RequestParam String ownId,@RequestParam String otherId){
        try{
           return new ApiResponse<List<MsgRecord>>(msgRecordService.getLeaveMsg(ownId, otherId));
        }catch (Exception e){
            e.printStackTrace();
            return new ApiResponse<>(ResultStatusEnum.SYSTEM_ERROR);
        }
    }


    @PostMapping("/queryMsgRecord")
    @ApiOperation("查询聊天记录")
    public ApiResponse queryMsgRecord(@RequestBody QueryMsgRecordDto dto){
        try {
            return new ApiResponse<List<MsgRecord>>(msgRecordService.queryMsgRecord(dto));
        }catch (Exception e){
            e.printStackTrace();
            return new ApiResponse<>(ResultStatusEnum.SYSTEM_ERROR);
        }
    }
}
