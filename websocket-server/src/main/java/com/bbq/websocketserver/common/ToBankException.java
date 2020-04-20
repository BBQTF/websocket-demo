package com.bbq.websocketserver.common;

import lombok.Getter;

public class ToBankException extends RuntimeException {

    @Getter
    private ResultStatusEnum resultStatusEnum;
    @Getter
    private int resultCode;
    @Getter
    private String resultMsg;


    public ToBankException(ResultStatusEnum errorCode) {
        super(errorCode.getResultMsg());
        this.resultCode = errorCode.getResultCode();
        this.resultMsg = errorCode.getResultMsg();
        this.resultStatusEnum = errorCode;
    }

    public ToBankException(int resultCode, String resultMsg) {
        super(resultMsg);
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }
}
