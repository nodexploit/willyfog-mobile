package com.popokis.models;

import java.util.List;

public class RequestInfo {

    private Integer id;
    private Integer student_id;
    private Integer subject_id;
    private String subject_code;
    private String subject_name;
    private String subject_credits;
    private Integer mobility_type_id;
    private String mobility_type;
    private String student_surname;
    private String student_name;
    private String student_email;
    private String created_at;
    private String status;
    private List<DestinationSubject> destination_subjects;

    public RequestInfo() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return student_id;
    }

    public void setStudentId(Integer student_id) {
        this.student_id = student_id;
    }

    public Integer getSubjectId() {
        return subject_id;
    }

    public void setSubjectId(Integer subject_id) {
        this.subject_id = subject_id;
    }

    public String getSubjectCode() {
        return subject_code;
    }

    public void setSubjectCode(String subject_code) {
        this.subject_code = subject_code;
    }

    public String getSubjectName() {
        return subject_name;
    }

    public void setSubjectName(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getSubjectCredits() {
        return subject_credits;
    }

    public void setSubjectCredits(String subject_credits) {
        this.subject_credits = subject_credits;
    }

    public Integer getMobilityTypeId() {
        return mobility_type_id;
    }

    public void setMobilityTypeId(Integer mobility_type_id) {
        this.mobility_type_id = mobility_type_id;
    }

    public String getMobilityType() {
        return mobility_type;
    }

    public void setMobilityType(String mobility_type) {
        this.mobility_type = mobility_type;
    }

    public List<DestinationSubject> getDestination_subjects() {
        return destination_subjects;
    }

    public void setDestination_subjects(List<DestinationSubject> destination_subjects) {
        this.destination_subjects = destination_subjects;
    }

    public String getStudent_surname() {
        return student_surname;
    }

    public void setStudent_surname(String student_surname) {
        this.student_surname = student_surname;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_email() {
        return student_email;
    }

    public void setStudent_email(String student_email) {
        this.student_email = student_email;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
