package com.comiyun.volunteer.common.controller;

import com.comiyun.core.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统登录控制层
 *
 * @author ydwcn
 * @ClassName: LoginController
 * @date 2014-6-16 上午10:17:18
 */
@Controller
public class LoginController extends BaseController {

    @RequestMapping(value = "/login")
    public void loginGet() {
        return;
    }
}
