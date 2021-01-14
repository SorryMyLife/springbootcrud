package com.crudcrm.systemmanage.utils;

import java.util.HashMap;

public class ResultMsg extends  HashMap<Object,Object> {

    public static ResultMsg add(ResultStatus resultStatus){
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.put("code",resultStatus.getStatus());
        resultMsg.put("msg",resultStatus.getMsg());
        return resultMsg;
    }

    public ResultMsg add(Object key, Object value) {
        super.put(key, value);
        return this;
    }

    public ResultMsg put(Object key, Object value){
        return add(key,value);
    }

}
