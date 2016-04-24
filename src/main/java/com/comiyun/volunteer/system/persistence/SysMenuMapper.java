package com.comiyun.volunteer.system.persistence;

import com.comiyun.core.persistence.BaseMapper;
import com.comiyun.volunteer.system.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统菜单
 *
 * @author ydwcn
 * @ClassName: SysMenuMapper
 * @date 2014-6-19 上午9:46:00
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 用户权限内的系统菜单树
     *
     * @return List<SysMenu> 菜单
     */
    public List<SysMenu> getMenuTree(@Param("userId") Long userId, @Param("isAdmin") Boolean isAdmin);

    /**
     * 获取当前租户的所有菜单
     *
     * @return List<SysMenu> 菜单
     */
    public List<SysMenu> getAllMenuTree();


}
