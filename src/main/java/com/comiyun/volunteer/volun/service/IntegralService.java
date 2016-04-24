package com.comiyun.volunteer.volun.service;

import com.comiyun.core.persistence.GenericMapper;
import com.comiyun.core.service.BaseService;
import com.comiyun.core.util.AppUtil;
import com.comiyun.core.web.util.SessionUtil;
import com.comiyun.volunteer.volun.entity.Integral;
import com.comiyun.volunteer.volun.entity.Persion;
import com.comiyun.volunteer.volun.enums.IntegralBizType;
import com.comiyun.volunteer.volun.persistence.IntegralMapper;
import com.comiyun.volunteer.volun.persistence.PersionMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class IntegralService extends BaseService<Integral> {

    @Resource
    private IntegralMapper mapper;
    @Resource
    private PersionMapper persionMapper;

    @Override
    protected GenericMapper<Integral, Long> getMapper() {
        return mapper;
    }

    /**
     * 新增积分
     *
     * @param bizType
     * @param bizId
     * @param bizContent
     * @param persionId
     * @param digit
     */
    public void addIntegral(IntegralBizType bizType, Long bizId, String bizContent, Long persionId, int digit) {
        if (digit != 0) {
            Persion p = persionMapper.get(persionId);
            int target = p.getIntegral() + digit;
            if (target < 0) {
                target = 0;
            }

            Integral v = new Integral();
            v.setId(AppUtil.generateId());
            String curruser = SessionUtil.getCurrentUser();
            v.setCreateBy(curruser);
            v.setCreateTime(new Date());
            v.setBizType(bizType);
            v.setBizId(bizId);
            v.setBizContent(bizContent);
            v.setPersionId(persionId);
            v.setDigit(digit);
            mapper.insert(v);

            p.setIntegral(target);
            if (v.getDigit() > 0) {
                p.setDegree(p.getDegree() + digit);
            }
            persionMapper.update(p);
        }
    }

}
