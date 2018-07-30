package yue;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.heavn.student.R;

public class AppointmentActivity extends AppCompatActivity {

    private ImageButton imageButton;
    private TextView nickname;
    private RadioButton go_date;
    private RadioButton find_date;
    private RadioGroup radioGroup;
    public static String s_nickname;
    public static String s_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        s_nickname = getIntent().getStringExtra("nickname");
        s_account = getIntent().getStringExtra("account");
         replaceFragment(new FindFragment());

        nickname = (TextView)findViewById(R.id.yue_nickname);
        nickname.setText(s_nickname);
        imageButton = (ImageButton) findViewById(R.id.back);
        go_date = (RadioButton) findViewById(R.id.go_date);
        find_date = (RadioButton) findViewById(R.id.find_date);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (go_date.getId() == checkedId) {
                    replaceFragment(new GoFragment());
                    go_date.setBackgroundResource(R.color.lightBlue);
                    find_date.setBackground(null);

                }
                if (find_date.getId() == checkedId) {
                    replaceFragment(new FindFragment());
                    find_date.setBackgroundResource(R.color.lightBlue);
                    go_date.setBackground(null);
                }
            }
        });
    }
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.commit();
    }
}
