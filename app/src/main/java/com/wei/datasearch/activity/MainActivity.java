package com.wei.datasearch.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.wei.datasearch.R;
import com.wei.datasearch.adapter.LvAdapter;
import com.wei.datasearch.bean.DataBean;
import com.wei.datasearch.db.MySQLiteOpenHelper;
import com.wei.datasearch.utils.ToastUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @Author WCL
 * @Date 2019/6/17 9:53
 * @Version 1.0
 * @Description 主界面.
 */
public class MainActivity extends Activity implements View.OnClickListener {

    private TextView mTvSetting;
    private EditText mEtContent;
    private TextView mTvSearch;
    private EditText mEtContent2;
    private TextView mTvSearch2;
    private MySQLiteOpenHelper mHelper;
    private ListView mLv;
    private LvAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHelper = new MySQLiteOpenHelper(this);
        //找控件
        mTvSetting = findViewById(R.id.tv_setting);
        mEtContent = findViewById(R.id.et_content);
        mTvSearch = findViewById(R.id.tv_search);
        mEtContent2 = findViewById(R.id.et_content2);
        mTvSearch2 = findViewById(R.id.tv_search2);
        mLv = findViewById(R.id.lv);
        //设置点击事件
        mTvSetting.setOnClickListener(this);
        mTvSearch.setOnClickListener(this);
        mTvSearch2.setOnClickListener(this);

        //初始化lv
        mAdapter = new LvAdapter(this, null);
        mLv.setAdapter(mAdapter);
//        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {//条目点击事件
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                DataBean bean = mAdapter.getData().get(i);
//                Intent intent = new Intent(MainActivity.this, ProduceInfoActivity.class);
//                intent.putExtra(ADD_OR_EDITOR, bean);
//                startActivityForResult(intent, 101);
//            }
//        });
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_setting://设置
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.tv_search://查询项目号数据
                search();
                break;
            case R.id.tv_search2://查询产品型号数据
                search2();
                break;
        }
    }

    /**
     * 根据输入的产品型号信息查询数据.
     */
    private void search2() {
        String content = mEtContent2.getText().toString();
        if (TextUtils.isEmpty(content)) {
            ToastUtils.showToast(MainActivity.this, "请输入后再查询", 100);
            return;
        }
        List<DataBean> list = mAdapter.getData();
        list.clear();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        // 查询数据库返回cursor
        //columns 要查询的列
        //selection 查询条件
        //selectionArgs 查询的参数
        //orderBy 排序
//                Cursor cursor = db.query("produce", null, null, null, null, null, null);
        Cursor cursor = db.query("produce",
                new String[]{"_id", "project", "model", "name", "pic", "type", "material", "land", "miZhong", "length", "num", "kg", "color", "pack"},
                "model = ?",
                new String[]{content},
                null,
                null,
                "project desc");//desc降序  asc升序
        int count = cursor.getCount();
        if (count <= 0) {
            ToastUtils.showToast(MainActivity.this, "没有数据", 100);
            mAdapter.clearAndRefresh();
            return;
        } else {
            ToastUtils.showToast(MainActivity.this, "共查询" + count + "条数据", 100);
        }
        // 如果cursor可以移动下一个说明 有数据 一直要移动 不能向下移动为止 这样就可以取出所有数据
        while (cursor.moveToNext()) {
            // 根据列的index 获取 相对应的内容辣条
            String id = cursor.getString(0);
            String project = cursor.getString(cursor.getColumnIndex("project"));
            String model = cursor.getString(cursor.getColumnIndex("model"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String pic = cursor.getString(cursor.getColumnIndex("pic"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            String material = cursor.getString(cursor.getColumnIndex("material"));
            String land = cursor.getString(cursor.getColumnIndex("land"));
            String miZhong = cursor.getString(cursor.getColumnIndex("miZhong"));
            String length = cursor.getString(cursor.getColumnIndex("length"));
            String num = cursor.getString(cursor.getColumnIndex("num"));
            String kg = cursor.getString(cursor.getColumnIndex("kg"));
            String color = cursor.getString(cursor.getColumnIndex("color"));
            String pack = cursor.getString(cursor.getColumnIndex("pack"));
            DataBean bean = new DataBean(id, project, model, name, pic, type, material, land, miZhong, length, num, kg, color, pack);
            list.add(bean);//添加数据
        }
        db.close();
        mAdapter.notifyDataSetChanged();//刷新
        Intent intent = new Intent(this, GridLayoutActivity.class);
        intent.putExtra("list", ((Serializable) list));
        startActivity(intent);
    }

    /**
     * 根据输入的项目号信息查询数据.
     */
    private void search() {
        String content = mEtContent.getText().toString();
        if (TextUtils.isEmpty(content)) {
            ToastUtils.showToast(MainActivity.this, "请输入后再查询", 100);
            return;
        }
        List<DataBean> list = mAdapter.getData();
        list.clear();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        // 查询数据库返回cursor
        //columns 要查询的列
        //selection 查询条件
        //selectionArgs 查询的参数
        //orderBy 排序
//                Cursor cursor = db.query("produce", null, null, null, null, null, null);
        Cursor cursor = db.query("produce",
                new String[]{"_id", "project", "model", "name", "pic", "type", "material", "land", "miZhong", "length", "num", "kg", "color", "pack"},
                "project = ?",
                new String[]{content},
                null,
                null,
                "model desc");//desc降序  asc升序
        int count = cursor.getCount();
        if (count <= 0) {
            ToastUtils.showToast(MainActivity.this, "没有数据", 100);
            mAdapter.clearAndRefresh();
            return;
        } else {
            ToastUtils.showToast(MainActivity.this, "共查询" + count + "条数据", 100);
        }
        // 如果cursor可以移动下一个说明 有数据 一直要移动 不能向下移动为止 这样就可以取出所有数据
        while (cursor.moveToNext()) {
            // 根据列的index 获取 相对应的内容辣条
            String id = cursor.getString(0);
            String project = cursor.getString(cursor.getColumnIndex("project"));
            String model = cursor.getString(cursor.getColumnIndex("model"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String pic = cursor.getString(cursor.getColumnIndex("pic"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            String material = cursor.getString(cursor.getColumnIndex("material"));
            String land = cursor.getString(cursor.getColumnIndex("land"));
            String miZhong = cursor.getString(cursor.getColumnIndex("miZhong"));
            String length = cursor.getString(cursor.getColumnIndex("length"));
            String num = cursor.getString(cursor.getColumnIndex("num"));
            String kg = cursor.getString(cursor.getColumnIndex("kg"));
            String color = cursor.getString(cursor.getColumnIndex("color"));
            String pack = cursor.getString(cursor.getColumnIndex("pack"));
            DataBean bean = new DataBean(id, project, model, name, pic, type, material, land, miZhong, length, num, kg, color, pack);
            list.add(bean);//添加数据
        }
        db.close();
        mAdapter.notifyDataSetChanged();//刷新
        Intent intent = new Intent(this, GridLayoutActivity.class);
        intent.putExtra("list", ((Serializable) list));
        startActivity(intent);
    }
}
