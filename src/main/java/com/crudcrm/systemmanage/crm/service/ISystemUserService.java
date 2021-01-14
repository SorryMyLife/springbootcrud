package com.crudcrm.systemmanage.crm.service;

import com.crudcrm.systemmanage.crm.entity.SystemUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.crudcrm.systemmanage.crm.vo.SystemUserVo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author test
 * @since 2020-12-24
 */
public interface ISystemUserService extends IService<SystemUser> {

    Boolean updateUserVo(SystemUserVo systemUserVo);

    SystemUser selectByIdVo(String systemUserVoId);

    Boolean delUserVo(SystemUserVo systemUserVo);

    Boolean delUserVo(String userid);

    Boolean delUserPwdVo(String userid, String pwd);

    Boolean delUserVo(String[] userids);

    Boolean delUserPwdVo(String userid);

    Boolean updateUserVo(SystemUserVo systemUserVo, HttpServletRequest request);

    Boolean delUserPwdVo(String userid, String pwd, HttpServletRequest request);

    Boolean changeUserSwitchVo(int i, String userid, Integer lock, HttpServletRequest request);

    String[] getCode(int codelen , int  codeheight);

}
