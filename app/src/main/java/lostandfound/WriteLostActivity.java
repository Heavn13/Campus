package lostandfound;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.heavn.student.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public class WriteLostActivity extends Activity implements View.OnClickListener{
    public static final int TAKE_PICTURE = 0;
    public static final int CUT_PICTURE = 1;
    public static final int SHOW_PICTURE = 2;

    private int k = 0,j = 0;

    private Button bar_cancel;
    private Button bar_report;
    private ImageView photo;
    private ImageView photo2;
    private ImageView photo3;
    private EditText edit_things_name;
    private EditText edit_content;
    private EditText edit_lost_date;
    private EditText edit_lost_location;
    private EditText edit_contact;
    private Spinner spinner;

    private PopupWindow popupWindow;
    private Button take_photo;
    private Button choose_albums;
    private Button pop_cancel;

    private ProgressBar progressBar;

    private Uri imageUri;
    //放置上传至Bmob的图片的路径
    private Uri uri;

    private int width;
    private int height;
    private String image_path;
    private String image_path2;
    private String image_path3;
    private String things_name;
    private String content;
    private String lost_date;
    private String lost_location;
    private String contact;
    private String campus_message;
    private boolean photo_empty = true;
    private boolean photo2_empty = true;
    private boolean photo3_empty = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.write_lost_layout);

        bar_cancel = (Button)findViewById(R.id.bar_cancel);
        bar_cancel.setOnClickListener(this);
        bar_report = (Button)findViewById(R.id.bar_report);
        bar_report.setOnClickListener(this);

        photo = (ImageView)findViewById(R.id.image_photo1);
        photo.setOnClickListener(this);
        photo2 = (ImageView)findViewById(R.id.image_photo2);
        photo2.setOnClickListener(this);
        photo3 = (ImageView)findViewById(R.id.image_photo3);
        photo3.setOnClickListener(this);

        edit_things_name = (EditText)findViewById(R.id.edit_things_name);
        edit_content = (EditText)findViewById(R.id.edit_content);
        edit_lost_date = (EditText)findViewById(R.id.edit_lost_date);
        edit_lost_location = (EditText)findViewById(R.id.edit_lost_location);
        edit_contact = (EditText)findViewById(R.id.edit_contact);

        //获取手机屏幕宽度
        WindowManager wm = (WindowManager) getBaseContext().getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();

        spinner = (Spinner)findViewById(R.id.spinner1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                campus_message = (String)spinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //点击事件
    @Override
    public void onClick(View v) {
        int i = v.getId();
        switch (i){
            case R.id.bar_cancel:
                Intent intent0 = new Intent(WriteLostActivity.this,LostMainActivity.class);
                startActivity(intent0);
                finish();
                break;
            case R.id.bar_report:
                things_name = edit_things_name.getText().toString();
                content = edit_content.getText().toString();
                lost_date = edit_lost_date.getText().toString();
                lost_location = edit_lost_location.getText().toString();
                contact = edit_contact.getText().toString();

                if(!things_name.equals("") && !content.equals("") && !lost_date.equals("") && !lost_location.equals("") && !contact.equals("")){
                    if(image_path != null && image_path2 != null && image_path3 != null){
                        //上传图片及保存其它信息
                        upload(image_path,image_path2,image_path3);
                    }else{
                        Toast.makeText(WriteLostActivity.this,"请上传三张图片",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(WriteLostActivity.this,"请填写完整",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.image_photo1:
                display_popupwindow();
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else {
                    initPopupWindowView();
                    //设置popupWindow从底部弹出
                    popupWindow.showAtLocation(v,Gravity.BOTTOM,0,0);
                }
                break;
            case R.id.image_photo2:
                display_popupwindow();
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else {
                    initPopupWindowView();
                    //设置popupWindow从底部弹出
                    popupWindow.showAtLocation(v,Gravity.BOTTOM,0,0);
                }
                break;
            case R.id.image_photo3:
                display_popupwindow();
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else {
                    initPopupWindowView();
                    //设置popupWindow从底部弹出
                    popupWindow.showAtLocation(v,Gravity.BOTTOM,0,0);
                }
                break;
            case R.id.take_photo:
                File outputImage = new File(Environment.getExternalStorageDirectory(),"tempImage"+k+".jpg");
                try{
                    if(outputImage.exists()){
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                k++;
                imageUri = Uri.fromFile(outputImage);
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,SHOW_PICTURE);
                break;
            case R.id.choose_albums:
                File outputImage2 = new File(Environment.getExternalStorageDirectory(),"output_image"+j+".jpg");
                try{
                    if(outputImage2.exists()){
                        outputImage2.delete();
                    }
                    outputImage2.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                j++;
                imageUri = Uri.fromFile(outputImage2);
//                "android.intent.action.GET_CONTENT"
                Intent intent2 = new Intent(Intent.ACTION_PICK,null);
//                intent.setType("image/*");
                intent2.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent2.putExtra("crop",true);
                intent2.putExtra("scale",true);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent2,CUT_PICTURE);
                break;
            case R.id.pop_cancel:
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                break;

        }
    }

    //弹出窗口初始化
    public void initPopupWindowView() {

        // // 获取自定义布局文件pop.xml的视图
        View customView = getLayoutInflater().inflate(R.layout.photo_style_layout, null, false);
        // 创建PopupWindow实例,200,150分别是宽度和高度
        popupWindow = new PopupWindow(customView, width, height);
        // 自定义view添加触摸事件，设置点击其他区域弹窗消失
        customView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow= null;
                }
                return false;
            }
        });

        //下拉菜单按钮
        take_photo = (Button)customView.findViewById(R.id.take_photo);
        take_photo.setOnClickListener(this);
        choose_albums = (Button)customView.findViewById(R.id.choose_albums);
        choose_albums.setOnClickListener(this);
        pop_cancel = (Button)customView.findViewById(R.id.pop_cancel);
        pop_cancel.setOnClickListener(this);

    }

    //弹出窗口
    public void display_popupwindow(){
        //关闭软键盘,防止popupWindow覆盖软键盘
        if(getCurrentFocus()!=null)
        {
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(getCurrentFocus()
                                    .getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        }


    }

    //对图片的处理
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK) {
                    //此处启动裁剪程序
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CUT_PICTURE);
                }
                break;
            case CUT_PICTURE:
                if (resultCode == RESULT_OK) {
                    //此处启动裁剪程序
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(data.getData(), "image/*");
                    //获取图片uri
                    uri = data.getData();
                    //以下方法将获取的uri转为String类型
                    String []imgs1={MediaStore.Images.Media.DATA};//将图片URI转换成存储路径
                    Cursor cursor = this.managedQuery(uri, imgs1, null, null, null);
                    int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();

                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, SHOW_PICTURE);
                }
                break;
            case SHOW_PICTURE:
                if (resultCode == RESULT_OK) {

                    try {
                        //将output_image.jpg对象解析成Bitmap对象，然后设置到ImageView中显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
                                .openInputStream(imageUri));

                        if(photo_empty == true && imageUri != null){
//                            去除掉前面uri的file://
                            image_path = ""+imageUri;
                            image_path = image_path.substring(7);
//                            判断尺寸过大时才对图片进行压缩
                            if(bitmap.getWidth()*bitmap.getHeight() > 2048000){
                                compressPicture(image_path,image_path);
                            }
                            photo.setImageBitmap(bitmap);
                            photo_empty = false;
                        }else if(photo2_empty == true && imageUri != null){
                            image_path2 = ""+imageUri;
                            image_path2 = image_path2.substring(7);
//                            判断尺寸过大时才对图片进行压缩
                            if(bitmap.getWidth()*bitmap.getHeight() > 2048000){
                                compressPicture(image_path2,image_path2);
                            }
                            photo2.setImageBitmap(bitmap);
                            photo2_empty = false;
                        }else if(photo3_empty == true && imageUri != null){
                            image_path3 = ""+imageUri;
//                            Bitmap bitmap = getBitmapFromUrl(image_path3,100,100);
                            image_path3 = image_path3.substring(7);
//                            判断尺寸过大时才对图片进行压缩
                            if(bitmap.getWidth()*bitmap.getHeight() > 2048000){
                                compressPicture(image_path3,image_path3);
                            }

                            photo3.setImageBitmap(bitmap);
                            photo3_empty = false;
                            photo2_empty = true;
                            photo_empty = true;
                        }
                        //图片完成设置之后，使popupWindow消失
                        popupWindow.dismiss();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    //将图片上传到Bmob
    private void upload(final String imgpath1, String imgpath2, String imgpath3){

        final String[] imgpaths = new String[3];
        imgpaths[0] = imgpath1;
        if(imgpath2 != null){
            imgpaths[1] = imgpath2;
        }
        if(imgpath3 != null){
            imgpaths[2] = imgpath3;
        }
        BmobFile.uploadBatch(imgpaths, new UploadBatchListener() {
            @Override
            public void onSuccess(List<BmobFile> list, List<String> urls) {
                if(urls.size() == imgpaths.length){
                    Lost lost = new Lost();
                    lost.setId(lost.getObjectId());
                    lost.setIcon(urls.get(0));
                    lost.setIcon2(urls.get(1));
                    lost.setIcon3(urls.get(2));
                    lost.setNickname(LostMainActivity.s_nickname);
                    lost.setAccount(LostMainActivity.s_account);
                    lost.setThings_name(things_name);
                    lost.setContent(content);
                    lost.setLost_date(lost_date);
                    lost.setLost_location(lost_location);
                    lost.setContact(contact);
                    lost.setLocation(campus_message);
                    lost.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            Toast.makeText(WriteLostActivity.this,"发表成功",Toast.LENGTH_SHORT).show();
                            k = 0;
                            j = 0;
                            final Intent intent3 = new Intent(WriteLostActivity.this,LostMainActivity.class);
                            //延时跳转，先让MainActivity的listView刷新
                            TimerTask task = new TimerTask() {
                                @Override
                                public void run() {
                                    intent3.putExtra("account",LostMainActivity.s_account);
                                    intent3.putExtra("nickname",LostMainActivity.s_nickname);
                                    startActivityForResult(intent3,0);
                                    finish();
                                }
                            };
                            Timer timer = new Timer();
                            timer.schedule(task, 2000);//3秒后执行TimeTask的run方法
                        }
                    });
                }

            }

            @Override
            public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
                if(curPercent < 90){
                    Toast.makeText(WriteLostActivity.this,"图片正在上传中，请稍后。。。",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });

    }

//    压缩图片
    private void compressPicture(String srcPath, String desPath) {
        FileOutputStream fos = null;
        BitmapFactory.Options op = new BitmapFactory.Options();

        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        op.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, op);
        op.inJustDecodeBounds = false;

        // 缩放图片的尺寸
        float w = op.outWidth;
        float h = op.outHeight;
//        Toast.makeText(WriteLostActivity.this,""+w+"  "+h,Toast.LENGTH_SHORT).show();
        float hh = 1024f;//
        float ww = 1024f;//
        float be = 4.0f;
        if(w * h >3000 * 4000){
            be = 18.0f;
        }else if(w * h > 2000 * 3000){
            be = 8.0f;
        }
        // 最长宽度或高度1024

        op.inSampleSize = (int) be;// 设置缩放比例,这个数字越大,图片大小越小.
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, op);
        int desWidth = (int) (w / be);
        int desHeight = (int) (h / be);
        bitmap = Bitmap.createScaledBitmap(bitmap, desWidth, desHeight, true);
        try {
            fos = new FileOutputStream(desPath);
            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
