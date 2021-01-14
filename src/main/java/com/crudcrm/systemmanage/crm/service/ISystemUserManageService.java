package com.crudcrm.systemmanage.crm.service;

import com.crudcrm.systemmanage.crm.entity.SystemUserManage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.crudcrm.systemmanage.crm.vo.SystemUserManageVo;
import com.crudcrm.systemmanage.crm.vo.SystemUserVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author test
 * @since 2020-12-24
 */
public interface ISystemUserManageService extends IService<SystemUserManage> {

    List<SystemUserManageVo> selectListVO();
    List<SystemUserManageVo> selectListVOLimit(Integer limit, Integer starts);

    Long getTotal();

    Long getTotal(Integer roleid);


    SystemUserManageVo selectByIdVO(SystemUserManageVo systemUserManageVo);

    SystemUserManage selectByIdVO(String systemUserManageVoID);

    List<SystemUserManageVo> selectListVo(HttpServletRequest httpServletRequest);

    Long getSearchTotal(HttpServletRequest httpServletRequest);

    List<SystemUserManageVo> selectListVOLimit(Integer limit, Integer starts, HttpServletRequest request);

}
