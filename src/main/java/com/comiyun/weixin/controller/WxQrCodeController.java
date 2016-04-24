package com.comiyun.weixin.controller;

import com.comiyun.core.controller.BaseController;
import com.comiyun.weixin.service.WxQrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 微信二维码
 *
 * @author david
 */
@Controller
@RequestMapping("/weixin/qrcode")
public class WxQrCodeController extends BaseController {
    @Autowired
    private WxQrCodeService wxQrCodeService;

    @RequestMapping("/get")
    public String get(Long id) {
        String url = wxQrCodeService.getQrCode(id);
        return "redirect:" + url;
    }

}
