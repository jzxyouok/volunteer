package com.comiyun.volunteer.volun.entity;

import com.comiyun.core.entity.BaseEntity;
import com.comiyun.core.web.json.JsonTimeSerializer;
import com.comiyun.volunteer.volun.enums.ActivityPersionStatus;
import com.comiyun.volunteer.volun.enums.ActivityResource;
import com.comiyun.volunteer.volun.enums.SexStatus;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 义工活动
 *
 * @author david
 */
public class ActivityPersion extends BaseEntity {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final long serialVersionUID = -1948826740083441064L;

    private Long actId;

    private String actCode;

    private String actName;

    private Date actStartTime;

    public String getActStartTimeStr() {
        String str = "";
        Date d = getActStartTime();
        if (d != null) {
            try {
                str = sdf.format(d);
            } catch (Exception e) {
                //ignore
            }
        }
        return str;
    }

    private Date actEndTime;

    public String getActEndTimeStr() {
        String str = "";
        Date d = getActEndTime();
        if (d != null) {
            try {
                str = sdf.format(d);
            } catch (Exception e) {
                //ignore
            }
        }
        return str;
    }

    private Long persionId;

    private String persionName;

    private String persionMobile;

    private SexStatus persionSex;

    public String getPersionSexDesc() {
        if (persionSex != null) {
            return persionSex.getText();
        } else {
            return SexStatus.unkown.getText();
        }
    }

    private ActivityResource resource;

    public String getResourceDesc() {
        if (resource != null) {
            return resource.getText();
        } else {
            return ActivityResource.sys.getText();
        }
    }

    @JsonSerialize(using = JsonTimeSerializer.class)
    private Date bmTime;
    @JsonSerialize(using = JsonTimeSerializer.class)
    private Date qdTime;
    @JsonSerialize(using = JsonTimeSerializer.class)
    private Date qtTime;

    private ActivityPersionStatus status;

    public String getStatusDesc() {
        if (status != null) {
            return status.getText();
        } else {
            return "";
        }
    }

    public Long getActId() {
        return actId;
    }

    public void setActId(Long actId) {
        this.actId = actId;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public Long getPersionId() {
        return persionId;
    }

    public void setPersionId(Long persionId) {
        this.persionId = persionId;
    }

    public String getPersionName() {
        return persionName;
    }

    public void setPersionName(String persionName) {
        this.persionName = persionName;
    }

    public ActivityResource getResource() {
        return resource;
    }

    public void setResource(ActivityResource resource) {
        this.resource = resource;
    }

    public Date getBmTime() {
        return bmTime;
    }

    public void setBmTime(Date bmTime) {
        this.bmTime = bmTime;
    }

    public Date getQdTime() {
        return qdTime;
    }

    public void setQdTime(Date qdTime) {
        this.qdTime = qdTime;
    }

    public ActivityPersionStatus getStatus() {
        return status;
    }

    public void setStatus(ActivityPersionStatus status) {
        this.status = status;
    }

    public String getPersionMobile() {
        return persionMobile;
    }

    public void setPersionMobile(String persionMobile) {
        this.persionMobile = persionMobile;
    }

    public SexStatus getPersionSex() {
        return persionSex;
    }

    public void setPersionSex(SexStatus persionSex) {
        this.persionSex = persionSex;
    }

    public String getActCode() {
        return actCode;
    }

    public void setActCode(String actCode) {
        this.actCode = actCode;
    }

    public Date getQtTime() {
        return qtTime;
    }

    public void setQtTime(Date qtTime) {
        this.qtTime = qtTime;
    }

    public Date getActStartTime() {
        return actStartTime;
    }

    public void setActStartTime(Date actStartTime) {
        this.actStartTime = actStartTime;
    }

    public Date getActEndTime() {
        return actEndTime;
    }

    public void setActEndTime(Date actEndTime) {
        this.actEndTime = actEndTime;
    }

}
