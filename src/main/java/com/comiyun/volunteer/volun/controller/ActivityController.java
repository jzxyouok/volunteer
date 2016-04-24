package com.comiyun.volunteer.volun.controller;

import com.comiyun.core.constant.AppConst;
import com.comiyun.core.controller.BaseController;
import com.comiyun.core.util.AppUtil;
import com.comiyun.core.web.json.DataGrid;
import com.comiyun.core.web.json.Message;
import com.comiyun.core.web.json.QueryFilter;
import com.comiyun.core.web.util.RequestUtil;
import com.comiyun.volunteer.volun.entity.Activity;
import com.comiyun.volunteer.volun.enums.ActivityStatus;
import com.comiyun.volunteer.volun.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 义工活动
 *
 * @author david
 */
@Controller
@RequestMapping("/volun/activity")
public class ActivityController extends BaseController {
    @Autowired
    private ActivityService activityService;

    @RequestMapping("manage")
    public ModelAndView manage() {
        ModelAndView mv = new ModelAndView();
        return mv;
    }

    @RequestMapping("list")
    @ResponseBody
    public DataGrid list() {
        QueryFilter filter = new QueryFilter(getRequest());
        List<Activity> list = activityService.getList(filter);
        DataGrid dg = new DataGrid(list);
        return dg;
    }

    @RequestMapping("add")
    @ResponseBody
    public Message add(Activity activity) {
        Message msg = getMessage();
        activity.setId(AppUtil.generateId());
        activity.setStatus(ActivityStatus.draft);
        activityService.insert(activity);
        msg.setMsg("保存成功！");
        log(AppConst.MODULE_ACTIVITY_MANAGE, AppConst.EVENT_ADD, "新增活动：" + activity.getName());
        return msg;
    }

    @RequestMapping("edit")
    @ResponseBody
    public Message edit(Activity activity) {
        Message msg = getMessage();
        activityService.update(activity);
        msg.setMsg("更新成功！");
        log(AppConst.MODULE_ACTIVITY_MANAGE, AppConst.EVENT_EDIT, "更新活动：" + activity.getName());
        return msg;
    }

    @RequestMapping("del")
    @ResponseBody
    public Message del() {
        Message msg = getMessage();
        msg.setMsg("删除成功！");
        List<Long> ids = RequestUtil.getLongListByStr(getRequest(), "ids");
        log(AppConst.MODULE_ACTIVITY_MANAGE, AppConst.EVENT_DEL, "删除活动");
        activityService.batchDelete(ids);
        return msg;
    }

    @RequestMapping("changestatus")
    @ResponseBody
    public Message changestatus(Long id, ActivityStatus status) {
        Message msg = getMessage();
        msg.setMsg("操作成功！");
        Activity activity = activityService.get(id);
        if (activity != null) {
            activityService.changestatus(id, status);
            if (status == ActivityStatus.draft) {
                log(AppConst.MODULE_ACTIVITY_MANAGE, AppConst.EVENT_DEL, "活动[" + activity.getName() + "]取消发布成功");
            }
            if (status == ActivityStatus.publish) {
                log(AppConst.MODULE_ACTIVITY_MANAGE, AppConst.EVENT_DEL, "活动[" + activity.getName() + "]发布成功");
            }
            if (status == ActivityStatus.started) {
                log(AppConst.MODULE_ACTIVITY_MANAGE, AppConst.EVENT_DEL, "活动[" + activity.getName() + "]已开始");
            }
            if (status == ActivityStatus.stop) {
                log(AppConst.MODULE_ACTIVITY_MANAGE, AppConst.EVENT_DEL, "活动[" + activity.getName() + "]已结束");
            }
        } else {
            msg.setSuccess(false);
            msg.setMsg("操作失败！");
        }

        return msg;
    }
}
