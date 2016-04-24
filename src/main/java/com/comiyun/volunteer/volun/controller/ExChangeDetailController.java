package com.comiyun.volunteer.volun.controller;

import com.comiyun.core.constant.AppConst;
import com.comiyun.core.controller.BaseController;
import com.comiyun.core.web.json.DataGrid;
import com.comiyun.core.web.json.Message;
import com.comiyun.core.web.json.QueryFilter;
import com.comiyun.core.web.util.SessionUtil;
import com.comiyun.volunteer.system.entity.SysRole;
import com.comiyun.volunteer.system.persistence.SysUserMapper;
import com.comiyun.volunteer.volun.entity.ExChangeDetail;
import com.comiyun.volunteer.volun.service.ExChangeDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/volun/exchangedetail")
public class ExChangeDetailController extends BaseController {
    @Autowired
    private ExChangeDetailService exChangeDetailService;
    @Autowired
    private SysUserMapper sysUserMapper;

    @RequestMapping("manage")
    public ModelAndView manage() {
        ModelAndView mv = new ModelAndView();
        return mv;
    }

    @RequestMapping("list")
    @ResponseBody
    public DataGrid list() {
        QueryFilter filter = new QueryFilter(getRequest());
        Long userId = SessionUtil.getCurrentUserId();
        List<SysRole> rlist = sysUserMapper.getUserRoleList(userId);
        for (SysRole r : rlist) {
            if (AppConst.ROLE_PROVIDER_ID.compareTo(r.getId()) == 0) {
                filter.getParams().put("providerId", userId);
                break;
            }
        }
        List<ExChangeDetail> list = exChangeDetailService.getList(filter);
        DataGrid dg = new DataGrid(list);
        return dg;
    }

    /**
     * 兑换物品
     *
     * @param id
     * @param exCode
     * @return
     */
    @RequestMapping("ex")
    @ResponseBody
    public Message ex(Long id, String code) {
        Message msg = exChangeDetailService.ex(id, code);
        return msg;
    }
}
