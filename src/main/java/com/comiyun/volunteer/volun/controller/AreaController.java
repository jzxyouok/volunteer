package com.comiyun.volunteer.volun.controller;

import com.comiyun.core.constant.AppConst;
import com.comiyun.core.controller.BaseController;
import com.comiyun.core.util.AppUtil;
import com.comiyun.core.web.json.DataGrid;
import com.comiyun.core.web.json.Message;
import com.comiyun.core.web.json.QueryFilter;
import com.comiyun.core.web.util.RequestUtil;
import com.comiyun.volunteer.volun.entity.Area;
import com.comiyun.volunteer.volun.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/volun/area")
public class AreaController extends BaseController {
    @Autowired
    private AreaService areaService;

    @RequestMapping("manage")
    public ModelAndView manage() {
        ModelAndView mv = new ModelAndView();
        return mv;
    }

    @RequestMapping("selectList")
    @ResponseBody
    public List<Area> selectList() {
        List<Area> list = areaService.getList();
        return list;
    }

    @RequestMapping("list")
    @ResponseBody
    public DataGrid list() {
        QueryFilter filter = new QueryFilter(getRequest());
        List<Area> list = areaService.getList(filter);
        DataGrid dg = new DataGrid(list);
        return dg;
    }

    @RequestMapping("add")
    @ResponseBody
    public Message add(Area area) {
        Message msg = getMessage();
        area.setId(AppUtil.generateId());
        areaService.insert(area);
        msg.setMsg("保存成功！");
        log(AppConst.MODULE_VOLUN_MANAGE, AppConst.EVENT_ADD, "新增社区：" + area.getName());
        return msg;
    }

    @RequestMapping("edit")
    @ResponseBody
    public Message edit(Area area) {
        Message msg = getMessage();
        areaService.update(area);
        msg.setMsg("更新成功！");
        log(AppConst.MODULE_VOLUN_MANAGE, AppConst.EVENT_EDIT, "更新社区：" + area.getName());
        return msg;
    }

    @RequestMapping("del")
    @ResponseBody
    public Message del() {
        Message msg = getMessage();
        msg.setMsg("删除成功！");
        List<Long> ids = RequestUtil.getLongListByStr(getRequest(), "ids");
        log(AppConst.MODULE_VOLUN_MANAGE, AppConst.EVENT_DEL, "删除社区");
        areaService.batchDelete(ids);
        return msg;
    }
}
