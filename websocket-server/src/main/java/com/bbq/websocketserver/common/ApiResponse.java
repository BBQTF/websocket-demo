package com.bbq.websocketserver.common;

import java.io.Serializable;

/**
 * @author liutf
 * @date 2020-03-24
 */
public class ApiResponse<T> implements Serializable {
    private int code;
    private String message;
    private T data;

    public ApiResponse() {
        this.code = ResultStatusEnum.SUCCESS.getResultCode();
        this.message = ResultStatusEnum.SUCCESS.getResultMsg();
    }

    public ApiResponse(ResultStatusEnum resultStatusEnum) {
        this.code = resultStatusEnum.getResultCode();
        this.message = resultStatusEnum.getResultMsg();
    }

    public ApiResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiResponse(ResultStatusEnum resultStatusEnum, T data) {
        this.code = resultStatusEnum.getResultCode();
        this.message = resultStatusEnum.getResultMsg();
        this.data = data;
    }

    public ApiResponse(T data) {
        this.code = ResultStatusEnum.SUCCESS.getResultCode();
        this.message = ResultStatusEnum.SUCCESS.getResultMsg();
        this.data = data;
    }

    public T data() throws BBQException {
        if (this.code == ResultStatusEnum.SUCCESS.getResultCode()) {
            return this.data;
        }
        throw new BBQException(this.code, this.message);
    }
}
