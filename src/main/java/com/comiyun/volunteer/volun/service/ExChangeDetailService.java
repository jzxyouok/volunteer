package com.comiyun.volunteer.volun.service;

import com.comiyun.core.persistence.GenericMapper;
import com.comiyun.core.service.BaseService;
import com.comiyun.core.web.json.Message;
import com.comiyun.volunteer.volun.entity.ExChangeDetail;
import com.comiyun.volunteer.volun.enums.ExChangeDetailStatus;
import com.comiyun.volunteer.volun.persistence.ExChangeDetailMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ExChangeDetailService extends BaseService<ExChangeDetail> {
    @Resource
    private ExChangeDetailMapper mapper;

    @Override
    protected GenericMapper<ExChangeDetail, Long> getMapper() {
        return mapper;
    }

    public Message ex(Long id, String exCode) {
        Message msg = new Message();
        ExChangeDetail detail = mapper.get(id);
        if (detail == null) {
            msg.setSuccess(false);
            msg.setMsg("兑换不存在");
            return msg;
        }
        if (detail.getStatus() == ExChangeDetailStatus.sended) {
            msg.setSuccess(false);
            msg.setMsg("已兑换成功");
            return msg;
        }
        if (detail.getStatus() == ExChangeDetailStatus.cancel) {
            msg.setSuccess(false);
            msg.setMsg("兑换已被取消");
            return msg;
        }
        if (StringUtils.isBlank(exCode)) {
            msg.setSuccess(false);
            msg.setMsg("请输入兑换码");
            return msg;
        }
        if (exCode.trim().equals(detail.getExCode())) {
            detail.setStatus(ExChangeDetailStatus.sended);
            mapper.update(detail);
        } else {
            msg.setSuccess(false);
            msg.setMsg("输入兑换码错误");
            return msg;
        }

        msg.setMsg("兑换成功");
        return msg;
    }

}
