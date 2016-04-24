package com.comiyun.core.entity;

import com.comiyun.core.web.json.JsonTimeSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础字段实体
 *
 * @author ydwcn
 * @ClassName: AbstractEntity
 * @date 2014-6-18 下午2:50:10
 */
public abstract class AbstractEntity implements Serializable {

    private static final long serialVersionUID = -8912128580642016692L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    @JsonSerialize(using = JsonTimeSerializer.class)
    private Date createTime;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
    @JsonSerialize(using = JsonTimeSerializer.class)
    private Date updateTime;
    /**
     * 是否逻辑删除
     */
    private boolean deleted;
    /**
     * 是否系统内置,默认:false
     */
    private boolean sysInit = false;

    public String getSysInitDesc() {
        return sysInit ? "是" : "否";
    }

    /**
     * 备注
     */
    private String remark;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isSysInit() {
        return sysInit;
    }

    public void setSysInit(boolean sysInit) {
        this.sysInit = sysInit;
    }
}
