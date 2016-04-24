package com.comiyun.volunteer.util;

import com.comiyun.core.constant.AppConst;
import com.comiyun.volunteer.system.entity.SysMenu;

import java.util.List;

/**
 * 菜单工具类
 *
 * @author ydwcn
 * @ClassName: SysMenuUtil
 * @date 2014年7月17日 上午10:34:41
 */
public class SysMenuUtil {

    /**
     * 递归树型菜单
     *
     * @param list 树的结查集
     * @return SysMenu 根节点树
     */
    public static SysMenu loopMenu(List<SysMenu> list) {
        if (list == null || list.size() == 0) {
            new SysMenu();
        }

        SysMenu root = new SysMenu();
        root.setId(AppConst.MENU_ROOT_ID);
        root.setMetaId(AppConst.MENU_ROOT_ID);
        root.setName("菜单根节点");
        getNode(root, list);
        return root;
    }

    private static void getNode(SysMenu p, List<SysMenu> list) {
        for (int i = 0; i < list.size(); i++) {
            SysMenu c = list.get(i);
            if (p.getMetaId().compareTo(c.getPid()) == 0) {
                p.addChildren(c);
                getNode(c, list);
            }
        }
    }
}
