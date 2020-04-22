package com.bbq.websocketserver.common;

import lombok.Getter;

/**
 *
 * @author liutf
 * @date 2020-04-22
 */
public enum RedisKeyPrefixEnum {
    /**
     * 有序set的组装key值
     */
    SORTED_SET("zs"),

    /**
     * list的组装key值
     */
    LIST("l");

    @Getter
    private String keyName;

    RedisKeyPrefixEnum(String keyName) {
        this.keyName = keyName;
    }
}
