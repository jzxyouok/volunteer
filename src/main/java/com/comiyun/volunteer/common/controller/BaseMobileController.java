package com.comiyun.volunteer.common.controller;

import com.comiyun.core.constant.AppConst;
import com.comiyun.core.controller.BaseController;
import com.comiyun.core.util.AppUtil;
import com.comiyun.core.web.util.RequestUtil;
import com.comiyun.weixin.entity.WxAccount;
import com.comiyun.weixin.service.WxAccountService;
import com.comiyun.weixin.utils.WeiXinUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class BaseMobileController extends BaseController {
    @Autowired
    public WxAccountService wxAccountService;

    public static final String TITLE = "module_title";

    public static final String BACK_URL = "back_url";

    public static final String JOIN_VOLUN_URL = "/mobile/persion/ujoin";

    public static final String UNKOWN_PERSION = "unkown_persion";

    /**
     * 获取OPENID
     *
     * @param request
     * @return
     */
    public String getOpenId(HttpServletRequest request) {
        Object o = request.getSession(true).getAttribute(AppConst.WX_OPEN_ID);
        String openId = o != null ? o.toString() : null;

        if (StringUtils.isBlank(openId)) {
            boolean isdebug = AppUtil.getConfigBoolean("isdebug");
            if (isdebug) {
                openId = "ogTnQv3d1g5fR4QuiO73CMJaAxf0";
            } else {
                //微信授权代码
                String code = RequestUtil.getString(request, "code");
                //不存在code
                if (StringUtils.isBlank(code)) {
                    return null;
                }

                openId = wxAccountService.getOpenIdByWebCode(code);
            }
            request.getSession(true).setAttribute(AppConst.WX_OPEN_ID, openId);
        }
        return openId;
    }

    public String getAuthUrl(HttpServletRequest request) {
        WxAccount account = wxAccountService.getAccount();
        String url = request.getRequestURI();
        String queryString = request.getQueryString();
        if (StringUtils.isNotBlank(queryString)) {
            url = url + "?" + queryString;
        }
        String authUrl = account.getAuthCallBackUrl() + url;

        String longUrl = WeiXinUtil.getAuthUrl(account.getAppId(), authUrl);

        return longUrl;
    }
}
