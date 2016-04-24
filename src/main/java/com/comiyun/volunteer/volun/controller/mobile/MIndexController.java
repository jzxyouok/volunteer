package com.comiyun.volunteer.volun.controller.mobile;

import com.comiyun.core.web.json.QueryFilter;
import com.comiyun.volunteer.common.controller.BaseMobileController;
import com.comiyun.volunteer.volun.entity.Activity;
import com.comiyun.volunteer.volun.entity.ExChange;
import com.comiyun.volunteer.volun.enums.ExChangeStatus;
import com.comiyun.volunteer.volun.service.ActivityService;
import com.comiyun.volunteer.volun.service.ExChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 手机端首页
 *
 * @author david
 */
@Controller
@RequestMapping("/")
public class MIndexController extends BaseMobileController {

    @Autowired
    private ActivityService activityService;
    @Autowired
    private ExChangeService exChangeService;

    @RequestMapping(value = {"mobile", "mobile/index"})
    public String index(Model model, HttpServletRequest request) {
        model.addAttribute(TITLE, "义工服务平台");

        //可参加活动的列表
        QueryFilter filter1 = new QueryFilter(request);
        filter1.setPage(0);
        filter1.setRows(5);
//		filter1.getParams().put("status", ActivityStatus.publish);
        List<Activity> actlist = activityService.getList(filter1);

        //可兑换的列表
        QueryFilter filter2 = new QueryFilter(request);
        filter2.setPage(0);
        filter2.setRows(5);
        filter2.getParams().put("status", ExChangeStatus.canEx);
        List<ExChange> exlist = exChangeService.getList(filter2);

        model.addAttribute("actlist", actlist);
        model.addAttribute("exlist", exlist);

        return "/mobile/index";
    }
}
