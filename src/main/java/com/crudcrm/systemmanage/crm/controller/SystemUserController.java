package com.crudcrm.systemmanage.crm.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.crudcrm.systemmanage.crm.entity.SystemCode;
import com.crudcrm.systemmanage.crm.entity.SystemUser;
import com.crudcrm.systemmanage.crm.entity.SystemUserManage;
import com.crudcrm.systemmanage.crm.service.ISystemCodeService;
import com.crudcrm.systemmanage.crm.service.ISystemUserManageService;
import com.crudcrm.systemmanage.crm.service.ISystemUserService;
import com.crudcrm.systemmanage.utils.ResultMsg;
import com.crudcrm.systemmanage.utils.ResultStatus;
import com.crudcrm.systemmanage.utils.SystemMangeUtils;
import com.crudcrm.systemmanage.utils.UserLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author test
 * @since 2020-12-24
 */
@RestController
@RequestMapping("/crm/system-user")
public class SystemUserController {

    @Autowired
    ISystemUserService systemUserService;

    @Autowired
    ISystemUserManageService systemUserManageService;

    @Autowired
    ISystemCodeService systemCodeService;

    @PostMapping(value = "/userlogin",produces = "application/json;charset=utf8")
    public Object userLogin(@RequestBody Map<String,Object> map , HttpServletResponse response){
        String username = (String) map.get("username");
        String password = (String) map.get("password");
        String code = (String) map.get("code");
        String randomStr = (String) map.get("randomStr");
        if(code != null && username != null && password != null && randomStr != null){
            SystemCode codeServiceOne = systemCodeService.getOne(new QueryWrapper<SystemCode>().eq("code", code.toLowerCase()).and((w)->{
                w.eq("cid",randomStr);
            }));
            if(codeServiceOne != null){
                SystemUser userServiceOne = systemUserService.getOne(new QueryWrapper<SystemUser>().eq("userid",username).and(w->{
                    w.eq("userpassword",password);
                }));
                if(userServiceOne != null){
                    SystemUserManage userManageServiceOne = systemUserManageService.getOne(new QueryWrapper<SystemUserManage>().eq("userid", userServiceOne.getUserid()));
                    if(userManageServiceOne != null){
                        if(userManageServiceOne.getRoleid() < UserLevel.user.getLevel()){
                            new SystemMangeUtils().RefreshCookie(userServiceOne,response);
                            if(systemCodeService.count() > 20){
                                systemCodeService.truncate();
                            }
                            return ResultMsg.add(ResultStatus.success);
                        }
                    }
                    return  ResultMsg.add(ResultStatus.noLogin);
                }else{
                    return ResultMsg.add(ResultStatus.errorLogin);
                }
            }else{
                return ResultMsg.add(ResultStatus.errorCode);
            }
        }
        return ResultMsg.add(ResultStatus.error);

    }

    @PostMapping(value = "/userloginOut",produces = "application/json;charset=utf8")
    public Object userLoginOut( HttpServletResponse response , HttpServletRequest request){
        String cookiefileds[] = {
                "uid","unm"
        };
        for (String cookiefiled : cookiefileds) {
            Cookie cookie = new Cookie(cookiefiled, "");
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return ResultMsg.add(ResultStatus.success);

    }

    @GetMapping(value = "/getcode")
    public Object getCode( HttpServletResponse response , HttpServletRequest request){
        if(request.getParameterMap() != null){
            String cid = request.getParameter("cid");
            if(cid != null){
                int len = 6 , h = 40;
                String[] code = systemUserService.getCode(len, h);
                if(systemCodeService.count() > 100){
                    systemCodeService.truncate();
                }
                SystemCode codeServiceOne = systemCodeService.getOne(new QueryWrapper<SystemCode>().eq("cid", cid));
                if(codeServiceOne != null){
                    codeServiceOne.setCid(cid);
                    codeServiceOne.setCode(code[0].toLowerCase());
                    systemCodeService.updateById(codeServiceOne);
                }else{
                    SystemCode systemCode = new SystemCode();
                    systemCode.setCid(cid);
                    systemCode.setCode(code[0].toLowerCase());
                    systemCodeService.save(systemCode);
                }
                return ResultMsg.add(ResultStatus.success).add("data",code[1]);
            }
        }
        return  ResultMsg.add(ResultStatus.error);
    }




}
