package share;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.heavn.student.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/1 0001.
 */

public class MineActivity extends Activity implements View.OnClickListener{
    private List<MyItem> lists = new ArrayList<>();
    private ListView listView;
    //三个碎片管理
    private FragmentManager fragmentManager;
    private share.AllMineFragment allMineFragment;
    private ProposeFragment proposeFragment;
    private ForgiveFragment forgiveFragment;
    private CommentFragment commentFragment;
    private TextView my_nickname;
    private TextView title;

    private ImageButton my_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mine_layout);

        title = (TextView)findViewById(R.id.my_title);
        title.setText("我的心里话");

        listView = (ListView)findViewById(R.id.my_list);
        my_back = (ImageButton)findViewById(R.id.my_back);
        my_back.setOnClickListener(this);
        fragmentManager = getFragmentManager();

        my_nickname = (TextView)findViewById(R.id.my_nickname);
        my_nickname.setText(ShareMainActivity.s_nickname);

//        init();

//        final MyItemAdapter myItemAdapter = new MyItemAdapter(MineActivity.this,R.layout.my_item_layout,lists);
//        listView.setAdapter(myItemAdapter);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideAllFragment(fragmentTransaction);
        if (allMineFragment == null) {
            allMineFragment = new share.AllMineFragment();
            fragmentTransaction.add(R.id.framelayout, allMineFragment);
        } else {
            fragmentTransaction.show(allMineFragment);
        }
        fragmentTransaction.commit();

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                hideAllFragment(fragmentTransaction);
//                if(position == 0){
//                    if (allMineFragment == null) {
//                        allMineFragment = new AllMineFragment();
//                        fragmentTransaction.add(R.id.framelayout, allMineFragment);
//                    } else {
//                        fragmentTransaction.show(allMineFragment);
//                    }
//                }
//                if(position == 1){
//                    if (proposeFragment == null) {
//                        proposeFragment = new ProposeFragment();
//                        fragmentTransaction.add(R.id.framelayout, proposeFragment);
//                    } else {
//                        fragmentTransaction.show(proposeFragment);
//                    }
//                }
//                else if(position == 2){
//                    if (commentFragment == null) {
//                        commentFragment = new CommentFragment();
//                        fragmentTransaction.add(R.id.framelayout, commentFragment);
//                    } else {
//                        fragmentTransaction.show(commentFragment);
//                    }
//                }
//                else if(position == 3){
//                    if (forgiveFragment == null) {
//                        forgiveFragment = new ForgiveFragment();
//                        fragmentTransaction.add(R.id.framelayout, forgiveFragment);
//                    } else {
//                        fragmentTransaction.show(forgiveFragment);
//                    }
//                }
//                fragmentTransaction.commit();
//            }
//        });
//
//        listView.setFocusable(true);
//        listView.setSelection(0);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        switch (i){
            case R.id.my_back:
//                Intent intent = new Intent(this,ShareMainActivity.class);
//                intent.putExtra("account", ShareMainActivity.s_account);
//                intent.putExtra("nickname",ShareMainActivity.s_nickname);
//                startActivity(intent);
                finish();
                break;
        }
    }

//    public void init(){
//        MyItem item = new MyItem(R.drawable.head,"我的心里话");
//        lists.add(item);
//        item = new MyItem(R.drawable.head,"我的表白");
//        lists.add(item);
//        item = new MyItem(R.drawable.head,"我的吐槽");
//        lists.add(item);
//        item = new MyItem(R.drawable.head,"我的原谅");
//        lists.add(item);
//    }

    //隐藏所有的fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (allMineFragment != null) fragmentTransaction.hide(allMineFragment);
        if (proposeFragment != null) fragmentTransaction.hide(proposeFragment);
        if (commentFragment != null) fragmentTransaction.hide(commentFragment);
        if (forgiveFragment != null) fragmentTransaction.hide(forgiveFragment);
    }

}
