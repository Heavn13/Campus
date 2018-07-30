package lostandfound;

/**
 * Created by Administrator on 2017/8/2 0002.
 */

public class ContentComment {
    private int fromID;
    private int toID;
    private String fromName;
    private String toName;
    private String comments;

    public ContentComment(){
    }

    public ContentComment(String fromName, String comments, String toName) {
        this.toName = toName;
        this.comments = comments;
        this.fromName = fromName;
    }

    public int getFromID() {
        return fromID;
    }

    public void setFromID(int fromID) {
        this.fromID = fromID;
    }

    public int getToID() {
        return toID;
    }

    public void setToID(int toID) {
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
