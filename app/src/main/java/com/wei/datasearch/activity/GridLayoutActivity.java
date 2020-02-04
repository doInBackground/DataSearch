package com.wei.datasearch.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wei.datasearch.R;
import com.wei.datasearch.adapter.TablePagerAdapter;
import com.wei.datasearch.bean.DataBean;
import com.wei.datasearch.utils.BaseDialog;
import com.wei.datasearch.utils.DirManager;
import com.wei.datasearch.utils.SharedPreferencesUtils;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author WCL
 * @Date 2019/6/21 10:57
 * @Version 1.0
 * @Description ViewPager界面, 内展示查询到的多张表格.
 */
public class GridLayoutActivity extends Activity implements View.OnClickListener {


    private final int ROW_COUNT_NUM = 7;
    private ViewPager mVp;
    private TextView mTvSetInfo;
    private List<View> mViewList;
    private AlertDialog mDialog;
    private EditText mEtAddress;
    private EditText mEtTel;
    private EditText mEtFax;
    private EditText mEtNo;
    private EditText mEtCustomerName;
    private EditText mEtMakeDate;
    private EditText mEtEndDate;
    private EditText mEtProjectName;
    private EditText mEtRemarks;
    private EditText mEtMakePerson;
    private EditText mEtAuditPerson;
    private EditText mEtApprovePerson;
    private String mAddress;
    private String mTel;
    private String mFax;
    private String mNo;
    private String mCustomerName;
    private String mMakeDate;
    private String mEndDate;
    private String mProjectName;
    private String mRemarks;
    private String mMakePerson;
    private String mAuditPerson;
    private String mApprovePerson;
    private TextView mTvAddress;
    private TextView mTvTel;
    private TextView mTvFax;
    private TextView mTvNo;
    private TextView mTvCustomerName;
    private TextView mTvMakeDate;
    private TextView mTvEndDate;
    private TextView mTvProjectName;
    private TextView mTvRemarks;
    private TextView mTvMakePerson;
    private TextView mTvAuditPerson;
    private TextView mTvApprovePerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_layout);
        mTvSetInfo = findViewById(R.id.tv_set_info);
        mTvSetInfo.setOnClickListener(this);
        initOrderInfoShow();//初始化订单信息展示.
        List<DataBean> list = (List) getIntent().getSerializableExtra("list");//从上个界面拿到每行的数据
        mViewList = getPageList(list);//获取ViewPager每页的数据.
        mVp = findViewById(R.id.vp);
        TablePagerAdapter adapter = new TablePagerAdapter(mViewList);
        mVp.setAdapter(adapter);
    }

    /**
     * 初始化订单信息展示.
     */
    private void initOrderInfoShow() {
        //获取表格标题名称
        String tableName = SharedPreferencesUtils.getParam(GridLayoutActivity.this, "tableName", "");
        mAddress = SharedPreferencesUtils.getParam(GridLayoutActivity.this, "address", "");
        mTel = SharedPreferencesUtils.getParam(GridLayoutActivity.this, "tel", "");
        mFax = SharedPreferencesUtils.getParam(GridLayoutActivity.this, "fax", "");
        mNo = SharedPreferencesUtils.getParam(GridLayoutActivity.this, "no", "");
        mCustomerName = SharedPreferencesUtils.getParam(GridLayoutActivity.this, "customerName", "");
        mMakeDate = SharedPreferencesUtils.getParam(GridLayoutActivity.this, "makeDate", "");
        mEndDate = SharedPreferencesUtils.getParam(GridLayoutActivity.this, "endDate", "");
        mProjectName = SharedPreferencesUtils.getParam(GridLayoutActivity.this, "projectName", "");
        mRemarks = SharedPreferencesUtils.getParam(GridLayoutActivity.this, "remarks", "");
        mMakePerson = SharedPreferencesUtils.getParam(GridLayoutActivity.this, "makePerson", "");
        mAuditPerson = SharedPreferencesUtils.getParam(GridLayoutActivity.this, "auditPerson", "");
        mApprovePerson = SharedPreferencesUtils.getParam(GridLayoutActivity.this, "approvePerson", "");
        //找控件
        TextView tvTableName = findViewById(R.id.tv_table_name);
        mTvAddress = findViewById(R.id.tv_address);
        mTvTel = findViewById(R.id.tv_tel);
        mTvFax = findViewById(R.id.tv_fax);
        mTvNo = findViewById(R.id.tv_no);
        mTvCustomerName = findViewById(R.id.tv_customer_name);
        mTvMakeDate = findViewById(R.id.tv_make_date);
        mTvEndDate = findViewById(R.id.tv_end_date);
        mTvProjectName = findViewById(R.id.tv_project_name);
        mTvRemarks = findViewById(R.id.tv_remarks);
        mTvMakePerson = findViewById(R.id.tv_make_person);
        mTvAuditPerson = findViewById(R.id.tv_audit_person);
        mTvApprovePerson = findViewById(R.id.tv_approve_person);
        //初始化订单信息.
        tvTableName.setText(tableName);
        mTvAddress.setText("地址:" + mAddress);
        mTvTel.setText("电话:" + mTel);
        mTvFax.setText("传真:" + mFax);
        mTvNo.setText("销售订单号:" + mNo);
        mTvCustomerName.setText("客户名称:" + mCustomerName);
        mTvMakeDate.setText("订货日期:" + mMakeDate);
        mTvEndDate.setText("交货日期:" + mEndDate);
        mTvProjectName.setText("工程名称:" + mProjectName);
        mTvRemarks.setText(mRemarks);
        mTvMakePerson.setText(mMakePerson);
        mTvAuditPerson.setText(mAuditPerson);
        mTvApprovePerson.setText(mApprovePerson);
    }

    /**
     * 获取ViewPager每页的数据.
     *
     * @param list 表格中每行的数据对象
     */
    private List<View> getPageList(List<DataBean> list) {
        List<View> viewList = new ArrayList<>();
        //计算需要几页数据
        int size = list.size();//数据量
        int needPagerNum = (size % ROW_COUNT_NUM) == 0 ? (size / ROW_COUNT_NUM) : (size / ROW_COUNT_NUM + 1);//需要几页数据

        LayoutInflater inflater = getLayoutInflater();
        for (int i = 0; i < needPagerNum; i++) {//展示每页的数据
            View pageView = inflater.inflate(R.layout.layout_table, null);//新建一个表格页面
            pageView.setOnClickListener(this);
            TextView tvNumCount = pageView.findViewById(R.id.tv_num_count);
            TextView tvKgCount = pageView.findViewById(R.id.tv_kg_count);
//            TextView tvTableName = pageView.findViewById(R.id.tv_table_name);
//            TextView tvAddress = pageView.findViewById(R.id.tv_address);
//            TextView tvTel = pageView.findViewById(R.id.tv_tel);
//            TextView tvFax = pageView.findViewById(R.id.tv_fax);
//            TextView tvNo = pageView.findViewById(R.id.tv_no);
//            TextView tvCustomerName = pageView.findViewById(R.id.tv_customer_name);
//            TextView tvMakeDate = pageView.findViewById(R.id.tv_make_date);
//            TextView tvEndDate = pageView.findViewById(R.id.tv_end_date);
//            TextView tvProjectName = pageView.findViewById(R.id.tv_project_name);
//            TextView tvRemarks = pageView.findViewById(R.id.tv_remarks);
//            TextView tvMakePerson = pageView.findViewById(R.id.tv_make_person);
//            TextView tvAuditPerson = pageView.findViewById(R.id.tv_audit_person);
//            TextView tvApprovePerson = pageView.findViewById(R.id.tv_approve_person);

//            //初始化订单信息.
//            tvTableName.setText(tableName);
//            tvAddress.setText("地址:" + mAddress);
//            tvTel.setText("电话:" + mTel);
//            tvFax.setText("传真:" + mFax);
//            tvNo.setText("销售订单号:" + mNo);
//            tvCustomerName.setText("客户名称:" + mCustomerName);
//            tvMakeDate.setText("订货日期:" + mMakeDate);
//            tvEndDate.setText("交货日期:" + mEndDate);
//            tvProjectName.setText("工程名称:" + mProjectName);
//            tvRemarks.setText(mRemarks);
//            tvMakePerson.setText(mMakePerson);
//            tvAuditPerson.setText(mAuditPerson);
//            tvApprovePerson.setText(mApprovePerson);

            double numCount = 0;
            double kgCount = 0;

            int whereData = i * ROW_COUNT_NUM + 0;//对应数据应该在集合中的位置
            if (whereData < size) {//该行有数据
                LinearLayout include1 = pageView.findViewById(R.id.include1);//第一行视图
                DataBean bean1 = list.get(whereData);//第一行视图对应的数据
                numCount = getCount(numCount, bean1.getNum());
                kgCount = getCount(kgCount, bean1.getKg());
                showData(include1, bean1);
            }

            whereData = i * ROW_COUNT_NUM + 1;//对应数据应该在集合中的位置
            if (whereData < size) {//该行有数据
                LinearLayout include2 = pageView.findViewById(R.id.include2);//第2行视图
                DataBean bean2 = list.get(whereData);//第2行视图对应的数据
                numCount = getCount(numCount, bean2.getNum());
                kgCount = getCount(kgCount, bean2.getKg());
                showData(include2, bean2);
            }

            whereData = i * ROW_COUNT_NUM + 2;//对应数据应该在集合中的位置
            if (whereData < size) {//该行有数据
                LinearLayout include3 = pageView.findViewById(R.id.include3);//第3行视图
                DataBean bean3 = list.get(whereData);//第3行视图对应的数据
                numCount = getCount(numCount, bean3.getNum());
                kgCount = getCount(kgCount, bean3.getKg());
                showData(include3, bean3);
            }

            whereData = i * ROW_COUNT_NUM + 3;//对应数据应该在集合中的位置
            if (whereData < size) {//该行有数据
                LinearLayout include4 = pageView.findViewById(R.id.include4);//第4行视图
                DataBean bean4 = list.get(whereData);//第4行视图对应的数据
                numCount = getCount(numCount, bean4.getNum());
                kgCount = getCount(kgCount, bean4.getKg());
                showData(include4, bean4);
            }

            whereData = i * ROW_COUNT_NUM + 4;//对应数据应该在集合中的位置
            if (whereData < size) {//该行有数据
                LinearLayout include5 = pageView.findViewById(R.id.include5);//第5行视图
                DataBean bean5 = list.get(whereData);//第5行视图对应的数据
                numCount = getCount(numCount, bean5.getNum());
                kgCount = getCount(kgCount, bean5.getKg());
                showData(include5, bean5);
            }

             whereData = i * ROW_COUNT_NUM + 5;//对应数据应该在集合中的位置
            if (whereData < size) {//该行有数据
                LinearLayout include6 = pageView.findViewById(R.id.include6);//第5行视图
                DataBean bean6 = list.get(whereData);//第5行视图对应的数据
                numCount = getCount(numCount, bean6.getNum());
                kgCount = getCount(kgCount, bean6.getKg());
                showData(include6, bean6);
            }

             whereData = i * ROW_COUNT_NUM + 6;//对应数据应该在集合中的位置
            if (whereData < size) {//该行有数据
                LinearLayout include7 = pageView.findViewById(R.id.include7);//第5行视图
                DataBean bean7 = list.get(whereData);//第5行视图对应的数据
                numCount = getCount(numCount, bean7.getNum());
                kgCount = getCount(kgCount, bean7.getKg());
                showData(include7, bean7);
            }



            tvNumCount.setText(numCount + "");
            tvKgCount.setText(kgCount + "");
            viewList.add(pageView);
        }
        return viewList;
    }


    /**
     * 求和.
     *
     * @param numCount
     * @param numStr
     * @return
     */
    private double getCount(double numCount, String numStr) {
        try {
            double num = Double.parseDouble(numStr);
            BigDecimal bigNum = BigDecimal.valueOf(num);
            BigDecimal bigNumCount = BigDecimal.valueOf(numCount);
            numCount = bigNumCount.add(bigNum).doubleValue();
        } catch (Exception e) {
        }
        return numCount;
    }


    private void showData(LinearLayout include, DataBean bean) {
        int childCount = include.getChildCount();
        for (int j = 0; j < childCount; j++) {
            if (j == 3) {//简图视图
                ImageView iv = (ImageView) include.getChildAt(j);
                String picAbsolutePath = DirManager.getFilesDir(GridLayoutActivity.this) + "/" + bean.getModel();//图片预定位置的绝对路径
                File file = new File(picAbsolutePath);
                if (file.exists()) {
                    //计算图片缩放比例
//                    int inSampleSize = PicUtils.inSampleSize(this, picAbsolutePath, 1920, 1920);
                    //设置参数
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = false; // 计算好压缩比例后，加载原图
//                    options.inSampleSize = inSampleSize; // 设置为刚才计算的压缩比例
                    Bitmap bm = BitmapFactory.decodeFile(picAbsolutePath, options); // 解码文件
                    iv.setImageBitmap(bm);//展示
                }
            } else {
                TextView tv = (TextView) include.getChildAt(j);
                switch (j) {
                    case 0://项目号
                        tv.setText(bean.getProject());
                        break;
                    case 1://产品型号
                        tv.setText(bean.getModel());
                        break;
                    case 2://产品名称
                        tv.setText(bean.getName());
                        break;
                    case 4://生产类型
                        tv.setText(bean.getType());
                        break;
                    case 5://材质
                        tv.setText(bean.getMaterial());
                        break;
                    case 6://厚度
                        tv.setText(bean.getLand());
                        break;
                    case 7://米重
                        tv.setText(bean.getMiZhong());
                        break;
                    case 8://长度
                        tv.setText(bean.getLength());
                        break;
                    case 9://订支数
                        tv.setText(bean.getNum());
                        break;
                    case 10://理重
                        tv.setText(bean.getKg());
                        break;
                    case 11://颜色
                        tv.setText(bean.getColor());
                        break;
                    case 12://包装名称
                        tv.setText(bean.getPack());
                        break;
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layout_table://点击表格:显示表格修改按钮
                int visibility = mTvSetInfo.getVisibility();
                if (visibility == View.INVISIBLE || visibility == View.GONE) {
                    mTvSetInfo.setVisibility(View.VISIBLE);
                } else {
                    mTvSetInfo.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.tv_set_info://修改表格信息
                mTvSetInfo.setVisibility(View.INVISIBLE);
                if (mDialog == null) {
                    mDialog = BaseDialog.createDialog(this, R.layout.dialog_edit_table_info, Gravity.CENTER);
                    //找控件
                    Window win = mDialog.getWindow();
                    Button btnCancel = win.findViewById(R.id.btn_cancel);
                    Button btnOk = win.findViewById(R.id.btn_ok);
                    mEtAddress = win.findViewById(R.id.et_address);
                    mEtTel = win.findViewById(R.id.et_tel);
                    mEtFax = win.findViewById(R.id.et_fax);
                    mEtNo = win.findViewById(R.id.et_no);
                    mEtCustomerName = win.findViewById(R.id.et_customer_name);
                    mEtMakeDate = win.findViewById(R.id.et_make_date);
                    mEtEndDate = win.findViewById(R.id.et_end_date);
                    mEtProjectName = win.findViewById(R.id.et_project_name);
                    mEtRemarks = win.findViewById(R.id.et_remarks);
                    mEtMakePerson = win.findViewById(R.id.et_make_person);
                    mEtAuditPerson = win.findViewById(R.id.et_audit_person);
                    mEtApprovePerson = win.findViewById(R.id.et_approve_person);
                    //设置默认数据
                    mEtAddress.setText(mAddress);
                    mEtTel.setText(mTel);
                    mEtFax.setText(mFax);
                    mEtNo.setText(mNo);
                    mEtCustomerName.setText(mCustomerName);
                    mEtMakeDate.setText(mMakeDate);
                    mEtEndDate.setText(mEndDate);
                    mEtProjectName.setText(mProjectName);
                    mEtRemarks.setText(mRemarks);
                    mEtMakePerson.setText(mMakePerson);
                    mEtAuditPerson.setText(mAuditPerson);
                    mEtApprovePerson.setText(mApprovePerson);
                    //设置点击事件
                    btnCancel.setOnClickListener(this);
                    btnOk.setOnClickListener(this);
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
                if (mDialog != null) {
                    String address = mEtAddress.getText().toString();
                    String tel = mEtTel.getText().toString();
                    String fax = mEtFax.getText().toString();
                    String no = mEtNo.getText().toString();
                    String customerName = mEtCustomerName.getText().toString();
                    String makeDate = mEtMakeDate.getText().toString();
                    String endDate = mEtEndDate.getText().toString();
                    String projectName = mEtProjectName.getText().toString();
                    String remarks = mEtRemarks.getText().toString();
                    String makePerson = mEtMakePerson.getText().toString();
                    String auditPerson = mEtAuditPerson.getText().toString();
                    String approvePerson = mEtApprovePerson.getText().toString();

                    SharedPreferencesUtils.setParam(GridLayoutActivity.this, "address", address);
                    SharedPreferencesUtils.setParam(GridLayoutActivity.this, "tel", tel);
                    SharedPreferencesUtils.setParam(GridLayoutActivity.this, "fax", fax);
                    SharedPreferencesUtils.setParam(GridLayoutActivity.this, "no", no);
                    SharedPreferencesUtils.setParam(GridLayoutActivity.this, "customerName", customerName);
                    SharedPreferencesUtils.setParam(GridLayoutActivity.this, "makeDate", makeDate);
                    SharedPreferencesUtils.setParam(GridLayoutActivity.this, "endDate", endDate);
                    SharedPreferencesUtils.setParam(GridLayoutActivity.this, "projectName", projectName);
                    SharedPreferencesUtils.setParam(GridLayoutActivity.this, "remarks", remarks);
                    SharedPreferencesUtils.setParam(GridLayoutActivity.this, "makePerson", makePerson);
                    SharedPreferencesUtils.setParam(GridLayoutActivity.this, "auditPerson", auditPerson);
                    SharedPreferencesUtils.setParam(GridLayoutActivity.this, "approvePerson", approvePerson);

                    mTvAddress.setText("地址:" + address);
                    mTvTel.setText("电话:" + tel);
                    mTvFax.setText("传真:" + fax);
                    mTvNo.setText("销售订单号:" + no);
                    mTvCustomerName.setText("客户名称:" + customerName);
                    mTvMakeDate.setText("订货日期:" + makeDate);
                    mTvEndDate.setText("交货日期:" + endDate);
                    mTvProjectName.setText("工程名称:" + projectName);
                    mTvRemarks.setText(remarks);
                    mTvMakePerson.setText(makePerson);
                    mTvAuditPerson.setText(auditPerson);
                    mTvApprovePerson.setText(approvePerson);

//                    for (View pageView : mViewList) {
//                        TextView tvAddress = pageView.findViewById(R.id.tv_address);
//                        TextView tvTel = pageView.findViewById(R.id.tv_tel);
//                        TextView tvFax = pageView.findViewById(R.id.tv_fax);
//                        TextView tvNo = pageView.findViewById(R.id.tv_no);
//                        TextView tvCustomerName = pageView.findViewById(R.id.tv_customer_name);
//                        TextView tvMakeDate = pageView.findViewById(R.id.tv_make_date);
//                        TextView tvEndDate = pageView.findViewById(R.id.tv_end_date);
//                        TextView tvProjectName = pageView.findViewById(R.id.tv_project_name);
//                        TextView tvRemarks = pageView.findViewById(R.id.tv_remarks);
//                        TextView tvMakePerson = pageView.findViewById(R.id.tv_make_person);
//                        TextView tvAuditPerson = pageView.findViewById(R.id.tv_audit_person);
//                        TextView tvApprovePerson = pageView.findViewById(R.id.tv_approve_person);
//
//                        tvAddress.setText("地址:" + address);
//                        tvTel.setText("电话:" + tel);
//                        tvFax.setText("传真:" + fax);
//                        tvNo.setText("销售订单号:" + no);
//                        tvCustomerName.setText("客户名称:" + customerName);
//                        tvMakeDate.setText("订货日期:" + makeDate);
//                        tvEndDate.setText("交货日期:" + endDate);
//                        tvProjectName.setText("工程名称:" + projectName);
//                        tvRemarks.setText(remarks);
//                        tvMakePerson.setText(makePerson);
//                        tvAuditPerson.setText(auditPerson);
//                        tvApprovePerson.setText(approvePerson);
//                    }
                    mDialog.dismiss();
                }
                break;
        }
    }
}
