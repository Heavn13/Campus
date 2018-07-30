package association;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.heavn.student.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by 张宇 on 2017/7/23.
 */

public class AssRegisteActivity extends AppCompatActivity {
    private String number;
     private Uri imageUri;
    public static final int TAKE_PHOTO=1;
    public static final int CHOOSE_PHOTO=2;
    ImageView asso_image;
    File outImage;
    boolean havePic=false;
    boolean iforg=false;//默认为社团
   private   String assNumber;
    EditText asso_name;
    EditText asso_info;
  private   String queryID=null;
   private  String imagePath=null;
    boolean take_photos=false;
    boolean get_photos=false;
     BmobFile bmobFile;
     int temp;
     EditText ass_id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.association_registe_info);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        number=format.format(new Date());
        //查询有几个组织了

     /*  BmobQuery<Association> query=new BmobQuery<>();
        query.findObjects(new FindListener<Association>() {
            @Override
            public void done(List<Association> list, BmobException e) {
                if(e==null){
                    temp=list.size()+1;
                }
            }
        });*/
//        if(temp<10){
//            assNumber="000"+temp;
//        }else if(temp<100){
//            assNumber="00"+temp;
//        }else if(temp<1000){
//            assNumber="0"+temp;
//        }else{
//            assNumber=""+temp;
//        }

        initAsso();
        //获取社团号
       // assNumber=getIntent().getExtras();
    }
    public void initAsso(){
        ass_id=(EditText)findViewById(R.id.ass_id);
        asso_image=(ImageView)findViewById(R.id.asso_image);
        asso_name=(EditText)findViewById(R.id.asso_name_et);
        asso_info=(EditText)findViewById(R.id.asso_info_et);
//        RadioButton ass_choose=(RadioButton)findViewById(R.id.ass_choose);
//        RadioButton org_choose=(RadioButton)findViewById(R.id.org_choose);
        final RadioGroup radioGroup=(RadioGroup)findViewById(R.id.ass_group);
        // RadioButton radioButton=(RadioButton)findViewById( radioGroup.getCheckedRadioButtonId()) ;
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.ass_choose){
                    iforg=false;
                }else{
                    iforg=true;
                }
            }
        });
        final Button take_photo=(Button)findViewById(R.id.asso_image_btn_take);
        Button choose_photo=(Button)findViewById(R.id.asso_image_btn_choose);
        take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                take_photos=true;
                get_photos=false;
                number=number+".jpg";
                outImage=new File(getExternalCacheDir(),number);
                try {
                    if(outImage.exists()){
                        outImage.delete();
                    }
                    outImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(Build.VERSION.SDK_INT>=24){
                    imageUri= FileProvider.getUriForFile(AssRegisteActivity.this,
                            "com.example.cameraalbumtest.fileprovider",outImage);
                }else{
                    imageUri= Uri.fromFile(outImage);
                }
                Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,TAKE_PHOTO);


            }
        });
        choose_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                take_photos=false;
                get_photos=true;
                number=number+".jpg";
                outImage=new File(getExternalCacheDir(),number);
                try {
                    if(outImage.exists()){
                        outImage.delete();
                    }
                    outImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(Build.VERSION.SDK_INT>=24){
                    imageUri= FileProvider.getUriForFile(AssRegisteActivity.this,
                            "com.example.cameraalbumtest.fileprovider",outImage);
                }else{
                    imageUri= Uri.fromFile(outImage);
                }
                if(ContextCompat.checkSelfPermission(AssRegisteActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(AssRegisteActivity.this,new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else{
                    openAlbum();
                }
            }
        });

        Button submit_btn=(Button)findViewById(R.id.asso_info_submit);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AssRegisteActivity.this, "提交中请稍后", Toast.LENGTH_SHORT).show();

                BmobQuery<Association> bmobquery=new BmobQuery<>();
                bmobquery.addWhereEqualTo("assID",ass_id.getText().toString());
                bmobquery.findObjects(new FindListener<Association>() {
                    @Override
                    public void done(List<Association> list, BmobException e) {
                        if(e==null){
                            if(list.size()!=0){
                                queryID=list.get(0).getObjectId();
                                Association assTem=new Association();
                                assTem.setObjectId(queryID);
                                assTem.delete(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if(e==null){
                                            Log.e("删除数据","成功");
                                        }else{
                                            Log.e("删除数据","失败");
                                        }
                                    }
                                });
                            }
                            registChangeInfo();
                        }
                    }
                });


            }
        });
    }

    public void registChangeInfo(){
        if(asso_name.getText().toString()!=null&&asso_info.getText().toString()!=null&&havePic){
            if(take_photos){
                compressPicture(outImage.getAbsolutePath(),outImage.getAbsolutePath());
                bmobFile= new BmobFile(outImage);

            }else{
                compressPicture(imagePath,imagePath);
                 bmobFile = new BmobFile(new File(imagePath));
            }

            bmobFile.upload(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if(e==null){
                        // String u=bmobFile.getFileUrl();//--返回的上传文件的完整地址
                        Association ass = new Association(asso_name.getText().toString(),asso_info.getText().toString(),iforg,ass_id.getText().toString(),bmobFile,bmobFile.getFileUrl());
                        Log.e("adc",asso_name.getText().toString()+asso_info.getText().toString()+iforg+ass_id.getText().toString()+bmobFile.getFileUrl());
                        ass.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                Toast.makeText(AssRegisteActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }else{
                        Log.e("上传失败",""+e);
                        Toast.makeText(AssRegisteActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }else{
            Toast.makeText(AssRegisteActivity.this, "您填写的信息不完整", Toast.LENGTH_SHORT).show();
        }
    }


    private void openAlbum(){
        Intent intent =new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else{
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if(resultCode==RESULT_OK){
                    try {
                        Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        asso_image.setImageBitmap(bitmap);
                        havePic=true;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if(resultCode==RESULT_OK){
                    if(Build.VERSION.SDK_INT>=19){
                        handleImageOnKitKat(data);
                    }else{
                        handleImageBeforKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data){

        Uri uri=data.getData();
        if(DocumentsContract.isDocumentUri(this,uri)){
            String docId =DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id =docId.split(":")[1];//解析出数据格式
                String selection =MediaStore.Images.Media._ID+"="+id;
                imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.provider.doenloads.documents".equals(uri.getAuthority())){
                Uri contentUri= ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath=getImagePath(contentUri,null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            imagePath=getImagePath(uri,null);
        }else if("file".equalsIgnoreCase(uri.getScheme())){
            imagePath=uri.getPath();
        }
        Log.e("imagepath",imagePath);
        Log.e("uri",""+uri.toString());
        displayImage(imagePath);
        Bitmap bitmap= null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        asso_image.setImageBitmap(bitmap);
        havePic=true;
    }
    private void handleImageBeforKitKat(Intent data){
        Uri uri=data.getData();
        String imagePath=getImagePath(uri,null);
        displayImage(imagePath);
        Bitmap bitmap= null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Log.e("Before",uri.toString());
        asso_image.setImageBitmap(bitmap);
        havePic=true;
    }

    private String getImagePath(Uri uri,String selection){
        String path=null;
        Cursor cursor=getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;

    }
    private void displayImage(String imagePath){
        if(imagePath!=null){
            Bitmap bitmap=BitmapFactory.decodeFile(imagePath);
            asso_image.setImageBitmap(bitmap);
            havePic=true;
        }
        else{
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }
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
        float be = 3.0f;
        if(w * h >3000 * 4000){
            be = 15.0f;
        }else if(w * h > 2000 * 3000){
            be = 6.0f;
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
