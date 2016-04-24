package com.comiyun.core.constant;

/**
 * 系统常量池
 *
 * @author ydwcn
 * @ClassName: AppConst
 * @date 2014-6-23 上午11:26:32
 */
public class AppConst {

    public static final String CURR_USER_ID = "curr_user_id";
    public static final String CURR_USER_NAME = "curr_user_name";
    public static final String CURR_USER_EMAIL = "curr_user_email";
    public static final String CURR_USER_MOBILE = "curr_user_mobile";

    public static final String WX_OPEN_ID = "wx_open_id";

    /**
     * 菜单树根节点
     */
    public static final long MENU_ROOT_ID = -1;

    public static final String MODULE_SYSTEM = "系统管理";

    public static final String MODULE_WEIXIN = "微信管理";

    public static final String MODULE_VOLUN_MANAGE = "义工管理";

    public static final String MODULE_ACTIVITY_MANAGE = "活动管理";

    public static final String MODULE_EXCHANGE_MANAGE = "兑换品管理";

    public static final String EVENT_LOGIN = "登录";
    public static final String EVENT_ADD = "新增";
    public static final String EVENT_EDIT = "编辑";
    public static final String EVENT_DEL = "删除";

    public static final Long USER_ADMIN_ID = 1L; //超级管理员
    public static final Long ROLE_ADMIN_ID = 1L; //超级管理员角色
    public static final Long ROLE_PROVIDER_ID = 2L;//服务商角色
}
