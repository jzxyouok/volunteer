package com.comiyun.volunteer.volun.enums;

public enum SexStatus {

    man("男"), woman("女"), unkown("未知");

    private String text;

    private SexStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
