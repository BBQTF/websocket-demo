package com.bbq.websocketserver.common;

import lombok.Getter;

public class BBQException extends RuntimeException {

    @Getter
    private ResultStatusEnum resultStatusEnum;
    @Getter
    private int resultCode;
    @Getter
    private String resultMsg;


    public BBQException(ResultStatusEnum errorCode) {
        super(errorCode.getResultMsg());
        this.resultCode = errorCode.getResultCode();
        this.resultMsg = errorCode.getResultMsg();
        this.resultStatusEnum = errorCode;
    }

    public BBQException(int resultCode, String resultMsg) {
        super(resultMsg);
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }
}
