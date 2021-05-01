package com.crudcrm.systemmanage.crm.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crudcrm.systemmanage.crm.entity.SystemRole;
import com.crudcrm.systemmanage.crm.entity.SystemUserManage;
import com.crudcrm.systemmanage.crm.service.ISystemRoleService;
import com.crudcrm.systemmanage.crm.service.ISystemUserManageService;
import com.crudcrm.systemmanage.utils.ResultMsg;
import com.crudcrm.systemmanage.utils.ResultStatus;
import com.crudcrm.systemmanage.utils.SystemMangeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
@RequestMapping("/crm/system-role")
public class SystemRoleController {

    @Autowired
    ISystemRoleService systemRoleService;

    @Autowired
    ISystemUserManageService systemUserManageService;


    @GetMapping("/searchrolelist")
    public Object searchRoleList(HttpServletRequest request, HttpServletResponse response){
        new SystemMangeUtils().RefreshCookie(request,response);
        List<SystemRole> roleList=systemRoleService.search(request);
        if(roleList != null){
            return ResultMsg.add(roleList.size() > 0? ResultStatus.success : ResultStatus.error).add("total",systemRoleService.getSearchTotal(request)).add("size",roleList.size()).add("roles",roleList);
        }
        return  ResultMsg.add(ResultStatus.error);
    }

    @PostMapping("/delrole")
    public Object delRole(@RequestBody Object roleid,HttpServletRequest request, HttpServletResponse response){
        new SystemMangeUtils().RefreshCookie(request,response);
        Boolean isok = systemRoleService.delrole(roleid,request);
        return  ResultMsg.add(isok ? ResultStatus.success: ResultStatus.nopermission);
    }

    @PostMapping("/changerole")
    public Object changeRole(@RequestBody Map<String,Object> map,HttpServletRequest request, HttpServletResponse response){
        new SystemMangeUtils().RefreshCookie(request,response);
        Boolean isok = systemRoleService.changerole(map,request);
        return  ResultMsg.add(isok ? ResultStatus.success: ResultStatus.nopermission);
    }


    @GetMapping("/getroles")
    public Object getRoles(HttpServletRequest request, HttpServletResponse response){
        new SystemMangeUtils().RefreshCookie(request,response);
        List<SystemRole> roleList = systemRoleService.list(request);
        if(roleList != null){
            return ResultMsg.add(roleList.size() > 0? ResultStatus.success : ResultStatus.error).add("total",systemRoleService.getSize(request)).add("size",roleList.size()).add("roles",roleList);
        }
        return  ResultMsg.add(ResultStatus.error);
    }

    @GetMapping("/getrole")
    public Object getRoles(@RequestParam("roleid") Integer roleid){
        SystemRole systemRole = systemRoleService.getBaseMapper().selectOne(new QueryWrapper<SystemRole>().eq("roleid",roleid));
        return ResultMsg.add((systemRole != null || systemRole.getUserrealname().isEmpty() == false)? ResultStatus.success : ResultStatus.error).add("role",systemRole);
    }


}
