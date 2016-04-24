package com.comiyun.volunteer.volun.persistence;

import com.comiyun.core.persistence.BaseMapper;
import com.comiyun.volunteer.volun.entity.Activity;
import com.comiyun.volunteer.volun.enums.ActivityStatus;
import org.apache.ibatis.annotations.Param;

public interface ActivityMapper extends BaseMapper<Activity> {

    void changestatus(@Param("id") Long id, @Param("status") ActivityStatus status);

    void updateQrcode(@Param("qrCodeId") Long qrCodeId, @Param("id") Long id);
}
