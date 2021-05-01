package com.crudcrm.systemmanage.crm.vo;

import com.crudcrm.systemmanage.crm.entity.SystemUserManage;
import lombok.Data;

@Data
public class SystemUserManageVo extends SystemUserManage {
    private String uoldid, systemNo , deptname , useremail , usermobile , usersex , userrealname , userpassword , username;


}
