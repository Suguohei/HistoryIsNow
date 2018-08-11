package com.example.suguoqing.historyisnow;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

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
    private boolean isClickCamera;//是否是拍照裁剪
    private  boolean isChanceHead = false;//是否更换的头像

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_info);

        circleImageView = findViewById(R.id.head_info_img);
        //imageView = findViewById(R.id.head_info_backimag);
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
        Head_info_Adapter adapter = new Head_info_Adapter(list,this);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        //每次打开之前都刷新一下
        refresh();
    }

    private void refresh() {
        //换了头像就显示它的背景，并且加载头像
        if(!isChanceHead){
            Log.d(TAG, "refresh: ================="+this.getExternalCacheDir());
            File file = new File(getExternalCacheDir(), "crop_image.jpg");
            if(file.exists()){
                outputUri = Uri.fromFile(file);
                //RequestOptions requestOptions = new RequestOptions().signature(new ObjectKey(System.currentTimeMillis()));
                String updateTime = String.valueOf(System.currentTimeMillis());
                Glide.with(this)
                        .load(outputUri)
                        //.apply(requestOptions)
                        .signature(new StringSignature(updateTime))
                        .into(circleImageView);


               // imageView.setBackgroundResource(R.drawable.ic_launcher_background);

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
                //点击拍照，打开摄像头
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if(requestCode == RESULT_OK){
                   // try {
                        //Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imguri));
                        Glide.with(this).load(imguri);
                    //} catch (FileNotFoundException e) {
                     //   e.printStackTrace();
                   // }
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
                //当使用Glide加载的时候，图片总是不会变化，是由于，Glide有缓存，当uri没有变，Glide会先加载缓存。
               // RequestOptions requestOptions = new RequestOptions().signature(new ObjectKey(System.currentTimeMillis()));
                String updateTime = String.valueOf(System.currentTimeMillis());
                Glide.with(this)
                        .load(outputUri)
                        .signature(new StringSignature(updateTime))
                        .into(circleImageView);
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
        Log.d(TAG, "handleImageOnKitkat: ------------------------"+uri);
        if(DocumentsContract.isDocumentUri(this,uri)){
            //如果是document类型的uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            Log.d(TAG, "handleImageOnKitkat: -------------"+docId);
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
        // 创建File对象，用于存储裁剪后的图片，避免更改原图
        File file = new File(getExternalCacheDir(), "crop_image.jpg");
        //File file = new File(Environment.getExternalStorageDirectory(),"crop_image.jpg");

        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            Log.d(TAG, "cropPhoto: -------------"+file.length());
        } catch (IOException e) {
            e.printStackTrace();
        }

        outputUri = Uri.fromFile(file);
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(uri, "image/*");
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
}
