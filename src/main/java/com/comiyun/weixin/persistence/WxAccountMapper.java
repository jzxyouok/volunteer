package com.comiyun.weixin.persistence;

import com.comiyun.core.persistence.BaseMapper;
import com.comiyun.weixin.entity.WxAccount;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface WxAccountMapper extends BaseMapper<WxAccount> {

    /**
     * 更新AccessToken
     *
     * @param id           公众号
     * @param accessToken  令牌
     * @param addTokenTime 添加时间
     */
    public void updateAccessToken(@Param("id") Long id, @Param("accessToken") String accessToken, @Param("addTokenTime") Date addTokenTime);

    /**
     * 更新JS API TICKET
     *
     * @param id              公众号
     * @param jsApiTicket     令牌
     * @param addJsTicketTime 添加时间
     */
    public void updateJsApiTicket(@Param("id") Long id, @Param("jsApiTicket") String jsApiTicket, @Param("addJsTicketTime") Date addJsTicketTime);

}
