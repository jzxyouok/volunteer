package com.comiyun.volunteer.volun.enums;

public enum ActivityPersionStatus {
    bm("已报名"), cj("已参加");

    private String text;

    private ActivityPersionStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
