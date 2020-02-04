package com.wei.datasearch.adapter;

import android.content.Context;

import com.wei.datasearch.R;
import com.wei.datasearch.base.CommonAdapter;
import com.wei.datasearch.base.ViewHolder;
import com.wei.datasearch.bean.DataBean;

import java.util.List;

/**
 * @Author WCL
 * @Date 2019/6/20 16:59
 * @Version 1.0
 * @Description 首页搜索列表的适配器
 */
public class LvAdapter extends CommonAdapter<DataBean> {

    public LvAdapter(Context context, List<DataBean> dataList) {
        super(context, dataList, R.layout.item_produce);
    }

    @Override
    public void convert(ViewHolder holder, DataBean bean) {
        String info = "项目号: " + bean.getProject()
                + "\r\n型号: " + bean.getModel()
                + "\r\n名称: " + bean.getName()
                + "\r\n机台: " + bean.getType()
                + "\r\n贴膜: " + bean.getMaterial()
                + "\r\n厚度: " + bean.getLand()
                + "\r\n米重: " + bean.getMiZhong()
                + "\r\n长度: " + bean.getLength()
                + "\r\n定支数: " + bean.getNum()
                + "\r\n理重: " + bean.getKg()
                + "\r\n颜色: " + bean.getColor()
                + "\r\n包装名称: " + bean.getPack();
        holder.setText(R.id.tv_info, info);
    }
}
