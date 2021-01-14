package com.crudcrm.systemmanage.crm.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
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
@TableName("crm_system_user_manage")
public class SystemUserManage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id,不能为空
     */
    @TableId
    private String userid;

    /**
     * 添加用户的上级,如果为空则为系统
     */
    @TableField("addUser")
    private Integer adduser;

    /**
     * 添加用户的上级
     */
    @TableField("editUser")
    private Integer edituser;

    /**
     * 添加用户的时间
     */
    @TableField("addTime")
    private LocalDateTime addtime;

    /**
     * 修改用户的时间
     */
    @TableField("editTime")
    private LocalDateTime edittime;

    /**
     * 是否锁定,(Y/N,是/否)
     */
    @TableField("isLock")
    private Integer islock;

    /**
     * 用户所属公司
     */
    @TableField("deptId")
    private String deptid;

    /**
     * 用户等级(如果为空则为普通用户)
     */
    @TableField("roleId")
    private Integer roleid;
    /**
     * 用户是否在线
     */
    @TableField("isonline")
    private Integer isonline;

}
