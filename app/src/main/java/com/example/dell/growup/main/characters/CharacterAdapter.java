package com.example.dell.growup.main.characters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.growup.R;
import com.example.dell.growup.network.result.CharactersResult;
import com.example.dell.growupbase.base.Adapter.BaseAdapter;

import java.util.List;

/**
 * Created by dell on 2017/10/10.
 */

public class CharacterAdapter extends BaseAdapter {

    private List<CharactersResult.CharacterList> characterLists;
    private List<CharactersResult.HeaderList> headerLists;
    private List<CharactersResult.BottomList> bottomLists;

    public CharacterAdapter(Context ctx) {
        super(ctx);

        characterLists = new CharacterData().getCharacter();
        headerLists = new CharacterData().getHeaderList();
        bottomLists = new CharacterData().getBottomList();

        mHeaderCount = headerLists.size();
        mBottomCount = bottomLists.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        if(holder instanceof HeaderViewHoder){
            switch (headerLists.get(position).getImg()){
                case "froest":
                    ((HeaderViewHoder)holder).background_img.setImageResource(R.mipmap.froest_cut);
                    break;
            }

            ((HeaderViewHoder)holder).title_tx.setText(headerLists.get(position).getText());
        }else if(holder instanceof ContentViewHolder){
            Log.e("test",(position - headerLists.size())+"");
            switch (characterLists.get(position - headerLists.size()).getImage()){
                case "wuming":
                    ((ContentViewHolder)holder).background_img.setImageResource(R.mipmap.wuming);
                    break;

                case "alixiya":
                    ((ContentViewHolder)holder).background_img.setImageResource(R.mipmap.alixiya);
                    break;
            }

            ((ContentViewHolder)holder).description.setText(
                    characterLists.get(position - headerLists.size()).getDescribe());
        }else if(holder instanceof BottomViewHolder){
            if(bottomLists.get(position - headerLists.size() -characterLists.size()).getImg().equals("")){
                ((BottomViewHolder)holder).background_img.setVisibility(View.GONE);
            }
            ((BottomViewHolder)holder).title_tx.setText(
                    bottomLists.get(position - headerLists.size() -characterLists.size()).getText());
        }
    }

    @Override
    protected int getContentItemCount() {
        return characterLists.size();
    }

    @Override
    protected RecyclerView.ViewHolder onCreateHeaderView(ViewGroup parent) {
        return new HeaderViewHoder(mLayoutImflater.inflate(R.layout.item_image, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateContentView(ViewGroup parent) {
        return new ContentViewHolder(mLayoutImflater.inflate(R.layout.item_character, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateBottomView(ViewGroup parent) {
        return new BottomViewHolder(mLayoutImflater.inflate(R.layout.item_image, parent, false));
    }

    private static class HeaderViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView background_img;
        TextView title_tx;

        public HeaderViewHoder(View itemView) {
            super(itemView);

            background_img = (ImageView)itemView.findViewById(R.id.image_view);
            title_tx = (TextView)itemView.findViewById(R.id.text_view);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    private static class ContentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView background_img;
        TextView title_tx;
        TextView description;

        ContentViewHolder(View itemView) {
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

    private static class BottomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView background_img;
        TextView title_tx;

        BottomViewHolder(View itemView) {
            super(itemView);

            background_img = (ImageView)itemView.findViewById(R.id.image_view);
            title_tx = (TextView)itemView.findViewById(R.id.text_view);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
