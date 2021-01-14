package com.crudcrm.systemmanage.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.crudcrm.systemmanage.crm.entity.SystemDept;
import com.crudcrm.systemmanage.crm.entity.SystemRole;
import com.crudcrm.systemmanage.crm.entity.SystemUser;
import com.crudcrm.systemmanage.crm.entity.SystemUserManage;
import com.crudcrm.systemmanage.crm.mapper.SystemUserMapper;
import com.crudcrm.systemmanage.crm.service.ISystemDeptService;
import com.crudcrm.systemmanage.crm.service.ISystemRoleService;
import com.crudcrm.systemmanage.crm.service.ISystemUserManageService;
import com.crudcrm.systemmanage.crm.service.ISystemUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crudcrm.systemmanage.crm.vo.SystemUserVo;
import com.crudcrm.systemmanage.utils.SystemMangeUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Logger;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author test
 * @since 2020-12-24
 */
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser> implements ISystemUserService {

    @Autowired
    SystemUserMapper systemUserMapper;

    @Autowired
    ISystemUserManageService systemUserManageService;

    @Autowired
    ISystemDeptService systemDeptService;

    @Autowired
    ISystemRoleService systemRoleService;

    public SystemUserManage InitSystemUserManage(HttpServletRequest request){
        SystemUserManage manageServiceOneUid = null;
        if(request != null){
            String uid = new SystemMangeUtils().getRequestCookieUid(request);
            if(uid != null) {
                manageServiceOneUid = systemUserManageService.getOne(new QueryWrapper<SystemUserManage>().eq("userid", uid));
            }
        }
        return  manageServiceOneUid;
    }


    @Override
    public Boolean updateUserVo(SystemUserVo systemUserVo) {
        return  updateUserVo(systemUserVo,null);
    }

    @Override
    public SystemUser selectByIdVo(String systemUserVoId) {
        return systemUserVoId.isEmpty() ? null : systemUserMapper.selectByIdVo(systemUserVoId);
    }

    @Override
    public Boolean delUserVo(SystemUserVo systemUserVo) {
        return delUserVo(systemUserVo.getUserid());
    }

    @Override
    public Boolean delUserVo(String userid) {
        if(userid!= null && !userid.isEmpty()){
            systemUserMapper.deleteById(userid);
            systemUserManageService.getBaseMapper().deleteById(userid);
            return true;
        }
        return false;
    }

    @Override
    public Boolean delUserPwdVo(String userid, String pwd) {
        return delUserPwdVo(userid, pwd,null);
    }

    @Override
    public Boolean delUserVo(String[] userids) {
        Logger.getLogger("我的userids内容").info(Arrays.toString(userids));
        if(userids!= null && userids.length > 0){
            for (String userid : userids) {
                systemUserMapper.deleteById(userid);
                systemUserManageService.getBaseMapper().deleteById(userid);
            }
            return true;
        }
        return false;
    }

    @Override
    public Boolean delUserPwdVo(String userid) {
        return delUserPwdVo(userid,"");
    }

    @Override
    public Boolean updateUserVo(SystemUserVo systemUserVo, HttpServletRequest request) {
        String roleid = systemUserVo.getRoleid();
        String userid = systemUserVo.getUserid();
        String deptid = systemUserVo.getDeptid();
        SystemUserManage userManageUid = InitSystemUserManage(request);
        if(userid != null && !userid.isEmpty()){
            if (roleid != null && !roleid.isEmpty() &&
                    deptid != null && !deptid.isEmpty()) {
                SystemRole systemRole = systemRoleService.selectByID(roleid);
                SystemDept systemDept = systemDeptService.selectById(deptid);
                SystemUserManage systemUserManage = systemUserManageService.selectByIdVO(userid);
                SystemUser systemUser = this.selectByIdVo(userid);
                if (systemUserManage != null && systemRole != null && systemUser != null && systemDept != null) {
                    BeanUtils.copyProperties(systemUserVo,systemUserManage);
                    systemUserManage.setEdittime(LocalDateTime.now());
                    systemUserManage.setRoleid(Integer.valueOf(roleid));
                    systemUserManage.setDeptid(deptid);
                    BeanUtils.copyProperties(systemUserVo,systemUser);
                    if(userManageUid != null){
                        systemUserManage.setEdituser(userManageUid.getRoleid());
                    }
                    systemUserManageService.getBaseMapper().updateById(systemUserManage);
                    this.getBaseMapper().updateById(systemUser);
                    return true;
                }
            }
        }else{
            SystemMangeUtils systemMangeUtils = new SystemMangeUtils();
            SystemUser user = new SystemUser();
            SystemUserManage systemUserManage = new SystemUserManage();
            systemUserManage.setIsonline(0);
            systemUserManage.setDeptid(systemUserVo.getDeptid());
            systemUserManage.setRoleid(Integer.valueOf(systemUserVo.getRoleid()));
            systemUserManage.setEdittime(LocalDateTime.now());
            systemUserManage.setAddtime(LocalDateTime.now());
            systemUserManage.setIslock(0);
            if(userManageUid != null){
                systemUserManage.setEdituser(userManageUid.getRoleid());
                systemUserManage.setAdduser(userManageUid.getRoleid());
            }
            BeanUtils.copyProperties(systemUserVo,user);
            user.setUserid(systemMangeUtils.getDATE());
            user.setUserpassword(systemMangeUtils.getRandom2(16));
            systemUserManage.setUserid(user.getUserid());
            systemUserMapper.insert(user);
            systemUserManageService.getBaseMapper().insert(systemUserManage);
            return true;
        }
        return false;
    }

    @Override
    public Boolean delUserPwdVo(String userid, String pwd, HttpServletRequest request) {
        if(userid!= null && !userid.isEmpty()){
            SystemUser systemUser = systemUserMapper.selectById(userid);
            SystemUserManage manageServiceOneUid = InitSystemUserManage(request);
            if(systemUser != null){
                if(!systemUser.getUserpassword().equals(pwd.replaceAll("\\s+",""))){
                    systemUser.setUserpassword(pwd);
                    systemUserMapper.updateById(systemUser);
                    if(manageServiceOneUid != null){
                        manageServiceOneUid.setEdituser(manageServiceOneUid.getRoleid());
                        manageServiceOneUid.setEdittime(LocalDateTime.now());
                        systemUserManageService.updateById(manageServiceOneUid);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Boolean changeUserSwitchVo(int i, String userid, Integer status, HttpServletRequest request) {
        SystemUserManage manageServiceOneUid = InitSystemUserManage(request);
        if(userid!= null && !userid.isEmpty()){
            SystemUserManage systemUserManage = systemUserManageService.getBaseMapper().selectById(userid);
            if(systemUserManage != null){
                switch (i){
                    case  1 :
                        if(systemUserManage.getIslock() != status){
                            systemUserManage.setIslock(status);
                        }
                    case 2:
                        if(systemUserManage.getIsonline() != status){
                            systemUserManage.setIsonline(status);
                        }
                    default:
                            System.err.println("SystemUserServiceImpl没有被支持：：：252");
                }
                systemUserManage.setEdittime(LocalDateTime.now());
                if(manageServiceOneUid != null){
                    systemUserManage.setEdituser(manageServiceOneUid.getRoleid());
                }
                return systemUserManageService.updateById(systemUserManage);
            }
        }
        return false;
    }

    @Override
    public String[] getCode(int codelen, int codeheight) {
        int len = codelen;
        int height = codeheight;
        int width = 10* (len * 2);
        BufferedImage buffImg = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();
        // 创建一个随机数生成器类。
        Random random = new Random();
        // 设定图像背景色(因为是做背景，所以偏淡)
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        // 创建字体，字体的大小应该根据图片的高度来定。
        Font font = new Font("Times New Roman", Font.HANGING_BASELINE, 28);      // 设置字体。
        g.setFont(font);
        // 画边框。
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, width - 1, height - 1);
        // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到。
        //g.setColor(Color.GRAY);
        g.setColor(getRandColor(160,200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }
        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuffer randomCode = new StringBuffer();
        // 设置备选验证码:包括"a-z"和数字"0-9"
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int size = base.length();
        // 随机产生4位数字的验证码。
        for (int i = 0; i < len; i++) {
            // 得到随机产生的验证码数字。
            int start = random.nextInt(size);
            String strRand = base.substring(start, start + 1);
            // 用随机产生的颜色将验证码绘制到图像中。
            // 生成随机颜色(因为是做前景，所以偏深) //g.setColor(getRandColor(1, 100)); 81
            //调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
            g.drawString(strRand, 15 * i + 6, 24);
            // 将产生的四个随机数组合在一起。
            randomCode.append(strRand);
        }

        g.dispose();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(buffImg, "png", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] encodeBase64 = Base64.encodeBase64(outputStream.toByteArray());
        String outbase64 = "data:image/png;base64,";

        String base64str = outbase64+new String(encodeBase64);
        String codestr = randomCode.toString();
        String data[] = {
          codestr,base64str
        };
        System.out.println(codestr + " --- " + base64str);
        return  data;
    }

    public Color getRandColor(int fc, int bc) {// 给定范围获得随机颜色
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
