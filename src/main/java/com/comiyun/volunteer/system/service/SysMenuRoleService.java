package com.comiyun.volunteer.system.service;

import com.comiyun.core.exception.ServiceException;
import com.comiyun.core.persistence.GenericMapper;
import com.comiyun.core.service.BaseService;
import com.comiyun.volunteer.system.entity.SysMenuRole;
import com.comiyun.volunteer.system.entity.SysRole;
import com.comiyun.volunteer.system.persistence.SysMenuRoleMapper;
import com.comiyun.volunteer.system.persistence.SysRoleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author the7last
 * @ClassName: SysMenuRoleService
 * @date 2014年8月1日 上午11:00:20
 */
@Service
public class SysMenuRoleService extends BaseService<SysMenuRole> {
    @Resource
    private SysMenuRoleMapper mapper;
    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    protected GenericMapper<SysMenuRole, Long> getMapper() {
        return mapper;
    }

    public void editMenuRole(SysMenuRole sysMenuRole, String idsString) {

        if (sysMenuRole != null) {
            SysRole role = sysRoleMapper.get(sysMenuRole.getRoleId());
            if (role != null && role.isSysInit()) {
                throw new ServiceException("内置角色不可设置菜单");
            }
        }

        mapper.deleteSysMenuRole(sysMenuRole);
        if (idsString != null && !idsString.isEmpty()) {
            String[] ids = idsString.split(",");
            List<Long> menuIds = new ArrayList<Long>();
            for (String menuId : ids) {
                menuIds.add(Long.parseLong(menuId));
            }
            sysMenuRole.setMenuIds(menuIds);
            mapper.batchInsertSysMenuRole(sysMenuRole);
        }
    }
}
