package com.crudcrm.systemmanage.crm.vo;

import com.crudcrm.systemmanage.crm.entity.SystemUser;
import lombok.Data;

@Data
public class SystemUserVo extends SystemUser {

    private String roleid,token,deptid;

}
