package com.crudcrm.systemmanage.crm.controller;


import com.crudcrm.systemmanage.crm.service.ISystemUserManageService;
import com.crudcrm.systemmanage.crm.service.ISystemUserService;
import com.crudcrm.systemmanage.crm.vo.SystemUserManageVo;
import com.crudcrm.systemmanage.crm.vo.SystemUserVo;
import com.crudcrm.systemmanage.utils.ResultMsg;
import com.crudcrm.systemmanage.utils.ResultStatus;
import com.crudcrm.systemmanage.utils.SystemMangeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author test
 * @since 2020-12-24
 */
@RestController
@RequestMapping("/crm/system-user-manage")
public class SystemUserManageController {

    @Autowired
    ISystemUserManageService systemUserManageService;

    @Autowired
    ISystemUserService systemUserService;

    @GetMapping("/getusers")
    public Object getUsers(){
        List<SystemUserManageVo> systemUserManageVos = systemUserManageService.selectListVO();
        return ResultMsg.add(systemUserManageVos.size() > 0 ?ResultStatus.success:ResultStatus.warning).add("size",systemUserManageVos.size()).add("total",systemUserManageService.getTotal()).add("data",systemUserManageVos);
    }

    @GetMapping("/getuserlist")
    public Object getUsers(@RequestParam("limit") Integer limit , @RequestParam("starts") Integer starts,HttpServletRequest request, HttpServletResponse response){
        new SystemMangeUtils().RefreshCookie(request,response);
        List<SystemUserManageVo> systemUserManageVos = systemUserManageService.selectListVOLimit(limit, starts,request);
        if(systemUserManageVos != null){
            return ResultMsg.add(systemUserManageVos.size() > 0 ?ResultStatus.success:ResultStatus.error).add("size",systemUserManageVos.size()).add("total",systemUserManageService.getTotal()).add("data",systemUserManageVos);
        }
        return  ResultMsg.add(ResultStatus.error);
        }

    @GetMapping("/searchuserlist")
    public Object getUsersTest(HttpServletRequest httpServletRequest, HttpServletResponse response){
        new SystemMangeUtils().RefreshCookie(httpServletRequest,response);
        List<SystemUserManageVo> systemUserManageVos =  systemUserManageService.selectListVo(httpServletRequest);
        if(systemUserManageVos != null){
            return  ResultMsg.add(ResultStatus.success).add("total",systemUserManageService.getSearchTotal(httpServletRequest)).add("size",systemUserManageVos.size()).add("data",systemUserManageVos);
        }
        return  ResultMsg.add(ResultStatus.error).add("data","null");
    }


    @PostMapping(value = "/getuser",produces = "application/json;charset=utf8")
    public Object getUser(@RequestBody SystemUserVo systemUserVo){
        return ResultMsg.add(ResultStatus.warning).add("data",systemUserVo);
    }

    @PostMapping(value = "/changeuser",produces = "application/json;charset=utf8")
    public Object changeUser(@RequestBody SystemUserVo systemUserVo , HttpServletRequest request, HttpServletResponse response){
        new SystemMangeUtils().RefreshCookie(request,response);
        Boolean isOk =  systemUserService.updateUserVo(systemUserVo,request);
        return ResultMsg.add(isOk ? ResultStatus.success : ResultStatus.warning);
    }

    @PostMapping(value = "/deluser",produces = "application/json;charset=utf8")
    public Object delUser(@RequestBody SystemUserVo systemUserVo){
        Boolean isOk =  systemUserService.delUserVo(systemUserVo);
        return ResultMsg.add(isOk ? ResultStatus.success : ResultStatus.warning);
    }

    @PostMapping(value = "/deluser")
    public Object delUser(@RequestParam("userid") String userid){
        Boolean isOk =  systemUserService.delUserVo(userid);
        return ResultMsg.add(isOk ? ResultStatus.success : ResultStatus.warning);
    }

    @PostMapping(value = "/delusers")
    public Object delUsers(@RequestBody String[] userids){
        Boolean isOk =  systemUserService.delUserVo(userids);
        return ResultMsg.add(isOk ? ResultStatus.success : ResultStatus.warning);
    }

    @PostMapping(value = "/changeuserpwd")
    public Object changeUserPwd(@RequestBody Map<String , Object> map , HttpServletRequest request, HttpServletResponse response){
        new SystemMangeUtils().RefreshCookie(request,response);
        String userid = (String) map.get("userid");
        String passwd = (String) map.get("passwd");
        String passwd2 = (String) map.get("passwd2");
        if((userid != null && !userid.isEmpty()) && (passwd != null && !passwd.isEmpty())&& (passwd2 != null && !passwd2.isEmpty())){
            if(passwd.equals(passwd2)){
                Boolean isOk =  systemUserService.delUserPwdVo(userid,passwd , request);
                return ResultMsg.add(isOk ? ResultStatus.success : ResultStatus.warning);
            }
            return ResultMsg.add( ResultStatus.errorLogin);
        }
        return ResultMsg.add(ResultStatus.error);
    }

    @PostMapping(value = "/deluserpwd")
    public Object delUserPwd(@RequestBody Map<String , Object> map){
        String userid = (String) map.get("userid");
        if(userid != null && !userid.isEmpty()){
            Boolean isOk =  systemUserService.delUserPwdVo(userid);
            return ResultMsg.add(isOk ? ResultStatus.success : ResultStatus.warning);
        }
        return ResultMsg.add(ResultStatus.error);
    }

    @PostMapping(value = "/changeonline")
    public Object changeUserOnline(@RequestBody Map<String , Object> map,HttpServletRequest request, HttpServletResponse response){
        new SystemMangeUtils().RefreshCookie(request,response);
        String userid = (String) map.get("userid");
        Integer online = (Integer) map.get("online");
        if((userid != null && !userid.isEmpty()) && (online != null)){
            Boolean isOk =  systemUserService.changeUserSwitchVo(2,userid,online,request);
            return ResultMsg.add(isOk ? ResultStatus.success : ResultStatus.warning);
        }
        return ResultMsg.add(ResultStatus.error);
    }

    @PostMapping(value = "/changelock")
    public Object changeUserLock(@RequestBody Map<String,Object> map,HttpServletRequest request, HttpServletResponse response){
        new SystemMangeUtils().RefreshCookie(request,response);
        String userid = (String) map.get("userid");
        Integer lock = (Integer) map.get("lock");
        if((userid != null && !userid.isEmpty()) && (lock != null)){
            Boolean isOk =  systemUserService.changeUserSwitchVo(1,userid,lock,request);
            return ResultMsg.add(isOk ? ResultStatus.success : ResultStatus.warning);
        }
        return ResultMsg.add(ResultStatus.error);
    }



}
