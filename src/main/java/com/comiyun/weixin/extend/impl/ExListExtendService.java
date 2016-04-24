package com.comiyun.weixin.extend.impl;

import com.comiyun.weixin.entity.WxAccount;
import com.comiyun.weixin.enums.MenuType;
import com.comiyun.weixin.extend.ExtendService;
import com.comiyun.weixin.service.WxAccountService;
import com.comiyun.weixin.service.WxShortUrlService;
import com.comiyun.weixin.utils.WeiXinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 兑换列表
 *
 * @author david
 */
@Service
public class ExListExtendService implements ExtendService {

    @Autowired
    private WxAccountService wxAccountService;
    @Autowired
    private WxShortUrlService wxShortUrlService;

    @Override
    public void autoWireMenu(Map<String, Object> btn) {
        btn.put("type", MenuType.view);
        WxAccount account = wxAccountService.getAccount();
        String callback = account.getAuthCallBackUrl() + "/mobile/ex/list";
        String longUrl = WeiXinUtil.getAuthUrl(account.getAppId(), callback);
        String shortUrl = wxShortUrlService.shortUrl(longUrl);
        btn.put("url", shortUrl);
    }

    @Override
    public String service(String fromUserName, String toUserName) {
        return null;
    }

}
