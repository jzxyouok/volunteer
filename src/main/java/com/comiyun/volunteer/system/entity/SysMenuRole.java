package com.comiyun.volunteer.system.entity;

import java.util.List;

/**
 * 菜单角色关系表
 *
 * @author ydwcn
 * @ClassName: MenuRole
 * @date 2014-6-24 上午11:56:35
 */
public class SysMenuRole {
    /**
     * 菜单ID
     */
    private Long menuId;
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 菜单ID集合List
     */
    private List<Long> menuIds;

    public List<Long> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<Long> menuIds) {
        this.menuIds = menuIds;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

}
