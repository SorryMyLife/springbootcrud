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
@TableName("crm_system_dept")
public class SystemDept implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公司id，唯一的
     */
    @TableId
    private String deptid;

    /**
     * 公司名称
     */
    @TableField("deptName")
    private String deptname;

    /**
     * 编辑公司的时间
     */
    @TableField("edittime")
    private LocalDateTime edittime;

    /**
     * 编辑公司的用户
     */
    @TableField("edituser")
    private String edituser;

    /**
     * 添加公司的时间
     */
    @TableField("addtime")
    private LocalDateTime addtime;

    /**
     * 添加公司的用户
     */
    @TableField("adduser")
    private String adduser;





}
