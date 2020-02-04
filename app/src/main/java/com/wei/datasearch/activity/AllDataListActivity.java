package com.wei.datasearch.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wei.datasearch.R;
import com.wei.datasearch.adapter.LvAdapter;
import com.wei.datasearch.bean.DataBean;
import com.wei.datasearch.db.MySQLiteOpenHelper;
import com.wei.datasearch.utils.AlertDialogUtils;

import java.util.List;

import static com.wei.datasearch.activity.ProduceInfoActivity.ADD_OR_EDITOR;

/**
 * @Author WCL
 * @Date 2019/6/19 10:30
 * @Version 1.0
 * @Description 所有已添加的产品的信息.
 */
public class AllDataListActivity extends Activity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvAdd;
    private ListView mLv;
    private MySQLiteOpenHelper mHelper;
    //    private CommonAdapter<DataBean> mAdapter;
    private LvAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_data_list);
        mHelper = new MySQLiteOpenHelper(this);
        //找控件
        mIvBack = findViewById(R.id.iv_back);
        mTvAdd = findViewById(R.id.tv_add);
        mLv = findViewById(R.id.lv);
        //点击事件
        mIvBack.setOnClickListener(this);
        mTvAdd.setOnClickListener(this);
        //初始化LV
//        mAdapter = new CommonAdapter<DataBean>(this, null, R.layout.item_produce) {
//            @Override
//            public void convert(ViewHolder holder, DataBean bean) {
//                String info = "项目号: " + bean.getProject()
//                        + "\r\n型号: " + bean.getModel()
//                        + "\r\n名称: " + bean.getName()
//                        + "\r\n生产类型: " + bean.getType()
//                        + "\r\n材质: " + bean.getMaterial()
//                        + "\r\n厚度: " + bean.getLand()
//                        + "\r\n米重: " + bean.getMiZhong()
//                        + "\r\n长度: " + bean.getLength()
//                        + "\r\n定支数: " + bean.getNum()
//                        + "\r\n理重: " + bean.getKg()
//                        + "\r\n颜色: " + bean.getColor()
//                        + "\r\n包装名称: " + bean.getPack();
//                holder.setText(R.id.tv_info, info);
//            }
//        };
        mAdapter = new LvAdapter(this, null);
        mLv.setAdapter(mAdapter);
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {//条目点击事件
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DataBean bean = mAdapter.getData().get(i);
                Intent intent = new Intent(AllDataListActivity.this, ProduceInfoActivity.class);
                intent.putExtra(ADD_OR_EDITOR, bean);
                startActivityForResult(intent, 101);
            }
        });
        mLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {//条目长按事件
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialogUtils.showDialog("删除提示", "确认删除该条信息吗?", AllDataListActivity.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DataBean bean = mAdapter.getData().get(i);
                        SQLiteDatabase db = mHelper.getWritableDatabase();
                        // whereClause 删除的条件
                        // whereArgs 删除的参数
                        // 删除影响了几行 删除了几行 没有删除返回0
                        int delete = db.delete("produce", "_id=?", new String[]{bean.getId()});
                        db.close();
                        queryAll();
                    }
                });
                return true;
            }
        });
        //数据库查询
        queryAll();
    }

    /**
     * 查询数据库,produce产品信息表的所有信息.
     */
    private void queryAll() {
        List<DataBean> list = mAdapter.getData();
        list.clear();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        // 查询数据库返回cursor
//		Cursor cursor = db.rawQuery("select * from person", null);

        //查询
        //columns 要查询的列
        //selection 查询条件
        //selectionArgs 查询的查询
        //orderBy 排序
        Cursor cursor = db.query("produce", null, null, null, null, null, null);

        // 如果cursor可以移动下一个说明 有数据 一直要移动 不能向下移动为止
        // 这样就可以取出所有数据
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
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_back://返回
                finish();
                break;
            case R.id.tv_add://新增产品信息
                Intent intent = new Intent(this, ProduceInfoActivity.class);
                startActivityForResult(intent, 100);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 100) {//新增
            queryAll();
        } else if (requestCode == 101 && resultCode == 100) {//编辑
            queryAll();
        }
    }
}
