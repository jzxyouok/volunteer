package com.comiyun.volunteer.system.service;

import com.comiyun.core.exception.ServiceException;
import com.comiyun.core.persistence.GenericMapper;
import com.comiyun.core.service.BaseService;
import com.comiyun.core.util.AppUtil;
import com.comiyun.core.util.MD5Util;
import com.comiyun.core.web.json.Message;
import com.comiyun.core.web.util.SessionUtil;
import com.comiyun.volunteer.system.entity.SysParam;
import com.comiyun.volunteer.system.entity.SysUser;
import com.comiyun.volunteer.system.persistence.SysUserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SysUserService extends BaseService<SysUser> {

    @Resource
    private SysUserMapper mapper;
    @Resource
    private SysParamService sysParamService;

    @Override
    protected GenericMapper<SysUser, Long> getMapper() {
        return mapper;
    }

    @Override
    public int insert(SysUser entity) throws ServiceException {
        if (StringUtils.isBlank(entity.getUsername())) {
            throw new ServiceException("请输入账户");
        }
        if (StringUtils.isBlank(entity.getRealname())) {
            throw new ServiceException("请输入真实姓名");
        }

        boolean existUserName = exist("sys_user", "username", entity.getUsername(), entity.getId());
        if (existUserName) {
            throw new ServiceException("账户已存在");
        }

        if (StringUtils.isNoneBlank(entity.getEmail())) {
            boolean existEmail = exist("sys_user", "email", entity.getEmail(), entity.getId());
            if (existEmail) {
                throw new ServiceException("邮箱已存在");
            }
        }

        if (StringUtils.isNoneBlank(entity.getMobile())) {
            boolean existMobile = exist("sys_user", "mobile", entity.getMobile(), entity.getId());
            if (existMobile) {
                throw new ServiceException("手机号已存在");
            }
        }

        SysParam sysparam = sysParamService.get(1L);
        entity.setId(AppUtil.generateId());
        String srcpwd = sysparam.getParamValue();
        srcpwd = StringUtils.isNotBlank(srcpwd) ? srcpwd : "123456";
        String pwd = MD5Util.md5(srcpwd);
        entity.setPassword(pwd);
        entity.setCreateBy(SessionUtil.getCurrentUser());
        entity.setCreateTime(new Date());
        return mapper.insert(entity);
    }

    @Override
    public int update(SysUser entity) throws ServiceException {
        if (StringUtils.isBlank(entity.getUsername())) {
            throw new ServiceException("请输入账户");
        }
        if (StringUtils.isBlank(entity.getRealname())) {
            throw new ServiceException("请输入真实姓名");
        }

        boolean existUserName = exist("sys_user", "username", entity.getUsername(), entity.getId());
        if (existUserName) {
            throw new ServiceException("账户已存在");
        }

        if (StringUtils.isNoneBlank(entity.getEmail())) {
            boolean existEmail = exist("sys_user", "email", entity.getEmail(), entity.getId());
            if (existEmail) {
                throw new ServiceException("邮箱已存在");
            }
        }

        if (StringUtils.isNoneBlank(entity.getMobile())) {
            boolean existMobile = exist("sys_user", "mobile", entity.getMobile(), entity.getId());
            if (existMobile) {
                throw new ServiceException("手机号已存在");
            }
        }
        return super.update(entity);
    }

    /**
     * 重置密码
     *
     * @param id
     * @return void
     * @throws
     * @Title: resetPwd
     */
    public Message changePwd(long id, String oldPwd, String newPwd) {
        Message msg = new Message();
        msg.setMsg("修改成功");

        if (StringUtils.isBlank(oldPwd)) {
            msg.setSuccess(false);
            msg.setMsg("请输入旧密码");
            return msg;
        }

        if (StringUtils.isBlank(newPwd)) {
            msg.setSuccess(false);
            msg.setMsg("请输入新密码");
            return msg;
        }

        SysUser user = mapper.get(id);
        String oldpwdmd5 = MD5Util.md5(oldPwd);
        String newpwdmd5 = MD5Util.md5(newPwd);
        if (!user.getPassword().equals(oldpwdmd5)) {
            msg.setSuccess(false);
            msg.setMsg("旧密码不正确");
            return msg;
        }

        mapper.resetPwd(id, newpwdmd5);

        return msg;
    }

    /**
     * 重置密码
     *
     * @param id
     * @return void
     * @throws
     * @Title: resetPwd
     */
    public void resetPwd(long id) {
        SysParam sysparam = sysParamService.get(1L);
        String secPwd = MD5Util.md5(sysparam.getParamValue());
        mapper.resetPwd(id, secPwd);
    }

    /**
     * 获取特约服务商列表
     *
     * @return
     */
    public List<SysUser> queryExProviderList() {
        return mapper.queryExProviderList();
    }

}
