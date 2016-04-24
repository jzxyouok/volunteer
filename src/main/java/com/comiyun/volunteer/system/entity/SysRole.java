package com.comiyun.volunteer.system.entity;

import com.comiyun.core.entity.BaseEntity;

/**
 * 系统权限
 */
public class SysRole extends BaseEntity {

    private static final long serialVersionUID = -1463824127628069317L;

    /**
     * 角色名称
     */
    private String roleName;

    private String text; // Easyui树显示用

    private String iconCls;

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
