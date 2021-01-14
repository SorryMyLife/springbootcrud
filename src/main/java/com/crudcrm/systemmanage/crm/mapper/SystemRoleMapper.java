package com.crudcrm.systemmanage.crm.mapper;

import com.crudcrm.systemmanage.crm.entity.SystemRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface SystemRoleMapper extends BaseMapper<SystemRole> {

    @Select("select * from crm_system_role where roleid = ${roleid}")
    SystemRole selectByIdVo(@Param("roleid") String roleid);

    @Select("select * from crm_system_role where userrealname like \"%roleName%\" and roleid like \"%roleid%\" and roleid > ${manageServiceOneUidRoleid} limit ${starts},${limit};")
    List<SystemRole> selectByLimitSearch(@Param("limit") Integer limit, @Param("starts") Integer starts, @Param("roleName") String roleName, @Param("roleid") String roleid, @Param("manageServiceOneUidRoleid") Integer manageServiceOneUidRoleid);

    @Select("select * from crm_system_role limit ${starts},${limit};")
    List<SystemRole> selectByLimit(@Param("limit") Integer limit, @Param("starts") Integer starts);

    @Select("select count(roleid) from crm_system_role where userrealname like \"%${rolename}%\" and roleid like \"%${roleid}%\" and roleid > ${roleid1} ")
    Long getSearchSize(@Param("roleid") String roleid, @Param("rolename") String rolename, @Param("roleid1") Integer roleid1);

    @Select("select count(roleid) from crm_system_role where roleid > ${roleid1} ")
    Long getSize(@Param("roleid1") Integer roleid1);


}
