package share;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/7/20 0020.
 * 上传到后端云的数据
 */

public class Content extends BmobObject {
    private String id;
    private String account;
    private String nickname;
    private String content;
    private String date;
    private String secret;
    private String location;
    //心里话类型
    private String type;
    //被赞次数
    private int count;
    private List<ContentComment> comment;

    public Content() {
    }

    public Content(String id,String nickname, String content, String date, String location,int count) {
        this.id = id;
        this.nickname = nickname;
        this.content = content;
        this.date = date;
        this.location = location;
        this.count = count;
    }

    public Content(String id,String nickname, String content, String date, String location,List<ContentComment> comment,int count) {
        this.id = id;
        this.nickname = nickname;
        this.content = content;
        this.date = date;
        this.location = location;
        this.comment = comment;
        this.count = count;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public void setComments(List<ContentComment> comment) {
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
