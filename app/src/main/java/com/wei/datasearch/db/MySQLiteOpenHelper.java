package com.wei.datasearch.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @Author WCL
 * @Date 2019/6/18 15:41
 * @Version 1.0
 * @Description 用来管理数据库的创建和更新.不用来进行增删查改操作.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    /**
     * 构造方法.
     *
     * @param context 上下文
     * @param name    数据库的名字
     * @param factory 用来创建cursor,默认为null
     * @param version 数据库版本
     */
    public MySQLiteOpenHelper(Context context) {
        super(context, "data.db", null, 1);
    }

    /**
     * 数据库第一次创建的时候调用.
     *
     * @param db 操作数据库
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table produce " +
                "(_id integer primary key autoincrement," +
                "project text," + //项目号
                "model text," + //产品型号
                "name text," + //产品名称
                "pic text," + //产品简图
                "type text," + //生产类型
                "material text," + //材质
                "land text," + //厚度
                "miZhong text," + //米重
                "length text," + //长度
                "num text," + //定支数
                "kg text," + //理重
                "color text," + //颜色名称
                "pack text)";//包装名称
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
