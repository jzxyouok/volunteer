package com.comiyun.weixin.service;

import com.comiyun.weixin.ApiConfig;
import com.comiyun.weixin.AppConfig;
import com.comiyun.weixin.entity.WxAccount;
import com.comiyun.weixin.exception.WxException;
import com.comiyun.weixin.persistence.WxAccountMapper;
import com.comiyun.weixin.utils.HttpsUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.codehaus.jackson.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信帐号配置服务
 *
 * @author david
 */
@Service
public class WxAccountService {

    @Resource
    private WxAccountMapper wxAccountMapper;

    /**
     * 查询
     *
     * @return
     */
    public List<WxAccount> getList() {
        return wxAccountMapper.getList();
    }

    /**
     * 新增微信账号
     *
     * @param account
     */
    public void insert(WxAccount account) {
        wxAccountMapper.insert(account);
    }

    /**
     * 更新微信账号
     *
     * @param account
     */
    public void update(WxAccount account) {
        wxAccountMapper.update(account);
    }

    /**
     * 获取微信帐号配置
     *
     * @return
     */
    public WxAccount getAccount() {
        WxAccount account = wxAccountMapper.get(AppConfig.DEFAULT_ACCOUNT_ID);
        return account;
    }

    /**
     * 跟据网页CODE码获取微信用户信息
     *
     * @param code
     * @return
     */
    public String getOpenIdByWebCode(String code) {
        WxAccount account = getAccount();
        Map<String, String> params = new HashMap<String, String>();
        params.put("appid", account.getAppId());
        params.put("secret", account.getAppSecret());
        params.put("code", code);
        params.put("grant_type", "authorization_code");
        JsonNode json = HttpsUtil.sendGet("https://api.weixin.qq.com/sns/oauth2/access_token", params);
        String openid = json.path("openid").asText();

        return openid;
    }

    /**
     * 会取公众号访问Token
     *
     * @return
     */
    @Transactional
    public String getAccessToken() {
        String accessToken = "";
        WxAccount account = getAccount();
        String token = account.getAccessToken();
        Date addTime = account.getAddTokenTime();

        boolean remoteGet = false; //是否远程获取

        if (StringUtils.isNotBlank(token) && addTime != null) {
            Date expireTime = DateUtils.addMinutes(addTime, 100);
            Date now = new Date();
            //过期
            if (expireTime.compareTo(now) == -1) {
                remoteGet = true;
            } else {
                remoteGet = false;
            }
        } else {
            remoteGet = true;
        }

        if (remoteGet) {
            Map<String, String> params = new HashMap<String, String>();
            params.put("grant_type", "client_credential");
            params.put("appid", account.getAppId());
            params.put("secret", account.getAppSecret());
            JsonNode result = HttpsUtil.sendGet(ApiConfig.ACCESS_TOKEN, params);

            accessToken = result.path("access_token").asText();
            if (StringUtils.isBlank(accessToken)) {
                throw new WxException("获取Token异常");
            }
            wxAccountMapper.updateAccessToken(account.getId(), accessToken, new Date());
        } else {
            accessToken = account.getAccessToken();
        }
        return accessToken;
    }

    /**
     * 获取JsApi ticket
     *
     * @return
     */
    public String getJsapiTicket() {
        String jsApiTicket = "";
        WxAccount account = getAccount();

        String token = account.getJsApiTicket();
        Date addTime = account.getAddJsTicketTime();

        boolean remoteGet = false; //是否远程获取
        if (StringUtils.isNotBlank(token) && addTime != null) {
            Date expireTime = DateUtils.addMinutes(addTime, 100);
            Date now = new Date();
            //过期
            if (expireTime.compareTo(now) == -1) {
                remoteGet = true;
            } else {
                remoteGet = false;
            }
        } else {
            remoteGet = true;
        }

        if (remoteGet) {
            String accessToken = getAccessToken();
            Map<String, String> params = new HashMap<String, String>();
            params.put("access_token", accessToken);
            params.put("type", "jsapi");
            JsonNode result = HttpsUtil.sendGet(ApiConfig.JSAPI_TICKET, params);

            jsApiTicket = result.path("ticket").asText();
            if (StringUtils.isBlank(jsApiTicket)) {
                throw new WxException("获取Token异常");
            }
            wxAccountMapper.updateJsApiTicket(account.getId(), jsApiTicket, new Date());
        } else {
            jsApiTicket = account.getJsApiTicket();
        }
        return jsApiTicket;
    }

}
