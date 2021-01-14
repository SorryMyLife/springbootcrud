package com.crudcrm.systemmanage.crm.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author test
 * @since 2021-01-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("crm_system_code")
public class SystemCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String cid;

    private String code;


}
