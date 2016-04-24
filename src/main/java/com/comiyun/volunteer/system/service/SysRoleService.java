package com.comiyun.volunteer.system.service;

import com.comiyun.core.persistence.GenericMapper;
import com.comiyun.core.service.BaseService;
import com.comiyun.volunteer.system.entity.SysMenu;
import com.comiyun.volunteer.system.entity.SysRole;
import com.comiyun.volunteer.system.persistence.SysRoleMapper;
import com.comiyun.volunteer.util.SysMenuUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色管理
 *
 * @author ydwcn
 * @ClassName: SysRoleService
 * @date 2014年7月16日 下午6:29:17
 */
@Service
public class SysRoleService extends BaseService<SysRole> {

    @Resource
    private SysRoleMapper mapper;

    @Override
    protected GenericMapper<SysRole, Long> getMapper() {
        return mapper;
    }

    /**
     * 获取角色关联的菜单
     *
     * @param roleId 角色ID
     * @return List<SysMenu> 菜单集合
     */
    public List<SysMenu> getRoleMenu(long roleId) {
        List<SysMenu> list = mapper.getRoleMenu(roleId);
        SysMenu root = SysMenuUtil.loopMenu(list);
        List<SysMenu> tree = root.getChildren();
        return tree;
    }
}
