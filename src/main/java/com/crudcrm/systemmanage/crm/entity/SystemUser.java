package com.crudcrm.systemmanage.crm.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("crm_system_user")
public class SystemUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id，唯一性
     */
    @TableId
    private String userid;

    /**
     * 用户名字
     */
    private String username;

    /**
     * 用户性别
     */
    @TableField("userSex")
    private Integer usersex;

    /**
     * 用户邮箱
     */
    @TableField("userEmail")
    private String useremail;

    /**
     * 用户联系方式
     */
    @TableField("userMobile")
    private String usermobile;

    /**
     * 用户密码
     */
    @TableField("userPassword")
    private String userpassword;



}
