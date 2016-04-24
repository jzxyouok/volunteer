package com.comiyun.volunteer.system.service;

import com.comiyun.core.constant.AppConst;
import com.comiyun.core.persistence.GenericMapper;
import com.comiyun.core.service.BaseService;
import com.comiyun.volunteer.system.entity.SysMenu;
import com.comiyun.volunteer.system.entity.SysRole;
import com.comiyun.volunteer.system.persistence.SysMenuMapper;
import com.comiyun.volunteer.system.persistence.SysUserMapper;
import com.comiyun.volunteer.util.SysMenuUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ydwcn
 * @ClassName: SysMenuService
 * @date 2014-6-25 上午11:11:26
 */
@Service
public class SysMenuService extends BaseService<SysMenu> {
    @Resource
    private SysMenuMapper mapper;
    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    protected GenericMapper<SysMenu, Long> getMapper() {
        return mapper;
    }

    /**
     * 获取当前用户的权限内菜单
     *
     * @return List<E> 用户菜单
     */
    public List<SysMenu> getMenuTree(Long userId) {

        List<SysRole> urlist = sysUserMapper.getUserRoleList(userId);
        boolean isadmin = false;

        if (AppConst.USER_ADMIN_ID.compareTo(userId) == 0) {
            isadmin = true;
        } else {
            if (urlist != null && urlist.size() > 0) {
                for (SysRole r : urlist) {
                    if (AppConst.ROLE_ADMIN_ID.compareTo(r.getId()) == 0) {
                        isadmin = true;
                        break;
                    }
                }
            }
        }
        List<SysMenu> list = mapper.getMenuTree(userId, isadmin);
        //递归组装树
        SysMenu root = SysMenuUtil.loopMenu(list);
        List<SysMenu> tree = root.getChildren();
        return tree;
    }

    /**
     * 获取租户所有菜单
     *
     * @return List<E> 所有菜单
     */
    public List<SysMenu> getAllMenuTree() {
        List<SysMenu> list = mapper.getAllMenuTree();
        SysMenu root = SysMenuUtil.loopMenu(list);
        List<SysMenu> tree = root.getChildren();
        return tree;
    }

}
