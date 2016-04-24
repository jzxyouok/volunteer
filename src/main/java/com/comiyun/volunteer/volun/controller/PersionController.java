package com.comiyun.volunteer.volun.controller;

import com.comiyun.core.constant.AppConst;
import com.comiyun.core.controller.BaseController;
import com.comiyun.core.util.AppUtil;
import com.comiyun.core.web.json.DataGrid;
import com.comiyun.core.web.json.Message;
import com.comiyun.core.web.json.QueryFilter;
import com.comiyun.core.web.util.RequestUtil;
import com.comiyun.core.web.util.SessionUtil;
import com.comiyun.volunteer.system.entity.SysParam;
import com.comiyun.volunteer.system.service.SysParamService;
import com.comiyun.volunteer.volun.entity.Persion;
import com.comiyun.volunteer.volun.enums.IntegralBizType;
import com.comiyun.volunteer.volun.enums.PersionStatus;
import com.comiyun.volunteer.volun.enums.VolunChannel;
import com.comiyun.volunteer.volun.service.IntegralService;
import com.comiyun.volunteer.volun.service.PersionService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/volun/persion")
public class PersionController extends BaseController {
    @Autowired
    private PersionService persionService;
    @Autowired
    private SysParamService sysParamService;
    @Autowired
    private IntegralService integralService;

    @RequestMapping("manage")
    public ModelAndView manage() {
        ModelAndView mv = new ModelAndView();
        return mv;
    }

    @RequestMapping("birthday")
    public ModelAndView birthday() {
        ModelAndView mv = new ModelAndView();
        return mv;
    }

    @RequestMapping("degree")
    public ModelAndView degree() {
        ModelAndView mv = new ModelAndView();
        return mv;
    }

    @RequestMapping("integral")
    public ModelAndView integral() {
        ModelAndView mv = new ModelAndView();
        return mv;
    }


    @RequestMapping("birthlist")
    @ResponseBody
    public DataGrid birthlist(Date startBirthday) {
        QueryFilter filter = new QueryFilter(getRequest());

        if (startBirthday != null) {
            int days = RequestUtil.getInt(getRequest(), "days", 0);
            Date endBirthday = DateUtils.addDays(startBirthday, days);
            endBirthday = DateUtils.setHours(endBirthday, 23);
            endBirthday = DateUtils.setMinutes(endBirthday, 59);
            endBirthday = DateUtils.setSeconds(endBirthday, 59);
            filter.getParams().put("endBirthday", endBirthday);
        }

        List<Persion> list = persionService.getList(filter);
        DataGrid dg = new DataGrid(list);
        return dg;
    }

    @RequestMapping("list")
    @ResponseBody
    public DataGrid list() {
        QueryFilter filter = new QueryFilter(getRequest());
        List<Persion> list = persionService.getList(filter);
        DataGrid dg = new DataGrid(list);
        return dg;
    }

    @RequestMapping("degreelist")
    @ResponseBody
    public DataGrid degreelist() {
        QueryFilter filter = new QueryFilter(getRequest());
        String defaultOder = filter.getParams().get("orderField") != null ? "degree desc," + filter.getParams().get("orderField").toString() : "degree desc";
        filter.getParams().put("orderField", defaultOder);

        List<Persion> list = persionService.getList(filter);
        DataGrid dg = new DataGrid(list);
        return dg;
    }

    @RequestMapping("integrallist")
    @ResponseBody
    public DataGrid integrallist() {
        QueryFilter filter = new QueryFilter(getRequest());
        String defaultOder = filter.getParams().get("orderField") != null ? "degree desc," + filter.getParams().get("orderField").toString() : "integral desc";
        filter.getParams().put("orderField", defaultOder);

        List<Persion> list = persionService.getList(filter);
        DataGrid dg = new DataGrid(list);
        return dg;
    }

    @RequestMapping("add")
    @ResponseBody
    public Message add(Persion persion) {
        Message msg = getMessage();
        persion.setId(AppUtil.generateId());
        persion.setCreateBy(SessionUtil.getCurrentUser());
        persion.setCreateTime(new Date());
        persion.setChannel(VolunChannel.sysadd);
        persion.setStatus(PersionStatus.audited);
        persionService.insert(persion);
        msg.setMsg("保存成功！");
        log(AppConst.MODULE_VOLUN_MANAGE, AppConst.EVENT_ADD, "新增义工：" + persion.getName());
        return msg;
    }

