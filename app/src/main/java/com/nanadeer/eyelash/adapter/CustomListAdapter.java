package com.nanadeer.eyelash.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nanadeer.eyelash.R;
import com.nanadeer.eyelash.activity.MainActivity;
import com.nanadeer.eyelash.database.CustomInfo;
import com.nanadeer.eyelash.fragment.CustomDetailFragment;
import com.nanadeer.eyelash.parameter.EyelashParameter;

import java.util.ArrayList;

/**
 * Created by Sabrina Kuo on 2016/5/30.
 */
public class CustomListAdapter extends RecyclerView.Adapter<CustomListAdapter.ViewHolder>{

    private ArrayList<CustomInfo> mDataInfo;
    private Context mContext;

    public CustomListAdapter(Context context, ArrayList<CustomInfo> dataInfo) {
        this.mContext = context;
        this.mDataInfo = dataInfo;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_item, null);
        return new ViewHolder(itemLayout);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.nameTextView.setText(mDataInfo.get(position).getName());
        holder.phoneTextView.setText(mDataInfo.get(position).getPhone());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataInfo.size();
    }

    private void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("PHONE", mDataInfo.get(position).getPhone());
        bundle.putString("NAME", mDataInfo.get(position).getName());

        CustomDetailFragment fragment = new CustomDetailFragment();
        fragment.setArguments(bundle);

        FragmentTransaction transaction = ((MainActivity)mContext).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainActivityContent, fragment);
        transaction.addToBackStack(EyelashParameter.FRAGMENT_CUSTOM_DETAIL);
        transaction.commit();
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
