package com.comiyun.volunteer.volun.entity;

import com.comiyun.core.entity.BaseEntity;
import com.comiyun.core.web.json.JsonTimeSerializer;
import com.comiyun.volunteer.volun.enums.ActivityStatus;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 义工活动
 *
 * @author david
 */
public class Activity extends BaseEntity {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final long serialVersionUID = -8993636084065351675L;

    private String code;

    private String name;

    private String content;

    @JsonSerialize(using = JsonTimeSerializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startTime;

    public String getStartTimeStr() {
        String str = "";
        Date d = getStartTime();
        if (d != null) {
            try {
                str = sdf.format(d);
            } catch (Exception e) {
                //ignore
            }
        }
        return str;
    }

    @JsonSerialize(using = JsonTimeSerializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endTime;

    private Integer persionNum;

    private Integer perIntegral;

    private Long areaId;

    private String areaName;

    private Long qrCodeId;

    private ActivityStatus status;

    public String getStatusDesc() {
        if (status != null) {
            return status.getText();
        } else {
            return "";
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getPersionNum() {
        return persionNum;
    }

    public void setPersionNum(Integer persionNum) {
        this.persionNum = persionNum;
    }

    public Integer getPerIntegral() {
        return perIntegral;
    }

    public void setPerIntegral(Integer perIntegral) {
        this.perIntegral = perIntegral;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public ActivityStatus getStatus() {
        return status;
    }

    public void setStatus(ActivityStatus status) {
        this.status = status;
    }

    public Long getQrCodeId() {
        return qrCodeId;
    }

    public void setQrCodeId(Long qrCodeId) {
        this.qrCodeId = qrCodeId;
    }


}
