package com.comiyun.volunteer.system.service;

import com.comiyun.core.persistence.GenericMapper;
import com.comiyun.core.service.BaseService;
import com.comiyun.volunteer.system.entity.SysLog;
import com.comiyun.volunteer.system.persistence.SysLogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 系统日志
 *
 * @author ydwcn
 * @ClassName: SysLogService
 * @date 2014-6-30 下午3:40:36
 */
@Service
public class SysLogService extends BaseService<SysLog> {
    @Resource
    private SysLogMapper mapper;

    @Override
    protected GenericMapper<SysLog, Long> getMapper() {
        return mapper;
    }


}
