package share;

/**
 * Created by Administrator on 2017/8/1 0001.
 */

public class MyItem {
    private int imageId;
    private String my_items;

    public MyItem(int imageId, String my_items) {
        this.imageId = imageId;
        this.my_items = my_items;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getMy_items() {
        return my_items;
    }

    public void setMy_items(String my_items) {
        this.my_items = my_items;
    }
}
