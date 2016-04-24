package com.comiyun.volunteer.volun.entity;

import com.comiyun.core.entity.BaseEntity;
import com.comiyun.volunteer.volun.enums.ExChangeDetailStatus;
import com.comiyun.volunteer.volun.enums.SexStatus;

public class ExChangeDetail extends BaseEntity {

    private static final long serialVersionUID = 7956330046986055256L;

    private Long exId;

    private String exName;

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

    private int perIntegral;

    private int num;

    private String exCode;

    private ExChangeDetailStatus status;

    public String getStatusDesc() {
        if (status != null) {
            return status.getText();
        } else {
            return "";
        }
    }

    public Long getExId() {
        return exId;
    }

    public void setExId(Long exId) {
        this.exId = exId;
    }

    public Long getPersionId() {
        return persionId;
    }

    public void setPersionId(Long persionId) {
        this.persionId = persionId;
    }

    public int getPerIntegral() {
        return perIntegral;
    }

    public void setPerIntegral(int perIntegral) {
        this.perIntegral = perIntegral;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public ExChangeDetailStatus getStatus() {
        return status;
    }

    public void setStatus(ExChangeDetailStatus status) {
        this.status = status;
    }

    public String getExName() {
        return exName;
    }

    public void setExName(String exName) {
        this.exName = exName;
    }

    public String getPersionName() {
        return persionName;
    }

    public void setPersionName(String persionName) {
        this.persionName = persionName;
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

    public String getExCode() {
        return exCode;
    }

    public void setExCode(String exCode) {
        this.exCode = exCode;
    }

}
