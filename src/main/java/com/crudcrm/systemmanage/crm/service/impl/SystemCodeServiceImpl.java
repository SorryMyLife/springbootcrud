package com.crudcrm.systemmanage.crm.service.impl;

import com.crudcrm.systemmanage.crm.entity.SystemCode;
import com.crudcrm.systemmanage.crm.mapper.SystemCodeMapper;
import com.crudcrm.systemmanage.crm.service.ISystemCodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author test
 * @since 2021-01-12
 */
@Service
public class SystemCodeServiceImpl extends ServiceImpl<SystemCodeMapper, SystemCode> implements ISystemCodeService {

    @Override
    public void truncate() {
        this.getBaseMapper().truncate();
    }
}
