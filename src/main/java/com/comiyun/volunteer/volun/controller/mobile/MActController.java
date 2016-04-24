package com.comiyun.volunteer.volun.controller.mobile;

import com.comiyun.core.web.json.Message;
import com.comiyun.core.web.util.RequestUtil;
import com.comiyun.volunteer.common.controller.BaseMobileController;
import com.comiyun.volunteer.volun.entity.Activity;
import com.comiyun.volunteer.volun.entity.ActivityPersion;
import com.comiyun.volunteer.volun.entity.Persion;
import com.comiyun.volunteer.volun.service.ActivityPersionService;
import com.comiyun.volunteer.volun.service.ActivityService;
import com.comiyun.volunteer.volun.service.PersionService;
import com.comiyun.weixin.entity.WxQrCode;
import com.comiyun.weixin.enums.QrBizType;
import com.comiyun.weixin.service.WxAccountService;
import com.comiyun.weixin.service.WxQrCodeService;
import com.comiyun.weixin.utils.WeiXinUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 手机端活动
 *
 * @author david
 */
@Controller
@RequestMapping("/mobile/act")
public class MActController extends BaseMobileController {

    @Autowired
    private PersionService persionService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private ActivityPersionService activityPersionService;
    @Autowired
    private WxAccountService wxAccountService;
    @Autowired
    private WxQrCodeService wxQrCodeService;

    /**
     * 活动列表
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("list")
    public String list(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute(TITLE, "全部活动");
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
//		params.put("status", ActivityStatus.publish);
        List<Activity> list = activityService.getList(params);

        model.addAttribute("actlist", list);

        return "/mobile/actList";
    }

    /**
     * 活动详细信息
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("actinfo")
    public String actinfo(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute(TITLE, "活动详情");
        model.addAttribute(BACK_URL, "/mobile/act/list");
        String openId = getOpenId(request);
        if (StringUtils.isBlank(openId)) {
            return "redirect:" + getAuthUrl(request);
        }
        Persion persion = persionService.getByOpenId(openId);

        if (persion == null) {
            return "redirect:" + JOIN_VOLUN_URL;
        }

        Long actId = RequestUtil.getLong(request, "actId");
        Activity act = activityService.get(actId);

        if (act != null) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("actId", act.getId());
            List<ActivityPersion> actPerList = activityPersionService.getList(params);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            model.addAttribute("name", act.getName());
            model.addAttribute("content", act.getContent());
            model.addAttribute("startTime", sdf.format(act.getStartTime()));
            model.addAttribute("endTime", sdf.format(act.getEndTime()));
            model.addAttribute("persionNum", act.getPersionNum());
            model.addAttribute("syNum", act.getPersionNum() - actPerList.size());
            model.addAttribute("perIntegral", act.getPerIntegral());
            model.addAttribute("areaName", act.getAreaName());
            model.addAttribute("statusDesc", act.getStatusDesc());

            model.addAttribute("actId", actId);
            model.addAttribute("isAdmin", persion.isAdmin());

            //jssdk
            String jsapiticket = wxAccountService.getJsapiTicket();
            String domain = RequestUtil.getDomain(request);
            String code = RequestUtil.getString(request, "code");
            String url = domain + "/mobile/act/actinfo?actId=" + actId;

            //是否来源于微信图文
            if (StringUtils.isNotBlank(code)) {
                url = url + "&code=" + code + "&state=comiyun";
            }
            String signature = WeiXinUtil.getJsApiSign(jsapiticket, url);
            model.addAttribute("signature", signature);
        }
        return "/mobile/actInfo";
    }

    /**
     * 活动报名
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("actbm")
    @ResponseBody
    public Message actbm(HttpServletRequest request, HttpServletResponse response, Long actId) {
        Message msg = getMessage();
        String openId = getOpenId(request);
        Persion persion = persionService.getByOpenId(openId);

        if (persion == null) {
            msg.setSuccess(false);
            msg.setMsg("请先注册为义工");
            return msg;
        }
        msg = activityPersionService.wxbm(actId, persion.getId());
        return msg;
    }

    /**
     * 活动签到
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("actqd")
    @ResponseBody
    public Message actqd(HttpServletRequest request, HttpServletResponse response, String qrCodeUrl) {
        Message msg = getMessage();
        String openId = getOpenId(request);
        Persion persion = persionService.getByOpenId(openId);

        if (persion == null) {
            msg.setSuccess(false);
            msg.setMsg("请先注册为义工");
            return msg;
        }

        WxQrCode qrcode = wxQrCodeService.getByUrl(qrCodeUrl);

        if (qrcode == null || QrBizType.volun_activity != qrcode.getBizType()) {
            msg.setSuccess(false);
            msg.setMsg("无法识别活动二维码");
            return msg;
        }
        msg = activityPersionService.wxqd(qrcode.getBizId(), persion.getId());
        return msg;
    }

    /**
     * 活动签退
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("actqt")
    @ResponseBody
    public Message actqt(HttpServletRequest request, HttpServletResponse response, String qrCodeUrl) {
        Message msg = getMessage();
        String openId = getOpenId(request);
        Persion persion = persionService.getByOpenId(openId);

        if (persion == null) {
            msg.setSuccess(false);
            msg.setMsg("请先注册为义工");
            return msg;
        }

        WxQrCode qrcode = wxQrCodeService.getByUrl(qrCodeUrl);

        if (qrcode == null || QrBizType.volun_activity != qrcode.getBizType()) {
            msg.setSuccess(false);
            msg.setMsg("无法识别活动二维码");
            return msg;
        }
        msg = activityPersionService.wxqt(qrcode.getBizId(), persion.getId());
        return msg;
    }

    /**
     * 活动报名
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("actdb")
    @ResponseBody
    public Message actdb(HttpServletRequest request, Long actId, String qrCodeUrl) {
        Message msg = getMessage();

        WxQrCode qrcode = wxQrCodeService.getByUrl(qrCodeUrl);

        if (qrcode == null || QrBizType.volun_persion != qrcode.getBizType()) {
            msg.setSuccess(false);
            msg.setMsg("无法识别义工二维码");
            return msg;
        }

        msg = activityPersionService.wxbm(actId, qrcode.getBizId());
        return msg;
    }

    /**
     * 活动签到
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("actdq")
    @ResponseBody
    public Message actdq(HttpServletRequest request, Long actId, String qrCodeUrl) {
        Message msg = getMessage();

        WxQrCode qrcode = wxQrCodeService.getByUrl(qrCodeUrl);

        if (qrcode == null || QrBizType.volun_persion != qrcode.getBizType()) {
            msg.setSuccess(false);
            msg.setMsg("无法识别义工二维码");
            return msg;
        }

        msg = activityPersionService.wxqd(actId, qrcode.getBizId());
        return msg;
    }

    /**
     * 活动签退
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("actdt")
    @ResponseBody
    public Message actdt(HttpServletRequest request, Long actId, String qrCodeUrl) {
        Message msg = getMessage();

        WxQrCode qrcode = wxQrCodeService.getByUrl(qrCodeUrl);

        if (qrcode == null || QrBizType.volun_persion != qrcode.getBizType()) {
            msg.setSuccess(false);
            msg.setMsg("无法识别义工二维码");
            return msg;
        }

        msg = activityPersionService.wxqt(actId, qrcode.getBizId());
        return msg;
    }
}
