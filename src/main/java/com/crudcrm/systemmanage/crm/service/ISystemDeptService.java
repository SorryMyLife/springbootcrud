package com.crudcrm.systemmanage.crm.service;

import com.crudcrm.systemmanage.crm.entity.SystemDept;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

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
public interface ISystemDeptService extends IService<SystemDept> {

    SystemDept selectById(String deptid);

    List<SystemDept> selectByLimitList(Integer limit, Integer starts);

    Long getTotal();

    Long getLine(String deptid);

    Long getLineCONVERT(String deptid);


    List<SystemDept> list(HttpServletRequest request);

    Boolean changedept(Map<String, Object> map, HttpServletRequest request);

    Boolean deldept(Object deptid, HttpServletRequest request);

    List<SystemDept> search(HttpServletRequest request);

    Long getSearchTotal(HttpServletRequest request);


}
