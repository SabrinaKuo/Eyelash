package com.nanadeer.eyelash.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nanadeer.eyelash.R;
import com.nanadeer.eyelash.activity.MainActivity;
import com.nanadeer.eyelash.customview.ItemSliderHelper;
import com.nanadeer.eyelash.database.CustomInfo;
import com.nanadeer.eyelash.database.CustomerTable;
import com.nanadeer.eyelash.fragment.NewCustomFragment;
import com.nanadeer.eyelash.parameter.EyelashParameter;

import java.util.ArrayList;

/**
 * Created by Sabrina Kuo on 2016/6/24.
 */
public class CustomDetailListAdapter extends RecyclerView.Adapter<CustomDetailListAdapter.ViewHolder> implements ItemSliderHelper.Callback{

    private ArrayList<CustomInfo> mDataInfo;
    private RecyclerView mRecyclerView;
    private Context mContext;


    public CustomDetailListAdapter(Context context, ArrayList<CustomInfo> mDataInfo) {
        this.mContext = context;
        this.mDataInfo = mDataInfo;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_detail_list_item, null);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(mDataInfo.get(position).getDate());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mDataInfo.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
        mRecyclerView.addOnItemTouchListener(new ItemSliderHelper(mRecyclerView.getContext(), this));
    }

    @Override
    public int getHorizontalRange(RecyclerView.ViewHolder holder) {
        if (holder.itemView instanceof LinearLayout){
            ViewGroup viewGroup = (ViewGroup) holder.itemView;
            if(viewGroup.getChildCount() == 2){
                return viewGroup.getChildAt(1).getLayoutParams().width;
            }
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder getChildViewHolder(View childView) {
        return mRecyclerView.getChildViewHolder(childView);
    }

    @Override
    public View findTargetView(float x, float y) {
        return mRecyclerView.findChildViewUnder(x, y);
    }

    @Override
    public void onSlideItemClick(int position) {
        removeItem(position);
    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("IS_NEW_RECORD", false);
        bundle.putLong("ORDER_ID", mDataInfo.get(position).getId());

        NewCustomFragment fragment = new NewCustomFragment();
        fragment.setArguments(bundle);

        FragmentTransaction transaction = ((MainActivity)mContext).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainActivityContent, fragment);
        transaction.addToBackStack(EyelashParameter.FRAGMENT_ADD);
        transaction.commit();
    }

    private void removeItem(int position){

        CustomerTable.deleteRecord(mDataInfo.get(position));

        mDataInfo.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mDataInfo.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.custom_detail_date);
        }
    }

}
