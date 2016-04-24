package com.comiyun.volunteer.system.controller;

import com.comiyun.core.controller.BaseController;
import com.comiyun.core.web.json.Message;
import com.comiyun.core.web.util.RequestUtil;
import com.comiyun.volunteer.system.entity.SysUserRole;
import com.comiyun.volunteer.system.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author the7last
 * @ClassName: SysUserRoleController
 * @date 2014年8月4日 下午10:47:18
 */
@Controller
@RequestMapping("/system/sysuserrole")
public class SysUserRoleController extends BaseController {
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @RequestMapping("userRoleByUserId")
    @ResponseBody
    public List<SysUserRole> userRoleByUserId(SysUserRole sysUserRole) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", sysUserRole.getUserId());
        List<SysUserRole> list = sysUserRoleService.getList(map);
        return list;
    }

    @RequestMapping("editUserRole")
    @ResponseBody
    public Message editUserRole(SysUserRole sysUserRole) {
        Message msg = getMessage();

        String idsString = RequestUtil.getString(getRequest(), "ids");
        sysUserRoleService.editUserRole(sysUserRole, idsString);
        msg.setMsg("更新成功！");
        return msg;
    }
}
