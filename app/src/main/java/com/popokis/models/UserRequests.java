package com.popokis.models;

import java.io.Serializable;

public class UserRequests implements Serializable {

    Integer subject_id;
    String subject_code;
    String subject_name;
    Integer id;
    String mobility_type;
    String category;

    public UserRequests() {}

    public Integer getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(Integer subject_id) {
        this.subject_id = subject_id;
    }

    public String getSubject_code() {
        return subject_code;
    }

    public void setSubject_code(String subject_code) {
        this.subject_code = subject_code;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobility_type() {
        return mobility_type;
    }

    public void setMobility_type(String mobility_type) {
        this.mobility_type = mobility_type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
