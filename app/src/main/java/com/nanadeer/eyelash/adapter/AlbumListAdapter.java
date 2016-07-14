package com.nanadeer.eyelash.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nanadeer.eyelash.R;
import com.nanadeer.eyelash.database.PhotoInfo;

import java.util.ArrayList;

/**
 * Created by Sabrina Kuo on 2016/6/4.
 */
public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.ViewHolder> {

    private ArrayList<PhotoInfo> mDataInfo;
    private Context mContext;

    public AlbumListAdapter(Context context, ArrayList<PhotoInfo> dataInfo) {
        mContext = context;
        mDataInfo = dataInfo;
    }

    @Override
    public AlbumListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_list_item, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(AlbumListAdapter.ViewHolder holder, int position) {
        CardView cardView = holder.cardView;

        ImageView imageView = (ImageView)cardView.findViewById(R.id.info_image);
        Drawable drawable = cardView.getResources().getDrawable(mDataInfo.get(position).getImageResourceId(mContext));
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(mDataInfo.get(position).getDescribe());

        TextView textView = (TextView)cardView.findViewById(R.id.info_text);
        textView.setText(mDataInfo.get(position).getDescribe());

    }

    @Override
    public int getItemCount() {
        return mDataInfo.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private CardView cardView;
        public ViewHolder(CardView itemView) {
            super(itemView);
            cardView = itemView;
        }
    }
}
