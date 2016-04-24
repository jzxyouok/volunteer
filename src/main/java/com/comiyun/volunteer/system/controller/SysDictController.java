package com.comiyun.volunteer.system.controller;

import com.comiyun.core.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 系统字典
 *
 * @author ydwcn
 * @ClassName: SysDictController
 * @date 2014年7月9日 上午10:35:00
 */
@Controller
@RequestMapping("/system/sysdict")
public class SysDictController extends BaseController {
    @RequestMapping("manage")
    public ModelAndView manage() {
        ModelAndView mv = new ModelAndView();
        return mv;
    }
}
