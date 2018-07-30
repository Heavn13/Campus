package yue;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.heavn.student.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends Fragment {
   MyListview listView;
    List<Appointment> onDataApps=new ArrayList<>();
    //  List<String> noOnDataAppsID=new ArrayList<>();
    String tempID=null;
    //QueryAppAdapter adapter=null;
    ItemAdapter adapter=null;
    String name_tempt=null;

    public FindFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_find, container, false);
        tempID=AppointmentActivity.s_account;
        name_tempt=AppointmentActivity.s_nickname;
        initViews(v);
        return v ;
    }



    public void initViews(View v){
        final Spinner query_spin=(Spinner)v.findViewById(R.id.query_app_type);
        listView=(MyListview)v. findViewById(R.id.query_list);
        //queryDifferentType((String) query_spin.getSelectedItem());
        query_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                queryDifferentType((String) query_spin.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void queryDifferentType(final String type){
        //Log.e("type",type);
        adapter=null;
        onDataApps.clear();
        BmobQuery<Appointment> appointmentBmobQuery=new BmobQuery<>();
        appointmentBmobQuery.order("-createdAt");
        appointmentBmobQuery.addWhereEqualTo("type",type);
        appointmentBmobQuery.addWhereEqualTo("ontime","true");
        appointmentBmobQuery.findObjects(new FindListener<Appointment>() {
            @Override
            public void done(List<Appointment> list, BmobException e) {
                if(e==null){
                    //检测这些list是不是合法
                    try {
                        //testAppIfOnTime(list);
                        for(int i=0;i<list.size();i++){
                            //  String s=app.get(i).getTime();
                            //如果没过期
                            if(DateCompare(list.get(i).getTime())){
                                Appointment temp = list.get(i);
                                //temp.setQuery_id(list.get(i).getObjectId());
                                onDataApps.add(temp);
                                //app.get(i).setOntime(true);
//                                Log.e("这个语句1","执行了");
                            }else{
                                //noOnDataAppsID.add(app.get(i).getObjectId());
                                // list.get(i).setOntime(false);
                                //list.get(i).setOntime(false);
                                String modifyID = list.get(i).getObjectId();
                                Appointment temp = new Appointment();
                                // temp.setObjectId(modifyID);
                                temp.setOntime("false");
                                temp.update(modifyID,new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if(e == null){
//                                            Log.e("这个~~","执行了");
                                        }
                                    }
                                });


                            }
                        }

                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    adapter = new ItemAdapter(getActivity(),R.layout.yue_inside_words_item_layout,onDataApps,tempID,name_tempt);
                    listView.setAdapter(adapter);
                    setListViewHeightBasedOnChildren(listView);
                }
                else{
//                    Log.e("问题",""+e);
                }
            }
        });
    }

    public void testAppIfOnTime(List<Appointment> app) throws Exception {
        for(int i=0;i<app.size();i++){
            //  String s=app.get(i).getTime();
            //如果没过期
            Log.e("时间问题",app.get(i).getTime());
            if(DateCompare(app.get(i).getTime())){
                onDataApps.add(app.get(i));
                //app.get(i).setOntime(true);
                Log.e("这个语句","执行了");
            }else{
                //noOnDataAppsID.add(app.get(i).getObjectId());
                //app.get(i).setOntime(false);


            }
        }
    }

    public boolean DateCompare(String s) throws Exception {
        //设定时间的模板
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //得到指定模范的时间
        Date d1 = sdf.parse(s);
        Date curDate    =   new    Date(System.currentTimeMillis());
        //比较
        if(((d1.getTime() - curDate.getTime())/(3600*1000)) >0) {
            Log.e("time","合法"+ ((d1.getTime() - curDate.getTime())/(3600*1000)));
            return true;
        }
        else{
            Log.e("time","不合法"+ ((d1.getTime() - curDate.getTime())/(3600*1000)));
        }
        return false;
    }


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if(listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for(int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

}