    @RequestMapping("edit")
    @ResponseBody
    public Message edit(Persion persion) {
        Message msg = getMessage();
        Persion p = persionService.get(persion.getId());

        p.setName(persion.getName());
        p.setMobile(persion.getMobile());
        p.setSex(persion.getSex());
        p.setBirthday(persion.getBirthday());
        p.setAreaId(persion.getAreaId());
        p.setUpdateBy(SessionUtil.getCurrentUser());
        p.setUpdateTime(new Date());

        persionService.update(p);
        msg.setMsg("更新成功！");
        log(AppConst.MODULE_VOLUN_MANAGE, AppConst.EVENT_EDIT, "更新义工：" + persion.getName());
        return msg;
    }

    @RequestMapping("del")
    @ResponseBody
    public Message del() {
        Message msg = getMessage();
        msg.setMsg("删除成功！");
        List<Long> ids = RequestUtil.getLongListByStr(getRequest(), "ids");
        log(AppConst.MODULE_VOLUN_MANAGE, AppConst.EVENT_DEL, "删除义工");
        persionService.batchDelete(ids);
        return msg;
    }

    @RequestMapping("audit")
    @ResponseBody
    public Message audit() {
        Message msg = getMessage();
        List<Long> ids = RequestUtil.getLongListByStr(getRequest(), "ids");
        for (Long id : ids) {
            int i = persionService.audit(id);
            if (i > 0) {
                Persion p = persionService.get(id);

                //微信注册积分
                SysParam sysParam = sysParamService.get(2L);
                String wxv = sysParam.getParamValue();
                int digit = 0;
                try {
                    digit = Integer.valueOf(wxv);
                } catch (NumberFormatException e) {
                    logger.error("格式转换错误 ::", e);
                }
                if (digit > 0) {
                    integralService.addIntegral(IntegralBizType.wxreg, null, "微信注册奖励积分", p.getId(), digit);
                }

                log(AppConst.MODULE_VOLUN_MANAGE, AppConst.EVENT_EDIT, "审核通过:" + p.getName());
            }
        }
        msg.setMsg("审核通过！");
        return msg;
    }

    @RequestMapping("logoff")
    @ResponseBody
    public Message logoff() {
        Message msg = getMessage();
        List<Long> ids = RequestUtil.getLongListByStr(getRequest(), "ids");
        for (Long id : ids) {
            int i = persionService.logoff(id);
            if (i > 0) {
                Persion p = persionService.get(id);
                log(AppConst.MODULE_VOLUN_MANAGE, AppConst.EVENT_EDIT, "义工注销:" + p.getName());
            }
        }
        msg.setMsg("注销成功！");
        return msg;
    }

    @RequestMapping("setadmin")
    @ResponseBody
    public Message setadmin(Long id) {
        Message msg = getMessage();
        Persion p = persionService.get(id);
        if (p != null) {
            p.setAdmin(!p.isAdmin());
            persionService.update(p);
            log(AppConst.MODULE_VOLUN_MANAGE, AppConst.EVENT_EDIT, "设置是否工作人员:" + p.getName());
        }
        msg.setMsg("设置成功！");
        return msg;
    }

    /**
     * 添加积分
     *
     * @param digit 积分
     * @return
     */
    @RequestMapping("tjjf")
    @ResponseBody
    public Message tjjf(Integer digit) {
        Message msg = getMessage();
        List<Long> list = RequestUtil.getLongListByStr(getRequest(), "ids");
        for (Long id : list) {
            integralService.addIntegral(IntegralBizType.hand, null, "系统添加积分", id, digit);
        }
        msg.setSuccess(true);
        msg.setMsg("操作成功");
        return msg;
    }

    /**
     * 扣减积分
     *
     * @param digit 积分
     * @return
     */
    @RequestMapping("kjjf")
    @ResponseBody
    public Message kjjf(Integer digit) {
        Message msg = getMessage();
        List<Long> list = RequestUtil.getLongListByStr(getRequest(), "ids");
        for (Long id : list) {
            integralService.addIntegral(IntegralBizType.hand, null, "系统添加积分", id, 0 - digit);
        }
        msg.setSuccess(true);
        msg.setMsg("操作成功");
        return msg;
    }
}
