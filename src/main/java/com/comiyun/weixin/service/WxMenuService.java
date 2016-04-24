package com.comiyun.weixin.service;

import com.comiyun.core.exception.ServiceException;
import com.comiyun.core.util.AppUtil;
import com.comiyun.weixin.ApiConfig;
import com.comiyun.weixin.AppConfig;
import com.comiyun.weixin.entity.WxAccount;
import com.comiyun.weixin.entity.WxExtend;
import com.comiyun.weixin.entity.WxMenu;
import com.comiyun.weixin.enums.MenuType;
import com.comiyun.weixin.extend.ExtendService;
import com.comiyun.weixin.persistence.WxMenuMapper;
import com.comiyun.weixin.utils.HttpsUtil;
import com.comiyun.weixin.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信菜单服务
 *
 * @author david
 */
@Service
public class WxMenuService {
    @Resource
    private WxMenuMapper wxMenuMapper;
    @Resource
    private WxAccountService wxAccountService;
    @Resource
    private WxExtendService wxExtendService;

    public WxMenu getByKey(String key) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("key", key);
        List<WxMenu> list = wxMenuMapper.getList(param);
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 获取所有一级菜单
     *
     * @return
     */
    public List<WxMenu> plist() {
        List<WxMenu> list = wxMenuMapper.plist();
        WxMenu root = new WxMenu();
        root.setId(-1L);
        root.setName("一级菜单");
        list.add(0, root);
        return list;
    }

    /**
     * 查询微信菜单树
     *
     * @return
     */
    public List<WxMenu> queryMenuTree() {
        List<WxMenu> root = new ArrayList<WxMenu>();
        List<WxMenu> list = wxMenuMapper.getList();
        if (list != null && list.size() > 0) {
            for (WxMenu m : list) {
                if (m.getPid().compareTo(AppConfig.DEFALUT_MENU_PID) == 0) {
                    root.add(m);
                }
            }
        }
        if (root.size() > 0) {
            for (WxMenu m : root) {
                for (WxMenu x : list) {
                    if (m.getId().compareTo(x.getPid()) == 0) {
                        m.addChildren(x);
                    }
                }
            }
        }
        return root;
    }

    public void insert(WxMenu wxMenu) {
        if (StringUtils.isBlank(wxMenu.getName())) {
            throw new ServiceException("请输入菜单名字");
        }
        wxMenu.setId(AppUtil.generateId());
        if (wxMenu.getPid() == null) {
            wxMenu.setPid(AppConfig.DEFALUT_MENU_PID);
        }
        WxAccount account = wxAccountService.getAccount();
        wxMenu.setAccountId(account.getId());
        wxMenuMapper.insert(wxMenu);
    }

    public void update(WxMenu wxMenu) {
        if (StringUtils.isBlank(wxMenu.getName())) {
            throw new ServiceException("请输入菜单名字");
        }
        if (wxMenu.getPid() == null) {
            wxMenu.setPid(AppConfig.DEFALUT_MENU_PID);
        }

        if (wxMenu.getId().compareTo(wxMenu.getPid()) == 0) {
            throw new ServiceException("父级菜单不能为自已");
        }
        wxMenuMapper.update(wxMenu);
    }

    public int batchDelete(List<Long> ids) {
        return wxMenuMapper.batchDelete(ids);
    }

    /**
     * 同步微信菜单
     */
    public void syncMenu() {
        List<WxMenu> list = queryMenuTree();
        //存在菜单
        if (list.size() > 0) {
            List<Map<String, Object>> btns = new ArrayList<Map<String, Object>>();
            for (WxMenu m : list) {
                if (m.getChildren().size() > 0) {
                    Map<String, Object> btn = new HashMap<String, Object>();
                    btn.put("name", m.getName());
                    List<Map<String, Object>> subbtns = new ArrayList<Map<String, Object>>();
                    btn.put("sub_button", subbtns);
                    for (WxMenu sub : m.getChildren()) {
                        subbtns.add(convertWxMenu(sub));
                    }
                    btns.add(btn);
                } else {
                    btns.add(convertWxMenu(m));
                }
            }

            Map<String, Object> body = new HashMap<String, Object>();
            body.put("button", btns);

            String json = JsonUtil.toJson(body);

            String accessToken = wxAccountService.getAccessToken();
            Map<String, String> params = new HashMap<String, String>();
            params.put("access_token", accessToken);

            HttpsUtil.sendPost(ApiConfig.MENU_CREATE, params, json);

        }
        //删除菜单
        else {
            String accessToken = wxAccountService.getAccessToken();
            Map<String, String> params = new HashMap<String, String>();
            params.put("access_token", accessToken);

            HttpsUtil.sendGet(ApiConfig.MENU_DELETE, params);

        }
    }

    /**
     * 转换微菜单格式
     *
     * @param m
     * @return
     */
    private Map<String, Object> convertWxMenu(WxMenu m) {
        Map<String, Object> btn = new HashMap<String, Object>();
        if (m.getType() == MenuType.extend) {
            WxExtend extend = wxExtendService.get(m.getExtendId());
            if (extend == null) {
                throw new ServiceException("菜单[" + m.getName() + "]未配置扩展");
            }
            btn.put("key", m.getKey());
            btn.put("name", m.getName());
            btn.put("type", MenuType.click);
            String clz = extend.getService();
            ExtendService service = (ExtendService) AppUtil.getBean(clz);
            service.autoWireMenu(btn);
        } else {
            btn.put("name", m.getName());
            btn.put("type", m.getType().toString());
            btn.put("key", m.getKey());
            btn.put("url", m.getUrl());
            btn.put("media_id", m.getMetiaId());
        }

        return btn;
    }
}
