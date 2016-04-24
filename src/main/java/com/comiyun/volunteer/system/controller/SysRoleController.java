package com.comiyun.volunteer.system.controller;

import com.comiyun.core.constant.AppConst;
import com.comiyun.core.controller.BaseController;
import com.comiyun.core.exception.ServiceException;
import com.comiyun.core.util.AppUtil;
import com.comiyun.core.web.json.DataGrid;
import com.comiyun.core.web.json.Message;
import com.comiyun.core.web.json.QueryFilter;
import com.comiyun.core.web.util.RequestUtil;
import com.comiyun.core.web.util.SessionUtil;
import com.comiyun.volunteer.system.entity.SysMenu;
import com.comiyun.volunteer.system.entity.SysRole;
import com.comiyun.volunteer.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

/**
 * 角色管理
 *
 * @author ydwcn
 * @ClassName: SysRoleController
 * @date 2014年7月9日 上午10:36:52
 */
@Controller
@RequestMapping("/system/sysrole")
public class SysRoleController extends BaseController {
    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping("manage")
    public ModelAndView manage() {
        ModelAndView mv = new ModelAndView();
        return mv;
    }

    @RequestMapping("list")
    @ResponseBody
    public DataGrid list() {
        QueryFilter filter = new QueryFilter(getRequest());
        List<SysRole> list = sysRoleService.getList(filter);
        DataGrid dg = new DataGrid(list);
        return dg;
    }

    @RequestMapping("add")
    @ResponseBody
    public Message add(SysRole role) {
        role.setId(AppUtil.generateId());
        role.setCreateBy(SessionUtil.getCurrentUser());
        role.setCreateTime(new Date());
        Message msg = getMessage();
        try {
            sysRoleService.insert(role);
            msg.setMsg("保存成功！");
            log(AppConst.MODULE_SYSTEM, AppConst.EVENT_ADD, "新增角色：" + role.getRoleName());
        } catch (ServiceException e) {
            msg.setMsg("保存失败！");
            e.printStackTrace();
        }
        return msg;
    }

    @RequestMapping("edit")
    @ResponseBody
    public Message edit(SysRole role) {
        Message msg = getMessage();
        try {
            String curruser = SessionUtil.getCurrentUser();
            role.setUpdateBy(curruser);
            role.setUpdateTime(new Date());
            sysRoleService.update(role);
            msg.setMsg("更新成功！");
            log(AppConst.MODULE_SYSTEM, AppConst.EVENT_EDIT, "更新角色：" + role.getRoleName());
        } catch (ServiceException e) {
            msg.setMsg("更新失败！");
            e.printStackTrace();
        }
        return msg;
    }

    @RequestMapping("del")
    @ResponseBody
    public Message del() {
        Message msg = getMessage();
        msg.setMsg("删除成功！");
        List<Long> ids = RequestUtil.getLongListByStr(getRequest(), "ids");
        log(AppConst.MODULE_SYSTEM, AppConst.EVENT_DEL, "删除角色");
        sysRoleService.batchDelete(ids);
        return msg;
    }

    /**
     * 获取角色关联的菜单
     *
     * @param roleId
     * @return
     */
    @RequestMapping("getRoleMenu")
    @ResponseBody
    public List<SysMenu> getRoleMenu(long roleId) {
        List<SysMenu> list = sysRoleService.getRoleMenu(roleId);
        return list;
    }

    @RequestMapping("getAllRole")
    @ResponseBody
    public List<SysRole> getAllRole() {
        List<SysRole> list = sysRoleService.getList();
        for (SysRole sysRole : list) {
            sysRole.setText(sysRole.getRoleName());
            sysRole.setIconCls("icon-sysrole");
        }
        return list;
    }
}
