package association;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by 张宇 on 2017/7/19.
 */

//每个社团的详细信息
public class Association extends BmobObject {
    private String name;
    private String info;
    private boolean if_orginzation;
    private BmobFile image;
    private String imagepath;
    private String assID;

    public Association() {
    }

    public Association(String name, String info,  boolean if_orginzation,String assID,BmobFile image,String imagepath) {
        this.name = name;
        this.info = info;
        this.if_orginzation = if_orginzation;
        this.imagepath=imagepath;
        this.assID=assID;
        this.image=image;
    }

    public String getAssID() {
        return assID;
    }

    public void setAssID(String assID) {
        this.assID = assID;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public BmobFile getImage() {
            return image;
        }

        public void setImage(BmobFile image) {
            this.image = image;
        }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    /*public boolean isIf_quality() {
        return if_quality;
    }

    public void setIf_quality(boolean if_quality) {
        this.if_quality = if_quality;
    }*/

    public boolean isIf_orginzation() {
        return if_orginzation;
    }

    public void setIf_orginzation(boolean if_orginzation) {
        this.if_orginzation = if_orginzation;
    }
}
