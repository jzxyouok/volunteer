package com.comiyun.weixin.service;

import com.comiyun.weixin.entity.WxExtend;
import com.comiyun.weixin.persistence.WxExtendMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WxExtendService {
    @Resource
    private WxExtendMapper mapper;

    public List<WxExtend> getList() {
        return mapper.getList();
    }

    public WxExtend get(Long id) {
        return mapper.get(id);
    }
}
