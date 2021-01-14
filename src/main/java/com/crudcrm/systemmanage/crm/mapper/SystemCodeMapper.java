package com.crudcrm.systemmanage.crm.mapper;

import com.crudcrm.systemmanage.crm.entity.SystemCode;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author test
 * @since 2021-01-12
 */
public interface SystemCodeMapper extends BaseMapper<SystemCode> {

    @Update("TRUNCATE crm_system_code")
    void truncate();
}
