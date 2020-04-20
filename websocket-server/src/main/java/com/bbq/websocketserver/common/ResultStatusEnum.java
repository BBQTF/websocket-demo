package com.bbq.websocketserver.common;

import lombok.Getter;

import java.util.Optional;

public enum ResultStatusEnum {

    /**
     * 系统异常
     */
    SYSTEM_ERROR(-1, "System error"),

    /**
     * 成功
     */
    SUCCESS(200, "success"),

    /**
     * 记录未找到
     */
    RECORD_NOT_FOUND(1000, "the resource you want to operate is not found in database!"),

    /**
     * 非法参数
     */
    ILLEGAL_PARAMETER(1001, "Illegal Parameter"),


    /**
     * 主键重复
     */
    PRIMARY_REPEAT(1002, "primary key repeat"),
    /**
     * page信息不对
     */
    PAGE_PARAM_ERROR(1016, "sorry! please input correct page information!");


    @Getter
    private int resultCode;
    @Getter
    private String resultMsg;

    ResultStatusEnum(int resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    public static ResultStatusEnum parse(int code, String msg) {

        for (ResultStatusEnum resultStatusEnum : ResultStatusEnum.values()) {

            if (code == resultStatusEnum.getResultCode()) {
                Optional.ofNullable(msg).ifPresent(ms -> resultStatusEnum.resultMsg = ms);
                return resultStatusEnum;
            }
        }

        throw new IllegalArgumentException();
    }
}
