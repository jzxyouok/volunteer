package com.comiyun.volunteer.system.entity;

import com.comiyun.core.entity.AbstractEntity;

/**
 * 系统菜单元数表
 *
 * @author ydwcn
 * @ClassName: SysMenu
 * @date 2014-6-16 下午5:09:44
 */
public class SysMenuMeta extends AbstractEntity {

    private static final long serialVersionUID = 1158947390890805019L;

    /**
     * 菜单名称
     */
    private String name;
    /**
     * ICON
     */
    private String iconCls;
    /**
     * 父ID
     */
    private String pid;
    /**
     * 是否显示
     */
    private boolean isVisble;
    /**
     * 是否叶节点
     */
    private boolean isLeaf;
    /**
     * 默认URL
     */
    private String url;
    /**
     * 排序
     */
    private Integer sn;

    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public boolean isVisble() {
        return isVisble;
    }

    public void setVisble(boolean isVisble) {
        this.isVisble = isVisble;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }


}
