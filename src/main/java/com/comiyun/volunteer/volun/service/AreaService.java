package com.comiyun.volunteer.volun.service;

import com.comiyun.core.persistence.GenericMapper;
import com.comiyun.core.service.BaseService;
import com.comiyun.volunteer.volun.entity.Area;
import com.comiyun.volunteer.volun.persistence.AreaMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AreaService extends BaseService<Area> {
    @Resource
    private AreaMapper mapper;

    @Override
    protected GenericMapper<Area, Long> getMapper() {
        return mapper;
    }

}
