package com.comiyun.volunteer.system.persistence;

import com.comiyun.core.persistence.BaseMapper;
import com.comiyun.volunteer.system.entity.SysUserRole;

/**
 * @author the7last
 * @ClassName: SysUserRoleMapper
 * @date 2014年8月4日 下午10:47:07
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    public int deleteSysUserRole(SysUserRole sysUserRole);

    public int batchInsertSysUserRole(SysUserRole sysUserRole);
}
