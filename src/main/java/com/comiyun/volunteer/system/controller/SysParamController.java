package com.comiyun.volunteer.system.controller;

import com.comiyun.core.constant.AppConst;
import com.comiyun.core.constant.DataType;
import com.comiyun.core.controller.BaseController;
import com.comiyun.core.exception.ServiceException;
import com.comiyun.core.util.AppUtil;
import com.comiyun.core.web.json.DataGrid;
import com.comiyun.core.web.json.Message;
import com.comiyun.core.web.json.QueryFilter;
import com.comiyun.core.web.util.RequestUtil;
import com.comiyun.core.web.util.SessionUtil;
import com.comiyun.volunteer.system.entity.SysParam;
import com.comiyun.volunteer.system.service.SysParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * 系统参数
 *
 * @author ydwcn
 * @ClassName: SysParamController
 * @date 2014年7月9日 上午10:35:57
 */
@Controller
@RequestMapping("/system/sysparam")
public class SysParamController extends BaseController {
    @Autowired
    private SysParamService sysParamService;

    @RequestMapping("manage")
    public ModelAndView manage() {
        ModelAndView mv = new ModelAndView();
        return mv;
    }

    @RequestMapping("list")
    @ResponseBody
    public DataGrid list() {
        QueryFilter filter = new QueryFilter(getRequest());
        List<SysParam> list = sysParamService.getList(filter);
        DataGrid dg = new DataGrid(list);
        return dg;
    }

    @RequestMapping("add")
    @ResponseBody
    public Message add(SysParam sysParam) {
        sysParam.setId(AppUtil.generateId());
        sysParam.setCreateBy(SessionUtil.getCurrentUser());
        sysParam.setCreateTime(new Date());
        Message msg = getMessage();
        try {
            sysParamService.insert(sysParam);
            msg.setMsg("保存成功！");
            log(AppConst.MODULE_SYSTEM, AppConst.EVENT_ADD, "新增系统参数：" + sysParam.getParamName());
        } catch (ServiceException e) {
            msg.setMsg("保存失败！");
            e.printStackTrace();
        }
        return msg;
    }

    @RequestMapping("edit")
    @ResponseBody
    public Message edit(SysParam sysParam) {
        Message msg = getMessage();
        try {
            String curruser = SessionUtil.getCurrentUser();
            sysParam.setUpdateBy(curruser);
            sysParam.setUpdateTime(new Date());
            sysParamService.update(sysParam);
            msg.setMsg("更新成功！");
            log(AppConst.MODULE_SYSTEM, AppConst.EVENT_EDIT, "更新系统参数：" + sysParam.getParamName());
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
        log(AppConst.MODULE_SYSTEM, AppConst.EVENT_DEL, "删除系统参数");
        sysParamService.batchDelete(ids);
        return msg;
    }

    @RequestMapping("datatypelist")
    @ResponseBody
    public List<Map<String, Object>> datatypelist() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> boo = new HashMap<String, Object>();
        boo.put("type", DataType.BOOLEAN);
        boo.put("name", DataType.BOOLEAN);

        Map<String, Object> in = new HashMap<String, Object>();
        in.put("type", DataType.INT);
        in.put("name", DataType.INT);

        Map<String, Object> str = new HashMap<String, Object>();
        str.put("type", DataType.STRING);
        str.put("name", DataType.STRING);

        Map<String, Object> date = new HashMap<String, Object>();
        date.put("type", DataType.DATE);
        date.put("name", DataType.DATE);

        list.add(boo);
        list.add(in);
        list.add(str);
        list.add(date);
        return list;
    }
}
