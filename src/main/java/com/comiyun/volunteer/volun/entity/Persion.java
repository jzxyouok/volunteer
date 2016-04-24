package com.comiyun.volunteer.volun.entity;

import com.comiyun.core.entity.BaseEntity;
import com.comiyun.core.web.json.JsonDateSerializer;
import com.comiyun.volunteer.volun.enums.PersionStatus;
import com.comiyun.volunteer.volun.enums.SexStatus;
import com.comiyun.volunteer.volun.enums.VolunChannel;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 义工信息
 *
 * @author david
 */
public class Persion extends BaseEntity {

    private static final long serialVersionUID = -684245262933678533L;

    private String name;

    private String mobile;

    private String telphone;

    private String qq;

    private String wxNum;

    private SexStatus sex;

    public String getSexDesc() {
        if (sex != null) {
            return sex.getText();
        } else {
            return SexStatus.unkown.getText();
        }
    }

    private VolunChannel channel;

    public String getChannelDesc() {
        if (channel != null) {
            return channel.getText();
        } else {
            return "";
        }
    }

    @JsonSerialize(using = JsonDateSerializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    private String jiguan;

    private String idcard;

    private String zzmm;

    private String wxOpenId;

    private Long areaId;

    private String areaName;

    private String address;

    private String company;

    private String position;

    private Integer integral = 0;

    private Integer degree = 0;

    private Long qrCodeId;

    private boolean isAdmin = false;

    public String getIsAdminDesc() {
        if (isAdmin) {
            return "是";
        } else {
            return "否";
        }
    }

    private Long familyId;

    private String familyName;

    private String familyOwner = "";

    private String familyMe = "";

    private PersionStatus status;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public VolunChannel getChannel() {
        return channel;
    }

    public void setChannel(VolunChannel channel) {
        this.channel = channel;
    }

    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    public PersionStatus getStatus() {
        return status;
    }

    public void setStatus(PersionStatus status) {
        this.status = status;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public SexStatus getSex() {
        return sex;
    }

    public void setSex(SexStatus sex) {
        this.sex = sex;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Long getQrCodeId() {
        return qrCodeId;
    }

    public void setQrCodeId(Long qrCodeId) {
        this.qrCodeId = qrCodeId;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getJiguan() {
        return jiguan;
    }

    public void setJiguan(String jiguan) {
        this.jiguan = jiguan;
    }

    public String getZzmm() {
        return zzmm;
    }

    public void setZzmm(String zzmm) {
        this.zzmm = zzmm;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWxNum() {
        return wxNum;
    }

    public void setWxNum(String wxNum) {
        this.wxNum = wxNum;
    }

    public Long getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Long familyId) {
        this.familyId = familyId;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getFamilyOwner() {
        return familyOwner;
    }

    public void setFamilyOwner(String familyOwner) {
        this.familyOwner = familyOwner;
    }

    public String getFamilyMe() {
        return familyMe;
    }

    public void setFamilyMe(String familyMe) {
        this.familyMe = familyMe;
    }

}
