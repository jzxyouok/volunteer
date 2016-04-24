package com.comiyun.core.controller;

import com.comiyun.core.util.AppUtil;
import com.comiyun.core.web.json.Message;
import com.comiyun.core.web.util.RequestUtil;
import com.comiyun.core.web.util.SessionUtil;
import com.comiyun.volunteer.system.entity.SysLog;
import com.comiyun.volunteer.system.service.SysLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class BaseController {
    protected Logger logger = null;
    @Autowired
    private SysLogService sysLogService;

    public BaseController() {
        logger = LoggerFactory.getLogger(getClass().getName());
    }

    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
//		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
//		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    /**
     * 获取Request
     *
     * @return HttpServletRequest
     * @throws
     * @Title: getRequest
     */
    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    /**
     * 系统日志
     *
     * @param @param module 所属模块 @see AppConst
     * @param @param event 操作事件  @see AppConst
     * @param @param content 日志内容
     * @return void
     * @throws
     * @Title: log
     */
    public void log(String module, String event, String content) {
        String opUser = SessionUtil.getCurrentUser();
        String ip = RequestUtil.getIpAddr(getRequest());
        SysLog l = new SysLog();
        l.setId(AppUtil.generateId());
        l.setOwnmodule(module);
        l.setOpType(event);
        l.setOpUser(opUser);
        l.setOpDate(new Date());
        l.setOpContent(content);
        l.setOpIp(ip);
        sysLogService.insert(l);
    }

    /**
     * 获取返回消息
     *
     * @param @return
     * @return Message
     * @throws
     * @Title: msg
     */
    protected Message getMessage() {
        Message msg = new Message();
        return msg;
    }
}
