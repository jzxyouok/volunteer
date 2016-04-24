package com.comiyun.volunteer.common.controller;

import com.comiyun.core.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController extends BaseController {

    @RequestMapping({"/", "index"})
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }
}
