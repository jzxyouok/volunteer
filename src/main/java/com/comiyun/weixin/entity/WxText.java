package com.comiyun.weixin.entity;

import com.comiyun.core.entity.BaseEntity;

/**
 * 文本消息素材
 *
 * @author david
 */
public class WxText extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String name;

    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
