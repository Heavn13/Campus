package com.example.heavn.student;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class OrgRigisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText usernamelg;//用户名  “请输入真实姓名”
    private EditText passwordlg;//密码
    private EditText nickname;//昵称
    private EditText phonenum;//绑定手机号，用于找回密码


    String user,pass,nick,phone;

    Button makesure;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_org_rigister);

        makesure= (Button) findViewById(R.id.makesure);


        usernamelg= (EditText) findViewById(R.id.rigis_username);
        passwordlg= (EditText) findViewById(R.id.rigis_password);
        nickname= (EditText) findViewById(R.id.rigis_nickname);
        phonenum= (EditText) findViewById(R.id.rigis_phonenum);



        usernamelg.setOnClickListener(this);
        passwordlg.setOnClickListener(this);
        nickname.setOnClickListener(this);
        phonenum.setOnClickListener(this);
        makesure.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        if (v.equals(makesure)){
            Toast.makeText(OrgRigisterActivity.this, "信息即将保存，请稍后", Toast.LENGTH_SHORT).show();


            //获取框中的文本，并转换为对应格式
            user=usernamelg.getText().toString();
            pass=passwordlg.getText().toString();
            nick=nickname.getText().toString();
            phone=phonenum.getText().toString();


            if (!(user.equals(""))&&!(pass.equals(""))&&!(nick.equals(""))&&!(phone.equals(""))){

                College college1=new College();
                college1.setName(user);
                college1.setPassword(pass);
                college1.setNickname(nick);
                college1.setPhone(phone);

                college1.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            //添加数据成功
                            Toast.makeText(OrgRigisterActivity.this, "添加数据成功", Toast.LENGTH_SHORT).show();

                        } else {
                            //添加数据失败
                            Toast.makeText(OrgRigisterActivity.this, "添加数据失败" + e.getMessage(), Toast.LENGTH_SHORT).show();


                        }

                    }
                });
                Toast.makeText(OrgRigisterActivity.this, "已保存您的个人信息，即将返回登录界面", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OrgRigisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }else {
                Toast.makeText(OrgRigisterActivity.this, "请将您的个人信息填写完整" , Toast.LENGTH_SHORT).show();

            }


        }

    }
}
