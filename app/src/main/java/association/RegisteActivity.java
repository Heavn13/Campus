package association;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.heavn.student.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class RegisteActivity extends AppCompatActivity {
    private List<Association> associations=new ArrayList<>();
    private RecyclerView recyclerView;
    private ImageView she_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registe);
        setPic();

        she_back = (ImageView)findViewById(R.id.she_back);
        she_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void setPic(){
         recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        SpacesItemDecoration decoration=new SpacesItemDecoration(16);
        recyclerView.addItemDecoration(decoration);
        initAssInfo();

    }
    public void initAssInfo(){
        BmobQuery<Association> query=new BmobQuery<Association>();
        query.findObjects(new FindListener<Association>() {
            @Override
            public void done(List<Association> list, BmobException e) {
                if(e==null){
                    associations=list;
//                    Log.e("适配器设置",""+list.size());
                    AssociationAdapter adapter=new AssociationAdapter(RegisteActivity.this,associations);
                    recyclerView.setAdapter(adapter);
//                    Log.e("适配器","设置成功");
                }
            }
        });
    }

}
