package com.comiyun.weixin.extend.impl;

import com.comiyun.volunteer.volun.entity.Activity;
import com.comiyun.volunteer.volun.service.ActivityService;
import com.comiyun.weixin.dto.NewsItem;
import com.comiyun.weixin.dto.ReplyNewsMsg;
import com.comiyun.weixin.dto.ReplyTextMsg;
import com.comiyun.weixin.entity.WxAccount;
import com.comiyun.weixin.extend.ExtendService;
import com.comiyun.weixin.service.WxAccountService;
import com.comiyun.weixin.utils.WeiXinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 活动列表
 *
 * @author david
 */
@Service
public class ActItemsExtendService implements ExtendService {
    @Autowired
    private ActivityService activityService;
    @Autowired
    private WxAccountService wxAccountService;

    @Override
    public void autoWireMenu(Map<String, Object> btn) {
        //ignore
    }

    @Override
    public String service(String fromUserName, String toUserName) {
        List<Activity> list = activityService.queryPublishList();
        WxAccount account = wxAccountService.getAccount();
        String msg = null;

        if (list != null && list.size() > 0) {
            ReplyNewsMsg news = new ReplyNewsMsg(toUserName, fromUserName);
            int i = 0;
            String domain = account.getAuthCallBackUrl();
            for (Activity v : list) {
                NewsItem item = new NewsItem();
                item.setTitle(v.getName());
                item.setDesc(v.getContent());
                if (i == 0) {
                    item.setPicUrl(domain + "/images/hd_title.png");
                } else {
                    item.setPicUrl(domain + "/images/hd_item.png");
                }

                String callback = account.getAuthCallBackUrl() + "/mobile/act/actinfo?actId=" + v.getId();
                String longUrl = WeiXinUtil.getAuthUrl(account.getAppId(), callback);

                item.setUrl(longUrl);
                news.addNewsItem(item);
                i++;
            }

            msg = news.serializeToXml();
        } else {
            ReplyTextMsg text = new ReplyTextMsg(toUserName, fromUserName, "您好，暂无活动");
            msg = text.serializeToXml();
        }

        return msg;
    }

}
