package share;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.heavn.student.R;

import java.util.List;

/**
 * Created by Administrator on 2017/8/2 0002.
 */

public class CommentItemAdapter extends ArrayAdapter<ContentComment>{
    private int resourceId;
    private TextView from_name;
    private TextView to_name;
    private TextView comment_content;
    private ContentComment item;


    public CommentItemAdapter(Context context,int textViewResourceId,List<ContentComment> objects) {
        super(context,textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        item = getItem(position);

        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);

        from_name = (TextView)view.findViewById(R.id.from_name);
        from_name.setText(item.getFromName()+" ");
        to_name = (TextView)view.findViewById(R.id.to_name);
        to_name.setText(item.getToName());
        comment_content = (TextView)view.findViewById(R.id.comment_content);
        comment_content.setText(" ："+item.getComments());

        return view;
    }

}
