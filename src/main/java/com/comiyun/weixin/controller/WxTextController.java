package com.comiyun.weixin.controller;

import com.comiyun.core.constant.AppConst;
import com.comiyun.core.controller.BaseController;
import com.comiyun.core.web.json.DataGrid;
import com.comiyun.core.web.json.Message;
import com.comiyun.core.web.json.QueryFilter;
import com.comiyun.core.web.util.RequestUtil;
import com.comiyun.core.web.util.SessionUtil;
import com.comiyun.weixin.entity.WxText;
import com.comiyun.weixin.service.WxTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

/**
 * 文本素材
 *
 * @author david
 */
@Controller
@RequestMapping("/weixin/text")
public class WxTextController extends BaseController {
    @Autowired
    private WxTextService wxTextService;

    @RequestMapping("manage")
    public ModelAndView manage() {
        ModelAndView mv = new ModelAndView();
        return mv;
    }

    @RequestMapping("list")
    @ResponseBody
    public DataGrid list() {
        QueryFilter filter = new QueryFilter(getRequest());
        List<WxText> list = wxTextService.getList(filter);
        DataGrid dg = new DataGrid(list);
        return dg;
    }

    @RequestMapping("add")
    @ResponseBody
    public Message add(WxText wxText) {
        Message msg = getMessage();
        wxTextService.insert(wxText);
        msg.setMsg("保存成功！");
        log(AppConst.MODULE_WEIXIN, AppConst.EVENT_ADD, "新增文本素材：" + wxText.getName());
        return msg;
    }

    @RequestMapping("update")
    @ResponseBody
    public Message update(WxText wxText) {
        Message msg = getMessage();
        String curruser = SessionUtil.getCurrentUser();
        wxText.setUpdateBy(curruser);
        wxText.setUpdateTime(new Date());
        wxTextService.update(wxText);
        msg.setMsg("更新成功！");
        log(AppConst.MODULE_WEIXIN, AppConst.EVENT_EDIT, "更新文本素材：" + wxText.getName());
        return msg;
    }

    @RequestMapping("del")
    @ResponseBody
    public Message del() {
        Message msg = getMessage();
        msg.setMsg("删除成功！");
        List<Long> ids = RequestUtil.getLongListByStr(getRequest(), "ids");
        log(AppConst.MODULE_WEIXIN, AppConst.EVENT_DEL, "删除文本素材");
        wxTextService.batchDelete(ids);
        return msg;
    }
}
