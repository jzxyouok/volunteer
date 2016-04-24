package com.comiyun.volunteer.volun.service;

import com.comiyun.core.persistence.GenericMapper;
import com.comiyun.core.service.BaseService;
import com.comiyun.volunteer.volun.entity.Activity;
import com.comiyun.volunteer.volun.entity.ActivityPersion;
import com.comiyun.volunteer.volun.enums.ActivityPersionStatus;
import com.comiyun.volunteer.volun.enums.ActivityStatus;
import com.comiyun.volunteer.volun.enums.IntegralBizType;
import com.comiyun.volunteer.volun.persistence.ActivityMapper;
import com.comiyun.volunteer.volun.persistence.ActivityPersionMapper;
import com.comiyun.weixin.entity.WxQrCode;
import com.comiyun.weixin.enums.QrBizType;
import com.comiyun.weixin.service.WxQrCodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 义工活动
 *
 * @author david
 */
@Service
public class ActivityService extends BaseService<Activity> {
    @Resource
    private ActivityMapper mapper;
    @Resource
    private WxQrCodeService wxQrCodeService;
    @Resource
    private ActivityPersionMapper activityPersionMapper;
    @Resource
    private IntegralService integralService;

    private static final long allow_loss_time = 15 * 60 * 1000; //允许迟到早退范围

    @Override
    protected GenericMapper<Activity, Long> getMapper() {
        return mapper;
    }

    public void changestatus(Long id, ActivityStatus status) {
        Activity act = mapper.get(id);
        mapper.changestatus(id, status);
        //发布状态时，添加而唯维码
        if (status == ActivityStatus.publish) {
            if (act.getQrCodeId() == null) {
                WxQrCode qrcode = wxQrCodeService.createQrCode(QrBizType.volun_activity, act.getId(), true, 0);
                mapper.updateQrcode(qrcode.getId(), id);
            }
        }
        //核算活动积分
        else if (act.getStatus() == ActivityStatus.started && status == ActivityStatus.stop) {
            List<ActivityPersion> plist = activityPersionMapper.queryByActId(act.getId());
            if (plist != null && plist.size() > 0) {
                for (ActivityPersion v : plist) {
                    //参加
                    if (v.getStatus() == ActivityPersionStatus.cj) {

                        Date startTime = act.getStartTime();
                        Date endTime = act.getEndTime();

                        long total = endTime.getTime() - startTime.getTime();

                        Date qdTime = v.getQdTime();
                        Date qtTime = v.getQtTime();
                        if (qdTime != null && qtTime != null && qdTime.compareTo(qtTime) == -1
                                && qdTime.compareTo(endTime) == -1 && qtTime.compareTo(startTime) == 1) {
                            Date vqd = (qdTime.compareTo(startTime) == 1 && qdTime.getTime() - startTime.getTime() > allow_loss_time) ? qdTime : startTime;
                            Date vqt = (qtTime.compareTo(endTime) == -1 && endTime.getTime() - qtTime.getTime() > allow_loss_time) ? qtTime : endTime;

                            long vuse = vqt.getTime() - vqd.getTime();
                            //总时长小于15分钟，忽略
                            if (vuse < allow_loss_time) {
                                continue;
                            }
                            int digit = (int) (act.getPerIntegral() * vuse / total);
                            if (digit > 0) {
                                integralService.addIntegral(IntegralBizType.actjoin, act.getId(), "参加[" + act.getName() + "]活动", v.getPersionId(), digit);
                            }
                        } else {
                            logger.error("volun persoin {} qdTime not less qtTime");
                        }
                    }
                }
            }
        }
    }

    public List<Activity> queryPublishList() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("status", ActivityStatus.publish);
        List<Activity> list = mapper.getList(params);

        Map<String, Object> params2 = new HashMap<String, Object>();
        params2.put("status", ActivityStatus.started);
        List<Activity> list2 = mapper.getList(params2);
        list.addAll(list2);
        return list;
    }

}
