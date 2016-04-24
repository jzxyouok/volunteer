package com.comiyun.weixin.entity;

import com.comiyun.weixin.enums.MenuType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 微信菜单
 *
 * @author david
 */
public class WxMenu implements Serializable {

    private static final long serialVersionUID = 5658650421078575916L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 菜单名字
     */
    private String name;

    /**
     * 菜单类型
     */
    private MenuType type;

    public String getTypeDesc() {
        if (type != null) {
            return type.getText();
        } else {
            return "";
        }
    }

    /**
     * Click Key
     */
    private String key;
    /**
     * View URL
     */
    private String url;

    /**
     * metia_id
     */
    private String metiaId;

    private Long extendId;

    private String extendName;

    /**
     * 序号
     */
    private Integer sn;
    /**
     * 父菜单ID
     */
    private Long pid;
    /**
     * 公众号
     */
    private Long accountId;
    /**
     * 子节点
     */
    private List<WxMenu> children = new ArrayList<WxMenu>();

    /**
     * 添加子菜单
     *
     * @param menu
     */
    public void addChildren(WxMenu menu) {
        children.add(menu);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public MenuType getType() {
        return type;
    }

    public void setType(MenuType type) {
        this.type = type;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public List<WxMenu> getChildren() {
        return children;
    }

    public void setChildren(List<WxMenu> children) {
        this.children = children;
    }

    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMetiaId() {
        return metiaId;
    }

    public void setMetiaId(String metiaId) {
        this.metiaId = metiaId;
    }

    public Long getExtendId() {
        return extendId;
    }

    public void setExtendId(Long extendId) {
        this.extendId = extendId;
    }

    public String getExtendName() {
        return extendName;
    }

    public void setExtendName(String extendName) {
        this.extendName = extendName;
    }

}
