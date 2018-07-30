package lostandfound;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.example.heavn.student.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/29 0029.
 */

public class FullImageDetail extends Activity implements View.OnClickListener{
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
//    private TextView current_number;
//    private TextView total_number;
    private View view1, view2, view3;
    private ViewPager viewPager;  //对应的viewPager

    private List<View> viewList;//view数组

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.full_image_viewpager);

        Intent intent = getIntent();
//        当前显示的图片
        int current = Integer.parseInt(intent.getStringExtra("current"));
//        收到图片地址
        String image_path1 = intent.getStringExtra("imageUrl1");
        String image_path2 = intent.getStringExtra("imageUrl2");
        String image_path3 = intent.getStringExtra("imageUrl3");

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        LayoutInflater inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.full_image1, null);
        view2 = inflater.inflate(R.layout.full_image2,null);
        view3 = inflater.inflate(R.layout.full_image3, null);

        imageView1 = (ImageView)view1.findViewById(R.id.full_image1);
        imageView1.setOnClickListener(this);
        GetImageByUrl getImageByUrl1 = new GetImageByUrl();
        getImageByUrl1.setImage(imageView1,image_path1);

        imageView2 = (ImageView)view2.findViewById(R.id.full_image2);
        imageView2.setOnClickListener(this);
        GetImageByUrl getImageByUrl2 = new GetImageByUrl();
        getImageByUrl2.setImage(imageView2,image_path2);

        imageView3 = (ImageView)view3.findViewById(R.id.full_image3);
        imageView3.setOnClickListener(this);
        GetImageByUrl getImageByUrl3 = new GetImageByUrl();
        getImageByUrl3.setImage(imageView3,image_path3);

        viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);

        PagerAdapter pagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return viewList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                // TODO Auto-generated method stub
                container.removeView(viewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                // TODO Auto-generated method stub
                container.addView(viewList.get(position));
                return viewList.get(position);
            }
        };

        viewPager.setAdapter(pagerAdapter);

        viewPager.setCurrentItem(current-1);


    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        switch (i){
            case R.id.full_image1:
                viewList.clear();
                finish();
                break;
            case R.id.full_image2:
                viewList.clear();
                finish();
                break;
            case R.id.full_image3:
                finish();
                break;
        }
    }
}
