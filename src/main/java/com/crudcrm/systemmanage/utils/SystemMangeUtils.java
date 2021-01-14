package com.crudcrm.systemmanage.utils;

import com.crudcrm.systemmanage.crm.entity.SystemUser;
import com.crudcrm.systemmanage.crm.service.ISystemUserManageService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SystemMangeUtils {

    @Autowired
    ISystemUserManageService systemUserManageService;

    public int getRandom(int max , int min){
        return new Random().nextInt(max)%(max-min+1) + min;
    }

    public String getDATE(){
        return new SimpleDateFormat("YYYYMMddHHmmssSS").format(new Date());
    }

    public String getRandom2(int len) {
        Random r = new Random();
        StringBuilder rs = new StringBuilder();
        for (int i = 0; i < len; i++) {
            rs.append(r.nextInt(10));
        }
        return rs.toString();
    }

    public String getRequestCookieUid(HttpServletRequest request){
        String uid = null;
        if(request.getCookies() != null){
            for (Cookie cookie : request.getCookies()) {
                if(cookie.getName().equals("uid")) {
                    uid = cookie.getValue();
                }
            }
        }
        return  uid;
    }

    public void RefreshCookie(HttpServletRequest request , HttpServletResponse response){
        if(request.getCookies() != null){
            HashMap<String,Object> cookie_map = new HashMap<>();
            for (Cookie cookie : request.getCookies()) {
                if(cookie.getValue() != null && !cookie.getValue().isEmpty()){
                    cookie_map.put(cookie.getName(),cookie.getValue());
                }
            }
            addCookie(cookie_map,response);
        }
    }

    public void addCookie(Map<String,Object> cookie_map,HttpServletResponse response){
        if(cookie_map.size() > 0){
            cookie_map.forEach((k,v)->{
                Cookie cookie = new Cookie(k, (String) v);
                cookie.setMaxAge(60*10);
                cookie.setPath("/");
                response.addCookie(cookie);
            });
        }
    }

    public void RefreshCookie(SystemUser userServiceOne ,HttpServletResponse response){
        HashMap<String,Object> cookie_map = new HashMap<>();
        cookie_map.put("uid",userServiceOne.getUserid());
        cookie_map.put("unm",userServiceOne.getUsername());
        addCookie(cookie_map,response);
    }

    public String getRamdonString(int len) {
        StringBuffer sb = new StringBuffer();
        String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }
        return sb.toString();
    }

}
