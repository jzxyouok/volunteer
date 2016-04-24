package com.comiyun.volunteer.system.controller;

import com.comiyun.core.controller.BaseController;
import com.comiyun.core.web.util.SessionUtil;
import com.comiyun.volunteer.system.entity.SysMenu;
import com.comiyun.volunteer.system.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 菜单
 *
 * @author ydwcn
 * @ClassName: SysMenuController
 * @date 2014-6-19 上午9:35:58
 */
@Controller
@RequestMapping("/system/sysmenu")
public class SysMenuController extends BaseController {
    @Autowired
    private SysMenuService sysMenuService;

    @RequestMapping("tree")
    @ResponseBody
    public List<SysMenu> tree() {
        Long userId = SessionUtil.getCurrentUserId();
        List<SysMenu> list = sysMenuService.getMenuTree(userId);
        return list;
    }

    /**
     * 获取当前租户的所有菜单
     *
     * @return List<SysMenu>
     * @throws
     * @Title: allTree
     */
    @RequestMapping("allTree")
    @ResponseBody
    public List<SysMenu> allTree() {
        List<SysMenu> list = sysMenuService.getAllMenuTree();
        return list;
    }
}
