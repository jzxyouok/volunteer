package com.comiyun.volunteer.system.persistence;

import com.comiyun.core.persistence.BaseMapper;
import com.comiyun.volunteer.system.entity.SysRole;
import com.comiyun.volunteer.system.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统用户Mapper
 *
 * @author ydwcn
 * @ClassName: SysUserMapper
 * @date 2014-6-18 下午2:25:17
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 重置密码
     *
     * @param id
     * @return void
     * @throws
     * @Title: resetPwd
     */
    public void resetPwd(@Param("id") long id, @Param("newpwd") String newpwd);

    public List<SysRole> getUserRoleList(@Param("id") long id);

    /**
     * 查询特约服务商
     *
     * @return
     */
    public List<SysUser> queryExProviderList();

}
