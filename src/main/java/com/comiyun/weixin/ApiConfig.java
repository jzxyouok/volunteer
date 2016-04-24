package com.comiyun.weixin;


/**
 * 微信API接口
 *
 * @author david
 */
public class ApiConfig {

    /**
     * 微信公众号二维码
     */
    public static final String APP_QRCODE_URL = "http://open.weixin.qq.com/qr/code/?username=";

    /**
     * 获取访问Token GET
     */
    public static final String ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token";

    /**
     * 创建菜单 POST
     */
    public static final String MENU_CREATE = "https://api.weixin.qq.com/cgi-bin/menu/create";

    /**
     * 删除菜单 GET
     */
    public static final String MENU_DELETE = "https://api.weixin.qq.com/cgi-bin/menu/delete";

    /**
     * 创建二维码 POST
     */
    public static final String QRCODE_CREATE = "https://api.weixin.qq.com/cgi-bin/qrcode/create";

    /**
     * 获取二维码 GET
     */
    public static final String QRCODE_GET = "https://mp.weixin.qq.com/cgi-bin/showqrcode";
    /**
     * 获取JS API TICKET GET
     */
    public static final String JSAPI_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";

}
