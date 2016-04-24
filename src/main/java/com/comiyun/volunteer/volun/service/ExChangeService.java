package com.comiyun.volunteer.volun.service;

import com.comiyun.core.persistence.GenericMapper;
import com.comiyun.core.service.BaseService;
import com.comiyun.core.util.AppUtil;
import com.comiyun.core.util.RandomUtil;
import com.comiyun.core.web.json.Message;
import com.comiyun.volunteer.volun.entity.ExChange;
import com.comiyun.volunteer.volun.entity.ExChangeDetail;
import com.comiyun.volunteer.volun.entity.Persion;
import com.comiyun.volunteer.volun.enums.ExChangeDetailStatus;
import com.comiyun.volunteer.volun.enums.ExChangeStatus;
import com.comiyun.volunteer.volun.enums.IntegralBizType;
import com.comiyun.volunteer.volun.persistence.ExChangeDetailMapper;
import com.comiyun.volunteer.volun.persistence.ExChangeMapper;
import com.comiyun.volunteer.volun.persistence.PersionMapper;
import com.comiyun.weixin.entity.WxQrCode;
import com.comiyun.weixin.enums.QrBizType;
import com.comiyun.weixin.service.WxQrCodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ExChangeService extends BaseService<ExChange> {
    @Resource
    private ExChangeMapper mapper;
    @Resource
    private WxQrCodeService wxQrCodeService;
    @Resource
    private PersionMapper persionMapper;
    @Resource
    private ExChangeDetailMapper exChangeDetailMapper;
    @Resource
    private IntegralService integralService;

    @Override
    protected GenericMapper<ExChange, Long> getMapper() {
        return mapper;
    }

    /**
     * 兑换物品
     *
     * @return
     */
    public Message exchange(Long exId, Long perId, int exNum) {
        Message msg = new Message();
        msg.setSuccess(true);
        msg.setMsg("兑换成功");

        ExChange exVo = mapper.get(exId);
        if (exVo == null) {
            msg.setSuccess(false);
            msg.setMsg("兑换品已不存在");
            return msg;
        }
        if (ExChangeStatus.canEx != exVo.getStatus()) {
            msg.setSuccess(false);
            msg.setMsg("兑换品已停止兑换");
            return msg;
        }
        Persion p = persionMapper.get(perId);
        if (p == null) {
            msg.setSuccess(false);
            msg.setMsg("义工帐号不存在,请注册");
            return msg;
        }

        if (exNum <= 0) {
            msg.setSuccess(false);
            msg.setMsg("兑换数量小于0");
            return msg;
        }

        int remainNum = exVo.getTotalNum() - exVo.getUseNum();
        if (exNum > remainNum) {
            msg.setSuccess(false);
            msg.setMsg("兑换品库存不足");
            return msg;
        }
        int needIntegral = exNum * exVo.getNeedIntegral(); //所需积分

        if (p.getIntegral() < needIntegral) {
            msg.setSuccess(false);
            msg.setMsg("您的积分不足");
            return msg;
        }

        ExChangeDetail exd = new ExChangeDetail();
        exd.setId(AppUtil.generateId());
        exd.setCreateBy("admin");
        exd.setCreateTime(new Date());
        exd.setExId(exVo.getId());
        exd.setPersionId(p.getId());
        exd.setNum(exNum);
        exd.setPerIntegral(exVo.getNeedIntegral());
        exd.setExCode(RandomUtil.random6()); //生成随机码
        exd.setStatus(ExChangeDetailStatus.unsend);
        exChangeDetailMapper.insert(exd);

        exVo.setUseNum(exVo.getUseNum() + exNum);

        mapper.update(exVo);

        //更新人员积分变化
        integralService.addIntegral(IntegralBizType.exchange, exVo.getId(), "兑换[" + exVo.getName() + "]", p.getId(), 0 - needIntegral);

        return msg;
    }


    public void processEx(List<Long> ids, ExChangeStatus status) {

        if (ids != null && ids.size() > 0) {
            for (Long id : ids) {
                mapper.processEx(id, status);
                if (status == ExChangeStatus.canEx) {
                    ExChange ex = mapper.get(id);
                    if (ex.getQrCodeId() == null) {
                        WxQrCode qrcode = wxQrCodeService.createQrCode(QrBizType.volun_exchange, ex.getId(), true, 0);
                        mapper.updateQrcode(qrcode.getId(), id);
                    }
                }
            }
        }
    }
}
