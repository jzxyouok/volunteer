package com.comiyun.volunteer.system.service;

import com.comiyun.core.persistence.GenericMapper;
import com.comiyun.core.service.BaseService;
import com.comiyun.volunteer.system.entity.SysFeedback;
import com.comiyun.volunteer.system.persistence.SysFeedbackMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysFeedbackService extends BaseService<SysFeedback> {
    @Resource
    private SysFeedbackMapper mapper;

    @Override
    protected GenericMapper<SysFeedback, Long> getMapper() {
        return mapper;
    }

}
