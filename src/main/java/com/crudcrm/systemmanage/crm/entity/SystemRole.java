package com.crudcrm.systemmanage.crm.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author test
 * @since 2020-12-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("crm_system_role")
public class SystemRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户等级，不能为空
     */
    @TableField("roleid")
    private Integer roleid;

    /**
     * 用户等级名称
     */
    @TableField("userRealName")
    private String userrealname;

    /**
     * 编辑用户等级的时间
     */
    @TableField("edittime")
    private LocalDateTime edittime;

    /**
     * 编辑用户等级的用户
     */
    @TableField("edituser")
    private String edituser;

    /**
     * 添加用户等级的时间
     */
    @TableField("addtime")
    private LocalDateTime addtime;

    /**
     * 添加用户等级的用户
     */
    @TableField("adduser")
    private String adduser;

}
