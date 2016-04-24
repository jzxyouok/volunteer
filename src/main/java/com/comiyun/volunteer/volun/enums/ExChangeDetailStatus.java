package com.comiyun.volunteer.volun.enums;

public enum ExChangeDetailStatus {
    unsend("未发送"), sended("已发送"), cancel("已取消");

    private String text;

    private ExChangeDetailStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
