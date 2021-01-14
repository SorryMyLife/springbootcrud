package com.crudcrm.systemmanage;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.crudcrm.systemmanage.crm.entity.SystemDept;
import com.crudcrm.systemmanage.crm.entity.SystemRole;
import com.crudcrm.systemmanage.crm.entity.SystemUser;
import com.crudcrm.systemmanage.crm.entity.SystemUserManage;
import com.crudcrm.systemmanage.crm.service.ISystemDeptService;
import com.crudcrm.systemmanage.crm.service.ISystemRoleService;
import com.crudcrm.systemmanage.crm.service.ISystemUserManageService;
import com.crudcrm.systemmanage.crm.service.ISystemUserService;
import com.crudcrm.systemmanage.utils.SystemMangeUtils;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootTest
@MapperScan("com.crudcrm.systemmanage.crm.mapper")
class SystemmanageApplicationTests {

    /**
     * 将数据插入进数据库里
     *
     * */

//    @Autowired
//    ISystemUserService iSystemUserService;
//    @Autowired
//    ISystemDeptService systemDeptService;
//    @Autowired
//    ISystemRoleService systemRoleService;
//    @Autowired
//    ISystemUserManageService systemUserManageService;

    private SystemMangeUtils systemMangeUtils = new SystemMangeUtils();
//    @Test
//    void contextLoads() {
////        for(int i =0 ;i<100;i++){
//////            System.out.println(systemMangeUtils.getDATE());
//////            try {
//////                new Thread().sleep(10);
//////            } catch (InterruptedException e) {
//////                e.printStackTrace();
//////            }
//////        }
//        String l[] = {
//                "root",
//                "系统管理员",
//                "二级系统管理员",
//                "三级系统管理员",
//                "用户"
//        };
//        for(int j = 0;j<l.length;j++){
//            SystemRole systemRole = new SystemRole();
//            systemRole.setRoleid(j);
//            systemRole.setUserrealname(l[j]);
//            systemRole.setAddtime(LocalDateTime.now());
//            systemRole.setEdittime(LocalDateTime.now());
//            systemRole.setAdduser("root");
//            systemRole.setEdituser("root");
//            systemRoleService.getBaseMapper().insert(systemRole);
//        }
//
//        System.out.println("hello");
//    }

//    @Test
//    void listdatas(){
//
//
//
//
//        systemUserManageService.list().stream().forEach((systemUserManage)->{
//            String userid = systemUserManage.getUserid();
//            LogManager.getLogger(LogManager.ROOT_LOGGER_NAME).error("userid :: " +userid);
//            SystemUser systemUser = iSystemUserService.getBaseMapper().selectOne(new QueryWrapper<SystemUser>().eq("userid", userid));
//            LogManager.getLogger(LogManager.ROOT_LOGGER_NAME).error("systemUser name  :: " +systemUser.getUsername());
//
//        });
//    }

//    @Test
//    void insertUserMange(){
//
//        List<SystemDept> systemDepts = systemDeptService.list();
//        List<SystemRole> systemRoles = systemRoleService.list();
//        iSystemUserService.list().stream().forEach((systemUser)->{
//            SystemUserManage systemUserManage = new SystemUserManage();
//            systemUserManage.setDeptid(systemDepts.get(systemMangeUtils.getRandom(systemDepts.size(),0)).getDeptid());
//           systemUserManage.setIslock(systemMangeUtils.getRandom(2,0));
//           systemUserManage.setAddtime(LocalDateTime.now());
//           systemUserManage.setEdittime(LocalDateTime.now());
//           systemUserManage.setEdituser(0);
//           systemUserManage.setIsonline(0);
//           systemUserManage.setAdduser(systemMangeUtils.getRandom(systemRoles.size(),0));
//           systemUserManage.setRoleid(systemRoles.get(systemMangeUtils.getRandom(systemRoles.size(),0)).getRoleid());
//           systemUserManage.setUserid(systemUser.getUserid());
//            systemUserManageService.getBaseMapper().insert(systemUserManage);
//        });
//        System.out.println("insert ok !!!!!!!!");
//    }
////
////
//    @Test
//    void test(){
//        for(int i =0 ;i<10;i++){
//            SystemUser systemUser = new SystemUser();
//            systemUser.setUserid(systemMangeUtils.getDATE());
//            systemUser.setUseremail(systemMangeUtils.getRandom2(systemMangeUtils.getRandom(10,5))+"@qq.com");
//            systemUser.setUsermobile(systemMangeUtils.getRandom2(11));
//            systemUser.setUsersex(systemMangeUtils.getRandom(2,0));
//            systemUser.setUsername("老王-"+i+"号");
//            systemUser.setUserpassword(systemMangeUtils.getRandom2(16));
//            iSystemUserService.getBaseMapper().insert(systemUser);
//            SystemDept systemDept = new SystemDept();
//            systemDept.setDeptid(systemMangeUtils.getRandom2(23));
//            systemDept.setDeptname("老王科技股份制有限公司"+i);
//            systemDept.setEdittime(LocalDateTime.now());
//            systemDept.setAddtime(LocalDateTime.now());
//            systemDept.setAdduser("root");
//            systemDept.setEdituser("root");
//            systemDeptService.getBaseMapper().insert(systemDept);
//            try {
//                new Thread().sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        String l[] = {
//                "root",
//                "系统管理员",
//                "二级系统管理员",
//                "三级系统管理员",
//                "用户"
//        };
//        for(int j = 0;j<l.length;j++){
//            SystemRole systemRole = new SystemRole();
//            systemRole.setRoleid(j);
//            systemRole.setUserrealname(l[j]);
//            systemRole.setEdituser("0");
//            systemRole.setAdduser("0");
//            systemRole.setEdittime(LocalDateTime.now());
//            systemRole.setAddtime(LocalDateTime.now());
//            systemRoleService.getBaseMapper().insert(systemRole);
//        }
//
//    }

//
//    @Test
//    void gentor(){
//// 代码生成器
//        AutoGenerator mpg = new AutoGenerator();
//
//        // 全局配置
//        GlobalConfig gc = new GlobalConfig();
//        String projectPath = System.getProperty("user.dir");
//        gc.setOutputDir(projectPath + "/src/main/java");
//        gc.setAuthor("test");
//        gc.setOpen(false);
//        // gc.setSwagger2(true); 实体属性 Swagger2 注解
//        mpg.setGlobalConfig(gc);
//
//        // 数据源配置
//        DataSourceConfig dsc = new DataSourceConfig();
//        dsc.setUrl("jdbc:mysql://192.168.198.130:3306/crudcrm?characterEncoding=utf8&useSSL=false&zeroDateTimeBehavior=convertToNull&tinyInt1isBit=false");
//        // dsc.setSchemaName("public");
//        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
//        dsc.setUsername("root");
//        dsc.setPassword("root");
//        mpg.setDataSource(dsc);
//
//        // 包配置
//        PackageConfig pc = new PackageConfig();
//        pc.setModuleName("crm");
//        pc.setParent("com.crudcrm.systemmanage");
//        mpg.setPackageInfo(pc);
//
//
//
//        // 配置模板
//        TemplateConfig templateConfig = new TemplateConfig();
//
//        // 配置自定义输出模板
//        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
//        // templateConfig.setEntity("templates/entity2.java");
//        // templateConfig.setService();
//        // templateConfig.setController();
//
//        templateConfig.setXml(null);
//        mpg.setTemplate(templateConfig);
//
//        // 策略配置
//        StrategyConfig strategy = new StrategyConfig();
//        strategy.setNaming(NamingStrategy.underline_to_camel);
//        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//
//        strategy.setEntityLombokModel(true);
//        strategy.setRestControllerStyle(true);
//
//        // 写于父类中的公共字段
//        strategy.setSuperEntityColumns("id");
//        strategy.setInclude(new String[]{"crm_system_code"});
//        strategy.setControllerMappingHyphenStyle(true);
//        strategy.setTablePrefix(pc.getModuleName() + "_");
//        mpg.setStrategy(strategy);
//        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
//        mpg.execute();
//    }




}
