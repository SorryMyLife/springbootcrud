package com.crudcrm.systemmanage.crm.controller;


import com.crudcrm.systemmanage.crm.entity.SystemDept;
import com.crudcrm.systemmanage.crm.entity.SystemRole;
import com.crudcrm.systemmanage.crm.service.ISystemDeptService;
import com.crudcrm.systemmanage.utils.ResultMsg;
import com.crudcrm.systemmanage.utils.ResultStatus;
import com.crudcrm.systemmanage.utils.SystemMangeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/crm/system-dept")
public class SystemDeptController {

    @Autowired
    ISystemDeptService systemDeptService;


    @GetMapping("/searchdeptlist")
    public Object searchDeptList(HttpServletRequest request, HttpServletResponse response){
        new SystemMangeUtils().RefreshCookie(request,response);
        List<SystemDept> systemDeptList=systemDeptService.search(request);
        if(systemDeptList != null){
            return ResultMsg.add(systemDeptList.size() > 0? ResultStatus.success : ResultStatus.error).add("total",systemDeptService.getSearchTotal(request)).add("size",systemDeptList.size()).add("depts",systemDeptList);
        }
        return  ResultMsg.add(ResultStatus.error);
    }


    @PostMapping("/deldept")
    public Object delDept(@RequestBody Object deptid, HttpServletRequest request, HttpServletResponse response){
        new SystemMangeUtils().RefreshCookie(request,response);
        Boolean isok = systemDeptService.deldept(deptid,request);
        return  ResultMsg.add(isok ? ResultStatus.success: ResultStatus.nopermission);
    }

    @PostMapping("/changedept")
    public Object changeDept(@RequestBody Map<String,Object> map, HttpServletRequest request, HttpServletResponse response){
        new SystemMangeUtils().RefreshCookie(request,response);
        Boolean isok = systemDeptService.changedept(map,request);
        return  ResultMsg.add(isok ? ResultStatus.success: ResultStatus.nopermission);
    }

    @GetMapping("/getdepts")
    public Object getDepts(HttpServletRequest request, HttpServletResponse response){
        new SystemMangeUtils().RefreshCookie(request,response);
        List<SystemDept> systemDepts = systemDeptService.list(request);
        if(systemDepts != null){
            return ResultMsg.add(systemDepts.size() > 0 ? ResultStatus.success : ResultStatus.error).add("total",systemDeptService.getTotal()).add("size",systemDepts.size()).add("depts",systemDepts);
        }
        return  ResultMsg.add(ResultStatus.error);
    }

}
