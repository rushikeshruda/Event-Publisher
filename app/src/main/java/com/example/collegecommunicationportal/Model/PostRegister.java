package com.example.collegecommunicationportal.Model;

public class PostRegister {

    String postId,id,studentId,studentName,studentClass,studentMobile,studentEmail;

    public PostRegister()
    {

    }

    public PostRegister(String postId, String id, String studentId, String studentName, String studentClass, String studentMobile, String studentEmail) {
        this.postId = postId;
        this.id = id;
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentClass = studentClass;
        this.studentMobile = studentMobile;
        this.studentEmail = studentEmail;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getStudentMobile() {
        return studentMobile;
    }

    public void setStudentMobile(String studentMobile) {
        this.studentMobile = studentMobile;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }
}
