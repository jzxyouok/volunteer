package com.comiyun.core.web.util;

import com.comiyun.core.constant.AppConst;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import java.util.Collection;

/**
 * Session工具类
 *
 * @author ydwcn
 * @ClassName: SessionUtil
 * @date 2014-6-20 上午9:56:56
 */
public class SessionUtil {
    /**
     * 获取当前请求的session，该session由shiro统一管理，成功登录后创建。 如果session不存在，自动创建。
     *
     * @return Session session对象
     */
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * 返回在应用中的唯一身份标识，如果是匿名登录，则返回null
     *
     * @return Object 身份标识对象
     */
    public static Object getPrincipal() {
        return SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 获取Shiro session设置session属性
     *
     * @param attrName  String 属性名称
     * @param attrValue Object 属性值对象
     */
    public static void setAttribute(String attrName, Object attrValue) {
        getSession().setAttribute(attrName, attrValue);
    }

    /**
     * 根据属性名称获取对应的session属性对象
     *
     * @param attrName String 属性名称
     * @param c        Class<T> 属性值对象所属类型
     * @return T 属性值对象
     */
    public static <T> T getAttribute(String attrName, Class<T> c) {
        Object attrValue = getSession().getAttribute(attrName);
        if (attrValue != null && c.isInstance(attrValue)) {
            return c.cast(attrValue);
        }
        return null;
    }

    /**
     * 根据属性名称获取对应的session属性对象
     *
     * @param attrName String 属性名称
     * @return T 属性值对象
     */
    public static Object getAttribute(String attrName) {
        Object attrValue = getSession().getAttribute(attrName);
        return attrValue;
    }

    /**
     * 根据属性名称获取对应的session属性对象（列表）
     *
     * @param attrName String 属性名称
     * @param c        Class<T> 属性值对象所属类型
     * @return Collection<T> 列表对象
     */
    @SuppressWarnings("unchecked")
    public static <T> Collection<T> getListAttribute(String attrName,
                                                     Collection<T> c) {
        Object attrValue = getSession().getAttribute(attrName);
        if (attrValue != null && c.getClass().isInstance(attrValue)) {
            return c.getClass().cast(attrValue);
        }
        return null;
    }

    public static Long getCurrentUserId() {
        Long userId = Long.valueOf(SessionUtil.getAttribute(AppConst.CURR_USER_ID).toString());
        return userId;
    }

    /**
     * 从上下文中取得当前用户
     *
     * @return Object 用户对象信息，需要由应用层转换成具体用户信息类。
     */
    public static String getCurrentUser() {
        String curruser = (String) SessionUtil.getAttribute(AppConst.CURR_USER_NAME);
        return curruser;
    }

    /**
     * 从session中移除给定名称的属性
     *
     * @param attrName String 要移除的属性名称
     */
    public static void removeAttribute(String attrName) {
        getSession().removeAttribute(attrName);
    }
}
