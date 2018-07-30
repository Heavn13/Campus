package lostandfound;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.heavn.student.R;

public class LostMainActivity extends Activity implements View.OnClickListener{
    private ImageButton back;
    private ImageButton add;
    private ImageButton head;
    private ImageView imageView;
    private TextView title;

    private Spinner spinner;
    private FragmentManager fragmentManager;
    private AllFragment allFragment;
    private ZhongXinFragment zhongXinFragment;
    private HongJiaLouFragfment hongJiaLouFragfment;
    private RuanJianYuanFragment ruanJianYuanFragment;
    private BaoTuQuanFragment baoTuQuanFragment;
    private XingLongShanFragment xingLongShanFragment;
    private QianFoShanFragment qianFoShanFragment;

    private String image_path = "da";
    private String [] img_paths;
    private int j = 0;
    private Bitmap [] bitmaps;

    public static String s_nickname;
    public static String s_account;
    private TextView lost_nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.lost_activity_main);

        Intent intent = getIntent();
        s_nickname = intent.getStringExtra("nickname");
        s_account = intent.getStringExtra("account");

        title = (TextView) findViewById(R.id.title);
        lost_nickname = (TextView)findViewById(R.id.lost_nickname);
        lost_nickname.setText(s_nickname);

        //添加一个内容
        add = (ImageButton) findViewById(R.id.add);
        add.setOnClickListener(this);

        //返回
        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(this);

        //点击头像进入自己发表的内容
        head = (ImageButton) findViewById(R.id.head);
        head.setOnClickListener(this);

        //碎片管理
        fragmentManager = getFragmentManager();
        spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setSelection(0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                hideAllFragment(fragmentTransaction);
                if (position == 0){
                    if (allFragment == null) {
                        allFragment = new AllFragment();
                        fragmentTransaction.add(R.id.framelayout, allFragment);
                    } else {
                        fragmentTransaction.show(allFragment);
                    }
                    spinner.setSelection(0);
                }
                else if(position == 1){
                    if (zhongXinFragment == null) {
                        zhongXinFragment = new ZhongXinFragment();
                        fragmentTransaction.add(R.id.framelayout, zhongXinFragment);
                    } else {
                        fragmentTransaction.show(zhongXinFragment);
                    }
                    spinner.setSelection(1);
                }
                else if(position == 2){
                    if (hongJiaLouFragfment == null) {
                        hongJiaLouFragfment = new HongJiaLouFragfment();
                        fragmentTransaction.add(R.id.framelayout, hongJiaLouFragfment);
                    } else {
                        fragmentTransaction.show(hongJiaLouFragfment);
                    }
                    spinner.setSelection(2);
                }
                else if(position == 3){
                    if (ruanJianYuanFragment == null) {
                        ruanJianYuanFragment = new RuanJianYuanFragment();
                        fragmentTransaction.add(R.id.framelayout, ruanJianYuanFragment);
                    } else {
                        fragmentTransaction.show(ruanJianYuanFragment);
                    }
                    spinner.setSelection(3);
                }
                else if(position == 4){
                    if (baoTuQuanFragment == null) {
                        baoTuQuanFragment = new BaoTuQuanFragment();
                        fragmentTransaction.add(R.id.framelayout, baoTuQuanFragment);
                    } else {
                        fragmentTransaction.show(baoTuQuanFragment);
                    }
                    spinner.setSelection(4);
                }
                else if(position == 5){
                    if (xingLongShanFragment == null) {
                        xingLongShanFragment = new XingLongShanFragment();
                        fragmentTransaction.add(R.id.framelayout, xingLongShanFragment);
                    } else {
                        fragmentTransaction.show(xingLongShanFragment);
                    }
                    spinner.setSelection(5);
                }
                else if(position == 6){
                    if (qianFoShanFragment == null) {
                        qianFoShanFragment = new QianFoShanFragment();
                        fragmentTransaction.add(R.id.framelayout, qianFoShanFragment);
                    } else {
                        fragmentTransaction.show(qianFoShanFragment);
                    }
                    spinner.setSelection(6);
                }
                fragmentTransaction.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        int i = v.getId();
        switch (i){
            case R.id.back:
//                intent = new Intent(LostMainActivity.this, MainActivity.class);
//                intent.putExtra("account",LostMainActivity.s_account);
//                intent.putExtra("nickname",LostMainActivity.s_nickname);
//                startActivity(intent);
                finish();
                break;
            case R.id.add:
                intent = new Intent(LostMainActivity.this,WriteLostActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.head:
                intent = new Intent(this,MineActivity.class);
                startActivity(intent);
                break;
        }
    }

    //启动该Activity前将刚写的内容刷新出来
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            spinner.setSelection(0);
        }
    }

    //隐藏所有的fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (zhongXinFragment != null) fragmentTransaction.hide(zhongXinFragment);
        if (hongJiaLouFragfment != null) fragmentTransaction.hide(hongJiaLouFragfment);
        if (allFragment != null) fragmentTransaction.hide(allFragment);
        if (baoTuQuanFragment != null) fragmentTransaction.hide(baoTuQuanFragment);
        if (qianFoShanFragment != null) fragmentTransaction.hide(qianFoShanFragment);
        if (ruanJianYuanFragment != null) fragmentTransaction.hide(ruanJianYuanFragment);
        if (xingLongShanFragment != null) fragmentTransaction.hide(xingLongShanFragment);
    }


}
