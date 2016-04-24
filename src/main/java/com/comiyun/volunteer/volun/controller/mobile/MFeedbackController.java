package com.comiyun.volunteer.volun.controller.mobile;

import com.comiyun.core.util.AppUtil;
import com.comiyun.core.web.json.Message;
import com.comiyun.volunteer.common.controller.BaseMobileController;
import com.comiyun.volunteer.system.entity.SysFeedback;
import com.comiyun.volunteer.system.service.SysFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 手机端反馈页面
 *
 * @author david
 */
@Controller
@RequestMapping("/mobile/feedback")
public class MFeedbackController extends BaseMobileController {
    @Autowired
    private SysFeedbackService sysFeedbackService;

    /**
     * 提交反馈页面
     *
     * @return
     */
    @RequestMapping("site")
    public String site(Model model) {
        model.addAttribute(TITLE, "问题反馈");
        return "/mobile/feedback";

    }

    /**
     * 提交反馈
     *
     * @return
     */
    @RequestMapping("tijiao")
    @ResponseBody
    public Message tijiao(String mobile, String email, String qq, String wxNum, String content) {
        Message msg = new Message();
        SysFeedback v = new SysFeedback();
        v.setId(AppUtil.generateId());
        v.setCreateTime(new Date());
        v.setEmail(email);
        v.setMobile(mobile);
        v.setQq(qq);
        v.setWxNum(wxNum);
        v.setContent(content);
        sysFeedbackService.insert(v);
        msg.setSuccess(true);
        msg.setMsg("反馈成功");
        return msg;
    }
}
