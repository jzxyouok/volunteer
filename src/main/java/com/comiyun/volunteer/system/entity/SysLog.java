package com.comiyun.volunteer.system.entity;

import com.comiyun.core.entity.BaseEntity;
import com.comiyun.core.web.json.JsonTimeSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;

/**
 * 系统日志
 *
 * @author ydwcn
 * @ClassName: SysLog
 * @date 2014-6-24 下午12:03:52
 */
public class SysLog extends BaseEntity {

    private static final long serialVersionUID = 3170918997321332594L;
    /**
     * 所属模块
     */
    private String ownmodule;
    /**
     * 操作类型
     */
    private String opType;
    /**
     * 操作人员
     */
    private String opUser;
    /**
     * 操作时间
     */
    @JsonSerialize(using = JsonTimeSerializer.class)
    private Date opDate;
    /**
     * 操作内容
     */
    private String opContent;
    /**
     * 操作IP
     */
    private String opIp;

    public String getOpContent() {
        return opContent;
    }

    public void setOpContent(String opContent) {
        this.opContent = opContent;
    }

    public String getOpIp() {
        return opIp;
    }

    public void setOpIp(String opIp) {
        this.opIp = opIp;
    }

    public String getOpUser() {
        return opUser;
    }

    public void setOpUser(String opUser) {
        this.opUser = opUser;
    }

    public Date getOpDate() {
        return opDate;
    }

    public void setOpDate(Date opDate) {
        this.opDate = opDate;
    }

    public String getOwnmodule() {
        return ownmodule;
    }

    public void setOwnmodule(String ownmodule) {
        this.ownmodule = ownmodule;
    }

    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

}
