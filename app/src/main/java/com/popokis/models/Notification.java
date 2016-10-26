package com.popokis.models;

import java.util.Date;

public class Notification {

    private Long user_id;
    private String content;
    private Date read_at;
    private Date created_at;

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getRead_at() {
        return read_at;
    }

    public void setRead_at(Date read_at) {
        this.read_at = read_at;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
