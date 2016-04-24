package com.comiyun.volunteer.system.entity;

import com.comiyun.core.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 租户菜单表
 *
 * @author ydwcn
 * @ClassName: TenantMemu
 * @date 2014-6-24 上午10:14:30
 */
public class SysMenu extends BaseEntity {

    private static final long serialVersionUID = 2988424110049621346L;

    /**
     * 菜单ID
     */
    private Long metaId;

    /**
     * 临时变量
     *
     * @see SysMenuMeta
     */
    private String name;
    public String text; //Easyui树显示用
    private String iconCls;
    private Long pid;
    private Boolean isVisble;
    private Boolean isLeaf;
    private String url;

    private List<SysMenu> children;

    /**
     * 添加子节点
     *
     * @param c
     * @throws
     * @Title: addChildren
     */
    public void addChildren(SysMenu c) {
        if (children == null) {
            children = new ArrayList<SysMenu>();
        }
        if (c.getPid().compareTo(this.getMetaId()) == 0) {
            children.add(c);
        }
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the isVisble
     */
    public Boolean getIsVisble() {
        return isVisble;
    }

    /**
     * @param isVisble the isVisble to set
     */
    public void setIsVisble(Boolean isVisble) {
        this.isVisble = isVisble;
    }

    /**
     * @return the isLeaf
     */
    public Boolean getIsLeaf() {
        return isLeaf;
    }

    /**
     * @param isLeaf the isLeaf to set
     */
    public void setIsLeaf(Boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getMetaId() {
        return metaId;
    }

    public void setMetaId(Long metaId) {
        this.metaId = metaId;
    }

    public String getText() {
        return name;
    }

    public void setText(String text) {
        this.text = name;
    }

    public List<SysMenu> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenu> children) {
        this.children = children;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }
}
