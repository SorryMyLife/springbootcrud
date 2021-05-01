package com.crudcrm.systemmanage.crm.mapper;

import com.crudcrm.systemmanage.crm.entity.SystemUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author test
 * @since 2020-12-24
 */
public interface SystemUserMapper extends BaseMapper<SystemUser> {

    @Select("select * from crm_system_user where userId = \"${systemUserVoId}\"")
    SystemUser selectByIdVo(@Param("systemUserVoId") String systemUserVoId);

    @Update("update crm_system_user set userpassword = ${pwd} WHERE userid = \"${userid}\"")
    Boolean resetPwd(String userid, String pwd);

}
