package com.example.dell.growup.main.backgroud;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.growup.R;
import com.example.dell.growup.network.result.BackgroudResult;
import com.example.dell.growupbase.base.Adapter.BaseAdapter;

import java.util.List;

/**
 * Created by dell on 2017/10/10.
 */

public class BackgroudAdapter extends BaseAdapter {

    private List<BackgroudResult.BackgroudList> backgroudLists;

    public BackgroudAdapter(Context ctx) {
        super(ctx);
        mHeaderCount = 0;
        mBottomCount = 0;

        backgroudLists = new BackgroudData().getBackgroud();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        if(holder instanceof ContentViewHolder){
            ((ContentViewHolder) holder).title_tx.setText(backgroudLists.get(position).getName());
            ((ContentViewHolder) holder).description.setText(backgroudLists.get(position).getDescribe());
            switch (backgroudLists.get(position).getImage()){
                case "froest":
                    ((ContentViewHolder) holder).background_img.setImageResource(R.mipmap.froest);
                    break;

                case "city":
                    ((ContentViewHolder) holder).background_img.setImageResource(R.mipmap.city);
                    break;

                case "desert":
                    ((ContentViewHolder) holder).background_img.setImageResource(R.mipmap.city);
                    break;
            }

        }
    }

    @Override
    protected int getContentItemCount() {
        return backgroudLists.size();
    }

    @Override
    protected RecyclerView.ViewHolder onCreateHeaderView(ViewGroup parent) {
        return null;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateContentView(ViewGroup parent) {
        return new ContentViewHolder(mLayoutImflater.inflate(R.layout.item_character, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateBottomView(ViewGroup parent) {
        return null;
    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView background_img;
        TextView title_tx;
        TextView description;

        public ContentViewHolder(View itemView) {
            super(itemView);

            background_img = (ImageView)itemView.findViewById(R.id.iv_background);
            title_tx = (TextView)itemView.findViewById(R.id.tv_title);
            description = (TextView)itemView.findViewById(R.id.tv_description);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
