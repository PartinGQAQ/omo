package com.itheima.model.domain;

import java.util.Date;

public class Email {

    private String sub;
    private String text;
    private String toEmail;
    private Date date = new Date();

    public Email(String sub, String text, String toEmail, Date date) {
        this.sub = sub;
        this.text = text;
        this.toEmail = toEmail;
        this.date = date;
    }

    public Email() {
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
