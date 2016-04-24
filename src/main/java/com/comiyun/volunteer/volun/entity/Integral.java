package com.comiyun.volunteer.volun.entity;

import com.comiyun.core.entity.BaseEntity;
import com.comiyun.volunteer.volun.enums.IntegralBizType;
import com.comiyun.volunteer.volun.enums.SexStatus;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author david
 */
public class Integral extends BaseEntity {

    private static final long serialVersionUID = 7087935112553139219L;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private IntegralBizType bizType;

    public String getBizTypeDesc() {
        if (bizType != null) {
            return bizType.getText();
        } else {
            return IntegralBizType.hand.getText();
        }
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

    private Long bizId;

    private String bizContent;

    public String getCreateTimeStr() {
        String str = "";
        Date d = getCreateTime();
        if (d != null) {
            try {
                str = sdf.format(d);
            } catch (Exception e) {
                //ignore
            }
        }
        return str;
    }

    private int digit;

    public IntegralBizType getBizType() {
        return bizType;
    }

    public void setBizType(IntegralBizType bizType) {
        this.bizType = bizType;
    }

    public Long getPersionId() {
        return persionId;
    }

    public void setPersionId(Long persionId) {
        this.persionId = persionId;
    }

    public Long getBizId() {
        return bizId;
    }

    public void setBizId(Long bizId) {
        this.bizId = bizId;
    }

    public int getDigit() {
        return digit;
    }

    public void setDigit(int digit) {
        this.digit = digit;
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

    public String getBizContent() {
        return bizContent;
    }

    public void setBizContent(String bizContent) {
        this.bizContent = bizContent;
    }

}
