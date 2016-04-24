package com.comiyun.volunteer.system.persistence;

import com.comiyun.core.persistence.BaseMapper;
import com.comiyun.volunteer.system.entity.SysMenu;
import com.comiyun.volunteer.system.entity.SysRole;

import java.util.List;

/**
 * 系统角色
 *
 * @author ydwcn
 * @ClassName: SysRoleMapper
 * @date 2014-6-24 下午12:09:02
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 获取角色关联的菜单
     *
     * @param roleId 角色ID
     * @return List<SysMenu> 菜单集合
     */
    public List<SysMenu> getRoleMenu(long roleId);
}
