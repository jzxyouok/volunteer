package com.comiyun.volunteer.volun.enums;

public enum ActivityStatus {

    draft("草稿"), publish("已发布"), started("进行中"), stop("已结束");

    private String text;

    private ActivityStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
