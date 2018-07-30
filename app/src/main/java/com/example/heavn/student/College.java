package com.example.heavn.student;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by CC on 2017/6/1.
 * 注册，用户表对应的实体类
 *
 */

public class College extends BmobObject implements Serializable {
    //学号
    String StudentId;
    //密码
    String password;
    //用户名
    String Name;
    //昵称
    String Nickname;
    //手机号
    String Phone;
    String School;
    //校区
    String Campus;
    //学院
    String Xueyuan;
    //专业
    String Major;
    //年级
    String Grade;


    public String getStudentId() {
        return StudentId;
    }

    public void setStudentId(String studentId) {
        StudentId = studentId;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getCampus() {
        return Campus;
    }

    public void setCampus(String campus) {
        Campus = campus;
    }

    public String getXueyuan() {
        return Xueyuan;
    }

    public void setXueyuan(String xueyuan) {
        Xueyuan = xueyuan;
    }

    public String getMajor() {
        return Major;
    }

    public void setMajor(String major) {
        Major = major;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }


    public College(){

    }

    public String getSchool() {
        return School;
    }

    public void setSchool(String school) {
        School = school;
    }
}
