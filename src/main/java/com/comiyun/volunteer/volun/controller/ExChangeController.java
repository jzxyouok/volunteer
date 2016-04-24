package com.comiyun.volunteer.volun.controller;

import com.comiyun.core.constant.AppConst;
import com.comiyun.core.controller.BaseController;
import com.comiyun.core.util.AppUtil;
import com.comiyun.core.web.json.DataGrid;
import com.comiyun.core.web.json.Message;
import com.comiyun.core.web.json.QueryFilter;
import com.comiyun.core.web.util.RequestUtil;
import com.comiyun.core.web.util.SessionUtil;
import com.comiyun.volunteer.system.entity.SysRole;
import com.comiyun.volunteer.system.persistence.SysUserMapper;
import com.comiyun.volunteer.volun.entity.ExChange;
import com.comiyun.volunteer.volun.enums.ExChangeStatus;
import com.comiyun.volunteer.volun.service.ExChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/volun/exchange")
public class ExChangeController extends BaseController {
    @Autowired
    private ExChangeService exChangeService;
    @Autowired
    private SysUserMapper sysUserMapper;

    @RequestMapping("manage")
    public ModelAndView manage() {
        ModelAndView mv = new ModelAndView();
        return mv;
    }

    @RequestMapping("list")
    @ResponseBody
    public DataGrid list() {
        QueryFilter filter = new QueryFilter(getRequest());
        Long userId = SessionUtil.getCurrentUserId();

        List<SysRole> rlist = sysUserMapper.getUserRoleList(userId);
        for (SysRole r : rlist) {
            if (AppConst.ROLE_PROVIDER_ID.compareTo(r.getId()) == 0) {
                filter.getParams().put("providerId", userId);
                break;
            }
        }
        List<ExChange> list = exChangeService.getList(filter);
        DataGrid dg = new DataGrid(list);
        return dg;
    }

    @RequestMapping("add")
    @ResponseBody
    public Message add(ExChange exChange) {
        Message msg = getMessage();
        exChange.setId(AppUtil.generateId());
        exChange.setProviderId(SessionUtil.getCurrentUserId());
        exChange.setCreateBy(SessionUtil.getCurrentUser());
        exChange.setCreateTime(new Date());

        if (exChange.getNeedIntegral() == null && exChange.getNeedIntegral() <= 0) {
            msg.setMsg("所需积分不可小于0！");
            msg.setSuccess(false);
            return msg;
        }

        exChangeService.insert(exChange);
        msg.setMsg("保存成功！");
        log(AppConst.MODULE_EXCHANGE_MANAGE, AppConst.EVENT_ADD, "新增兑换品：" + exChange.getName());
        return msg;
    }

    @RequestMapping("edit")
    @ResponseBody
    public Message edit(ExChange exChange) {
        Message msg = getMessage();

        if (exChange.getNeedIntegral() == null && exChange.getNeedIntegral() <= 0) {
            msg.setMsg("所需积分不可小于0！");
            msg.setSuccess(false);
            return msg;
        }

        exChange.setUpdateBy(SessionUtil.getCurrentUser());
        exChange.setUpdateTime(new Date());
        exChangeService.update(exChange);
        msg.setMsg("更新成功！");
        log(AppConst.MODULE_EXCHANGE_MANAGE, AppConst.EVENT_EDIT, "更新兑换品：" + exChange.getName());
        return msg;
    }

    @RequestMapping("del")
    @ResponseBody
    public Message del() {
        Message msg = getMessage();
        msg.setMsg("删除成功！");
        List<Long> ids = RequestUtil.getLongListByStr(getRequest(), "ids");
        log(AppConst.MODULE_EXCHANGE_MANAGE, AppConst.EVENT_DEL, "删除兑换品");
        exChangeService.batchDelete(ids);
        return msg;
    }

    @RequestMapping("processEx")
    @ResponseBody
    public Message processEx(String status) {
        List<Long> ids = RequestUtil.getLongListByStr(getRequest(), "ids");
        Message msg = getMessage();
        ExChangeStatus s = status.equals(ExChangeStatus.canEx.toString()) ? ExChangeStatus.canEx : ExChangeStatus.stopEx;
        exChangeService.processEx(ids, s);
        if (s == ExChangeStatus.canEx) {
            log(AppConst.MODULE_EXCHANGE_MANAGE, AppConst.EVENT_EDIT, "开启兑换品");
            msg.setMsg("开启兑换成功！");
        } else {
            log(AppConst.MODULE_EXCHANGE_MANAGE, AppConst.EVENT_EDIT, "停止兑换品");
            msg.setMsg("停止兑换成功！");
        }
        return msg;
    }
}
