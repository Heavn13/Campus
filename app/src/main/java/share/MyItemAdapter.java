package share;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.heavn.student.R;

import java.util.List;

/**
 * Created by Administrator on 2017/8/1 0001.
 */

public class MyItemAdapter extends ArrayAdapter<MyItem>{
    private int resourceId;

    public MyItemAdapter(Context context, int textViewResourceId, List<MyItem> obejects){
        super(context,textViewResourceId,obejects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        MyItem myItem = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);

        ImageView imageView = (ImageView)view.findViewById(R.id.image);
        imageView.setImageResource(myItem.getImageId());
        TextView textView = (TextView)view.findViewById(R.id.my_items);
        textView.setText(myItem.getMy_items());
        return view;
    }
}
