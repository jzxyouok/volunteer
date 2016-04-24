package com.comiyun.volunteer.volun.entity;

import com.comiyun.core.entity.BaseEntity;
import com.comiyun.volunteer.volun.enums.ExChangeStatus;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 义工兑换品
 *
 * @author david
 */
public class ExChange extends BaseEntity {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final long serialVersionUID = -5576997880041985400L;

    private String name;

    private Long providerId;

    private String providerName;

    private String content;

    private Integer totalNum;

    private Integer useNum = 0;

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

    public Integer getRemainNum() {
        if (totalNum != null && useNum != null) {
            return totalNum - useNum;
        } else {
            return 0;
        }
    }

    private Integer needIntegral;

    private Long qrCodeId;

    private ExChangeStatus status = ExChangeStatus.stopEx;

    public String getStatusDesc() {
        if (status != null) {
            return status.getText();
        } else {
            return "";
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getUseNum() {
        return useNum;
    }

    public void setUseNum(Integer useNum) {
        this.useNum = useNum;
    }

    public Integer getNeedIntegral() {
        return needIntegral;
    }

    public void setNeedIntegral(Integer needIntegral) {
        this.needIntegral = needIntegral;
    }

    public ExChangeStatus getStatus() {
        return status;
    }

    public void setStatus(ExChangeStatus status) {
        this.status = status;
    }

    public Long getQrCodeId() {
        return qrCodeId;
    }

    public void setQrCodeId(Long qrCodeId) {
        this.qrCodeId = qrCodeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

}
