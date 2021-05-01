package com.crudcrm.systemmanage.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crudcrm.systemmanage.crm.entity.SystemDept;
import com.crudcrm.systemmanage.crm.entity.SystemRole;
import com.crudcrm.systemmanage.crm.entity.SystemUserManage;
import com.crudcrm.systemmanage.crm.mapper.SystemDeptMapper;
import com.crudcrm.systemmanage.crm.service.ISystemDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crudcrm.systemmanage.crm.service.ISystemUserManageService;
import com.crudcrm.systemmanage.utils.SystemMangeUtils;
import com.crudcrm.systemmanage.utils.UserLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author test
 * @since 2020-12-24
 */
@Service
public class SystemDeptServiceImpl extends ServiceImpl<SystemDeptMapper, SystemDept> implements ISystemDeptService {

    @Autowired
    SystemDeptMapper systemDeptMapper;

    @Autowired
    ISystemUserManageService systemUserManageService;

    public SystemUserManage InitSystemUserManage(HttpServletRequest request){
        SystemUserManage manageServiceOneUid = null;
        if(request != null){
            String uid = new SystemMangeUtils().getRequestCookieUid(request);
            if(uid != null) {
                manageServiceOneUid = systemUserManageService.getOne(new QueryWrapper<SystemUserManage>().eq("userid", uid));
            }
        }
        return  manageServiceOneUid;
    }


    @Override
    public SystemDept selectById(String deptid)
    {
        return systemDeptMapper.selectOne(new QueryWrapper<SystemDept>().eq("deptid",deptid));
    }

    @Override
    public List<SystemDept> selectByLimitList(Integer limit, Integer starts) {
        int seek  = starts == 0 ? 0 : starts - 1;
        starts = limit*seek;
        return systemDeptMapper.selectByLimit(limit, starts);
    }

    @Override
    public Long getTotal() {
        return systemDeptMapper.getSize();
    }

    @Override
    public Long getLine(String deptid) {
        return systemDeptMapper.getLine(deptid);
    }

    @Override
    public Long getLineCONVERT(String deptid) {
        return systemDeptMapper.getLineCONVERT(deptid);
    }

    @Override
    public List<SystemDept> list(HttpServletRequest request) {
        SystemUserManage manageServiceOneUid = InitSystemUserManage(request);
        if(manageServiceOneUid != null){
            String limit = request.getParameter("limit");
            if(limit != null){
                Integer starts = getStartsNum(Integer.valueOf(request.getParameter("page")),Integer.valueOf(limit));
                return  systemDeptMapper.selectByLimit(Integer.valueOf(limit),starts);
            }
            return this.list();

        }
        return  null;
    }

    @Override
    public Boolean changedept(Map<String, Object> map, HttpServletRequest request) {
        SystemUserManage manageServiceOneUid = InitSystemUserManage(request);
        if(manageServiceOneUid != null){
            if(manageServiceOneUid.getRoleid() < UserLevel.user.getLevel()){
                String deptid = map.get("deptid").toString();
                String deptname = (String) map.get("deptname");
                if(deptid != null && deptname != null){
                    Boolean findok = false;
                    List<SystemDept> systemRoles = this.list();
                    for (SystemDept systemDept : systemRoles) {
                        if(systemDept.getDeptid().equals(deptid)){
                            if(!systemDept.getDeptname().equals(deptname)){
                                systemDept.setDeptname(deptname);
                                systemDept.setEdituser(manageServiceOneUid.getUserid());
                                systemDept.setEdittime(LocalDateTime.now());
                                return this.updateById(systemDept);
                            }
                            findok =  true;
                            break;
                        }
                    }
                    if(findok == false){
                        SystemDept systemDept = new SystemDept();
                        systemDept.setEdittime(LocalDateTime.now());
                        systemDept.setAddtime(LocalDateTime.now());
                        systemDept.setAdduser(manageServiceOneUid.getUserid());
                        systemDept.setEdituser(manageServiceOneUid.getUserid());
                        systemDept.setDeptname(deptname);
                        systemDept.setDeptid(deptid);
                        return this.getBaseMapper().insert(systemDept) != 0;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Boolean deldept(Object deptid, HttpServletRequest request) {
        SystemUserManage manageServiceOneUid = InitSystemUserManage(request);
        if(manageServiceOneUid != null){
            if(manageServiceOneUid.getRoleid() < UserLevel.user.getLevel()){
                if(deptid != null ){
                    return systemDeptMapper.delete(new QueryWrapper<SystemDept>().eq("deptid", deptid)) != 0;
                }
            }
        }
        return false;
    }

    private  int getStartsNum(int starts,int limit){
        int seek  = starts == 0 ? 0 : starts - 1;
        starts = limit*seek;
        return  starts;
    }

    @Override
    public List<SystemDept> search(HttpServletRequest request) {
        SystemUserManage manageServiceOneUid = InitSystemUserManage(request);
        if(manageServiceOneUid != null){
            Integer limit = Integer.valueOf(request.getParameter("limit"));
            String deptname = request.getParameter("deptname");
            Integer starts = getStartsNum(Integer.valueOf(request.getParameter("page")),limit);
            String deptid = request.getParameter("deptid");

            return  systemDeptMapper.selectByLimitSearch(limit,starts,deptname,deptid);
        }
        return  null;
    }

    @Override
    public Long getSearchTotal(HttpServletRequest httpServletRequest) {
        SystemUserManage systemUserManageOne = InitSystemUserManage(httpServletRequest);
        if(systemUserManageOne != null) {
            String deptid = httpServletRequest.getParameter("deptid");
            String deptname = httpServletRequest.getParameter("deptname");
            return systemDeptMapper.getSearchSize(deptid,deptname);
        }
        return  null;
    }
}
