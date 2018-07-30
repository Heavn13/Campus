package lostandfound;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public class Lost extends BmobObject {
    private String id;
    private String icon;
    private String icon2;
    private String icon3;
    private String head_image;
    private String nickname;
    private String things_name;
    private String lost_location;
    private String content;
    private String location;
    private String things_image;
    private String contact;
    private String lost_date;
    private String date;
    private String account;
    //被赞次数
    private int count;
    private List<ContentComment> comment;

    public Lost(){

    }

    public Lost(String id ,String nickname, String things_name,String content,String image_path,String image_path2,String image_path3,String lost_date, String lost_location, String contact, String location, String date,List<ContentComment> comment,int count) {
        this.id = id;
        this.nickname = nickname;
        this.things_name = things_name;
        this.content = content;
        icon = image_path;
        icon2 = image_path2;
        icon3 = image_path3;
        this.lost_date = lost_date;
        this.lost_location = lost_location;
        this.contact = contact;
        this.location = location;
        this.date = date;
        this.comment = comment;
        this.count = count;
    }

    public String getHead_image() {
        return head_image;
    }

    public void setHead_image(String head_image) {
        this.head_image = head_image;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getThings_name() {
        return things_name;
    }

    public void setThings_name(String things_name) {
        this.things_name = things_name;
    }

    public String getLost_location() {
        return lost_location;
    }

    public void setLost_location(String lost_location) {
        this.lost_location = lost_location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getThings_image() {
        return things_image;
    }

    public void setThings_image(String things_image) {
        this.things_image = things_image;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String  contact) {
        this.contact = contact;
    }

    public String getLost_date() {
        return lost_date;
    }

    public void setLost_date(String lost_date) {
        this.lost_date = lost_date;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon2() {
        return icon2;
    }

    public void setIcon2(String icon2) {
        this.icon2 = icon2;
    }

    public String getIcon3() {
        return icon3;
    }

    public void setIcon3(String icon3) {
        this.icon3 = icon3;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ContentComment> getComment() {
        return comment;
    }

    public void setComment(List<ContentComment> comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
