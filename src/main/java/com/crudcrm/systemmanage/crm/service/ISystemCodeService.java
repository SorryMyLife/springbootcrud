package com.crudcrm.systemmanage.crm.service;

import com.crudcrm.systemmanage.crm.entity.SystemCode;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author test
 * @since 2021-01-12
 */
public interface ISystemCodeService extends IService<SystemCode> {
    void truncate();

}
