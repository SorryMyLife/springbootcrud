package com.crudcrm.systemmanage.crm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crudcrm.systemmanage.crm.entity.SystemRole;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author test
 * @since 2020-12-24
 */
public interface ISystemRoleService extends IService<SystemRole> {

    SystemRole selectByID(String roleid);


    List<SystemRole> list(HttpServletRequest request);

    Boolean changerole(Map<String, Object> map, HttpServletRequest request);


    Boolean delrole(Object map, HttpServletRequest request);

    List<SystemRole> search(HttpServletRequest request);

    Long getSearchTotal(HttpServletRequest request);

    Long getSize(HttpServletRequest request);

}
