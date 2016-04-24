package com.comiyun.volunteer.volun.enums;

public enum PersionStatus {

    auditing("审核中"), audited("审核通过"), logoff("注销");

    private String text;

    private PersionStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
