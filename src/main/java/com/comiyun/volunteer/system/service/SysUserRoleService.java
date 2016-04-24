package com.comiyun.volunteer.system.service;

import com.comiyun.core.constant.AppConst;
import com.comiyun.core.exception.ServiceException;
import com.comiyun.core.persistence.GenericMapper;
import com.comiyun.core.service.BaseService;
import com.comiyun.volunteer.system.entity.SysRole;
import com.comiyun.volunteer.system.entity.SysUserRole;
import com.comiyun.volunteer.system.persistence.SysRoleMapper;
import com.comiyun.volunteer.system.persistence.SysUserRoleMapper;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author the7last
 * @ClassName: SysUserRoleService
 * @date 2014年8月4日 下午10:47:14
 */
@Service
public class SysUserRoleService extends BaseService<SysUserRole> {
    @Resource
    private SysUserRoleMapper mapper;
    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    protected GenericMapper<SysUserRole, Long> getMapper() {
        return mapper;
    }

    public void editUserRole(SysUserRole sysUserRole, String idsString) {
        if (idsString != null && !idsString.isEmpty()) {
            String[] ids = idsString.split(",");
            //供应商
            boolean exprovider = ArrayUtils.contains(ids, AppConst.ROLE_PROVIDER_ID.toString());
            if (exprovider && ids.length > 1) {
                SysRole role = sysRoleMapper.get(AppConst.ROLE_PROVIDER_ID);
                throw new ServiceException("[" + role.getRoleName() + "]不可与其他角色一同设置");
            }

            List<Long> roleIds = new ArrayList<Long>();
            for (String roleId : ids) {
                roleIds.add(Long.parseLong(roleId));
            }
            sysUserRole.setRoleIds(roleIds);
        }
        mapper.deleteSysUserRole(sysUserRole);
        mapper.batchInsertSysUserRole(sysUserRole);
    }
}
