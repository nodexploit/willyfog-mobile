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
    private Integer accepted;
    private Integer rejected;
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

    public Integer getAccepted() {
        return accepted;
    }

    public void setAccepted(Integer accepted) {
        this.accepted = accepted;
    }

    public Integer getRejected() {
        return rejected;
    }

    public void setRejected(Integer rejected) {
        this.rejected = rejected;
    }

    public List<DestinationSubject> getDestination_subjects() {
        return destination_subjects;
    }

    public void setDestination_subjects(List<DestinationSubject> destination_subjects) {
        this.destination_subjects = destination_subjects;
    }
}
