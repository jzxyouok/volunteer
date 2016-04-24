package com.comiyun.volunteer.system.service;

import com.comiyun.core.persistence.GenericMapper;
import com.comiyun.core.service.BaseService;
import com.comiyun.volunteer.system.entity.SysParam;
import com.comiyun.volunteer.system.persistence.SysParamMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 系统参数管理
 *
 * @author david
 */
@Service
public class SysParamService extends BaseService<SysParam> {
    @Resource
    private SysParamMapper mapper;

    @Override
    protected GenericMapper<SysParam, Long> getMapper() {
        return mapper;
    }

}
