package com.comiyun.core.shiro.realm;

import com.comiyun.core.constant.AppConst;
import com.comiyun.core.shiro.CustomUsernamePasswordToken;
import com.comiyun.core.util.RegexUtil;
import com.comiyun.core.web.util.SessionUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.JdbcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 用户认证域
 *
 * @author ydwcn
 * @ClassName: UserRealm
 * @date 2014-6-24 下午4:35:21
 */
public class UserRealm extends AuthorizingRealm {

    private static Logger logger = LoggerFactory.getLogger(UserRealm.class);

    protected DataSource dataSource;
    //租户账号认证SQL
    private static final String AUTH_USERNAME_SQL = "select id,username,email,mobile,password from sys_user t where t.username = ?";
    //手机账号认证SQL
    private static final String AUTH_MOBILE_SQL = "select id,username,email,mobile,password from sys_user t where t.mobile = ?";
    //邮箱账号认证SQL
    private static final String AUTH_EMAIL_SQL = "select id,username,email,mobile,password from sys_user t where t.email = ?";

    /**
     * 登录认证回调函数
     */
    @SuppressWarnings("all")
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        CustomUsernamePasswordToken token = (CustomUsernamePasswordToken) authcToken;
        String username = token.getUsername();
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }
        Connection conn = null;
        SimpleAuthenticationInfo info = null;

        try {
            conn = dataSource.getConnection();
            String uid = null; // 内部用户ID
            String uname = null; //内部用户账号
            String uemail = null; //内部用户邮箱
            String umobile = null; //内部用户手机
            String password = null; //密码

            PreparedStatement ps = null;
            ResultSet rs = null;
            try {

                //邮箱登录
                if (RegexUtil.isEmail(username)) {
                    ps = conn.prepareStatement(AUTH_EMAIL_SQL);
                    ps.setString(1, username);
                } else if (RegexUtil.isMobile(username)) {
                    ps = conn.prepareStatement(AUTH_MOBILE_SQL);
                    ps.setString(1, username);
                } else {
                    ps = conn.prepareStatement(AUTH_USERNAME_SQL);
                    ps.setString(1, username);
                }
                rs = ps.executeQuery();

                boolean foundResult = false;
                while (rs.next()) {
                    if (foundResult) {
                        throw new AuthenticationException("More than one user row found for user [" + username + "]. Usernames must be unique.");
                    }
                    uid = rs.getString("id");
                    uname = rs.getString("username");
                    uemail = rs.getString("email");
                    umobile = rs.getString("mobile");
                    password = rs.getString("password");
                }
            } finally {
                JdbcUtils.closeResultSet(rs);
                JdbcUtils.closeStatement(ps);
            }

            if (password == null) {
                throw new UnknownAccountException("No account found for user [" + username + "]");
            }
            info = new SimpleAuthenticationInfo(username, password.toCharArray(), getName());
            SessionUtil.setAttribute(AppConst.CURR_USER_ID, uid);
            SessionUtil.setAttribute(AppConst.CURR_USER_NAME, uname);
            SessionUtil.setAttribute(AppConst.CURR_USER_EMAIL, uemail);
            SessionUtil.setAttribute(AppConst.CURR_USER_MOBILE, umobile);
        } catch (SQLException e) {
            final String message = "There was a SQL error while authenticating user [" + username + "]";
            if (logger.isErrorEnabled()) {
                logger.error(message, e);
            }
            throw new AuthenticationException(message, e);
        } finally {
            JdbcUtils.closeConnection(conn);
        }

        return info;
    }

    /**
     * 授权查询回调函数
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {
        return null;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
