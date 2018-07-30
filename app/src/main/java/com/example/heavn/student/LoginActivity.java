package com.example.heavn.student;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText usernamelg;//用户名  “请输入真实姓名”
    private EditText passwordlg;//密码
    private Button login,rigister;
    private String userlg,passlg;
    public static CheckBox remember;
    private SharedPreferences preferences;
    public static SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);

        //默认初始化
        Bmob.initialize(this, "c7ca59964874653b57e437a721acb166");

        //初始化各组件
        usernamelg= (EditText) findViewById(R.id.login_username);
        passwordlg= (EditText) findViewById(R.id.login_password);
        remember= (CheckBox) findViewById(R.id.cb1);
        login= (Button) findViewById(R.id.user_login_button);
        rigister= (Button) findViewById(R.id.user_register_button);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember = preferences.getBoolean("remember_password",false);

        //记住密码
        if(isRemember){
            String account1 = preferences.getString("用户名","");
            String password1 = preferences.getString("密码","");
            usernamelg.setText(account1);
            passwordlg.setText(password1);
            remember.setChecked(true);
        }
        else{
            usernamelg.setText("");
            passwordlg.setText("");
            remember.setChecked(false);
        }

        login.setOnClickListener(this);
        rigister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        //Log.e("A","OK");
        if (v.equals(login)){
            userlg=usernamelg.getText().toString();
            passlg=passwordlg.getText().toString();
            //Toast.makeText(LoginActivity.this,userlg,Toast.LENGTH_SHORT).show();
            //Toast.makeText(LoginActivity.this,passlg,Toast.LENGTH_SHORT).show();
            //记住密码提交数据
            editor = preferences.edit();
            if(remember.isChecked()){
                editor.putBoolean("remember_password",true);
                editor.putString("用户名",userlg);
                editor.putString("密码",passlg);
            }else{
                editor.clear();
            }
            editor.commit();

            //获取一行数据
            BmobQuery<College> bmobQuery = new BmobQuery<>();

            //查询username中，数值等于userlg的数据
            bmobQuery.addWhereEqualTo("StudentId",userlg);
            bmobQuery.findObjects(new FindListener<College>() {
                @Override
                public void done(List<College> list, BmobException e) {
                    if (e==null){
                        //Toast.makeText(LoginActivity.this,""+list.size(),Toast.LENGTH_SHORT).show();
                        for (College user:list){
                            if (user.getStudentId().equals(userlg)&&user.getPassword().equals(passlg)){
                                //获取已注册用户的id
                                String account = user.getStudentId();
                                String userid = user.getObjectId();
                                String nickname = user.getNickname();
                                Toast.makeText(LoginActivity.this,"登录成功，即将为您服务",Toast.LENGTH_SHORT).show();
                                //跳转到  主界面
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                                Log.e("zhi",userid+nickname+account);
                                intent.putExtra("account",account);
                                intent.putExtra("nickname",nickname);
                                intent.putExtra("id",userid);
                                startActivity(intent);
                                finish();
                            }else if (user.getStudentId().equals(userlg)&&!(user.getPassword().equals(passlg))){
                                Toast.makeText(LoginActivity.this,"密码错误，请重新输入",Toast.LENGTH_SHORT).show();
                            }else if (!(user.getStudentId().equals(userlg))){
                                Toast.makeText(LoginActivity.this,"登录失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(LoginActivity.this,"请先进行注册",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, beforeRegActivity.class);
                                startActivity(intent);

                            }
                        }
                    }
                }
            });


        }

        //点击注册按钮，跳转到“注册界面”
        else if (v.equals(rigister)){
            Toast.makeText(LoginActivity.this,"您即将进入注册页面",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, beforeRegActivity.class);
            startActivity(intent);
        }

    }
}
