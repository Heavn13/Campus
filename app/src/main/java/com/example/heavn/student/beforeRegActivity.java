package com.example.heavn.student;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import association.AssRegisteActivity;

public class beforeRegActivity extends AppCompatActivity {
    private RadioButton rd1,rd2,rd3;
    private RadioGroup group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_before_reg);


        rd1= (RadioButton) findViewById(R.id.radioStu);
        rd2= (RadioButton) findViewById(R.id.radioTea);
        rd3= (RadioButton) findViewById(R.id.radioOrg);
        group= (RadioGroup) findViewById(R.id.radioGroup);


        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                //获取变更后的选中项的ID
                rd1.setChecked(false);
                int radioButtonId = group.getCheckedRadioButtonId();
                //根据ID获取RadioButton的实例
                RadioButton rb = (RadioButton)beforeRegActivity.this.findViewById(radioButtonId);
                if (rb.getText().equals("学生")){
                    Intent intent=new Intent(beforeRegActivity.this,StuRigisterActivity.class);
                    startActivity(intent);
                }else if (rb.getText().equals("辅导员")){
                    Intent intent=new Intent(beforeRegActivity.this,TeaRigisterActivity.class);
                    startActivity(intent);

                }else if (rb.getText().equals("社团或学生组织")){
                    Intent intent=new Intent(beforeRegActivity.this, AssRegisteActivity.class);
                    startActivity(intent);
                    finish();

                }
            }
        });

    }



    }

