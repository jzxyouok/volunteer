package com.comiyun.weixin.persistence;

import com.comiyun.core.persistence.BaseMapper;
import com.comiyun.weixin.entity.WxQrCode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WxQrCodeMapper extends BaseMapper<WxQrCode> {

    Long nextSceneId();

    WxQrCode getByTicket(@Param("ticket") String ticket);

    List<WxQrCode> getByUrl(@Param("url") String url);
}
