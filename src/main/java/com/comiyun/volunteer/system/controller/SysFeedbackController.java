package com.comiyun.volunteer.system.controller;

import com.comiyun.core.controller.BaseController;
import com.comiyun.core.web.json.DataGrid;
import com.comiyun.core.web.json.QueryFilter;
import com.comiyun.volunteer.system.entity.SysFeedback;
import com.comiyun.volunteer.system.service.SysFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 问题反馈管理
 *
 * @author david
 */
@Controller
@RequestMapping("/system/feedback")
public class SysFeedbackController extends BaseController {
    @Autowired
    private SysFeedbackService sysFeedbackService;

    @RequestMapping("manage")
    public ModelAndView manage() {
        ModelAndView mv = new ModelAndView();
        return mv;
    }

    @RequestMapping("list")
    @ResponseBody
    public DataGrid list() {
        QueryFilter filter = new QueryFilter(getRequest());
        List<SysFeedback> list = sysFeedbackService.getList(filter);
        DataGrid dg = new DataGrid(list);
        return dg;
    }
}
