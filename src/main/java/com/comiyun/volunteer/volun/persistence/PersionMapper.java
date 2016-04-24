package com.comiyun.volunteer.volun.persistence;

import com.comiyun.core.persistence.BaseMapper;
import com.comiyun.volunteer.volun.entity.Persion;
import com.comiyun.volunteer.volun.enums.PersionStatus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PersionMapper extends BaseMapper<Persion> {

    int updateStatus(@Param("id") Long id, @Param("status") PersionStatus status);

    void updateQrcode(@Param("qrCodeId") Long qrCodeId, @Param("id") Long id);

    void updateFamily(@Param("familyId") Long familyId, @Param("id") Long id);

    Persion getByQrcode(@Param("qrCodeId") Long qrCodeId);

    Persion getByOpenId(@Param("openId") String openId);

    public List<Persion> queryByFamilyId(@Param("familyId") Long familyId);
}
