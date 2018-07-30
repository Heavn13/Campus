package com.example.heavn.student;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class StuRigisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText studentid;//学号
    private EditText usernamelg;//用户名  “请输入真实姓名”
    private EditText passwordlg;//密码
    private EditText nickname;//昵称
    private EditText phonenum;//绑定手机号，用于找回密码
    private EditText xueyuan;//学院
    private EditText major;//专业

    private static final String[] m = {"中心校区", "洪楼校区", "千佛山校区", "软件园校区", "兴隆山校区", "趵突泉校区", "青岛校区", "山大威海分校"};
    private static final String[] n = {"大一", "大二", "大三", "大四"};

    private TextView campus;
    private TextView grade;
    private Spinner sp;
    private Spinner sp2;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter2;
    String stu, user, pass, nick, phone, xue, maj;
    public String grade_mes, campus_mes;

    Button makesure;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_register);

        makesure = (Button) findViewById(R.id.makesure);


        studentid = (EditText) findViewById(R.id.rigis_studentid);
        usernamelg = (EditText) findViewById(R.id.rigis_username);
        passwordlg = (EditText) findViewById(R.id.rigis_password);
        nickname = (EditText) findViewById(R.id.rigis_nickname);
        phonenum = (EditText) findViewById(R.id.rigis_phonenum);
        xueyuan = (EditText) findViewById(R.id.rigis_xueyua);
        major = (EditText) findViewById(R.id.rigis_major);

        campus = (TextView) findViewById(R.id.campus);
        sp = (Spinner) findViewById(R.id.spin);
        //将可选内容与ArrayAdapter联系起来
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, m);

        //设置下拉列表的风格
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        //将adapter添加到spinner中
        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                grade_mes = (String) parent.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp.setVisibility(View.VISIBLE);


        grade = (TextView) findViewById(R.id.rigis_grade);
        sp2 = (Spinner) findViewById(R.id.spin2);
        //将可选内容与ArrayAdapter联系起来
        adapter2 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, n);

        //设置下拉列表的风格
        adapter2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        //将adapter添加到spinner中
        sp2.setAdapter(adapter2);

        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                campus_mes = (String)parent.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp.setVisibility(View.VISIBLE);

        studentid.setOnClickListener(this);
        usernamelg.setOnClickListener(this);
        passwordlg.setOnClickListener(this);
        nickname.setOnClickListener(this);
        phonenum.setOnClickListener(this);
        xueyuan.setOnClickListener(this);
        major.setOnClickListener(this);
        makesure.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v.equals(makesure)) {
            Toast.makeText(StuRigisterActivity.this, "信息即将保存，请稍后", Toast.LENGTH_SHORT).show();


            //获取框中的文本，并转换为对应格式
            stu = studentid.getText().toString();
            user = usernamelg.getText().toString();
            pass = passwordlg.getText().toString();
            nick = nickname.getText().toString();
            phone = phonenum.getText().toString();
            xue = xueyuan.getText().toString();
            maj = major.getText().toString();



            if (!(stu.equals("")) && !(user.equals("")) && !(pass.equals("")) && !(nick.equals("")) && !(phone.equals("")) && !(xue.equals("")) && !(maj.equals(""))) {

                College college1 = new College();
                college1.setStudentId(stu);
                college1.setName(user);
                college1.setPassword(pass);
                college1.setNickname(nick);
                college1.setPhone(phone);
                college1.setXueyuan(xue);
                college1.setMajor(maj);
                college1.setGrade(grade_mes);
                college1.setCampus(campus_mes);


                college1.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            //添加数据成功
                            Toast.makeText(StuRigisterActivity.this, "添加数据成功", Toast.LENGTH_SHORT).show();

                        } else {
                            //添加数据失败
                            Toast.makeText(StuRigisterActivity.this, "添加数据失败" + e.getMessage(), Toast.LENGTH_SHORT).show();


                        }

                    }
                });
                Toast.makeText(StuRigisterActivity.this, "已保存您的个人信息，即将返回登录界面", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(StuRigisterActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);

            } else {
                Toast.makeText(StuRigisterActivity.this, "请将您的个人信息填写完整", Toast.LENGTH_SHORT).show();

            }


        }

    }





}
