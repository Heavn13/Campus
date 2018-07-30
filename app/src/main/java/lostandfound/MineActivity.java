package lostandfound;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.heavn.student.R;

import java.util.ArrayList;
import java.util.List;

import share.AllMineFragment;
import share.ShareMainActivity;

/**
 * Created by Administrator on 2017/8/7 0007.
 */

public class MineActivity extends Activity implements View.OnClickListener{
    private ImageButton my_back;
    private ImageButton my_head;
    private MyListview my_list;
    private List<Lost> lists = new ArrayList<>();
    private ItemAdapter itemAdapter;
    private String image_path = "";
    private String image_path2 = "";
    private String image_path3 = "";
    private TextView title;
    private TextView my_nickname;
    private lostandfound.AllMineFragment allMineFragment;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mine_layout);

        title = (TextView)findViewById(R.id.my_title);
        title.setText("我的失物招领");

        my_back = (ImageButton)findViewById(R.id.my_back);
        my_back.setOnClickListener(this);

        my_nickname = (TextView)findViewById(R.id.my_nickname);
        my_nickname.setText(LostMainActivity.s_nickname);

//        init();

//        final MyItemAdapter myItemAdapter = new MyItemAdapter(MineActivity.this,R.layout.my_item_layout,lists);
//        listView.setAdapter(myItemAdapter);
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideAllFragment(fragmentTransaction);
        if (allMineFragment == null) {
            allMineFragment = new lostandfound.AllMineFragment();
            fragmentTransaction.add(R.id.framelayout, allMineFragment);
        } else {
            fragmentTransaction.show(allMineFragment);
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        switch (i){
            case R.id.my_back:
//                Intent intent = new Intent(this,LostMainActivity.class);
//                intent.putExtra("account",LostMainActivity.s_account);
//                intent.putExtra("nickname",LostMainActivity.s_nickname);
//                startActivity(intent);
                finish();
                break;
            case R.id.my_head:
                break;
            default:
                break;
        }
    }

    //计算listView的定高
    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
//        Log.e("height",""+comment_lists.size());
        for (int i = 0; i < lists.size(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
//            Log.e("height",""+listItem.getMeasuredHeight());
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (lists.size()+1));
        listView.setLayoutParams(params);
    }

    //隐藏所有的fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (allMineFragment != null) fragmentTransaction.hide(allMineFragment);
    }

}
