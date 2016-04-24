package com.comiyun.weixin.service;

import com.comiyun.core.exception.ServiceException;
import com.comiyun.core.util.AppUtil;
import com.comiyun.core.web.json.QueryFilter;
import com.comiyun.core.web.util.SessionUtil;
import com.comiyun.weixin.entity.WxText;
import com.comiyun.weixin.persistence.WxTextMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class WxTextService {
    @Resource
    private WxTextMapper wxTextMapper;

    public List<WxText> getList(QueryFilter queryFilter) {
        List<WxText> list = wxTextMapper.getList(queryFilter.getParams(), queryFilter.getRowBounds());
        return list;
    }

    public void insert(WxText wxText) {
        if (StringUtils.isBlank(wxText.getName())) {
            throw new ServiceException("请输入素材名字");
        }
        if (StringUtils.isBlank(wxText.getContent())) {
            throw new ServiceException("请输入素材内容");
        }
        wxText.setId(AppUtil.generateId());
        wxText.setCreateBy(SessionUtil.getCurrentUser());
        wxText.setCreateTime(new Date());
        wxTextMapper.insert(wxText);
    }

    public void update(WxText wxText) {
        if (StringUtils.isBlank(wxText.getName())) {
            throw new ServiceException("请输入素材名字");
        }
        if (StringUtils.isBlank(wxText.getContent())) {
            throw new ServiceException("请输入素材内容");
        }
        wxTextMapper.update(wxText);
    }

    public void delete(Long id) {
        wxTextMapper.delete(id);
    }

    public int batchDelete(List<Long> ids) {
        return wxTextMapper.batchDelete(ids);
    }
}
