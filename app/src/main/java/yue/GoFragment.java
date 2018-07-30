package yue;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.heavn.student.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class GoFragment extends Fragment {
    private String type;
    private String month;
    private String day;
    private String hour;
    private String minute;
    private String all_day="31";
    private int  year=0;
    private  String  student_ID=null;//这里要改
    private String name_temp=null;

    public GoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v=inflater.inflate(R.layout.fragment_go, container, false);
        student_ID=AppointmentActivity.s_account;
        name_temp=AppointmentActivity.s_nickname;
        Calendar c=Calendar.getInstance();
        year=c.get(Calendar.YEAR);
        //对界面元素进行初始化和监听
        setAddDate(v);
        return  v;
    }

    public void setAddDate(View v){
        final Spinner app_type=(Spinner)v.findViewById(R.id.app_type);
        final Spinner app_month=(Spinner)v.findViewById(R.id.spin_month);
        final Spinner app_day31=(Spinner)v.findViewById(R.id.spin_day_31);
        final Spinner app_day30=(Spinner)v.findViewById(R.id.spin_day_30);
        final Spinner app_day29=(Spinner)v.findViewById(R.id.spin_day_29);
        final Spinner app_day28=(Spinner)v.findViewById(R.id.spin_day_28);
        final Spinner app_hour=(Spinner)v.findViewById(R.id.spin_hour);
        final Spinner app_minute=(Spinner)v.findViewById(R.id.spin_mintue);
        final EditText editText=(EditText)v.findViewById(R.id.app_demand);
        final Button go_date_button=(Button)v.findViewById(R.id.go_date_button);
        app_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month=(String)app_month.getSelectedItem();
                switch (month){
                    case "1月":
                    case "3月":
                    case "5月":
                    case "7月":
                    case "8月":
                    case "10月":
                    case "12月":
                        all_day="31";
                        app_day31.setVisibility(View.VISIBLE);
                        app_day30.setVisibility(View.GONE);
                        app_day29.setVisibility(View.GONE);
                        app_day28.setVisibility(View.GONE);
                        //31
                        break;
                    case "4月":
                    case "6月":
                    case "9月":
                    case "11月":
                        //30
                        all_day="30";
                        app_day31.setVisibility(View.GONE);
                        app_day30.setVisibility(View.VISIBLE);
                        app_day29.setVisibility(View.GONE);
                        app_day28.setVisibility(View.GONE);

                        break;
                    case "2月":
                        //分情况
                        //这里在进行修改
                        if((year%4==0&&year%100!=0)||year%400==0){
                            all_day="29";
                            app_day31.setVisibility(View.GONE);
                            app_day30.setVisibility(View.GONE);
                            app_day29.setVisibility(View.VISIBLE);
                            app_day28.setVisibility(View.GONE);
                        }else{
                            all_day="28";
                            app_day31.setVisibility(View.GONE);
                            app_day30.setVisibility(View.GONE);
                            app_day29.setVisibility(View.GONE);
                            app_day28.setVisibility(View.VISIBLE);
                        }
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        go_date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText()!=null) {
                    type=(String)app_type.getSelectedItem();
                    month = (String) app_month.getSelectedItem();
                    switch (all_day) {
                        case "31":
                            day = (String) app_day31.getSelectedItem();
                            break;
                        case "30":
                            day = (String) app_day30.getSelectedItem();
                            break;
                        case "29":
                            day = (String) app_day29.getSelectedItem();
                            break;
                        case "28":
                            day = (String) app_day28.getSelectedItem();
                            break;
                    }
                    hour = (String) app_hour.getSelectedItem();
                    minute = (String) app_minute.getSelectedItem();
                    if(month.length()==2){
                        month="0"+month.substring(0,1);
                    }
                    else {
                        month=month.substring(0,2);
                        Log.e("月",month);
                    }
                    String time_str = ""+year+"-" + month + "-" + day + " " + hour + ":" + minute;
                    try {
                        if( DateCompare(time_str)){
                            Toast.makeText(getActivity(), "内容上传中，请稍后...", Toast.LENGTH_SHORT).show();
                            Appointment appo=new Appointment(type,editText.getText().toString(),time_str,student_ID,name_temp,"true");
                            appo.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if(e==null){
                                        Log.e("内容","保存成功");
                                        Toast.makeText(getActivity(), "您的内容已上传成功", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(getActivity(), "您输入的时间有误，请重新操作", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    //判断输入的时间是否合法
    public boolean DateCompare(String s) throws Exception {
        //设定时间的模板
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //得到指定模范的时间
        Date d1 = sdf.parse(s);
        // Date d2 = sdf.parse(s2);
        Date curDate    =   new    Date(System.currentTimeMillis());
        //比较
        if(((d1.getTime() - curDate.getTime())/(3600*1000)) >0) {
            //System.out.println("大于三天");

            Log.e("time","超过一天"+ ((d1.getTime() - curDate.getTime())/(3600*1000)));
            return true;
        }
        else{
            Log.e("time23","超过一天"+ ((d1.getTime() - curDate.getTime())/(3600*1000)));
        }
        return false;
    }

}
