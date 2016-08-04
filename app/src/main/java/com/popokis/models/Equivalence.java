package com.popokis.models;

import java.io.Serializable;

public class Equivalence implements Serializable {

    String subject_id;
    String equivalent_name;
    String subject_name;
    String id;
    String equivalent_id;

    Equivalence () {}

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getEquivalent_name() {
        return equivalent_name;
    }

    public void setEquivalent_name(String equivalent_name) {
        this.equivalent_name = equivalent_name;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEquivalent_id() {
        return equivalent_id;
    }

    public void setEquivalent_id(String equivalent_id) {
        this.equivalent_id = equivalent_id;
    }
}
