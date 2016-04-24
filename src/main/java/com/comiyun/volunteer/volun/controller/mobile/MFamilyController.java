package com.comiyun.volunteer.volun.controller.mobile;

import com.comiyun.core.web.json.Message;
import com.comiyun.volunteer.common.controller.BaseMobileController;
import com.comiyun.volunteer.volun.entity.Family;
import com.comiyun.volunteer.volun.entity.Persion;
import com.comiyun.volunteer.volun.service.FamilyService;
import com.comiyun.volunteer.volun.service.PersionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 家庭信息
 *
 * @author david
 */
@Controller
@RequestMapping("/mobile/family")
public class MFamilyController extends BaseMobileController {
    @Autowired
    private FamilyService familyService;
    @Autowired
    private PersionService persionService;

    /**
     * 创建家庭
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("tocreate")
    public String tocreate(Model model, HttpServletRequest request) {
        model.addAttribute(TITLE, "创建家庭");
        model.addAttribute(BACK_URL, "/mobile/family/info");
        return "/mobile/familycreate";
    }

    @RequestMapping("create")
    public String create(Model model, HttpServletRequest request, Family family) {
        String openId = getOpenId(request);
        if (StringUtils.isBlank(openId)) {
            return "redirect:" + getAuthUrl(request);
        }

        Persion persion = persionService.getByOpenId(openId);
        if (persion == null) {
            return "redirect:" + JOIN_VOLUN_URL;
        }

        family.setOwnerId(persion.getId());

        Message msg = familyService.create(family);

        if (!msg.getSuccess()) {
            return "redirect:/mobile/family/tocreate";
        } else {
            return "redirect:/mobile/family/info";
        }
    }

    /**
     * 加入家庭
     *
     * @param request
     * @return
     */
    @RequestMapping("join")
    @ResponseBody
    public Message join(HttpServletRequest request, String code) {
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
        msg = familyService.join(persion, code);
        return msg;
    }

    /**
     * 退出家庭
     *
     * @param request
     * @return
     */
    @RequestMapping("quit")
    @ResponseBody
    public Message quit(HttpServletRequest request) {
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
        msg = familyService.quit(persion);
        return msg;
    }


    @RequestMapping("info")
    public String info(Model model, HttpServletRequest request) {
        model.addAttribute(TITLE, "家庭信息");
        model.addAttribute(BACK_URL, "/mobile/persion/umain");

        String openId = getOpenId(request);
        if (StringUtils.isBlank(openId)) {
            return "redirect:" + getAuthUrl(request);
        }

        Persion persion = persionService.getByOpenId(openId);
        if (persion == null) {
            return "redirect:" + JOIN_VOLUN_URL;
        }

        //是否不存在家庭
        boolean nofamily = true;
        //是否为家庭管理员
        boolean isowner = false;

        Family family = familyService.get(persion.getFamilyId());
        List<Persion> members = new ArrayList<Persion>();
        if (family != null) {
            //家庭成员
            List<Persion> list = persionService.queryByFamilyId(persion.getFamilyId());

            if (persion.getId().compareTo(family.getOwnerId()) == 0) {
                isowner = true;
            }

//			isowner = false;
            if (list != null && list.size() != 0) {
                nofamily = false;
                for (int i = 0; i < list.size(); i++) {
                    Persion p = list.get(i);
                    if (family.getOwnerId().compareTo(p.getId()) == 0) {
                        p.setFamilyOwner("拥有者");
                    }
                    if (p.getId().compareTo(persion.getId()) == 0) {
                        p.setFamilyMe("自已");
                    } else {
                        p.setFamilyMe("成员");
                    }
                    members.add(p);
                }
            }
        }

        model.addAttribute("nofamily", nofamily);
        model.addAttribute("isowner", isowner);
        model.addAttribute("family", family);
        model.addAttribute("me", persion);
        model.addAttribute("members", members);

        return "/mobile/familyinfo";
    }

    /**
     * 积分转换
     *
     * @param targetId 目标
     * @param jifen    积分
     * @return
     */
    @RequestMapping("jfzh")
    @ResponseBody
    public Message jfzh(Long targetId, int jifen, HttpServletRequest request) {
        Message msg = getMessage();
        String openId = getOpenId(request);
        Persion persion = persionService.getByOpenId(openId);

        if (persion == null) {
            msg = getMessage();
            msg.setSuccess(false);
            msg.setCode(UNKOWN_PERSION);
            msg.setMsg("义工不存在");
            return msg;
        }

        if (jifen > persion.getIntegral()) {
            msg.setSuccess(false);
            msg.setMsg("积分不足");
            return msg;
        }

        msg = familyService.jfzh(persion.getId(), targetId, jifen);
        return msg;
    }
}
