package com.comiyun.core.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 自定义Token
 *
 * @author ydwcn
 * @ClassName: CaptchaUsernamePasswordToken
 * @date 2014-6-20 上午9:43:29
 */
public class CustomUsernamePasswordToken extends UsernamePasswordToken {

    private static final long serialVersionUID = -886153992650415404L;

    private String captchaCode;//验证码字符串

    public CustomUsernamePasswordToken(String username, char[] password,
                                       boolean rememberMe, String host, String captchaCode) {
        super(username, password, rememberMe, host);
        this.captchaCode = captchaCode;
    }

    public String getCaptchaCode() {
        return captchaCode;
    }

    public void setCaptchaCode(String captchaCode) {
        this.captchaCode = captchaCode;
    }


}
