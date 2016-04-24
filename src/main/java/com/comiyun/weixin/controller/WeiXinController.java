package com.comiyun.weixin.controller;

import com.comiyun.core.controller.BaseController;
import com.comiyun.weixin.entity.WxAccount;
import com.comiyun.weixin.service.WxAccountService;
import com.comiyun.weixin.service.WxChatService;
import com.comiyun.weixin.utils.WeiXinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/wx")
public class WeiXinController extends BaseController {
    @Autowired
    private WxAccountService wxAccountService;
    @Autowired
    private WxChatService wxChatService;

    /**
     * 微信消息 GET
     *
     * @param request
     * @param response
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     */
    @RequestMapping(value = "wechat", method = RequestMethod.GET)
    public void wechatGet(HttpServletRequest request, HttpServletResponse response,
                          String signature, String timestamp, String nonce, String echostr) throws IOException {
        logger.debug("微信接入配置参数---signature:{},timestamp:{},nonce:{},echostr:{}", signature, timestamp, nonce, echostr);

        WxAccount account = wxAccountService.getAccount();

        if (account != null && WeiXinUtil.checkSignature(account.getAppToken(), signature, timestamp, nonce)) {
            response.getWriter().print(echostr);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE); //406 不受理服务
        }
    }

    /**
     * 微信消息 POST
     *
     * @param request
     * @param response
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     */
    @RequestMapping(value = "wechat", method = RequestMethod.POST)
    public void wechatPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        wxChatService.chat(request, response);
    }

}
