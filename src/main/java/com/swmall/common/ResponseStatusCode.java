package com.swmall.common;

/**
 * 返回对象的状态码
 * @author suwei
 * @date 2017/10/16/016.
 */
public enum ResponseStatusCode {

    SUCCESS(0,"SUCCESS"),

    ERROR(1,"ERROR"),

    NEED_LOGIN(10,"NEED_LOGIN"),

    ILLEGAL_ARGUMENT(2,"ILLEGAL_ARGUMENT");

    private final int code;

    private final String desc;

    ResponseStatusCode(int code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
