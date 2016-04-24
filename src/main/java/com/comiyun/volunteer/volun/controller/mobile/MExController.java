package com.comiyun.volunteer.volun.controller.mobile;

import com.comiyun.core.web.json.Message;
import com.comiyun.core.web.util.RequestUtil;
import com.comiyun.volunteer.common.controller.BaseMobileController;
import com.comiyun.volunteer.system.entity.SysUser;
import com.comiyun.volunteer.system.service.SysUserService;
import com.comiyun.volunteer.volun.entity.ExChange;
import com.comiyun.volunteer.volun.entity.Persion;
import com.comiyun.volunteer.volun.enums.ExChangeStatus;
import com.comiyun.volunteer.volun.service.ExChangeService;
import com.comiyun.volunteer.volun.service.PersionService;
import com.comiyun.weixin.entity.WxQrCode;
import com.comiyun.weixin.enums.QrBizType;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 手机端兑换
 *
 * @author david
 */
@Controller
@RequestMapping("/mobile/ex")
public class MExController extends BaseMobileController {
    @Autowired
    private PersionService persionService;
    @Autowired
    private ExChangeService exChangeService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private WxQrCodeService wxQrCodeService;

    @RequestMapping("list")
    public String list(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute(TITLE, "积分兑换");
        String openId = getOpenId(request);
        if (StringUtils.isBlank(openId)) {
            return "redirect:" + getAuthUrl(request);
        }
        Persion persion = persionService.getByOpenId(openId);

        if (persion == null) {
            return "redirect:" + JOIN_VOLUN_URL;
        }

        //可兑换的列表
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("status", ExChangeStatus.canEx);
        List<ExChange> list = exChangeService.getList(params);

        model.addAttribute("perId", persion.getId());
        model.addAttribute("exlist", list);

        return "/mobile/exlist";
    }

    @RequestMapping("exinfo")
    public String exinfo(Model model, HttpServletRequest request) {
        model.addAttribute(TITLE, "兑换详情");
        model.addAttribute(BACK_URL, "/mobile/ex/list");

        Long exId = RequestUtil.getLong(request, "exId");
        ExChange ex = exChangeService.get(exId);

        model.addAttribute("ex", ex);

        //jssdk
        String jsapiticket = wxAccountService.getJsapiTicket();
        String domain = RequestUtil.getDomain(request);
        String code = RequestUtil.getString(request, "code");
        String url = domain + "/mobile/ex/exinfo?exId=" + exId;

        //是否来源于微信图文
        if (StringUtils.isNotBlank(code)) {
            url = url + "&code=" + code + "&state=comiyun";
        }
        String signature = WeiXinUtil.getJsApiSign(jsapiticket, url);
        model.addAttribute("signature", signature);
        return "/mobile/exInfo";
    }

    /**
     * 兑换物品
     *
     * @param exId
     * @param perId
     * @param exNum
     * @return
     */
    @RequestMapping("exchange")
    @ResponseBody
    public Message exchange(HttpServletRequest request, HttpServletResponse response, Long exId, int exNum) {
        Message msg = null;
        String openId = getOpenId(request);
        Persion persion = persionService.getByOpenId(openId);

        if (persion == null) {
            msg = getMessage();
            msg.setSuccess(false);
            msg.setCode(UNKOWN_PERSION);
            msg.setMsg("义工不存在");
            return msg;
        }
        msg = exChangeService.exchange(exId, persion.getId(), exNum);
        return msg;
    }

    @RequestMapping("dwex")
    @ResponseBody
    public Message dwex(HttpServletRequest request, HttpServletResponse response, Long exId, int exNum, Long persionId) {
        Message msg = null;
        Persion persion = persionService.get(persionId);

        if (persion == null) {
            msg = getMessage();
            msg.setSuccess(false);
            msg.setCode(UNKOWN_PERSION);
            msg.setMsg("义工不存在");
            return msg;
        }
        msg = exChangeService.exchange(exId, persion.getId(), exNum);
        return msg;
    }

    /**
     * 代为兑换
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("dwdh")
    @ResponseBody
    public Message dwdh(HttpServletRequest request, Long exId, String qrCodeUrl) {
        Message msg = getMessage();

        WxQrCode qrcode = wxQrCodeService.getByUrl(qrCodeUrl);

        if (qrcode == null || QrBizType.volun_persion != qrcode.getBizType()) {
            msg.setSuccess(false);
            msg.setMsg("无法识别义工二维码");
            return msg;
        }
        Persion persion = persionService.get(qrcode.getBizId());

        if (persion == null) {
            msg = getMessage();
            msg.setSuccess(false);
            msg.setCode(UNKOWN_PERSION);
            msg.setMsg("义工不存在");
            return msg;
        }

        msg.addDataItem("persionId", persion.getId());
        msg.addDataItem("persionName", persion.getName());

        return msg;
    }

    @RequestMapping("provider")
    public String provider(Model model, HttpServletRequest request) {
        model.addAttribute(TITLE, "服务商排行榜");
        model.addAttribute(BACK_URL, "/mobile/index");
        List<SysUser> list = sysUserService.queryExProviderList();
        model.addAttribute("list", list);
        return "/mobile/exprovider";
    }
}
