package com.comiyun.core.shiro;

import com.comiyun.core.constant.BizCode;
import com.comiyun.core.shiro.exception.IncorrectCaptchaException;
import com.comiyun.core.util.AppUtil;
import com.comiyun.core.web.json.Message;
import com.comiyun.core.web.util.RequestUtil;
import com.comiyun.weixin.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 重写登录验证
 *
 * @author ydwcn
 * @ClassName: CustomFormAuthenticationFilter
 * @date 2014-6-20 上午9:48:37
 */
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {
    private static final Logger logger = LoggerFactory.getLogger(CustomFormAuthenticationFilter.class);
    public static final String DEFAULT_CAPTCHA_PARAM = "captchaCode";
    private String captchaParam = DEFAULT_CAPTCHA_PARAM;

    @Override
    protected CustomUsernamePasswordToken createToken(ServletRequest request,
                                                      ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);
        String captcha = getCaptcha(request);
        boolean rememberMe = isRememberMe(request);
        String host = RequestUtil.getIpAddr((HttpServletRequest) request);

        CustomUsernamePasswordToken token = new CustomUsernamePasswordToken(username,
                password.toCharArray(), rememberMe, host, captcha);
        return token;
    }

    @Override
    protected boolean executeLogin(ServletRequest request,
                                   ServletResponse response) throws Exception {
        CustomUsernamePasswordToken token = createToken(request, response);
        Subject subject = getSubject(request, response);
        try {
            Boolean captchaEnable = AppUtil.getConfigBoolean("login.captchaEnable");
            if (captchaEnable) {
                /*图形验证码验证*/
                doCaptchaValidate((HttpServletRequest) request, token);
            }
            boolean rememberMe = RequestUtil.getBoolean((HttpServletRequest) request, "rememberMe");

            token.setRememberMe(rememberMe);
            subject.login(token);//正常验证
            return onLoginSuccess(token, subject, request, response);
        } catch (AuthenticationException e) {
            logger.warn("{}登录失败--{}", token.getUsername(), e.getMessage());
            return onLoginFailure(token, e, request, response);
        }
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token,
                                     Subject subject, ServletRequest request, ServletResponse response)
            throws Exception {
        boolean contextRelative = false;

        WebUtils.issueRedirect(request, response, DEFAULT_SUCCESS_URL, null, contextRelative);
        return false;
    }

    @Override
    protected void saveRequestAndRedirectToLogin(ServletRequest request,
                                                 ServletResponse response) throws IOException {
        saveRequest(request);
        if (RequestUtil.isAjax((HttpServletRequest) request)
                || (request.getContentType() != null && request.getContentType().indexOf("x-www-form") != -1)) {
            Message msg = new Message();
            msg.setSuccess(false);
            msg.setCode(BizCode.login_timeout);
            msg.setMsg("登录超时，请重新登录");
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
            resp.setCharacterEncoding("UTF-8");
            resp.setHeader("Cache-Control", "no-cache, must-revalidate");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write(JsonUtil.toJson(msg));
        } else {
            redirectToLogin(request, response);
        }

    }

    @Override
    protected void setFailureAttribute(ServletRequest request,
                                       AuthenticationException ae) {
        super.setFailureAttribute(request, ae);
    }

    /**
     * 验证验证码
     *
     * @param request
     * @param token
     * @return void
     * @throws
     * @Title: doCaptchaValidate
     */
    private void doCaptchaValidate(HttpServletRequest request,
                                   CustomUsernamePasswordToken token) {
        String sessionCaptcha = token.getCaptchaCode();
        String captcha = (String) request.getSession().getAttribute("code");
        if (StringUtils.isNoneBlank(captcha)) {
            // 比对
            if (!captcha.equalsIgnoreCase(sessionCaptcha)) {
                throw new IncorrectCaptchaException("验证码错误！");
            }
        }
    }

    protected String getCaptcha(ServletRequest request) {
        return WebUtils.getCleanParam(request, getCaptchaParam());
    }


    public void setCaptchaParam(String captchaParam) {
        this.captchaParam = captchaParam;
    }

    public String getCaptchaParam() {
        return captchaParam;
    }

}
