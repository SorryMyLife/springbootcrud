package com.crudcrm.systemmanage.crm.mapper;

import com.crudcrm.systemmanage.crm.entity.SystemDept;
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
public interface SystemDeptMapper extends BaseMapper<SystemDept> {

    @Select("select count(deptid) from crm_system_dept")
    Long getSize();

    @Select("select * from crm_system_dept limit ${starts},${limit}; ")
    List<SystemDept> selectByLimit(@Param("limit") Integer limit, @Param("starts") Integer starts);

    @Select("select count(0) as 当前行数 from crm_system_dept where deptid <= ${deptid}")
    Long getLine(@Param("deptid") String deptid);

    @Select("select count(0) as 当前行数 from crm_system_dept where CONVERT(deptid,UNSIGNED) <= ${deptid}")
    Long getLineCONVERT(@Param("deptid") String deptid);

    @Select("select * from crm_system_dept deptid > ${deptid} limit ${limit}; ")
    List<SystemDept> getNextDeptList(@Param("limit") Integer limit, @Param("deptid") String deptid);

    @Select("select * from crm_system_dept where deptname like \"%${deptname}%\" and deptid like \"%${deptid}%\" limit ${starts},${limit}; ")
    List<SystemDept> selectByLimitSearch(@Param("limit") Integer limit, @Param("starts") Integer starts, @Param("deptname") String deptname, @Param("deptid") String deptid);

    @Select("select count(deptid) from crm_system_dept where deptname like \"%${deptname}%\" and deptid like \"%${deptid}%\" ")
    Long getSearchSize(@Param("deptid") String deptid, @Param("deptname") String deptname);
}
