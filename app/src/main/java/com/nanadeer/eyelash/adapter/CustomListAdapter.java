package com.nanadeer.eyelash.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nanadeer.eyelash.R;
import com.nanadeer.eyelash.database.CustomInfo;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by sabrinakuo on 2016/5/30.
 */
public class CustomListAdapter extends RecyclerView.Adapter<CustomListAdapter.ViewHolder>{

    private ArrayList<CustomInfo> mDataInfo;

    public CustomListAdapter(ArrayList<CustomInfo> dataInfo) {
        this.mDataInfo = dataInfo;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_item, null);
        ViewHolder viewHolder = new ViewHolder(itemLayout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nameTextView.setText(mDataInfo.get(position).getName());
        holder.phoneTextView.setText(mDataInfo.get(position).getPhone());

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mDataInfo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView phoneTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.custom_name);
            phoneTextView = (TextView) itemView.findViewById(R.id.custom_phone);
        }
    }
}
