package com.example.suguoqing.historyisnow;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.signature.StringSignature;
import com.example.suguoqing.bean.User;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class HeadInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "HeadInfoActivity";
    public CircleImageView circleImageView;
    public ImageView imageView;
    public CollapsingToolbarLayout layout;
    public FloatingActionButton btn;
    public RecyclerView recyclerView;
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    public static final int PICTURE_CUT = 3;
    private Uri imguri;//拍照相片保存地址
    private Uri outputUri;//裁剪完照片保存地址
    private String imagePath;//打开相册选择照片的路径
    private User user;//当前用户

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //让顶部的状态栏和背景合二为一
        if(Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_head_info);

        //获取当前用户
        Intent intent = getIntent();
        //Bundle bundle = intent.getBundleExtra("bundle");
       // user = (User) bundle.getSerializable("user");
        String name = intent.getStringExtra("name");
        user = DataSupport.where("name = ?",name).find(User.class).get(0);

        Log.d(TAG, "onCreate: user is"+ user+"---------"+user.getImg());


        circleImageView = findViewById(R.id.head_info_img);
        imageView = findViewById(R.id.head_info_backimg);
        layout = findViewById(R.id.head_info_coll);
        btn = findViewById(R.id.head_info_btn);
        recyclerView = findViewById(R.id.head_info_recycle);

        circleImageView.setOnClickListener(this);
        btn.setOnClickListener(this);

        ArrayList<String> list = new ArrayList<>();
        list.add("姓名");
        list.add("邮箱");

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        Head_info_Adapter adapter = new Head_info_Adapter(list,this,user);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        //每次打开之前都刷新一下
        refresh();
    }

    private void refresh() {
        //换了头像就显示它的背景，并且加载头像

        if(user.getImg() != null){
            File file = new File(user.getImg());
            if(file.exists()){

                outputUri = Uri.fromFile(file);

                Glide.with(this)
                        .load(outputUri)
                        .into(circleImageView);


                Glide.with(this)
                        .load(outputUri)
                        .bitmapTransform(new BlurTransformation(this, 20))
                        .into(imageView);

            }
            
        }

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.head_info_img:
                //点击头像打开相册
                if(ContextCompat.checkSelfPermission(HeadInfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(HeadInfoActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else{
                    openAlbum();
                }
                break;
            case R.id.head_info_btn:
               File file = new File(Environment.getExternalStorageDirectory(),"take_photo.jpg");
                try {
                    if(file.exists()){
                        file.delete();
                    }
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(Build.VERSION.SDK_INT >= 24){
                    imguri = FileProvider.getUriForFile(HeadInfoActivity.this,"com.example.suguoqing.historyisnow.fileprovider",file);

                }else{
                    imguri = Uri.fromFile(file);
                }
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imguri);
                startActivityForResult(intent,TAKE_PHOTO);

                break;
                default:
                    break;

        }
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);//这里使用startactivityForResult，打开相册，同时有请求码

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){

            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else{
                    Toast.makeText(this, "没有权限", Toast.LENGTH_SHORT).show();
                }
                break;
                default:
                    break;

        }

    }

    //重写返回键代码
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("name",user.getName());
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if (hasSdcard()) {
                    File tempFile = new File(
                           Environment.getExternalStorageDirectory(),
                            "take_photo.jpg");
                    cropPhoto(Uri.fromFile(tempFile));
                } else {
                    Toast.makeText(getApplication(), "没有SDCard!", Toast.LENGTH_LONG)
                            .show();
                }
                break;

            case CHOOSE_PHOTO:
                if(resultCode == RESULT_OK){
                    if(Build.VERSION.SDK_INT >= 19){
                        //4.4及以上用这个方法处理图片
                        handleImageOnKitkat(data);
                    }else{
                        //4.4以下
                       handleImageBeforeKitkat(data);
                    }

                }
                break;
            case PICTURE_CUT://裁剪完成

                if(resultCode == RESULT_OK){
                    refresh();
                }

                break;
        }

    }


    //android 版本在4.4以下
    private void handleImageBeforeKitkat(Intent data) {
        Uri uri = data.getData();
        String path = getImagePath(uri,null);
        displayImage(path);
    }

    //android 版本在4.4以上用这个方法处理图像
    @TargetApi(19)
    private void handleImageOnKitkat(Intent data) {
      imagePath = null;
        Uri uri = data.getData();
        //Glide.with(this).load(uri).into(circleImageView);
        if(DocumentsContract.isDocumentUri(this,uri)){
            //如果是document类型的uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
                //displayImage(imagePath);

            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://download/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }

        }else if("content".equalsIgnoreCase(uri.getScheme())){
            //如果这个是content类型的uri，则使用普通方式处理
            imagePath = getImagePath(uri,null);
        }else if("file".equalsIgnoreCase(uri.getScheme())){
            //如果是file类型的uri，直接获取图片的路径
            imagePath = uri.getPath();
        }
        //displayImage(imagepath);
        cropPhoto(uri);
    }

    /*显示图片*/
    private void displayImage(String imagepath) {
        Log.d(TAG, "displayImage: "+imagepath);
        if(imagepath != null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagepath);
            circleImageView.setImageBitmap(bitmap);
        }else{
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *获取相片路径
     */
    private String getImagePath(Uri externalContentUri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(externalContentUri,null,selection,null,null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 裁剪图片
     */
    private void cropPhoto(Uri uri) {
        String currentTime = Long.toString(System.currentTimeMillis());
        // 创建File对象，用于存储裁剪后的图片，避免更改原图
        File file = new File(getExternalCacheDir(), currentTime+".jpg");
        //File file = new File(Environment.getExternalStorageDirectory(),"crop_image.jpg");

        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        outputUri = Uri.fromFile(file);
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(uri, "image/*");

        user.setImg(file.getPath());
        Log.d(TAG, "cropPhoto: +"+user.getImg());
        user.save();

        Log.d(TAG, "refresh: 为空 + outputuri"+outputUri);
        //裁剪图片的宽高比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("crop", "true");//可裁剪
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 400);
        intent.putExtra("outputY", 400);
        intent.putExtra("scale", true);//支持缩放
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());//输出图片格式
        intent.putExtra("noFaceDetection", true);//取消人脸识别
        startActivityForResult(intent, PICTURE_CUT);

    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }
}
