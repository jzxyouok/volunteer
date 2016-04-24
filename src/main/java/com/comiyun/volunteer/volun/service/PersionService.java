package com.comiyun.volunteer.volun.service;

import com.comiyun.core.exception.ServiceException;
import com.comiyun.core.persistence.GenericMapper;
import com.comiyun.core.service.BaseService;
import com.comiyun.volunteer.volun.entity.Persion;
import com.comiyun.volunteer.volun.enums.PersionStatus;
import com.comiyun.volunteer.volun.persistence.PersionMapper;
import com.comiyun.weixin.entity.WxQrCode;
import com.comiyun.weixin.enums.QrBizType;
import com.comiyun.weixin.service.WxQrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PersionService extends BaseService<Persion> {

    @Resource
    private PersionMapper mapper;
    @Autowired
    private WxQrCodeService wxQrCodeService;

    @Override
    protected GenericMapper<Persion, Long> getMapper() {
        return mapper;
    }

    public void updateQrcode(Long qrCodeId, Long id) {
        mapper.updateQrcode(qrCodeId, id);
    }

    public void updateFamily(Long familyId, Long id) {
        mapper.updateFamily(familyId, id);
    }

    public Persion getByQrcode(Long qrCodeId) {
        return mapper.getByQrcode(qrCodeId);
    }

    public synchronized Persion getByOpenId(String openId) {
        return mapper.getByOpenId(openId);
    }

    public List<Persion> queryByFamilyId(Long familyId) {
        return mapper.queryByFamilyId(familyId);
    }

    /**
     * 审核微信身份
     *
     * @param ids
     */
    public int audit(Long id) {
        int i = mapper.updateStatus(id, PersionStatus.audited);
        if (i > 0) {
            Persion p = get(id);
            if (p.getQrCodeId() == null) {
                WxQrCode qrcode = wxQrCodeService.createQrCode(QrBizType.volun_persion, p.getId(), true, 0);
                updateQrcode(qrcode.getId(), p.getId());
            }
        }
        return i;
    }

    /**
     * 义工注销
     *
     * @param ids
     */
    public int logoff(Long id) {
        return mapper.updateStatus(id, PersionStatus.logoff);
    }

    @Override
    public int insert(Persion entity) throws ServiceException {
        int i = super.insert(entity);
        if (PersionStatus.audited == entity.getStatus()) {
            WxQrCode qrcode = wxQrCodeService.createQrCode(QrBizType.volun_persion, entity.getId(), true, 0);
            updateQrcode(qrcode.getId(), entity.getId());
        }
        return i;
    }

    @Override
    public int update(Persion entity) throws ServiceException {
        int i = super.update(entity);
        if (PersionStatus.audited == entity.getStatus() && entity.getQrCodeId() == null) {
            WxQrCode qrcode = wxQrCodeService.createQrCode(QrBizType.volun_persion, entity.getId(), true, 0);
            updateQrcode(qrcode.getId(), entity.getId());
        }
        return i;
    }
}
