package association;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.heavn.student.R;

import java.util.List;

import yue.FullImage;
import yue.GetImageByUrl;

/**
 * Created by 张宇 on 2017/7/20.
 */

//显示社团信息的的Adapter
public class AssociationAdapter extends RecyclerView.Adapter<AssociationAdapter.ViewHolder>
{
    List<Association> massociation;
    private Context context;
    ViewHolder viewholder;
    Bitmap bitmap;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView assImage;
        TextView assName;
        public ViewHolder(View v){
            super(v);
            assImage=(ImageView)v.findViewById(R.id.ass_image);
            assName=(TextView)v.findViewById(R.id.ass_text);
        }

    }
    public AssociationAdapter(Context context,List<Association> associations){
        this.context=context;
        massociation=associations;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.ass_item,parent,false);
         viewholder=new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Association a = massociation.get(position);
        holder.assName.setText(a.getName());
        holder.assName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(a.getInfo());
                builder.setPositiveButton("确定",null);
                builder.show();
            }
        });
        holder.assImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FullImage.class);
                intent.putExtra("imageUrl",a.getImagepath());
                context.startActivity(intent);
            }
        });
        GetImageByUrl getImageByUrl = new GetImageByUrl();
        getImageByUrl.setImage(holder.assImage,a.getImagepath());
    }

    @Override
    public int getItemCount() {
        return massociation.size();
    }
}
