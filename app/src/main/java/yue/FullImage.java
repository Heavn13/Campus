package yue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.example.heavn.student.R;

import java.util.ArrayList;

import lostandfound.*;

/**
 * Created by Administrator on 2017/9/16 0016.
 */

public class FullImage extends Activity implements View.OnClickListener{
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.full_image);

    Intent intent = getIntent();
    //        收到图片地址
    String image_path = intent.getStringExtra("imageUrl");
//        Log.e("ada",image_path);
//
    imageView = (ImageView)findViewById(R.id.ass_full_image);
    imageView.setOnClickListener(this);
    association.GetImageByUrl getImageByUrl1 = new association.GetImageByUrl();
    getImageByUrl1.setImage(imageView,image_path);

}

    @Override
    public void onClick(View v) {
        int i = v.getId();
        switch (i){
            case R.id.ass_full_image:
                finish();
                break;
            default:
                break;

        }
    }
}
