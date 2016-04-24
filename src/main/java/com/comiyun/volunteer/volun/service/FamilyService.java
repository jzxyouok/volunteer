package com.comiyun.volunteer.volun.service;

import com.comiyun.core.persistence.GenericMapper;
import com.comiyun.core.service.BaseService;
import com.comiyun.core.util.AppUtil;
import com.comiyun.core.web.json.Message;
import com.comiyun.volunteer.volun.entity.Family;
import com.comiyun.volunteer.volun.entity.Persion;
import com.comiyun.volunteer.volun.enums.IntegralBizType;
import com.comiyun.volunteer.volun.persistence.FamilyMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 家庭管理
 *
 * @author david
 */
@Service
public class FamilyService extends BaseService<Family> {

    private Logger logger = LoggerFactory.getLogger(FamilyService.class);

    @Resource
    private FamilyMapper mapper;
    @Autowired
    private PersionService persionService;
    @Autowired
    private IntegralService integralService;

    @Override
    protected GenericMapper<Family, Long> getMapper() {
        return mapper;
    }

    /**
     * 下一个family code
     *
     * @return
     */
    public synchronized long nextFamilyCode() {
        Long curr = mapper.currFamilyCode();
        Long next = null;
        if (curr == null) {
            next = 80001L;
        } else {
            next = curr + 1L;
        }
        return next;
    }

    public Message create(Family family) {
        Message msg = new Message();
        msg.setMsg("创建成功");
        if (StringUtils.isBlank(family.getName())) {
            msg.setSuccess(false);
            msg.setMsg("家庭名称不能为空");
            return msg;
        }

        Long code = nextFamilyCode();
        family.setId(AppUtil.generateId());
        family.setCode(code);
        try {
            mapper.insert(family);
            persionService.updateFamily(family.getId(), family.getOwnerId());
        } catch (Exception e) {
            msg.setSuccess(false);
            msg.setMsg("创建失败");
            logger.error("创建家庭失败：：", e);
        }

        return msg;
    }

    public Message join(Persion p, String code) {
        Message msg = new Message();
        msg.setMsg("加入成功");

        if (p.getFamilyId() != null) {
            msg.setSuccess(false);
            msg.setMsg("已加入家庭");
            return msg;
        }

        List<Family> fs = mapper.queryByCode(code);
        if (fs != null && fs.size() != 0) {
            Family f = fs.get(0);
            persionService.updateFamily(f.getId(), p.getId());
        } else {
            msg.setSuccess(false);
            msg.setMsg("家庭不存在");
            return msg;
        }

        return msg;
    }

    public Message quit(Persion p) {
        Message msg = new Message();
        msg.setMsg("退出成功");
        Family family = mapper.get(p.getFamilyId());

        boolean isowner = false;
        if (family.getOwnerId().compareTo(p.getId()) == 0) {
            isowner = true;
        }
        Long tempFamilyId = p.getFamilyId();
        p.setFamilyId(null);
        persionService.updateFamily(null, p.getId());
        List<Persion> members = persionService.queryByFamilyId(tempFamilyId);
        if (members != null && members.size() > 0) {
            if (isowner) {
                Persion x = members.get(0);
                family.setOwnerId(x.getId());
                mapper.update(family);
            }
        } else {
            mapper.delete(family.getId());
        }
        return msg;
    }

    public Message jfzh(Long srcId, Long targetId, int jifen) {
        Message msg = new Message();
        msg.setMsg("转换成功");

        Persion p = persionService.get(targetId);
        if (p == null) {
            msg.setSuccess(false);
            msg.setMsg("目标成员不存在");
            return msg;
        }

        if (jifen <= 0) {
            msg.setSuccess(false);
            msg.setMsg("积分小于等零");
            return msg;
        }

        integralService.addIntegral(IntegralBizType.transfe, srcId, "积分转入", targetId, jifen);
        integralService.addIntegral(IntegralBizType.transfe, targetId, "积分转出", srcId, 0 - jifen);
        return msg;
    }

}
