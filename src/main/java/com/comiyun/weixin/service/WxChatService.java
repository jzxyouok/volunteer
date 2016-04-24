package com.comiyun.weixin.service;

import com.comiyun.core.util.AppUtil;
import com.comiyun.volunteer.volun.entity.Persion;
import com.comiyun.volunteer.volun.service.PersionService;
import com.comiyun.weixin.dto.ReplyTextMsg;
import com.comiyun.weixin.entity.WxExtend;
import com.comiyun.weixin.entity.WxMenu;
import com.comiyun.weixin.entity.WxQrCode;
import com.comiyun.weixin.enums.MenuType;
import com.comiyun.weixin.enums.MsgType;
import com.comiyun.weixin.enums.QrBizType;
import com.comiyun.weixin.extend.ExtendService;
import com.comiyun.weixin.utils.WeiXinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * 微信会话服务
 *
 * @author david
 */
@Service
public class WxChatService {
    private static final Logger logger = LoggerFactory.getLogger("");

    @Autowired
    private WxAccountService wxAccountService;
    @Autowired
    private WxQrCodeService wxQrCodeService;
    @Autowired
    private PersionService persionService;
    @Autowired
    private WxMenuService wxMenuService;
    @Autowired
    private WxExtendService wxExtendService;

    public void chat(HttpServletRequest request, HttpServletResponse response) {

        String respMsg = "";

        Map<String, String> param = WeiXinUtil.parseXml(request);
        //发送方帐号（open_id）
        String fromUserName = param.get("FromUserName");
        //公众帐号
        String toUserName = param.get("ToUserName");
        //消息类型
        String msgType = param.get("MsgType");

        if (MsgType.text.toString().equals(msgType)) {
            ReplyTextMsg text = new ReplyTextMsg(toUserName, fromUserName, "回复文本消息");
            respMsg = text.serializeToXml();
        } else if (MsgType.image.toString().equals(msgType)) {

        } else if (MsgType.voice.toString().equals(msgType)) {

        } else if (MsgType.video.toString().equals(msgType)) {

        } else if (MsgType.shortvideo.toString().equals(msgType)) {

        } else if (MsgType.location.toString().equals(msgType)) {

        } else if (MsgType.link.toString().equals(msgType)) {

        } else if (MsgType.event.toString().equals(msgType)) {
            String event = param.get("Event").toLowerCase();
            //关注事件
            if ("subscribe".equals(event)) {
                ReplyTextMsg text = new ReplyTextMsg(toUserName, fromUserName, "您好，欢迎关注");
                respMsg = text.serializeToXml();
            }
            //取消关注事件
            else if ("unsubscribe".equals(event)) {

            }
            //click
            else if ("click".equals(event)) {
                String eventKey = param.get("EventKey");
                WxMenu wxMenu = wxMenuService.getByKey(eventKey);
                if (wxMenu != null) {
                    //扩展
                    if (MenuType.extend == wxMenu.getType()) {
                        Long extendId = wxMenu.getExtendId();
                        WxExtend extend = wxExtendService.get(extendId);

                        ExtendService extendService = (ExtendService) AppUtil.getBean(extend.getService());
                        respMsg = extendService.service(fromUserName, toUserName);
                    }
                } else {
                    ReplyTextMsg text = new ReplyTextMsg(toUserName, fromUserName, "您好，未解析响应");
                    respMsg = text.serializeToXml();
                }

            }
            //扫描二维码事件
            else if ("scan".equals(event)) {
                String ticket = param.get("Ticket");
                WxQrCode qrcode = wxQrCodeService.getByTicket(ticket);
                if (qrcode == null) {
                    ReplyTextMsg text = new ReplyTextMsg(toUserName, fromUserName, "二维码不能识别");
                    respMsg = text.serializeToXml();
                } else {
                    if (QrBizType.volun_persion == qrcode.getBizType()) {
                        Persion p = persionService.getByQrcode(qrcode.getId());
                        ReplyTextMsg text = new ReplyTextMsg(toUserName, fromUserName, "您的身份是：" + p.getName());
                        respMsg = text.serializeToXml();
                    }
                }
            }
        }

        PrintWriter out = null;
        try {
            logger.info(respMsg);
            out = response.getWriter();
            out.print(respMsg);
        } catch (IOException e) {
            logger.error("响应微信服务异常:", e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}
