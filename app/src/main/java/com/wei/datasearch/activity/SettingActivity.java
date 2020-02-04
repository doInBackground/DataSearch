package com.wei.datasearch.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wei.datasearch.R;
import com.wei.datasearch.utils.BaseDialog;
import com.wei.datasearch.utils.SharedPreferencesUtils;
import com.wei.datasearch.utils.ToastUtils;

public class SettingActivity extends Activity implements View.OnClickListener {

    private AlertDialog mDialog;
    private EditText mEtTableName;
    private TextView mTvSetTableName;
    private String mTableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ImageView ivBack = findViewById(R.id.iv_back);
        TextView tvAllData = findViewById(R.id.tv_all_data);
        mTvSetTableName = findViewById(R.id.tv_set_table_name);
        mTableName = SharedPreferencesUtils.getParam(this, "tableName", "");
        if (!TextUtils.isEmpty(mTableName)) {
            mTvSetTableName.setText("设置表名(" + mTableName + ")");
        }
        ivBack.setOnClickListener(this);
        tvAllData.setOnClickListener(this);
        mTvSetTableName.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_back://返回
                finish();
                break;
            case R.id.tv_all_data://查询所有数据
                startActivity(new Intent(this, AllDataListActivity.class));
                break;
            case R.id.tv_set_table_name://设置表名
                if (mDialog == null) {
                    mDialog = BaseDialog.createDialog(this, R.layout.dialog_edit_table_name, Gravity.CENTER);
                    //找控件
                    Window win = mDialog.getWindow();
                    mEtTableName = win.findViewById(R.id.et_table_name);
                    Button btnCancel = win.findViewById(R.id.btn_cancel);
                    Button btnOk = win.findViewById(R.id.btn_ok);
                    //设置点击事件
                    btnCancel.setOnClickListener(this);
                    btnOk.setOnClickListener(this);
                    mEtTableName.setText(mTableName);
                } else {
                    mDialog.show();
                }
                break;
            case R.id.btn_cancel:
                if (mDialog != null) {
                    mDialog.dismiss();
                }
                break;
            case R.id.btn_ok:
                if (mDialog != null && mEtTableName != null) {
                    String newTableName = mEtTableName.getText().toString();
                    if (TextUtils.isEmpty(newTableName)) {
                        ToastUtils.showToast(this, "表名不能为空", 100);
                    } else {
                        mTableName = newTableName;
                        SharedPreferencesUtils.setParam(this, "tableName", newTableName);
                        mTvSetTableName.setText("设置表名(" + newTableName + ")");
                        mDialog.dismiss();
                    }
                }
                break;
        }
    }


}
