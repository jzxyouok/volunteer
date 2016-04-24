/*******************************************************************************
 * (C) Copyright cloudkd Corporation 2015 All Rights Reserved.
 * 产品名字   ： 云企网盘
 * 文件名字   ： CustMappingExceptionResolver.java
 * 文件作者   ：Administrator
 * 创建时间   ：2015年5月15日 下午16:19:08
 * 联系方式   ：admin@cloudkd.com.cn
 * *****************************************************************************
 * 注意： 本内容仅限于北京云创智企科技有限公司内部使用，禁止转发
 ******************************************************************************/
package com.comiyun.core.web.handler;

import com.comiyun.core.constant.BizCode;
import com.comiyun.core.exception.ServiceException;
import com.comiyun.core.web.json.Message;
import com.comiyun.weixin.exception.WxException;
import com.comiyun.weixin.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义异常处理机制
 * @author david
 *
 */
public class CustMappingExceptionResolver extends SimpleMappingExceptionResolver {
    Logger logger = LoggerFactory.getLogger(CustMappingExceptionResolver.class);

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request,
                                              HttpServletResponse response, Object handler, Exception ex) {
        if (!(handler instanceof HandlerMethod)) {
            return super.doResolveException(request, response, handler, ex);
        }
        HandlerMethod method = (HandlerMethod) handler;
        ResponseBody body = method.getMethodAnnotation(ResponseBody.class);
        if (body == null) {
            return super.doResolveException(request, response, handler, ex);
        }
        ModelAndView mv = new ModelAndView();
        //设置状态码
        //response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        //设置ContentType
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        //避免乱码
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache, must-revalidate");
        try {
            Message msg = new Message();
            msg.setSuccess(false);
            msg.setCode(BizCode.system_exception);
            if (ex instanceof WxException) {
                WxException e = (WxException) ex;
                msg.setMsg(e.getMessage());
            } else if (ex instanceof ServiceException) {
                msg.setMsg(ex.getMessage());
            } else if (ex instanceof DataIntegrityViolationException) {
                msg.setMsg("删除失败,数据已被引用");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                msg.setMsg("系统异常,请联系管理员");
                logger.error("system_exception", ex);
            }
            response.getWriter().write(JsonUtil.toJson(msg));
        } catch (IOException e) {
            logger.error("system_exception", e);
        }
        return mv;
    }

}
