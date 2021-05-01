package com.crudcrm.systemmanage.utils;

public enum  ResultStatus {
    success(0,"请求成功"),
    error(1,"请求失败"),warning(2,"请求参数不完整或存在非法字符"),noLogin(3,"此用户没权限登录此系统!"),
    errorLogin(4,"用户账号或者密码错误,请重试"),errorCode(5,"验证码有误,请重输!")
    ,longLogin(6,"今日错误登录次数已达到上限!")
    ,nopermission(7,"此用户没权限")
    ;

    private Integer status;
private String msg;

    ResultStatus(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
