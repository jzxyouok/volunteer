package com.comiyun.weixin.controller;

import com.comiyun.core.controller.BaseController;
import com.comiyun.weixin.entity.WxExtend;
import com.comiyun.weixin.service.WxExtendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/weixin/extend")
public class WxExtendController extends BaseController {
    @Autowired
    private WxExtendService wxExtendService;

    @RequestMapping("list")
    @ResponseBody
    public List<WxExtend> list() {
        List<WxExtend> list = wxExtendService.getList();
        return list;
    }
}
