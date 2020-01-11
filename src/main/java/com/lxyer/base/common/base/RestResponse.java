package com.lxyer.base.common.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "返回结果")
public class RestResponse<T>  {
    @ApiModelProperty("状态码 0:成功")
    private int code;
    @ApiModelProperty("消息")
    private String message;
    @ApiModelProperty("返回数据")
    private T data;

    public RestResponse() {

    }

    public static RestResponse succuess() {
        RestResponse restResponse = new RestResponse();
        restResponse.setResultCode(ResultCode.SUCCESS);
        return restResponse;
    }

    public static<T> RestResponse succuess(T data) {
        RestResponse<T> restResponse = new RestResponse<T>();
        restResponse.setResultCode(ResultCode.SUCCESS);
        restResponse.setData(data);
        return restResponse;
    }
    public static<T> RestResponse<T> succuess(T data,String message) {
        RestResponse<T> restResponse = new RestResponse();
        restResponse.setResultCode(ResultCode.SUCCESS);
        restResponse.setMessage(message);
        restResponse.setData(data);
        return restResponse;
    }
    public static RestResponse fail() {
        RestResponse restResponse = new RestResponse();
        restResponse.setResultCode(ResultCode.FAIL);
        return restResponse;
    }


    public static RestResponse fail(ResultCode resultCode) {
        RestResponse restResponse = new RestResponse();
        restResponse.setResultCode(resultCode);
        return restResponse;
    }

    public static RestResponse fail(String message) {
        RestResponse restResponse = new RestResponse();
        restResponse.setCode(ResultCode.FAIL.code());
        restResponse.setMessage(message);
        return restResponse;
    }

    public static RestResponse fail(Integer code, String message) {
        RestResponse restResponse = new RestResponse();
        restResponse.setCode(code);
        restResponse.setMessage(message);
        return restResponse;
    }

    public static RestResponse fail(ResultCode resultCode, Object data) {
        RestResponse restResponse = new RestResponse();
        restResponse.setResultCode(resultCode);
        restResponse.setData(data);

        return restResponse;
    }

    private void setResultCode(ResultCode resultCode) {
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

