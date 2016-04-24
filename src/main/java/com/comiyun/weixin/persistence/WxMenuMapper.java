package com.comiyun.weixin.persistence;

import com.comiyun.core.persistence.BaseMapper;
import com.comiyun.weixin.entity.WxMenu;

import java.util.List;

public interface WxMenuMapper extends BaseMapper<WxMenu> {

    List<WxMenu> plist();

}
