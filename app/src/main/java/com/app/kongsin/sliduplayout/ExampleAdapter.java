package com.app.kongsin.sliduplayout;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by kongsin on 3/19/2017.
 */

public class ExampleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "ExampleAdapter";
    private final Context       mContext;
    private GradientDrawable    mItemBg;

    public ExampleAdapter(Context context){
        mContext = context;
    }

    public void setItemBackground(Drawable drawable){
        if (drawable instanceof ColorDrawable) {
            mItemBg = new GradientDrawable();
            mItemBg.setColor(((ColorDrawable) drawable).getColor());
            mItemBg.setAlpha(95);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ExampleViewHolder(new View(mContext));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, toDp(200));
        holder.itemView.setLayoutParams(params);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            holder.itemView.setBackground(mItemBg);
        } else {
            holder.itemView.setBackgroundDrawable(mItemBg);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.itemView.setElevation(toDp(8));
        }
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    private int toDp(int val){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, val, mContext.getResources().getDisplayMetrics());
    }

    private class ExampleViewHolder extends RecyclerView.ViewHolder{
        ExampleViewHolder(View itemView) {
            super(itemView);
        }
    }

}
