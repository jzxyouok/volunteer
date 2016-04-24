package com.comiyun.volunteer.volun.persistence;

import com.comiyun.core.persistence.BaseMapper;
import com.comiyun.volunteer.volun.entity.ExChange;
import com.comiyun.volunteer.volun.enums.ExChangeStatus;
import org.apache.ibatis.annotations.Param;

public interface ExChangeMapper extends BaseMapper<ExChange> {

    public void processEx(@Param("id") Long id, @Param("status") ExChangeStatus status);

    void updateQrcode(@Param("qrCodeId") Long qrCodeId, @Param("id") Long id);
}
