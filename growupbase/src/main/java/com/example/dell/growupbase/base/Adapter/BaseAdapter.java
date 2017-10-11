package com.example.dell.growupbase.base.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by dell on 2017/10/10.
 */

public abstract class BaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private enum ITEM_TYPE{
        ITEM_TYPE_HEADER,
        ITEM_TYPE_CONTENT,
        ITEM_TYPE_BOTTOM
    }

    protected LayoutInflater mLayoutImflater;
    protected Context mContext;
    protected int mHeaderCount; //头部View的个数
    protected int mBottomCount; //底部View的个数

    public BaseAdapter(Context ctx){
        mContext = ctx;
        mLayoutImflater = LayoutInflater.from(ctx);
    }

    public boolean isHeader(int position){
        return mHeaderCount != 0 && position < mHeaderCount;
    }

    public boolean isBottom(int position){
        return mBottomCount != 0 && position >= (mHeaderCount + getContentItemCount());
    }

    protected abstract int getContentItemCount();


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //这里的第二个参数就是View的类型，可以根据这个类型判断去创建不同item的ViewHolder

        if(viewType == ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()){   //ordinal值获得枚举常量对象
            return onCreateHeaderView(parent);
        }else if(viewType == ITEM_TYPE.ITEM_TYPE_CONTENT.ordinal()){
            return onCreateContentView(parent);
        }else if(viewType == ITEM_TYPE.ITEM_TYPE_BOTTOM.ordinal()){
            return onCreateBottomView(parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        //getItemViewType方法是用来获取当前项Item(position参数)是哪种类型的布局

        int itemCount = getContentItemCount();
        if(mHeaderCount != 0 && position < mHeaderCount){
            return ITEM_TYPE.ITEM_TYPE_HEADER.ordinal();
        }else if(mBottomCount != 0 && position >= (mHeaderCount + itemCount)){
            return ITEM_TYPE.ITEM_TYPE_BOTTOM.ordinal();
        }else {
            return ITEM_TYPE.ITEM_TYPE_CONTENT.ordinal();
        }
    }

    @Override
    public int getItemCount() {
        return mHeaderCount + getContentItemCount() + mBottomCount;
    }

    protected abstract RecyclerView.ViewHolder onCreateHeaderView(ViewGroup parent);    //创建头部View

    protected abstract RecyclerView.ViewHolder onCreateContentView(ViewGroup parent);   //创建内容View

    protected abstract RecyclerView.ViewHolder onCreateBottomView(ViewGroup parent);    //创建底部View
}
