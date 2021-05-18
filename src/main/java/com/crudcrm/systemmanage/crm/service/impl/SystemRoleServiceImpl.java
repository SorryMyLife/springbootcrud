package com.crudcrm.systemmanage.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crudcrm.systemmanage.crm.entity.SystemRole;
import com.crudcrm.systemmanage.crm.entity.SystemUserManage;
import com.crudcrm.systemmanage.crm.mapper.SystemRoleMapper;
import com.crudcrm.systemmanage.crm.service.ISystemRoleService;
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
public class SystemRoleServiceImpl extends ServiceImpl<SystemRoleMapper, SystemRole> implements ISystemRoleService {

    @Autowired
    SystemRoleMapper systemRoleMapper;

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
    public SystemRole selectByID(String roleid) {
        return roleid.isEmpty() ? null : systemRoleMapper.selectByIdVo(roleid);
    }

    @Override
    public List<SystemRole> list(HttpServletRequest request) {
        List<SystemRole> roleList =null;
        SystemUserManage manageServiceOneUid = InitSystemUserManage(request);
        if(manageServiceOneUid != null){
            String limit = request.getParameter("limit");
            Integer roleid = manageServiceOneUid.getRoleid();
            Integer starts = null;
            if(limit != null){
                starts = getStartsNum(Integer.valueOf(request.getParameter("page")),Integer.valueOf(limit));
            }
            if(roleid == 0){
                roleList =  this.list();
            }else{
                if(starts != null){
                    roleList = systemRoleMapper.selectByLimit(Integer.valueOf(limit),starts);
                }else{
                    roleList = this.list().stream().map((systemRole)->{
                        return  systemRole.getRoleid() > roleid ? systemRole : null;
                    }).collect(Collectors.toList()) ;
                    roleList.removeIf(Objects::isNull);
                }
            }

        }
        return roleList;
    }

    @Override
    public Boolean changerole(Map<String, Object> map, HttpServletRequest request) {
        SystemUserManage manageServiceOneUid = InitSystemUserManage(request);
        if(manageServiceOneUid != null){
            if(manageServiceOneUid.getRoleid() < UserLevel.root.getLevel()){
                Integer roleid = Integer.valueOf(map.get("roleid").toString() );
                String rolename = (String) map.get("rolename");
                if(roleid != null && rolename != null){
                    Boolean findok = false;
                    List<SystemRole> systemRoles = this.list();
                    for (SystemRole systemRole : systemRoles) {
                        if(systemRole.getRoleid().equals(roleid)){
                            if(!systemRole.getUserrealname().equals(rolename)){
                                systemRole.setUserrealname(rolename);
                                systemRole.setEdituser(manageServiceOneUid.getUserid());
                                systemRole.setEdittime(LocalDateTime.now());
                                return this.updateById(systemRole);
                            }
                            findok =  true;
                            break;
                        }
                    }
                    if(findok == false){
                        SystemRole systemRole = new SystemRole();
                        systemRole.setEdittime(LocalDateTime.now());
                        systemRole.setAddtime(LocalDateTime.now());
                        systemRole.setAdduser(manageServiceOneUid.getUserid());
                        systemRole.setEdituser(manageServiceOneUid.getUserid());
                        systemRole.setUserrealname(rolename);
                        systemRole.setRoleid(Integer.valueOf(roleid));
                        return this.getBaseMapper().insert(systemRole) != 0;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Boolean delrole(Object roleid, HttpServletRequest request) {
        SystemUserManage manageServiceOneUid = InitSystemUserManage(request);
        if(manageServiceOneUid != null){
            if(manageServiceOneUid.getRoleid() < UserLevel.root.getLevel()){
                if(roleid != null ){
                    return systemRoleMapper.delete(new QueryWrapper<SystemRole>().eq("roleid", roleid)) != 0;
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
    public List<SystemRole>  search(HttpServletRequest request) {
        SystemUserManage manageServiceOneUid = InitSystemUserManage(request);
        if(manageServiceOneUid != null){
            String roleName = request.getParameter("rolename");
            Integer limit = Integer.valueOf(request.getParameter("limit"));
            Integer starts = getStartsNum(Integer.valueOf(request.getParameter("page")),limit);
            String roleid = request.getParameter("roleid");
            return systemRoleMapper.selectByLimitSearch(limit,starts,roleName,roleid,manageServiceOneUid.getRoleid());
        }
        return  null;
    }

    @Override
    public Long getSearchTotal(HttpServletRequest httpServletRequest) {
        SystemUserManage systemUserManageOne = InitSystemUserManage(httpServletRequest);
        if(systemUserManageOne != null) {
            String roleid = httpServletRequest.getParameter("roleid");
            String rolename = httpServletRequest.getParameter("rolename");
            return  systemRoleMapper.getSearchSize(roleid,rolename,systemUserManageOne.getRoleid());
        }
        return null;
    }

    @Override
    public Long getSize(HttpServletRequest httpServletRequest) {
        SystemUserManage systemUserManageOne = InitSystemUserManage(httpServletRequest);
        if(systemUserManageOne != null) {
            return systemRoleMapper.getSize(systemUserManageOne.getRoleid());
        }
        return null;
    }
}
