package com.crudcrm.systemmanage.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crudcrm.systemmanage.crm.entity.SystemDept;
import com.crudcrm.systemmanage.crm.entity.SystemRole;
import com.crudcrm.systemmanage.crm.entity.SystemUser;
import com.crudcrm.systemmanage.crm.entity.SystemUserManage;
import com.crudcrm.systemmanage.crm.mapper.SystemUserManageMapper;
import com.crudcrm.systemmanage.crm.mapper.SystemUserMapper;
import com.crudcrm.systemmanage.crm.service.ISystemDeptService;
import com.crudcrm.systemmanage.crm.service.ISystemRoleService;
import com.crudcrm.systemmanage.crm.service.ISystemUserManageService;
import com.crudcrm.systemmanage.crm.service.ISystemUserService;
import com.crudcrm.systemmanage.crm.vo.SystemUserManageVo;
import com.crudcrm.systemmanage.utils.SystemMangeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
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
public class SystemUserManageServiceImpl extends ServiceImpl<SystemUserManageMapper, SystemUserManage> implements ISystemUserManageService {

    @Autowired
    ISystemUserService systemUserService;
    @Autowired
    ISystemDeptService systemDeptService;
    @Autowired
    ISystemRoleService systemRoleService;

    @Autowired
    SystemUserManageMapper systemUserManageMapper;

    private Long total;
    private int seek;

    public SystemUserManage InitSystemUserManage(HttpServletRequest request){
        SystemUserManage manageServiceOneUid = null;
        if(request != null){
            String uid = new SystemMangeUtils().getRequestCookieUid(request);
            if(uid != null) {
                manageServiceOneUid = this.getOne(new QueryWrapper<SystemUserManage>().eq("userid", uid));
            }
        }
        return  manageServiceOneUid;
    }


    @Override
    public List<SystemUserManageVo> selectListVO() {
        List<SystemUserManage> systemUserManages = this.list();
        List<SystemUserManageVo> systemUserManageVoList = systemUserManages.stream().map((userManage) -> {
            SystemUserManageVo systemUserManageVo = new SystemUserManageVo();
            SystemUser systemUser = systemUserService.getBaseMapper().selectOne(new QueryWrapper<SystemUser>().eq("userid", userManage.getUserid()));
            SystemDept systemDept = systemDeptService.getBaseMapper().selectOne(new QueryWrapper<SystemDept>().eq("deptid", userManage.getDeptid()));
            SystemRole systemRole = systemRoleService.getBaseMapper().selectOne(new QueryWrapper<SystemRole>().eq("roleid", userManage.getRoleid()));
            BeanUtils.copyProperties(systemUser,systemUserManageVo);
            BeanUtils.copyProperties(systemDept,systemUserManageVo);
            BeanUtils.copyProperties(systemRole,systemUserManageVo);
            systemUserManageVo.setUsersex(systemUser.getUsersex()==0?"女":"男");
            return systemUserManageVo;
        }).collect(Collectors.toList());
        return systemUserManageVoList;
    }



    @Override
    public List<SystemUserManageVo> selectListVOLimit(Integer limit, Integer starts) {

        /**
         * 现在的问题，
         * 我们需要从传过来的starts跟limit计算出数据库里的数据
         * 从而进行分页传递
         * 假如数据库里一共有30条数据
         * 那么我们就需要返回limit这么多数据，如果没有这么多 ，则返回剩余内容
         * 但是我们需要从starts这个关键分页进行计算
         * */
        int seek  = starts == 0 ? 0 : starts - 1;
        starts = limit*seek;
        List<SystemUserManage> systemUserManages = this.getBaseMapper().selectByLimit(limit, starts);
        List<SystemUserManageVo> systemUserManageVos = systemUserManages.stream().map((systemUserManage) -> {
           return cpSystemUserManageVo(systemUserManage);
        }).collect(Collectors.toList());
        return systemUserManageVos;
    }

    @Override
    public Long getTotal() {
        return  total != null && total > 0 ? total :systemUserManageMapper.getSize();
    }

    @Override
    public Long getTotal(Integer roleid) {
        return systemUserManageMapper.getRoleidSize(roleid);
    }

