package com.comiyun.weixin.service;

import com.comiyun.core.util.AppUtil;
import com.comiyun.weixin.ApiConfig;
import com.comiyun.weixin.entity.WxQrCode;
import com.comiyun.weixin.enums.QrBizType;
import com.comiyun.weixin.persistence.WxQrCodeMapper;
import com.comiyun.weixin.utils.HttpsUtil;
import com.comiyun.weixin.utils.JsonUtil;
import org.codehaus.jackson.JsonNode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信二维码服务
 *
 * @author david
 */
@Service
public class WxQrCodeService {
    @Resource
    private WxQrCodeMapper wxQrCodeMapper;
    @Resource
    private WxAccountService wxAccountService;

    /**
     * 创建二维码
     *
     * @param bizType       业务来源
     * @param qrLimit       是否持久
     * @param expireSeconds 过期时间（秒）
     */
    public synchronized WxQrCode createQrCode(QrBizType bizType, Long bizId, boolean qrLimit, int expireSeconds) {
        //场景ID
        Long sceneId = null;
        String body = null;
        String accessToken = wxAccountService.getAccessToken();
        Map<String, String> params = new HashMap<String, String>();
        params.put("access_token", accessToken);
        if (qrLimit) {
            sceneId = wxQrCodeMapper.nextSceneId();
            body = convertParam(String.valueOf(sceneId), true, 0);
        } else {
            sceneId = AppUtil.generateId();
            body = convertParam(String.valueOf(sceneId), false, expireSeconds);
        }
        JsonNode json = HttpsUtil.sendPost(ApiConfig.QRCODE_CREATE, params, body);
        String ticket = json.path("ticket").asText();
        String url = json.path("url").asText();
        WxQrCode qr = new WxQrCode();
        qr.setId(AppUtil.generateId());
        qr.setQrLimit(qrLimit);
        qr.setSceneId(sceneId);
        qr.setTicket(ticket);
        qr.setUrl(url);
        qr.setBizType(bizType);
        qr.setBizId(bizId);

        wxQrCodeMapper.insert(qr);

        return qr;
    }

    public String getQrCode(Long id) {
        WxQrCode qrcode = wxQrCodeMapper.get(id);
        String ticket = null;
        if (qrcode != null) {
            ticket = qrcode.getTicket();
        }

        return ApiConfig.QRCODE_GET + "?ticket=" + ticket;
    }

    public WxQrCode getByUrl(String url) {
        List<WxQrCode> list = wxQrCodeMapper.getByUrl(url);
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public WxQrCode getByTicket(String ticket) {
        return wxQrCodeMapper.getByTicket(ticket);
    }


    /**
     * 组装参数
     *
     * @param qrLimit
     * @param expireSeconds
     * @param sceneId
     * @return
     */
    private String convertParam(String sceneId, boolean qrLimit, int expireSeconds) {
        Map<String, Object> param = new HashMap<String, Object>();
        String actionName = "";

        if (qrLimit) {
            actionName = "QR_LIMIT_SCENE";
        } else {
            actionName = "QR_SCENE";
            param.put("expire_seconds", expireSeconds);
        }

        param.put("action_name", actionName);
        Map<String, Object> action = new HashMap<String, Object>();
        Map<String, Object> scene = new HashMap<String, Object>();
        scene.put("scene_id", sceneId);
        action.put("scene", scene);
        param.put("action_info", action);

        return JsonUtil.toJson(param);
    }

}
