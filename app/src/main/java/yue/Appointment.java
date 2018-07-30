package yue;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by 张宇 on 2017/7/20.
 */

public class Appointment extends BmobObject{
    //每条约的详细信息
    //private String query_id;
    private String type;
    private String demand;
    private String time;
    private String student_id;
    private String student_name;
    private String ontime;
    private List<AppointComment> comment;
    private int score;
   // private List<String> zanlist;


    public Appointment(){

    }



    public Appointment(String type, String demand, String time,String  student_id,String student_name,String ontime) {
        this.type = type;
        this.demand = demand;
        this.time = time;
        this.student_id = student_id;
        this.student_name=student_name;
        this.ontime=ontime;
        score=0;

    }

/*
    public int[][] getPeoIDArray() {
        return peoIDArray;
    }

    public void setPeoIDArray(int[][] peoIDArray) {
        this.peoIDArray = peoIDArray;
    }

    public String[] getComment() {
        return comment;
    }

    public void setComment(String[] comment) {
        this.comment = comment;
    }*/



    /*public List<String> getZanlist() {
        return zanlist;
    }

    public void setZanlist(List<String> zanlist) {
        this.zanlist = zanlist;
    }*/

 /*   public String getQuery_id() {
        return query_id;
    }

    public void setQuery_id(String query_id) {
        this.query_id = query_id;
    }
*/


    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }


   public List<AppointComment> getComment() {
        return comment;
    }

    public void setComment(List<AppointComment> comment) {
        this.comment = comment;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getOntime() {
        return ontime;
    }

    public void setOntime(String ontime) {
        this.ontime = ontime;
    }
}
