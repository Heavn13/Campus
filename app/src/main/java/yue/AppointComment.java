package yue;

/**
 * Created by 张宇 on 2017/7/24.
 */

//显示评论内容的类
public class AppointComment {
    private String fromID;
    private String toID;
    private String fromName;
    private String toName;
    private String comments;


    public AppointComment(){

    }


    public AppointComment(String fromID,  String fromName,String comments, String toID, String toName) {
        this.fromID = fromID;
        this.toID = toID;
        this.toName = toName;
        this.comments = comments;
        this.fromName = fromName;
    }


    public String getFromID() {
        return fromID;
    }

    public void setFromID(String fromID) {
        this.fromID = fromID;
    }

    public String getToID() {
        return toID;
    }

    public void setToID(String toID) {
        this.toID = toID;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }
}
