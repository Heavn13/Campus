package com.example.heavn.student;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2017/9/17 0017.
 */

public class ChangePassword extends Activity {
    private TextView account;
    private EditText old_password;
    private EditText new_password1;
    private EditText new_password2;
    private Button confirm;
    public static boolean change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.change_password_layout);

        account = (TextView)findViewById(R.id.account1);
        account.setText(MainActivity.s_account);

        old_password = (EditText)findViewById(R.id.old_edit);
        new_password1 = (EditText)findViewById(R.id.new_edit1);
        new_password2 = (EditText)findViewById(R.id.new_edit2);

        confirm = (Button)findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });

    }
    public void changePassword(){
        final String old = old_password.getText().toString();
        final String new1 = new_password1.getText().toString();
        final String new2 = new_password2.getText().toString();
        
        BmobQuery<College> users = new BmobQuery<>();
        users.addWhereEqualTo("account",MainActivity.s_account);
        users.findObjects(new FindListener<College>() {
            @Override
            public void done(List<College> list, BmobException e) {
                if(e == null){
                    for(College i : list){
                        if(i.getStudentId().equals(MainActivity.s_account) && i.getPassword().equals(old)){
                            if(new1.equals(new2)){
                                College college = new College();
                                i.setStudentId(MainActivity.s_account);
                                college.setPassword(new1);
                                college.update(MainActivity.id, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        Toast.makeText(ChangePassword.this,"修改密码成功",Toast.LENGTH_SHORT).show();
                                        //重新设置登录界面
                                        LoginActivity.editor.clear();
                                        LoginActivity.editor.commit();
                                        LoginActivity.remember.setChecked(false);
                                        //发送强制下线的广播
                                        Intent intent = new Intent(ChangePassword.this,LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            }else{
                                Toast.makeText(ChangePassword.this,"两次输入的密码不一致",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(ChangePassword.this,"旧密码输入错误",Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            }
        });
    }
}
