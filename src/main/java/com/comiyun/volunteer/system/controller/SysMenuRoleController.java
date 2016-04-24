package com.comiyun.volunteer.system.controller;

import com.comiyun.core.controller.BaseController;
import com.comiyun.core.web.json.Message;
import com.comiyun.core.web.util.RequestUtil;
import com.comiyun.volunteer.system.entity.SysMenuRole;
import com.comiyun.volunteer.system.service.SysMenuRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author the7last
 * @ClassName: SysMenuRoleController
 * @date 2014年8月2日 下午4:01:27
 */
@Controller
@RequestMapping("/system/sysmenurole")
public class SysMenuRoleController extends BaseController {
    @Autowired
    private SysMenuRoleService sysMenuRoleService;

    @RequestMapping("menuRoleByRoleId")
    @ResponseBody
    public List<SysMenuRole> menuRoleByRoleId(SysMenuRole sysMenuRole) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("roleId", sysMenuRole.getRoleId());
        List<SysMenuRole> list = sysMenuRoleService.getList(map);
        return list;
    }

    @RequestMapping("editMenuRole")
    @ResponseBody
    public Message editMenuRole(SysMenuRole sysMenuRole) {
        Message msg = getMessage();

        String idsString = RequestUtil.getString(getRequest(), "ids");
        sysMenuRoleService.editMenuRole(sysMenuRole, idsString);
        msg.setMsg("更新成功！");
        return msg;
    }
}
