package com.ld.elasticsearch.enums;

import lombok.Getter;

@Getter
public enum  ResultEnum {
    //ResponseLayUICode
    LAYUI_SUCCESS(0,"成功"),
    LAYUI_FAIL(1,"失败"),
    NAME_PWD_ERROR(1,"用户名或密码错误"),
    NAME_PWD_FORMAT_ERROR(2,"用户名或密码格式错误"),
    NAME_EXISTS_ERROR(3,"用户名重复"),
    ORDER_ERROR(6,"订单有误"),


    //function 权限相关错误
    FUNCTION_EXISTS_ERROR(1,"当前节点下名称重复"),
    FUNCTION_ERROR(1,"权限节点操作异常！")




    ;

    private Integer code;
    private String message;

    ResultEnum(Integer code,String message){
        this.code = code;
        this.message = message;
    }
}
