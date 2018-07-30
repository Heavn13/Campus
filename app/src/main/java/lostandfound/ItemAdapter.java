package lostandfound;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heavn.student.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public class ItemAdapter extends ArrayAdapter<Lost>{
    private int resourceId;
//    头像
    private ImageView head_massage;
//    昵称
    private TextView nickname;
//    丢失物品名称
    private TextView things_name;
//    丢失物品内容描述
    private TextView content;
//    丢失图片1、 2、 3
    public ImageView lost_image1;
    private ImageView lost_image2;
    private ImageView lost_image3;
//    丢失时间
    private TextView lost_date;
    private TextView lost_location;
    private TextView contact;
    private TextView location;
    private TextView date;
    //    评论内容数组
    private List<ContentComment> comments = new ArrayList<>();
    //    评论适配器
    private CommentItemAdapter commentItemAdapter = null;
    //自定义listView
    private MyListview listView;
    //    评论编辑text按钮
    private TextView edit_comment;
    //    点赞人数显示
    private TextView well_done;
    //    弹出窗口编辑框发送内容
    private Button pop_send;
    //    弹出窗口编辑框
    private EditText pop_edit;
    //    单项内容的objectId
    private String id;
    private Lost item;
    //    单项内容
    private String s_content;
    //    评论内容
    private String s_comment;
    //    弹出窗口
    private PopupWindow popupWindow;
    //    点赞次数
    private int c;

    private ImageView image_good;
    private ImageView image_comment;

    public ItemAdapter(Context context, int textViewResourceId, List<Lost> obejects){
        super(context,textViewResourceId,obejects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        item = getItem(position);
        final Lost lost0 = item;

        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);

        nickname = (TextView)view.findViewById(R.id.nickname);
        nickname.setText(item.getNickname());
        things_name = (TextView)view.findViewById(R.id.things_name);
        things_name.setText(item.getThings_name());

        content = (TextView)view.findViewById(R.id.content);
        content.setText(item.getContent());

        lost_image1 = (ImageView) view.findViewById(R.id.lost_image1);
        lost_image2 = (ImageView)view.findViewById(R.id.lost_image2);
        lost_image3 = (ImageView)view.findViewById(R.id.lost_image3);
//        Log.e("image_path",item.getImage_path());

        lost_image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FullImageDetail.class);
                intent.putExtra("current","1");
                intent.putExtra("imageUrl1", lost0.getIcon());
                intent.putExtra("imageUrl2", lost0.getIcon2());
                intent.putExtra("imageUrl3", lost0.getIcon3());
                getContext().startActivity(intent);
            }
        });

        lost_image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FullImageDetail.class);
                intent.putExtra("current","2");
                intent.putExtra("imageUrl1", lost0.getIcon());
                intent.putExtra("imageUrl2", lost0.getIcon2());
                intent.putExtra("imageUrl3", lost0.getIcon3());
                getContext().startActivity(intent);
            }
        });

        lost_image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FullImageDetail.class);
                intent.putExtra("current","3");
                intent.putExtra("imageUrl1", lost0.getIcon());
                intent.putExtra("imageUrl2", lost0.getIcon2());
                intent.putExtra("imageUrl3", lost0.getIcon3());
                getContext().startActivity(intent);
            }
        });

        ImageDownloadTask imgtask =new ImageDownloadTask();
        /**这里是获取手机屏幕的分辨率用来处理 图片 溢出问题的。begin*/
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        imgtask.setDisplayWidth(dm.widthPixels);
        imgtask.setDisplayHeight(dm.heightPixels);
        imgtask.execute(item.getIcon(),lost_image1);
