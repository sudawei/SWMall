package com.swmall.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * 高复用服务响应对象
 * @author suwei
 * @date 2017/10/16/016.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServerResponse<T> implements Serializable {

    private int status;
    private String msg;
    private T data;

    private ServerResponse(int status){
        this.status = status;
    }

    private ServerResponse(int status, String msg){
        this.status = status;
        this.msg = msg;
    }

    private ServerResponse(int status, String msg, T data){
        this.status = status;
        this.msg = msg;
        this.data =data;
    }

    private ServerResponse(int status, T data){
        this.status = status;
        this.data =data;
    }

    @JsonIgnore
    public boolean isSuccess(){
        return this.status == ResponseStatusCode.SUCCESS.getCode();
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public static <T> ServerResponse<T> createBySuccess(){
        return new ServerResponse<T>(ResponseStatusCode.SUCCESS.getCode());
    }

    public static <T> ServerResponse<T> createBySuccessMsg(String msg){
        return new ServerResponse<T>(ResponseStatusCode.SUCCESS.getCode(),msg);
    }

    public static <T> ServerResponse<T> createBySuccess(T data){
        return new ServerResponse<T>(ResponseStatusCode.SUCCESS.getCode(),data);
    }

    public static <T> ServerResponse<T> createBySuccess(String msg,T data){
        return new ServerResponse<T>(ResponseStatusCode.SUCCESS.getCode(),msg,data);
    }

    public static <T> ServerResponse<T> createByError(){
        return new ServerResponse<T>(ResponseStatusCode.ERROR.getCode(),ResponseStatusCode.ERROR.getDesc());
    }

    public static <T> ServerResponse<T> createByErrorMsg(String errorMsg){
        return new ServerResponse<T>(ResponseStatusCode.ERROR.getCode(),errorMsg);
    }

    public static <T> ServerResponse<T> createByErrorCodeMsg(int errorCode,String errorMsg){
        return new ServerResponse<T>(errorCode,errorMsg);
    }

}
