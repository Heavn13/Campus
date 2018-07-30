package yue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.heavn.student.R;

import java.util.List;

/**
 * Created by 张宇 on 2017/7/24.
 */

public class CommentAdapter extends ArrayAdapter<AppointComment> {
    int resourceId;
    String myID;
    public CommentAdapter(Context context, int textViewResourceId, List<AppointComment> objects ,String myID){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
        this.myID=myID;
    };
    public View getView(int position, View convertView, ViewGroup parent){
        AppointComment a=getItem(position);
        String comment=a.getComments();
        String fromID=a.getFromID();
        String toID=a.getToID();
        String fromName=a.getFromName();
        String toName=a.getToName();
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView comment_from=(TextView)view.findViewById(R.id.comment_from);
        TextView comment_to=(TextView)view.findViewById(R.id.comment_to);
        TextView comment_content=(TextView)view.findViewById(R.id.comment_content);
        if(myID.equals(fromID)){
            comment_from.setText("我");
            comment_to.setText(toName);

        }
       else if(myID.equals(fromID)){
            comment_from.setText(fromName);
            comment_to.setText("我");

        }
        else{
            comment_from.setText(fromName);
            comment_to.setText(toName);
        }
        comment_content.setText("："+comment);
        return view;
    }
}
