package com.comiyun.weixin.controller;

import com.comiyun.core.constant.AppConst;
import com.comiyun.core.controller.BaseController;
import com.comiyun.core.web.json.DataGrid;
import com.comiyun.core.web.json.Message;
import com.comiyun.core.web.util.RequestUtil;
import com.comiyun.weixin.ApiConfig;
import com.comiyun.weixin.entity.WxAccount;
import com.comiyun.weixin.service.WxAccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信公众号配置
 *
 * @author david
 */
@Controller
@RequestMapping("/weixin/account")
public class WxAccountController extends BaseController {
    @Autowired
    private WxAccountService wxAccountService;

    @RequestMapping("manage")
    public ModelAndView manage() {
        ModelAndView mv = new ModelAndView();
        return mv;
    }

    @RequestMapping("info")
    @ResponseBody
    public DataGrid info(HttpServletRequest request) {
        WxAccount account = wxAccountService.getAccount();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        String domain = RequestUtil.getDomain(request);
        Map<String, String> wxUrl = new HashMap<String, String>();
        wxUrl.put("name", "接入地址(WeiXinUrl)");
        wxUrl.put("key", "wxUrl");
        wxUrl.put("value", domain + "/wx/wechat");
        list.add(wxUrl);

        Map<String, String> qrCodeUrl = new HashMap<String, String>();
        qrCodeUrl.put("name", "公众号二维码(QrCode)");
        qrCodeUrl.put("key", "qrCodeUrl");
        String qrcode = StringUtils.isNotBlank(account.getWxNumber()) ? (ApiConfig.APP_QRCODE_URL + StringUtils.trim(account.getWxNumber())) : "";
        qrCodeUrl.put("value", "<img style=\"width:111px;height: 111px;\" src='" + qrcode + "' />");
        list.add(qrCodeUrl);


        Map<String, String> appId = new HashMap<String, String>();
        appId.put("name", "应用ID(AppId)");
        appId.put("key", "appId");
        appId.put("value", account.getAppId());
        appId.put("editor", "text");
        list.add(appId);
        Map<String, String> appSecret = new HashMap<String, String>();
        appSecret.put("name", "应用密钥(AppSecret)");
        appSecret.put("key", "appSecret");
        appSecret.put("value", account.getAppSecret());
        appSecret.put("editor", "text");
        list.add(appSecret);
        Map<String, String> appToken = new HashMap<String, String>();
        appToken.put("name", "应用令牌(Token)");
        appToken.put("key", "appToken");
        appToken.put("value", account.getAppToken());
        appToken.put("editor", "text");
        list.add(appToken);
        Map<String, String> wxNumber = new HashMap<String, String>();
        wxNumber.put("name", "微信公众号");
        wxNumber.put("key", "wxNumber");
        wxNumber.put("value", account.getWxNumber());
        wxNumber.put("editor", "text");
        list.add(wxNumber);
        Map<String, String> wxId = new HashMap<String, String>();
        wxId.put("name", "微信原始ID");
        wxId.put("key", "wxId");
        wxId.put("value", account.getWxId());
        wxId.put("editor", "text");
        list.add(wxId);

        Map<String, String> authCallBackUrl = new HashMap<String, String>();
        authCallBackUrl.put("name", "授权回调页面域名");
        authCallBackUrl.put("key", "authCallBackUrl");
        authCallBackUrl.put("value", account.getAuthCallBackUrl());
        authCallBackUrl.put("editor", "text");
        list.add(authCallBackUrl);
        DataGrid dg = new DataGrid(list);
        return dg;
    }

    /**
     * 添加微信帐号
     *
     * @param account
     * @return
     */
    @RequestMapping("add")
    @ResponseBody
    public Message add(WxAccount account) {
        Message msg = new Message();
        wxAccountService.insert(account);
        msg.setMsg("保存成功！");
        return msg;
    }

    /**
     * 更新微信帐号
     *
     * @param appId
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public Message update(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        WxAccount account = wxAccountService.getAccount();
        boolean change = false;
        if (params.containsKey("appId")) {
            account.setAppId(request.getParameter("appId"));
            change = true;
        }
        if (params.containsKey("appSecret")) {
            account.setAppSecret(request.getParameter("appSecret"));
            change = true;
        }
        if (params.containsKey("appToken")) {
            account.setAppToken(request.getParameter("appToken"));
            change = true;
        }
        if (params.containsKey("wxNumber")) {
            account.setWxNumber(request.getParameter("wxNumber"));
            change = true;
        }
        if (params.containsKey("wxId")) {
            account.setWxId(request.getParameter("wxId"));
            change = true;
        }
        if (params.containsKey("authCallBackUrl")) {
            account.setAuthCallBackUrl(request.getParameter("authCallBackUrl"));
            change = true;
        }
        if (change) {
            wxAccountService.update(account);
            log(AppConst.MODULE_WEIXIN, AppConst.EVENT_EDIT, "编辑公众号配置");
        }
        Message msg = new Message();
        msg.setMsg("更新成功！");
        return msg;
    }
}
