package lostandfound;

import android.app.Fragment;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.heavn.student.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2017/8/4 0004.
 */

public class BaoTuQuanFragment extends Fragment {
    private List<Lost> lists = new ArrayList<>();
    private ItemAdapter itemAdapter;
    private ListView listView;
    private String image_path = "";
    private String image_path2 = "";
    private String image_path3 = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_layout,container,false);

        //刷新管理器
        NotificationManager manager = (NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(1);

        init();

        listView = (ListView)view.findViewById(R.id.lists);
        itemAdapter = new ItemAdapter(getActivity(),R.layout.item_layout,lists);
        listView.setAdapter(itemAdapter);

        return view;
    }

    public void init(){
        BmobQuery<Lost> bmobQuery = new BmobQuery<>();
        //前面加个“-”为倒序排列
        bmobQuery.order("-createdAt");
        bmobQuery.addWhereEqualTo("location","趵突泉校区");
        bmobQuery.findObjects(new FindListener<Lost>() {
            @Override
            public void done(List<Lost> list, BmobException e) {
                Lost item;
                for(Lost i : list){
                    //获取上传图片的url
                    image_path = i.getIcon();
//                    未上传的图片不要显示
                    if(i.getIcon2() != null){
                        image_path2 = i.getIcon2();
                    }else image_path2 = "";
                    if(i.getIcon3() != null){
                        image_path3 = i.getIcon3();
                    }else image_path3 = "";

                    Log.e("path",image_path);
                    //将url传到itemAdapter中  在itemAdapter中加载出来
                    item = new Lost(i.getObjectId(),i.getNickname(),i.getThings_name(),i.getContent(),image_path,image_path2,image_path3,i.getLost_date(),
                            i.getLost_location(),i.getContact(),i.getLocation(),i.getCreatedAt(),i.getComment(),i.getCount());
                    lists.add(item);
                    //通知Adapter要刷新数据
                    itemAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
