package com.comiyun.volunteer.volun.controller;

import com.comiyun.core.controller.BaseController;
import com.comiyun.core.web.json.DataGrid;
import com.comiyun.core.web.json.QueryFilter;
import com.comiyun.volunteer.volun.entity.Family;
import com.comiyun.volunteer.volun.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 家庭信息
 *
 * @author david
 */
@Controller
@RequestMapping("/volun/family")
public class FamilyController extends BaseController {
    @Autowired
    private FamilyService familyService;

    @RequestMapping("manage")
    public ModelAndView manage() {
        ModelAndView mv = new ModelAndView();
        return mv;
    }

    @RequestMapping("list")
    @ResponseBody
    public DataGrid list() {
        QueryFilter filter = new QueryFilter(getRequest());
        List<Family> list = familyService.getList(filter);
        DataGrid dg = new DataGrid(list);
        return dg;
    }
}
