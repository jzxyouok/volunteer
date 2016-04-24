package com.comiyun.volunteer.system.controller;

import com.comiyun.core.controller.BaseController;
import com.comiyun.core.web.json.DataGrid;
import com.comiyun.core.web.json.QueryFilter;
import com.comiyun.volunteer.system.entity.SysLog;
import com.comiyun.volunteer.system.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 系统日志管理
 *
 * @author ydwcn
 * @ClassName: SysLogController
 * @date 2014-6-30 下午3:42:13
 */
@Controller
@RequestMapping("/system/syslog")
public class SysLogController extends BaseController {
    @Autowired
    private SysLogService sysLogService;

    @RequestMapping("manage")
    public ModelAndView manage() {
        ModelAndView mv = new ModelAndView();
        return mv;
    }

    @RequestMapping("list")
    @ResponseBody
    public DataGrid list() {
        QueryFilter filter = new QueryFilter(getRequest());
        List<SysLog> list = sysLogService.getList(filter);
        DataGrid dg = new DataGrid(list);
        return dg;
    }
}
