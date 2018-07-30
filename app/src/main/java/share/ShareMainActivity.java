package share;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.heavn.student.R;

public class ShareMainActivity extends Activity implements RadioGroup.OnCheckedChangeListener,View.OnClickListener {
    private ImageButton back;
    private ImageButton add;
    private ImageButton head;

    private PopupWindow popupWindow;
    private Button x_propose;
    private Button x_comment;
    private Button x_forgive;

    private TextView title;
    private TextView share_nickname;
    private RadioGroup radioGroup;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private FrameLayout frameLayout;
    //三个碎片管理
    private FragmentManager fragmentManager;
    private ProposeFragment proposeFragment;
    private ForgiveFragment forgiveFragment;
    private CommentFragment commentFragment;
    private Intent intent;

    private int width;
    private int height;

    public static String s_nickname;
    public static String s_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.share_activity_main);

        Intent intent = getIntent();
        s_nickname = intent.getStringExtra("nickname");
        s_account = intent.getStringExtra("account");

        title = (TextView) findViewById(R.id.title);

        share_nickname = (TextView)findViewById(R.id.share_nickname);
        share_nickname.setText(s_nickname);

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
        //按钮组
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(this);

        radioButton1 = (RadioButton) findViewById(R.id.propose_button);
        radioButton2 = (RadioButton) findViewById(R.id.comment_button);
        radioButton3 = (RadioButton) findViewById(R.id.forgive_button);
        radioButton1.setChecked(true);

        //获取手机屏幕宽度
        WindowManager wm = (WindowManager) getBaseContext().getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();

    }

    //各个按钮点击事件
    @Override
    public void onClick(View v) {
        int i = v.getId();
        switch (i) {
            //点击弹出窗口选择要写的类型
            case R.id.add:
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    add.setBackgroundResource(R.drawable.add);
                } else {
                    initPopupWindowView();
                    popupWindow.showAsDropDown(v, 0, 5);
                    add.setBackgroundResource(R.drawable.add1);
                }
                break;
            case R.id.back:
//                intent = new Intent(ShareMainActivity.this,MainActivity.class);
//                intent.putExtra("account", ShareMainActivity.s_account);
//                intent.putExtra("nickname",ShareMainActivity.s_nickname);
//                startActivity(intent);
                finish();
                break;
            //进入到我的空间
            case R.id.head:
                intent = new Intent(ShareMainActivity.this,MineActivity.class);
                startActivity(intent);
                break;
            //写表白
            case R.id.x_propose:
                intent = new Intent(ShareMainActivity.this,WriteWordsActivity.class);
                intent.putExtra("StyleId","0");
                startActivity(intent);
                finish();
                break;
            //写吐槽
            case R.id.x_comment:
                intent = new Intent(ShareMainActivity.this,WriteWordsActivity.class);
                intent.putExtra("StyleId","1");
                startActivity(intent);
                finish();
                break;
            //写原谅
            case R.id.x_forgive:
                intent = new Intent(ShareMainActivity.this,WriteWordsActivity.class);
                intent.putExtra("StyleId","2");
                startActivity(intent);
                finish();
                break;
        }

    }

    //点击不同按钮的切换事件
    @Override
    public void onCheckedChanged(RadioGroup group, int i) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideAllFragment(fragmentTransaction);
        switch (i) {
            case R.id.propose_button:
//                Intent intent1 = new Intent(ShareMainActivity.this,LoadingActivity.class);
//                startActivity(intent1);
//                Toast.makeText(ShareMainActivity.this,"ProposeFragment",Toast.LENGTH_SHORT).show();
                if (proposeFragment == null) {
                    proposeFragment = new ProposeFragment();
                    fragmentTransaction.add(R.id.framelayout, proposeFragment);
                } else {
                    fragmentTransaction.show(proposeFragment);
                }
                radioButton1.setBackgroundResource(R.color.lightBlue);
                radioButton2.setBackgroundResource(R.color.transparant);
                radioButton3.setBackgroundResource(R.color.transparant);
                break;
            case R.id.comment_button:
//                Toast.makeText(ShareMainActivity.this,"CommentFragment",Toast.LENGTH_SHORT).show();
                if (commentFragment == null) {
                    commentFragment = new CommentFragment();
                    fragmentTransaction.add(R.id.framelayout, commentFragment);
                } else {
                    fragmentTransaction.show(commentFragment);
                }
                radioButton2.setBackgroundResource(R.color.lightBlue);
                radioButton1.setBackgroundResource(R.color.transparant);
                radioButton3.setBackgroundResource(R.color.transparant);
                break;
            case R.id.forgive_button:
//                Toast.makeText(ShareMainActivity.this,"ForgiveFragment",Toast.LENGTH_SHORT).show();
                if (forgiveFragment == null) {
                forgiveFragment = new ForgiveFragment();
                fragmentTransaction.add(R.id.framelayout, forgiveFragment);
                } else {
                fragmentTransaction.show(forgiveFragment);
                }
                radioButton3.setBackgroundResource(R.color.lightBlue);
                radioButton2.setBackgroundResource(R.color.transparant);
                radioButton1.setBackgroundResource(R.color.transparant);
                break;
        }
        fragmentTransaction.commit();

    }

    //启动该Activity前将刚写的内容刷新出来
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            radioButton1.setChecked(true);
        }
    }

    //隐藏所有的fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (proposeFragment != null) {
            fragmentTransaction.hide(proposeFragment);
        }
        if (commentFragment != null) {
            fragmentTransaction.hide(commentFragment);

        }
        if (forgiveFragment != null) {
            fragmentTransaction.hide(forgiveFragment);
        }
    }

    //点击“+”弹出菜单，选择要写的心里话类型
    public void initPopupWindowView() {
        // // 获取自定义布局文件pop.xml的视图
        View customView = getLayoutInflater().inflate(R.layout.write_style_layout, null, false);
        // 创建PopupWindow实例,200,150分别是宽度和高度
        popupWindow = new PopupWindow(customView, width, height);
        // 自定义view添加触摸事件，设置点击其他区域弹窗消失
        customView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow= null;
                    add.setBackgroundResource(R.drawable.add);
                }
                return false;
            }
        });

        //下拉菜单按钮
        x_propose = (Button)customView.findViewById(R.id.x_propose);
        x_propose.setOnClickListener(this);
        x_comment = (Button)customView.findViewById(R.id.x_comment);
        x_comment.setOnClickListener(this);
        x_forgive = (Button)customView.findViewById(R.id.x_forgive);
        x_forgive.setOnClickListener(this);
    }


}
