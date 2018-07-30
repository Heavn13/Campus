package share;

import android.app.Fragment;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.heavn.student.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2017/8/1 0001.
 * 原谅
 */

public class ForgiveFragment extends Fragment {
//    原谅内容的数组
    private List<Content> forgive_lists = new ArrayList<Content>();
    private ItemAdapter itemAdapter;
    private MyListview listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.inside_words_layout,container,false);

        //刷新管理器
        NotificationManager manager = (NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(1);

        //设置长度为零才初始化
        if(forgive_lists.size() == 0){
            init();
        }else{
            forgive_lists.clear();
            init();
        }

        listView = (MyListview) view.findViewById(R.id.propose_list);
        itemAdapter = new ItemAdapter(getActivity(),R.layout.inside_words_item_layout,forgive_lists);
        listView.setAdapter(itemAdapter);
        setListViewHeightBasedOnChildren(listView);

        return view;
    }

    //listView初始化，查询Bmob中类型为2，即为原谅的内容，加载到listView中显示出来
    public void init(){
        BmobQuery<Content> content0 = new BmobQuery<>();
        content0.addWhereEqualTo("type","原谅");
        content0.order("-createdAt");
        content0.findObjects(new FindListener<Content>() {
            @Override
            public void done(List<Content> list, BmobException e) {
                Content item;
                for(Content i : list){
                    item = new Content(i.getObjectId(),i.getNickname(),
                            i.getContent(),i.getCreatedAt(),i.getLocation(),i.getComment(),i.getCount());
                    forgive_lists.add(item);
                    //通知Adapter要刷新数据
                    itemAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    //计算listView的定高
    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
//        Log.e("height",""+comment_lists.size());
        for (int i = 0; i < forgive_lists.size(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
//            Log.e("height",""+listItem.getMeasuredHeight());
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (forgive_lists.size()+1));
        listView.setLayoutParams(params);
    }
}
