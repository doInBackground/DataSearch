package com.wei.datasearch.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wei.datasearch.R;
import com.wei.datasearch.bean.DataBean;
import com.wei.datasearch.db.MySQLiteOpenHelper;
import com.wei.datasearch.utils.DirManager;
import com.wei.datasearch.utils.PicUtils;
import com.wei.datasearch.utils.ToastUtils;

import java.io.File;

/**
 * @Author WCL
 * @Date 2019/6/19 11:00
 * @Version 1.0
 * @Description 产品信息编辑界面
 */
public class ProduceInfoActivity extends Activity implements View.OnClickListener {

    /**
     * Filed Comment:编辑的Bean的信息.
     */
    public static final String ADD_OR_EDITOR = "addOrEditor";
    /**
     * Filed Comment:该界面的Bean信息.
     */
    private DataBean mBean;
    private MySQLiteOpenHelper mHelper;
    private EditText mEtProject;
    private EditText mEtModel;
    private EditText mEtName;
    private EditText mEtType;
    private EditText mEtMaterial;
    private EditText mEtLand;
    private EditText mEtMiZhong;
    private EditText mEtLength;
    private EditText mEtNum;
    private EditText mEtKg;
    private EditText mEtColor;
    private EditText mEtPack;
    private ImageView mIvPic;
    /**
     * Filed Comment:请求码:请求打开图库的标志.
     */
    private final int REQUEST_SYSTEM_PIC = 100;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produce_info);
        mBean = (DataBean) getIntent().getSerializableExtra(ADD_OR_EDITOR);
        mHelper = new MySQLiteOpenHelper(this);
        //找控件
        ImageView ivBack = findViewById(R.id.iv_back);
        TextView tvSure = findViewById(R.id.tv_sure);
        mEtProject = findViewById(R.id.et_project);
        mEtModel = findViewById(R.id.et_model);
        mEtName = findViewById(R.id.et_name);
        mIvPic = findViewById(R.id.iv_pic);
        mEtType = findViewById(R.id.et_type);
        mEtMaterial = findViewById(R.id.et_material);
        mEtLand = findViewById(R.id.et_land);
        mEtMiZhong = findViewById(R.id.et_miZhong);
        mEtLength = findViewById(R.id.et_length);
        mEtNum = findViewById(R.id.et_num);
        mEtKg = findViewById(R.id.et_kg);
        mEtColor = findViewById(R.id.et_color);
        mEtPack = findViewById(R.id.et_pack);
        //设置点击事件
        ivBack.setOnClickListener(this);
        tvSure.setOnClickListener(this);
        mIvPic.setOnClickListener(this);
        //初始化值
        if (mBean != null) {
            mEtProject.setText(mBean.getProject());
            mEtModel.setText(mBean.getModel());
            mEtName.setText(mBean.getName());
            mEtType.setText(mBean.getType());
            mEtMaterial.setText(mBean.getMaterial());
            mEtLand.setText(mBean.getLand());
            mEtMiZhong.setText(mBean.getMiZhong());
            mEtLength.setText(mBean.getLength());
            mEtNum.setText(mBean.getNum());
            mEtKg.setText(mBean.getKg());
            mEtColor.setText(mBean.getColor());
            mEtPack.setText(mBean.getPack());

            String picAbsolutePath = DirManager.getFilesDir(ProduceInfoActivity.this) + "/" + mBean.getModel();//图片预定位置的绝对路径
            File file = new File(picAbsolutePath);
            if (file.exists()) {
                Log.d("www", "图片存在");
                //计算图片缩放比例
                int inSampleSize = PicUtils.inSampleSize(this, picAbsolutePath, 1920, 1920);
                //设置参数
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = false; // 计算好压缩比例后，加载原图
                options.inSampleSize = inSampleSize; // 设置为刚才计算的压缩比例
                Bitmap bm = BitmapFactory.decodeFile(picAbsolutePath, options); // 解码文件
                mIvPic.setImageBitmap(bm);//展示
            } else {
                Log.d("www", "图片不存在");
            }
        }
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_back://返回
                finish();
                break;
            case R.id.tv_sure://确定
                String project = mEtProject.getText().toString();
                String model = mEtModel.getText().toString();
                String name = mEtName.getText().toString();
                String type = mEtType.getText().toString();
                String material = mEtMaterial.getText().toString();
                String land = mEtLand.getText().toString();
                String miZhong = mEtMiZhong.getText().toString();
                String length = mEtLength.getText().toString();
                String num = mEtNum.getText().toString();
                String kg = mEtKg.getText().toString();
                String color = mEtColor.getText().toString();
                String pack = mEtPack.getText().toString();
                if (TextUtils.isEmpty(project)
                        || TextUtils.isEmpty(model)
                        || TextUtils.isEmpty(name)
//                        || TextUtils.isEmpty(type)
//                        || TextUtils.isEmpty(material)
//                        || TextUtils.isEmpty(land)
//                        || TextUtils.isEmpty(miZhong)
//                        || TextUtils.isEmpty(length)
//                        || TextUtils.isEmpty(num)
//                        || TextUtils.isEmpty(kg)
//                        || TextUtils.isEmpty(color)
//                        || TextUtils.isEmpty(pack)
                        ) {
                    ToastUtils.showToast(ProduceInfoActivity.this, "至少填写前3项", 100);
                } else {
                    // 获取可写的数据库
                    SQLiteDatabase db = mHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("project", project);
                    values.put("model", model);
                    values.put("name", name);
                    values.put("pic", model);
                    values.put("type", type);
                    values.put("material", material);
                    values.put("land", land);
                    values.put("miZhong", miZhong);
                    values.put("length", length);
                    values.put("num", num);
                    values.put("kg", kg);
                    values.put("color", color);
                    values.put("pack", pack);
                    if (mBean == null) {//添加
                        //数据库添加
                        // table 表名
                        // nullColumnHack null 列的占位符
                        // insert into person values(1,null,8);
                        // insert 当前的数据插入到了哪一行
                        long insert = db.insert("produce", null, values);
                        // 数据用完了记得关闭
                        db.close();
                        //更新Bean
                        mBean = new DataBean(project, model, name, model, type, material, land, miZhong, length, num, kg, color, pack);
                    } else {//编辑
                        //数据库修改
                        // 修改影响了几行
                        int update = db.update("produce", values, "_id=?", new String[]{mBean.getId()});
                        db.close();
                        //更新Bean
                        mBean.setProject(project);
                        mBean.setModel(model);
                        mBean.setName(name);
                        mBean.setType(type);
                        mBean.setMaterial(material);
                        mBean.setLand(land);
                        mBean.setMiZhong(miZhong);
                        mBean.setLength(length);
                        mBean.setNum(num);
                        mBean.setKg(kg);
                        mBean.setColor(color);
                        mBean.setPack(pack);
                    }

                    if (mBitmap != null) {//选择了图片
                        Log.d("www", "保存图片");
                        String absolutePath = DirManager.getFilesDir(ProduceInfoActivity.this);//绝对路径
                        PicUtils.saveBitmapToFile(mBitmap, absolutePath, mBean.getModel());
                    } else {
                        Log.d("www", "图片未变更,未保存");
                    }

                    /*Intent intent = new Intent();
                    intent.putExtra(ADD_OR_EDITOR, mBean);
                    setResult(100, intent);*/
                    setResult(100);
                    finish();
                }
                break;
            case R.id.iv_pic://选择简图
                if (ContextCompat.checkSelfPermission(ProduceInfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ProduceInfoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum(); //打开系统相册
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "未开启存储权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    /**
     * 打开相册.
     */
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_SYSTEM_PIC);//打开系统相册
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SYSTEM_PIC && resultCode == RESULT_OK && null != data) {
            if (Build.VERSION.SDK_INT >= 19) {
                handleImageOnKitkat(data);
            } else {
                handleImageBeforeKitkat(data);
            }
        }
    }

    /**
     * API19及以上.
     *
     * @param data
     */
    @TargetApi(19)
    private void handleImageOnKitkat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果是document类型的uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content:" +
                        "//downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是File类型的uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath);//根据图片路径显示图片
    }

    /**
     * API19以前.
     *
     * @param data
     */
    private void handleImageBeforeKitkat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 根据图片String路径,展示图片.
     *
     * @param imagePath
     */
    private void displayImage(String imagePath) {
        if (imagePath != null) {
            //计算图片缩放比例
            int inSampleSize = PicUtils.inSampleSize(this, imagePath, 1920, 1920);
            //设置参数
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false; // 计算好压缩比例后，加载原图
            options.inSampleSize = inSampleSize; // 设置为刚才计算的压缩比例
            mBitmap = BitmapFactory.decodeFile(imagePath, options); // 解码文件

//            mBitmap = BitmapFactory.decodeFile(imagePath);
            mIvPic.setImageBitmap(mBitmap);//展示
        } else {
            Toast.makeText(this, "图片读取失败", Toast.LENGTH_SHORT).show();
        }
    }


}
