package com.comiyun.volunteer.system.persistence;

import com.comiyun.core.persistence.BaseMapper;
import com.comiyun.volunteer.system.entity.SysMenuRole;

/**
 * @author the7last
 * @ClassName: SysMenuRoleMapper
 * @date 2014年8月1日 上午11:03:39
 */
public interface SysMenuRoleMapper extends BaseMapper<SysMenuRole> {

    public int deleteSysMenuRole(SysMenuRole sysMenuRole);

    public int batchInsertSysMenuRole(SysMenuRole sysMenuRole);

}
