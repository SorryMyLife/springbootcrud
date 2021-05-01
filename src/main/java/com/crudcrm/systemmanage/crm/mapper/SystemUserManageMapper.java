package com.crudcrm.systemmanage.crm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.crudcrm.systemmanage.crm.entity.SystemUserManage;
import com.crudcrm.systemmanage.crm.vo.SystemUserManageVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author test
 * @since 2020-12-24
 */
public interface SystemUserManageMapper extends BaseMapper<SystemUserManage> {

    @Select("select * from crm_system_user_manage where CONVERT(userid,UNSIGNED) >${starts}   limit ${limit}; ")
    List<SystemUserManage> selectLimit(@Param("limit") Integer limit, @Param("starts") Integer starts);

    @Select("select COUNT(userid) from crm_system_user_manage")
    Long getSize();

    @Select("select * from crm_system_user_manage limit ${starts},${limit}; ")
    List<SystemUserManage> selectByLimit(@Param("limit") Integer limit, @Param("starts") Integer starts);

    @Select("select userid from crm_system_user_manage")
    List<String> selectIdList();

    @Select("select * from crm_system_user_manage where userid = \"${systemUserManageVoID}\"")
    SystemUserManage selectByVoId(@Param("systemUserManageVoID") String systemUserManageVoID);

    @Select("select * from crm_system_user uleft left join  crm_system_user_manage cm on uleft.userid = \"cm.userId\" left join crm_system_dept as dept on dept.deptid = cm.deptid  left join crm_system_role as role on role.roleid = cm.roleid where cm.islock = ${lock} and uleft.usermobile LIKE \"%${usermobile}%\" and uleft.username like \"%${username}%\" limit ${starts}, ${limit}")
    List<SystemUserManageVo> selectListByVo(@Param("limit") Integer limit, @Param("starts") Integer starts, @Param("username") String username, @Param("usermobile") String usermobile, @Param("lock") int lock);

    @Select("select count(uleft.userid)  from crm_system_user uleft left join  crm_system_user_manage cm on uleft.userid = cm.userId left join crm_system_dept as dept on dept.deptid = cm.deptid  left join crm_system_role as role on role.roleid = cm.roleid where cm.islock = ${lock} and uleft.usermobile LIKE \"%${usermobile}%\" and uleft.username like \"%${username}%\"")
    Long getSearchSize(@Param("lock") int lock, @Param("usermobile") String usermobile, @Param("username") String username);

    @Select("select * from crm_system_user_manage where roleid > ${roleid} limit ${starts},${limit}; ")
    List<SystemUserManage> selectRoleidByLimit(@Param("starts") Integer starts, @Param("limit") Integer limit, @Param("roleid") Integer roleid);

    @Select("select COUNT(userid) from crm_system_user_manage where roleid > ${roleid}")
    Long getRoleidSize(@Param("roleid") Integer roleid);

    @Select("select count(uleft.userid)  from crm_system_user uleft left join  crm_system_user_manage cm on uleft.userid = cm.userId left join crm_system_dept as dept on dept.deptid = cm.deptid  left join crm_system_role as role on role.roleid = cm.roleid where cm.islock = ${lock} and uleft.usermobile LIKE \"%${usermobile}%\" and uleft.username like \"%${username}%\" and cm.roleid > ${roleid}")
    Long getSearchSizeOnRoleid(@Param("lock") int lock, @Param("usermobile") String usermobile, @Param("username") String username, @Param("roleid") Integer roleid);

    @Select("select * from crm_system_user uleft left join  crm_system_user_manage cm on uleft.userid = cm.userId left join crm_system_dept as dept on dept.deptid = cm.deptid  left join crm_system_role as role on role.roleid = cm.roleid where cm.islock = ${lock} and uleft.usermobile LIKE \"%${usermobile}%\" and uleft.username like \"%${username}%\" and cm.roleid > ${roleid} limit ${starts}, ${limit}")
    List<SystemUserManageVo> selectListByVoRoleid(@Param("limit") Integer limit, @Param("starts") Integer starts, String username, @Param("usermobile") String usermobile, @Param("lock") int lock, @Param("roleid") Integer roleid);

}
