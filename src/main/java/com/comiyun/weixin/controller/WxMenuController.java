package com.comiyun.weixin.controller;

import com.comiyun.core.constant.AppConst;
import com.comiyun.core.controller.BaseController;
import com.comiyun.core.web.json.Message;
import com.comiyun.core.web.util.RequestUtil;
import com.comiyun.weixin.entity.WxMenu;
import com.comiyun.weixin.enums.MenuType;
import com.comiyun.weixin.service.WxMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信公众号菜单
 *
 * @author david
 */
@Controller
@RequestMapping("/weixin/menu")
public class WxMenuController extends BaseController {
    @Autowired
    private WxMenuService wxMenuService;

    @RequestMapping("manage")
    public ModelAndView manage() {
        ModelAndView mv = new ModelAndView();
        return mv;
    }

    @RequestMapping("plist")
    @ResponseBody
    public List<WxMenu> plist() {
        List<WxMenu> list = wxMenuService.plist();
        return list;
    }

    @RequestMapping("menuTypeList")
    @ResponseBody
    public List<Map<String, String>> menuTypeList() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        Map<String, String> m1 = new HashMap<String, String>();
        m1.put("key", MenuType.click.toString());
        m1.put("name", MenuType.click.getText());
        list.add(m1);
        Map<String, String> m2 = new HashMap<String, String>();
        m2.put("key", MenuType.view.toString());
        m2.put("name", MenuType.view.getText());
        list.add(m2);
        Map<String, String> m3 = new HashMap<String, String>();
        m3.put("key", MenuType.scancode_push.toString());
        m3.put("name", MenuType.scancode_push.getText());
        list.add(m3);
        Map<String, String> m4 = new HashMap<String, String>();
        m4.put("key", MenuType.scancode_waitmsg.toString());
        m4.put("name", MenuType.scancode_waitmsg.getText());
        list.add(m4);
        Map<String, String> m5 = new HashMap<String, String>();
        m5.put("key", MenuType.media_id.toString());
        m5.put("name", MenuType.media_id.getText());
        list.add(m5);
        Map<String, String> m6 = new HashMap<String, String>();
        m6.put("key", MenuType.view_limited.toString());
        m6.put("name", MenuType.view_limited.getText());
        list.add(m6);

        Map<String, String> m7 = new HashMap<String, String>();
        m7.put("key", MenuType.extend.toString());
        m7.put("name", MenuType.extend.getText());
        list.add(m7);

        return list;
    }

    @RequestMapping("list")
    @ResponseBody
    public List<WxMenu> list() {
        List<WxMenu> list = wxMenuService.queryMenuTree();
        return list;
    }

    @RequestMapping("add")
    @ResponseBody
    public Message add(WxMenu wxMenu) {
        Message msg = getMessage();
        wxMenuService.insert(wxMenu);
        msg.setMsg("保存成功！");
        log(AppConst.MODULE_WEIXIN, AppConst.EVENT_ADD, "新增菜单：" + wxMenu.getName());
        return msg;
    }

    @RequestMapping("update")
    @ResponseBody
    public Message update(WxMenu wxMenu) {
        Message msg = getMessage();
        wxMenuService.update(wxMenu);
        msg.setMsg("更新成功！");
        log(AppConst.MODULE_WEIXIN, AppConst.EVENT_EDIT, "更新菜单：" + wxMenu.getName());
        return msg;
    }

    @RequestMapping("del")
    @ResponseBody
    public Message del() {
        Message msg = getMessage();
        msg.setMsg("删除成功！");
        List<Long> ids = RequestUtil.getLongListByStr(getRequest(), "ids");
        log(AppConst.MODULE_WEIXIN, AppConst.EVENT_DEL, "删除菜单");
        wxMenuService.batchDelete(ids);
        return msg;
    }

    /**
     * 同步菜单
     *
     * @return
     */
    @RequestMapping("sync")
    @ResponseBody
    public Message sync() {
        Message msg = new Message();
        wxMenuService.syncMenu();
        msg.setMsg("同步成功");
        return msg;
    }
}