    @Override
    public SystemUserManageVo selectByIdVO(SystemUserManageVo systemUserManageVo) {
        SystemUserManageVo userManageVo = new SystemUserManageVo();
        String userid = systemUserManageVo.getUserid();
        String deptid = systemUserManageVo.getDeptid();
        Integer roleid = systemUserManageVo.getRoleid();
        if(userid != null){
            SystemUser systemUser = systemUserService.getBaseMapper().selectOne(new QueryWrapper<SystemUser>().eq("userid", userid));
            if(deptid != null){
                SystemDept systemDept = systemDeptService.getBaseMapper().selectOne(new QueryWrapper<SystemDept>().eq("deptid", deptid));
                if(roleid != null){
                    SystemRole systemRole = systemRoleService.getBaseMapper().selectOne(new QueryWrapper<SystemRole>().eq("roleid", roleid));
                    BeanUtils.copyProperties(systemUser,systemUserManageVo);
                    BeanUtils.copyProperties(systemDept,systemUserManageVo);
                    BeanUtils.copyProperties(systemRole,systemUserManageVo);
                    userManageVo.setUsersex(systemUser.getUsersex()==0?"女":"男");
                    return userManageVo;
                }
            }
        }
        return  null;

    }

    @Override
    public SystemUserManage selectByIdVO(String systemUserManageVoID) {
        return systemUserManageVoID.isEmpty() ? null : systemUserManageMapper.selectByVoId(systemUserManageVoID);
    }

    @Override
    public List<SystemUserManageVo> selectListVo(HttpServletRequest httpServletRequest) {

        SystemUserManage systemUserManage = InitSystemUserManage(httpServletRequest);
        if(systemUserManage != null){
            Integer limit = Integer.valueOf(httpServletRequest.getParameter("limit"));
            String username = httpServletRequest.getParameter("username");
            Integer starts = Integer.valueOf(httpServletRequest.getParameter("starts"));
            String islock = httpServletRequest.getParameter("islock");
            String usermobile = httpServletRequest.getParameter("usermobile");
            int seek  = starts == 0 ? 0 : starts - 1;
            starts = limit*seek;
            int lock = islock.equals("Y")?0:1;
            List<SystemUserManageVo> systemUserManageVos =  systemUserManageMapper.selectListByVo(limit,starts,username,usermobile,lock);
            if (systemUserManage.getUserid().equals("root")) {
                return systemUserManageVos;
            }

            return systemUserManageMapper.selectListByVoRoleid(limit,starts,username,usermobile,lock,systemUserManage.getRoleid());

        }
        return  null;
    }

    @Override
    public Long getSearchTotal(HttpServletRequest httpServletRequest) {
        SystemUserManage systemUserManageOne = InitSystemUserManage(httpServletRequest);
        if(systemUserManageOne != null) {
            String username = httpServletRequest.getParameter("username");
            String islock = httpServletRequest.getParameter("islock");
            String usermobile = httpServletRequest.getParameter("usermobile");
            int lock = islock.equals("Y")?0:1;
            if (systemUserManageOne.getUserid().equals("root")) {
                return systemUserManageMapper.getSearchSize(lock,usermobile,username);
            }
            return systemUserManageMapper.getSearchSizeOnRoleid(lock,usermobile,username,systemUserManageOne.getRoleid());

        }

        return  null;
    }

    protected  SystemUserManageVo cpSystemUserManageVo(SystemUserManage systemUserManage){
        SystemUserManageVo userManageVo = new SystemUserManageVo();
        SystemUser systemUser = systemUserService.getBaseMapper().selectOne(new QueryWrapper<SystemUser>().eq("userid", systemUserManage.getUserid()));
        SystemDept systemDept = systemDeptService.getBaseMapper().selectOne(new QueryWrapper<SystemDept>().eq("deptid", systemUserManage.getDeptid()));
        SystemRole systemRole = systemRoleService.getBaseMapper().selectOne(new QueryWrapper<SystemRole>().eq("roleid", systemUserManage.getRoleid()));
        BeanUtils.copyProperties(systemUser,userManageVo);
        BeanUtils.copyProperties(systemDept,userManageVo);
        BeanUtils.copyProperties(systemRole,userManageVo);
        BeanUtils.copyProperties(systemUserManage, userManageVo);
        userManageVo.setUsersex(systemUser.getUsersex()==0?"女":"男");
        return userManageVo;
    }

    @Override
    public List<SystemUserManageVo> selectListVOLimit(Integer limit, Integer starts, HttpServletRequest request) {
        SystemUserManage systemUserManageOne = InitSystemUserManage(request);
        if(systemUserManageOne != null){
            if(systemUserManageOne.getUserid().equals("root")){
                return selectListVOLimit(limit, starts);
            }
            int seek  = starts == 0 ? 0 : starts - 1;
            starts = limit*seek;
            Integer roleid = systemUserManageOne.getRoleid();
            total = getTotal(roleid);
            List<SystemUserManageVo> systemUserManageVos = systemUserManageMapper.selectRoleidByLimit(starts, limit, roleid).stream().map((systemUserManage) -> {
                return cpSystemUserManageVo(systemUserManage);
            }).collect(Collectors.toList());
            return  systemUserManageVos;
        }
        return null;
    }

}
