package share;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heavn.student.R;

import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2017/8/1 0001.
 */

public class WriteWordsActivity extends Activity implements View.OnClickListener{
    private Button bar_cancel;
    private Button bar_report;
    private TextView bar_title;
    private EditText editText;
    private CheckBox is_secret;
    private String s_content;
    private int styleId;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.write_words_layout);

        bar_cancel = (Button)findViewById(R.id.bar_cancel);
        bar_cancel.setOnClickListener(this);
        bar_report = (Button)findViewById(R.id.bar_report);
        bar_report.setOnClickListener(this);

        bar_title = (TextView)findViewById(R.id.bar_title);
        editText = (EditText)findViewById(R.id.editText);
        is_secret = (CheckBox) findViewById(R.id.is_secret);

        Intent intent0 = getIntent();
        String s = intent0.getStringExtra("StyleId");
        styleId = Integer.valueOf(s).intValue();

        //设置标题栏文字
        if (styleId == 0){
            bar_title.setText("表白");
            type= "表白";
        } else if(styleId == 1){
            bar_title.setText("吐槽");
            type= "吐槽";
        } else if(styleId == 2){
            bar_title.setText("原谅");
            type= "原谅";
        }

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        switch (i){
            //取消，返回上一级
            case R.id.bar_cancel:
                Intent intent = new Intent(WriteWordsActivity.this,ShareMainActivity.class);
                startActivity(intent);
                finish();
                break;
            //保存所写的内容
            case R.id.bar_report:
                s_content = editText.getText().toString();
                Content content = new Content();
                content.setContent(s_content);
                content.setAccount(ShareMainActivity.s_account);
                if(is_secret.isChecked()){
                    content.setNickname("匿名");
                }else{
                    content.setNickname(ShareMainActivity.s_nickname);
                }
                content.setLocation("软件园校区");
                content.setType(type);
                content.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if(e == null){
                            Toast.makeText(WriteWordsActivity.this, "发表成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                //活动之间跳转延时1秒
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent intent1 = new Intent(WriteWordsActivity.this,ShareMainActivity.class);
                        intent1.putExtra("account", ShareMainActivity.s_account);
                        intent1.putExtra("nickname",ShareMainActivity.s_nickname);
                        startActivityForResult(intent1,1);
                        finish();
                    }
                }, 1000);
                break;
        }
    }
}
