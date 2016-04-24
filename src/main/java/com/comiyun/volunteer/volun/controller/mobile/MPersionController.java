package com.comiyun.volunteer.volun.controller.mobile;

import com.comiyun.core.exception.ServiceException;
import com.comiyun.core.util.AppUtil;
import com.comiyun.volunteer.common.controller.BaseMobileController;
import com.comiyun.volunteer.volun.entity.*;
import com.comiyun.volunteer.volun.enums.PersionStatus;
import com.comiyun.volunteer.volun.enums.VolunChannel;
import com.comiyun.volunteer.volun.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 手机端义工
 *
 * @author david
 */
@Controller
@RequestMapping("/mobile/persion")
public class MPersionController extends BaseMobileController {
    @Autowired
    private ExChangeDetailService exChangeDetailService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private PersionService persionService;
    @Autowired
    private IntegralService integralService;

    @Autowired
    private ActivityPersionService activityPersionService;

    @RequestMapping("umain")
    public String umain(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute(TITLE, "我的信息");

        String openId = getOpenId(request);
        if (StringUtils.isBlank(openId)) {
            return "redirect:" + getAuthUrl(request);
        }

        Persion persion = persionService.getByOpenId(openId);

        if (persion == null) {
            return "redirect:" + JOIN_VOLUN_URL;
        }

        model.addAttribute("u", persion);
        return "/mobile/umain";
    }

    /**
     * 注册成为义工页面
     *
     * @param request
     * @return
     */
    @RequestMapping("ujoin")
    public String ujoin(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute(TITLE, "成为志愿者");
        String openId = getOpenId(request);
        if (StringUtils.isBlank(openId)) {
            return "redirect:" + getAuthUrl(request);
        }
        List<Area> areas = areaService.getList();
        model.addAttribute("areas", areas);
        model.addAttribute("openId", openId);
        return "/mobile/ujoin";
    }

    /**
     * 注册义工
     *
     * @param persion
     * @return
     */
    @RequestMapping("doJoinvolun")
    public String doJoinvolun(Model model, HttpServletRequest request, Persion persion) {
        try {
            persion.setId(AppUtil.generateId());
            persion.setChannel(VolunChannel.wxreg);
            persion.setCreateBy("admin");
            persion.setCreateTime(new Date());
            persion.setStatus(PersionStatus.auditing);

            if (StringUtils.isBlank(persion.getWxOpenId())) {
                model.addAttribute("status", "注册异常,openId不存在");
            } else {
                Persion extp = persionService.getByOpenId(persion.getWxOpenId());
                if (extp == null) {
                    persionService.insert(persion);
                }
                return "redirect:/mobile/persion/uinfo?openId=" + persion.getWxOpenId();
            }
        } catch (ServiceException e) {
            logger.error("微信注册失败", e);
            model.addAttribute("status", "注册异常,请稍后再试");
        }

        return "/mobile/joinvolun_status";
    }

    /**
     * 义工信息
     *
     * @return
     */
    @RequestMapping("uinfo")
    public String uinfo(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute(TITLE, "基本信息");
        model.addAttribute(BACK_URL, "/mobile/persion/umain");
        String openId = getOpenId(request);
        if (StringUtils.isBlank(openId)) {
            return "redirect:" + getAuthUrl(request);
        }
        Persion persion = persionService.getByOpenId(openId);

        if (persion == null) {
            return "redirect:" + JOIN_VOLUN_URL;
        }

        model.addAttribute("name", persion.getName());
        model.addAttribute("areaName", persion.getAreaName());
        model.addAttribute("integral", persion.getIntegral());
        model.addAttribute("degree", persion.getDegree());
        model.addAttribute("mobile", persion.getMobile());
        model.addAttribute("statusDesc", persion.getStatusDesc());

        return "/mobile/uinfo";
    }

    /**
     * 积分明细
     *
     * @return
     */
    @RequestMapping("jfmx")
    public String jfmx(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute(TITLE, "积分明细");
        model.addAttribute(BACK_URL, "/mobile/persion/umain");

        String openId = getOpenId(request);
        if (StringUtils.isBlank(openId)) {
            return "redirect:" + getAuthUrl(request);
        }
        Persion persion = persionService.getByOpenId(openId);

        if (persion == null) {
            return "redirect:" + JOIN_VOLUN_URL;
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("persionId", persion.getId());
        List<Integral> list = integralService.getList(params);

        model.addAttribute("jfList", list);
        return "/mobile/ujfmx";
    }

    /**
     * 我的兑换
     *
     * @return
     */
    @RequestMapping("wddh")
    public String wddh(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute(TITLE, "我的兑换");
        model.addAttribute(BACK_URL, "/mobile/persion/umain");
        String openId = getOpenId(request);
        if (StringUtils.isBlank(openId)) {
            return "redirect:" + getAuthUrl(request);
        }
        Persion persion = persionService.getByOpenId(openId);

        if (persion == null) {
            return "redirect:" + JOIN_VOLUN_URL;
        }

        //兑换列表
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("persionId", persion.getId());
        List<ExChangeDetail> list = exChangeDetailService.getList(params);

        model.addAttribute("exlist", list);

        return "/mobile/uwddh";
    }

    /**
     * 活动列表
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("wdhd")
    public String wdhd(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute(TITLE, "我的活动");
        model.addAttribute(BACK_URL, "/mobile/persion/umain");
        String openId = getOpenId(request);
        if (StringUtils.isBlank(openId)) {
            return "redirect:" + getAuthUrl(request);
        }
        Persion persion = persionService.getByOpenId(openId);

        if (persion == null) {
            return "redirect:" + JOIN_VOLUN_URL;
        }

        //可参加活动的列表
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("persionId", persion.getId());
        List<ActivityPersion> list = activityPersionService.getList(params);

        model.addAttribute("actlist", list);

        return "/mobile/uwdhd";
    }

}
