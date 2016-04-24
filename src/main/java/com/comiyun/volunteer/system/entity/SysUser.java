package com.comiyun.volunteer.system.entity;

import com.comiyun.core.entity.BaseEntity;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 系统账户表
 *
 * @author ydwcn
 * @ClassName: SysUser
 * @date 2014-6-16 上午10:50:45
 */
@JsonIgnoreProperties(value = {"password"})
public class SysUser extends BaseEntity {
    private static final long serialVersionUID = -7699687903637296125L;

    /**
     * 企业内部账号
     */
    private String username;
    /**
     * 真实姓名
     */
    private String realname;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    @NotEmpty
    private String mobile;
    /**
     * 密码
     */
    private String password;

    /**
     * 供应商态度
     */
    private int providerTd = 0;
    /**
     * 供应商效率
     */
    private int providerXl = 0;

    private String roles; //权限字符串

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public int getProviderTd() {
        return providerTd;
    }

    public void setProviderTd(int providerTd) {
        this.providerTd = providerTd;
    }

    public int getProviderXl() {
        return providerXl;
    }

    public void setProviderXl(int providerXl) {
        this.providerXl = providerXl;
    }

}
