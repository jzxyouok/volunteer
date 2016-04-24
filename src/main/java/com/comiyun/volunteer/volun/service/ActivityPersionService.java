package com.comiyun.volunteer.volun.service;

import com.comiyun.core.exception.ServiceException;
import com.comiyun.core.persistence.GenericMapper;
import com.comiyun.core.service.BaseService;
import com.comiyun.core.util.AppUtil;
import com.comiyun.core.web.json.Message;
import com.comiyun.volunteer.volun.entity.Activity;
import com.comiyun.volunteer.volun.entity.ActivityPersion;
import com.comiyun.volunteer.volun.entity.Persion;
import com.comiyun.volunteer.volun.enums.*;
import com.comiyun.volunteer.volun.persistence.ActivityMapper;
import com.comiyun.volunteer.volun.persistence.ActivityPersionMapper;
import com.comiyun.volunteer.volun.persistence.PersionMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ActivityPersionService extends BaseService<ActivityPersion> {
    @Resource
    private ActivityPersionMapper mapper;
    @Resource
    private ActivityMapper activityMapper;
    @Resource
    private PersionMapper persionMapper;
    @Resource
    private IntegralService integralService;

    @Override
    protected GenericMapper<ActivityPersion, Long> getMapper() {
        return mapper;
    }

    public void changestatus(List<Long> ids, ActivityPersionStatus status) {
        for (Long id : ids) {
            ActivityPersion actPer = mapper.get(id);
            if (ActivityPersionStatus.cj == status) {
                actPer.setQdTime(new Date());
                mapper.update(actPer);
                Activity act = activityMapper.get(actPer.getActId());
                //处理积分
                integralService.addIntegral(IntegralBizType.actjoin, act.getId(), "参加[" + act.getName() + "]", actPer.getPersionId(), act.getPerIntegral());
            }
            mapper.changestatus(id, status);
        }
    }

    public synchronized Message adminbm(Long actId, List<Long> ids) {
        Message msg = new Message();
        msg.setMsg("操作成功！");

        Activity act = activityMapper.get(actId);
        if (act == null) {
            throw new ServiceException("活动已不存在");
        }

        if (ActivityStatus.publish != act.getStatus()) {
            msg.setSuccess(false);
            msg.setMsg("当前活动不可报名");
            return msg;
        }

        for (Long id : ids) {
            Persion p = persionMapper.get(id);
            if (p != null && PersionStatus.audited == p.getStatus()) {
                List<ActivityPersion> ext = mapper.queryByActIdAndPerId(actId, p.getId());
                if (ext == null || ext.size() == 0) {
                    ActivityPersion ap = new ActivityPersion();
                    ap.setId(AppUtil.generateId());
                    ap.setActId(actId);
                    ap.setPersionId(p.getId());
                    ap.setResource(ActivityResource.sys);
                    ap.setBmTime(new Date());
                    ap.setStatus(ActivityPersionStatus.bm);
                    mapper.insert(ap);
                }
            }
        }

        return msg;
    }

    public synchronized Message wxbm(Long actId, Long persionId) {
        Message msg = new Message();
        msg.setMsg("报名成功！");

        Activity act = activityMapper.get(actId);
        if (act == null) {
            throw new ServiceException("活动已不存在");
        }

        if (ActivityStatus.publish != act.getStatus()) {
            msg.setSuccess(false);
            msg.setMsg("当前活动不可报名");
            return msg;
        }

        Persion p = persionMapper.get(persionId);
        if (p != null && PersionStatus.audited == p.getStatus()) {
            List<ActivityPersion> ext = mapper.queryByActIdAndPerId(actId, p.getId());
            if (ext == null || ext.size() == 0) {
                ActivityPersion ap = new ActivityPersion();
                ap.setId(AppUtil.generateId());
                ap.setActId(actId);
                ap.setPersionId(p.getId());
                ap.setResource(ActivityResource.weixin);
                ap.setBmTime(new Date());
                ap.setStatus(ActivityPersionStatus.bm);
                mapper.insert(ap);
            } else {
                msg.setSuccess(false);
                msg.setMsg("您已报名");
                return msg;
            }
        } else {
            msg.setSuccess(false);
            msg.setMsg("您正在审核中");
        }

        return msg;
    }

    public synchronized Message wxqd(Long actId, Long persionId) {
        Message msg = new Message();
        msg.setMsg("签到成功");

        Activity act = activityMapper.get(actId);
        if (act == null) {
            throw new ServiceException("活动已不存在");
        }

        if (ActivityStatus.started != act.getStatus()) {
            msg.setSuccess(false);
            msg.setMsg("活动尚未开始");
            return msg;
        }

        Persion p = persionMapper.get(persionId);
        if (p != null && PersionStatus.audited == p.getStatus()) {
            List<ActivityPersion> ext = mapper.queryByActIdAndPerId(actId, p.getId());
            if (ext == null || ext.size() == 0) {
                msg.setSuccess(false);
                msg.setMsg("您未报名当前活动");
            } else {
                ActivityPersion ap = ext.get(0);

                if (ap.getQdTime() != null) {
                    ap.setQdTime(new Date());
                    ap.setStatus(ActivityPersionStatus.cj);
                    mapper.update(ap);
                    //处理积分
//					integralService.addIntegral(IntegralBizType.actjoin, act.getId(), "参加[" + act.getName() + "]", persionId, act.getPerIntegral());
                } else {
                    msg.setSuccess(false);
                    msg.setMsg("已签到活动");
                }
            }
        } else {
            msg.setSuccess(false);
            msg.setMsg("您正在审核中");
        }
        return msg;
    }

    public synchronized Message wxqt(Long actId, Long persionId) {
        Message msg = new Message();
        msg.setMsg("签退成功");

        Activity act = activityMapper.get(actId);
        if (act == null) {
            throw new ServiceException("活动已不存在");
        }

        if (ActivityStatus.started != act.getStatus()) {
            msg.setSuccess(false);
            msg.setMsg("活动尚未开始");
            return msg;
        }

        Persion p = persionMapper.get(persionId);
        if (p != null && PersionStatus.audited == p.getStatus()) {
            List<ActivityPersion> ext = mapper.queryByActIdAndPerId(actId, p.getId());
            if (ext == null || ext.size() == 0) {
                msg.setSuccess(false);
                msg.setMsg("您未报名当前活动");
            } else {
                ActivityPersion ap = ext.get(0);

                if (ap.getStatus() == ActivityPersionStatus.cj) {
                    ap.setQtTime(new Date());
                    mapper.update(ap);
                    //处理积分
//					integralService.addIntegral(IntegralBizType.actjoin, act.getId(), "参加[" + act.getName() + "]", persionId, act.getPerIntegral());
                }
            }
        } else {
            msg.setSuccess(false);
            msg.setMsg("您正在审核中");
        }
        return msg;
    }

}
