package com.example.heavn.student;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import association.RegisteActivity;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import lostandfound.LostMainActivity;
import share.ShareMainActivity;
import yue.AppointmentActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {
    private Button zhizhetongxing,shetuan,xinli,shiwu,zhizhejiehuo,ziliao;
    private TextView nickname;
    private TextView account;
    private String s_nickname;
    public static String s_account;
    public static String id;
    private Menu menu;
    private MenuItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        id = intent.getStringExtra("userid");
        s_nickname = intent.getStringExtra("nickname");
        s_account = intent.getStringExtra("account");

//        设置drawerlayout下半部分的内容
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        menu = navigationView.getMenu();
        View headerView = navigationView.getHeaderView(0);
        nickname = (TextView)headerView.findViewById(R.id.head_nickname);
        nickname.setText("昵称："+s_nickname);
        account = (TextView)headerView.findViewById(R.id.head_account);
        account.setText("学号："+s_account);

        BmobQuery<College> c = new BmobQuery<>();
        c.addWhereEqualTo("StudentId",s_account);
        c.findObjects(new FindListener<College>() {
            @Override
            public void done(List<College> list, BmobException e) {
                for(College i : list){
                    item = menu.findItem(R.id.nav_name);
                    item.setTitle("姓名： "+i.getName());
                    item = menu.findItem(R.id.nav_campus);
                    item.setTitle("校区： "+i.getCampus());
                    item = menu.findItem(R.id.nav_college);
                    item.setTitle("学院： "+i.getXueyuan());
                    item = menu.findItem(R.id.nav_major);
                    item.setTitle("专业： "+i.getMajor());
                    item = menu.findItem(R.id.nav_grade);
                    item.setTitle("年级： "+i.getGrade());
                }
            }
        });

        zhizhetongxing= (Button) findViewById(R.id.zhizhetongxing);
        shetuan= (Button) findViewById(R.id.shetuan);
        xinli= (Button) findViewById(R.id.xinli);
        shiwu= (Button) findViewById(R.id.shiwu);
//        zhizhejiehuo= (Button) findViewById(R.id.zhizhejiehuo);
//        ziliao= (Button) findViewById(R.id.ziliao);

        zhizhetongxing.setOnClickListener(this);
        shetuan.setOnClickListener(this);
        xinli.setOnClickListener(this);
        shiwu.setOnClickListener(this);
//        zhizhejiehuo.setOnClickListener(this);
//        ziliao.setOnClickListener(this);

//        设置标题的内容
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Campus");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id){
//            case R.id.nav_change_password:
//                Intent intent1 = new Intent(MainActivity.this,ChangePassword.class);
//                startActivity(intent1);
//                finish();
            case R.id.nav_change_account:
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.nav_exit:
                finish();
//                System.exit(0);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        int i = v.getId();
        switch (i){
            case R.id.zhizhetongxing:
                intent = new Intent(MainActivity.this, AppointmentActivity.class);
                intent.putExtra("account",s_account);
                intent.putExtra("nickname",s_nickname);
                startActivity(intent);
                break;
            case R.id.shetuan:
                intent = new Intent(MainActivity.this, RegisteActivity.class);
                intent.putExtra("account",s_account);
                startActivity(intent);
                break;
            case R.id.xinli:
                intent = new Intent(MainActivity.this, ShareMainActivity.class);
                intent.putExtra("account",s_account);
                intent.putExtra("nickname",s_nickname);
                startActivity(intent);
                break;
            case R.id.shiwu:
                intent = new Intent(MainActivity.this, LostMainActivity.class);
                intent.putExtra("account",s_account);
                intent.putExtra("nickname",s_nickname);
                startActivity(intent);
                break;
//            case R.id.zhizhejiehuo:
//                break;
//            case R.id.ziliao:
//                break;
            default:
                break;
        }
    }
}