//
//        GetImageByUrl getImageByUrl = new GetImageByUrl();
//        getImageByUrl.setImage(lost_image1,item.getIcon());
        if(!item.getIcon2().equals("")){
            ImageDownloadTask imgtask2 =new ImageDownloadTask();
            /**这里是获取手机屏幕的分辨率用来处理 图片 溢出问题的。begin*/
            DisplayMetrics dm2 = getContext().getResources().getDisplayMetrics();
            imgtask2.setDisplayWidth(dm2.widthPixels);
            imgtask2.setDisplayHeight(dm2.heightPixels);
            imgtask2.execute(item.getIcon2(),lost_image2);
//            GetImageByUrl getImageByUr2 = new GetImageByUrl();
//            getImageByUr2.setImage(lost_image2,item.getIcon2());
        }
        if(!item.getIcon3().equals("")){
            ImageDownloadTask imgtask3 =new ImageDownloadTask();
            /**这里是获取手机屏幕的分辨率用来处理 图片 溢出问题的。begin*/
            DisplayMetrics dm3 = getContext().getResources().getDisplayMetrics();
            imgtask3.setDisplayWidth(dm3.widthPixels);
            imgtask3.setDisplayHeight(dm3.heightPixels);
            imgtask3.execute(item.getIcon3(),lost_image3);
//            GetImageByUrl getImageByUr3 = new GetImageByUrl();
//            getImageByUr3.setImage(lost_image3,item.getIcon3());
        }

        lost_date = (TextView)view.findViewById(R.id.lost_date);
        lost_date.setText(item.getLost_date());
        lost_location = (TextView)view.findViewById(R.id.lost_location);
        lost_location.setText(item.getLost_location());
        contact = (TextView)view.findViewById(R.id.contact);
        contact.setText(item.getContact());
        location = (TextView)view.findViewById(R.id.location);
        location.setText(item.getLocation());
        date = (TextView)view.findViewById(R.id.date);
        date.setText(item.getDate());

        //        onClickListener传数据需写在adapter里
        image_good = (ImageView)view.findViewById(R.id.image_good);
        image_good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = getItem(position);
                id = item.getId();
                //单个item获赞次数累加
                c = item.getCount();
//                Log.e("item",""+c);
                c++;
//                Log.e("item",""+id);
                item.setCount(c);
                //更新保存获赞次数
                Lost lost = new Lost();
                lost.increment("count");
                lost.update(id, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                    }
                });
                //listview数据更新
                notifyDataSetChanged();
            }
        });

        image_comment = (ImageView)view.findViewById(R.id.image_comment);
        image_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = getItem(position);
                id = item.getId();
                s_content = item.getContent();
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else {
                    initPopupWindowView(item);
                    popupWindow.showAsDropDown(v, Gravity.BOTTOM, 0, 0);
                }
            }
        });


        edit_comment = (TextView) view.findViewById(R.id.edit_comment);
        edit_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = getItem(position);
                id = item.getId();
                s_content = item.getContent();
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else {
                    initPopupWindowView(item);
                    popupWindow.showAsDropDown(v, Gravity.BOTTOM, -130, 0);
                }
            }
        });

        well_done = (TextView) view.findViewById(R.id.well_done);
        //初始化点赞
        if(item.getCount() != 0){
            well_done.setText(item.getCount()+"人觉得很赞");
        }

        listView = (MyListview)view.findViewById(R.id.comment_lists);
//        初始化评论
        if(item.getComment() != null){
            comments =  item.getComment();
            commentItemAdapter = new CommentItemAdapter(getContext(),R.layout.comment_item_layout,comments);
            listView.setAdapter(commentItemAdapter);
            setListViewHeightBasedOnChildren(listView);
            notifyDataSetChanged();
        }
        return view;
    }


    //    弹出窗口
    public void initPopupWindowView(final Lost item) {
        // // 获取自定义布局文件pop.xml的视图
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.edit_comment_layout, null, false);
        // 创建PopupWindow实例,让它能够适应键盘的出现和消失,注意LinearLayout.LayoutParams.FILL_PARENT,130才能达到该效果
        popupWindow = new PopupWindow(customView, LinearLayout.LayoutParams.MATCH_PARENT,130,true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        // 自定义view添加触摸事件，设置点击其他区域弹窗消失
        customView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
                return false;
            }
        });

        pop_edit = (EditText)customView.findViewById(R.id.pop_edit);
        //打开软键盘
        InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        // 获取编辑框焦点
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(false);
        //软键盘不会挡着popupwindow
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //设置菜单显示的位置
        pop_edit.setFocusable(true);
        pop_edit.requestFocus();

        pop_send = (Button)customView.findViewById(R.id.pop_send);
//        发表内容
        pop_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_comment = pop_edit.getText().toString();
                if( !s_comment.equals("")){
                    final ContentComment contentComment = new ContentComment("小栋栋",s_comment,item.getNickname());
//                    不能new出一个新的对象再添加，否则会出现点赞数重新变为0的情况
                    item.add("comment",contentComment);
                    item.update(item.getId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                Toast.makeText(getContext(),"发表成功",Toast.LENGTH_SHORT).show();
//                                必须使用item.getComment()方法获取到的comments对象添加，使其显示在对应的item中
//                                 如果直接使用comments添加对象，会出现内容更新后只会显示在最后一个item上的情况
                                item.getComment().add(contentComment);
                                commentItemAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                    popupWindow.dismiss();
                }else{
                    Toast.makeText(getContext(),"请填写内容后发表",Toast.LENGTH_SHORT).show();
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
        for (int i = 0; i < comments.size(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
//            Log.e("height",""+listItem.getMeasuredHeight());
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (comments.size()+1));
        listView.setLayoutParams(params);
    }



}
