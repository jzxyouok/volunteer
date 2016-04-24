package com.comiyun.volunteer.system.controller;

import com.comiyun.core.constant.AppConst;
import com.comiyun.core.controller.BaseController;
import com.comiyun.core.web.json.DataGrid;
import com.comiyun.core.web.json.Message;
import com.comiyun.core.web.json.QueryFilter;
import com.comiyun.core.web.util.RequestUtil;
import com.comiyun.core.web.util.SessionUtil;
import com.comiyun.volunteer.system.entity.SysUser;
import com.comiyun.volunteer.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

/**
 * 用户管理
 *
 * @author ydwcn
 * @ClassName: SysUserController
 * @date 2014-6-30 下午4:04:21
 */
@Controller
@RequestMapping("/system/sysuser")
public class SysUserController extends BaseController {
    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("manage")
    public ModelAndView manage() {
        ModelAndView mv = new ModelAndView();
        return mv;
    }

    @RequestMapping("list")
    @ResponseBody
    public DataGrid list() {
        QueryFilter filter = new QueryFilter(getRequest());
        List<SysUser> list = sysUserService.getList(filter);
        DataGrid dg = new DataGrid(list);
        return dg;
    }

    @RequestMapping("add")
    @ResponseBody
    public Message add(SysUser user) {
        Message msg = getMessage();
        sysUserService.insert(user);
        msg.setMsg("保存成功！");
        log(AppConst.MODULE_SYSTEM, AppConst.EVENT_ADD, "新增用户：" + user.getUsername());
        return msg;
    }

    @RequestMapping("edit")
    @ResponseBody
    public Message edit(SysUser user) {
        Message msg = getMessage();
        String curruser = SessionUtil.getCurrentUser();
        user.setUpdateBy(curruser);
        user.setUpdateTime(new Date());
        sysUserService.update(user);
        msg.setMsg("更新成功！");
        log(AppConst.MODULE_SYSTEM, AppConst.EVENT_EDIT, "更新用户：" + user.getUsername());
        return msg;
    }

    @RequestMapping("del")
    @ResponseBody
    public Message del() {
        Message msg = getMessage();
        msg.setMsg("删除成功！");
        List<Long> ids = RequestUtil.getLongListByStr(getRequest(), "ids");
        log(AppConst.MODULE_SYSTEM, AppConst.EVENT_DEL, "删除用户");
        sysUserService.batchDelete(ids);
        return msg;
    }

    @RequestMapping("changePwd")
    @ResponseBody
    public Message changePwd(String oldPwd, String newPwd) {
        Long userId = SessionUtil.getCurrentUserId();
        Message msg = sysUserService.changePwd(userId, oldPwd, newPwd);
        log(AppConst.MODULE_SYSTEM, AppConst.EVENT_EDIT, "修改密码");

        return msg;
    }

    @RequestMapping("resetPwd")
    @ResponseBody
    public void resetPwd(long id) {
        sysUserService.resetPwd(id);
        log(AppConst.MODULE_SYSTEM, AppConst.EVENT_EDIT, "重置密码");
    }
}
