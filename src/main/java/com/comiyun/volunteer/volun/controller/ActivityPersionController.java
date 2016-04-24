package com.comiyun.volunteer.volun.controller;

import com.comiyun.core.constant.AppConst;
import com.comiyun.core.controller.BaseController;
import com.comiyun.core.web.json.DataGrid;
import com.comiyun.core.web.json.Message;
import com.comiyun.core.web.json.QueryFilter;
import com.comiyun.core.web.util.RequestUtil;
import com.comiyun.volunteer.volun.entity.ActivityPersion;
import com.comiyun.volunteer.volun.enums.ActivityPersionStatus;
import com.comiyun.volunteer.volun.service.ActivityPersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/volun/activitypersion")
public class ActivityPersionController extends BaseController {
    @Autowired
    private ActivityPersionService activityPersionService;

    @RequestMapping("manage")
    public ModelAndView manage() {
        ModelAndView mv = new ModelAndView();
        return mv;
    }

    @RequestMapping("list")
    @ResponseBody
    public DataGrid list() {
        QueryFilter filter = new QueryFilter(getRequest());
        List<ActivityPersion> list = activityPersionService.getList(filter);
        DataGrid dg = new DataGrid(list);
        return dg;
    }

    /**
     * 后台报名
     *
     * @return
     */
    @RequestMapping("adminbm")
    @ResponseBody
    public Message adminbm(Long actId) {
        List<Long> ids = RequestUtil.getLongListByStr(getRequest(), "ids");
        log(AppConst.MODULE_ACTIVITY_MANAGE, AppConst.EVENT_EDIT, "活动报名");
        Message msg = activityPersionService.adminbm(actId, ids);
        return msg;
    }

    @RequestMapping("changestatus")
    @ResponseBody
    public Message changestatus(ActivityPersionStatus status) {
        Message msg = getMessage();
        msg.setMsg("操作成功！");
        List<Long> ids = RequestUtil.getLongListByStr(getRequest(), "ids");
        log(AppConst.MODULE_ACTIVITY_MANAGE, AppConst.EVENT_EDIT, "参加确认");
        activityPersionService.changestatus(ids, status);
        return msg;
    }
}
