package com.comiyun.core.shiro;

import com.comiyun.core.constant.AppConst;
import com.comiyun.core.util.AppUtil;
import com.comiyun.core.web.util.SessionUtil;
import com.comiyun.volunteer.system.entity.SysLog;
import com.comiyun.volunteer.system.persistence.SysLogMapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationListener;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * shiro验证监听器
 *
 * @author ydwcn
 * @ClassName: ShiroAuthenticationListener
 * @date 2014-6-23 上午11:22:32
 */
public class ShiroAuthenticationListener implements AuthenticationListener {

    private static final Logger logger = LoggerFactory.getLogger(ShiroAuthenticationListener.class);

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public void onFailure(AuthenticationToken token, AuthenticationException ex) {
        String account = token.getPrincipal().toString();
        String ip = getIp(token);
        logLogin(ip, "凭证{" + account + "}登录失败");
    }

    @Override
    public void onLogout(PrincipalCollection arg0) {
        logger.debug(arg0.getPrimaryPrincipal() + "退出成功");
    }

    @Override
    public void onSuccess(AuthenticationToken token, AuthenticationInfo info) {
        String account = info.getPrincipals().toString();
        String ip = getIp(token);
        logLogin(ip, "凭证{" + account + "}登录成功");
    }

    /**
     * 获取IP
     *
     * @param token
     * @return String
     * @throws
     * @Title: getIp
     */
    private String getIp(AuthenticationToken token) {
        //远程IP
        String ip = ((CustomUsernamePasswordToken) token).getHost();
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        return ip;
    }

    /**
     * 记录登录日志
     *
     * @param ip
     * @return void
     * @throws
     * @Title: logLogin
     */
    private void logLogin(String ip, String content) {
        String opUser = SessionUtil.getCurrentUser();
        SysLog l = new SysLog();
        l.setId(AppUtil.generateId());
        l.setOwnmodule(AppConst.MODULE_SYSTEM);
        l.setOpType(AppConst.EVENT_LOGIN);
        l.setOpUser(opUser);
        l.setOpDate(new Date());
        l.setOpContent(content);
        l.setOpIp(ip);
        sysLogMapper.insert(l);
    }
}
