package com.app.kongsin.sliduplayout;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by kongsin on 3/18/2017.
 */

public class Item extends FrameLayout {

    private RelativeLayout  mRootItemLayout;
    private LinearLayout    mContentItem;
    private TextView        mItemText;
    private ImageView       mItemImage;
    private View            mTopShadow;
    private View            mBottomShadow;

    public Item(Context context) {
        super(context);
        initView(null);
    }

    public Item(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public Item(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Item(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(attrs);
    }

    private void initView(@Nullable AttributeSet attributeSet) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_layout, this);
        mRootItemLayout = (RelativeLayout) findViewById(R.id.rootItemGroup);
        mContentItem = (LinearLayout) findViewById(R.id.linearLayout_itemContent_itemLayout);
        mItemText = (TextView) findViewById(R.id.textView_itemText_itemLayout);
        mItemImage = (ImageView) findViewById(R.id.imageView_itemImage_itemLayout);
        mTopShadow = findViewById(R.id.topShadowView);
        mBottomShadow = findViewById(R.id.bottomShadowView);

        if (attributeSet != null){
            TypedArray a = getContext().obtainStyledAttributes(attributeSet, R.styleable.Item);
            int count = a.getIndexCount();
            for (int i = 0; i < count; i++) {
                switch (a.getIndex(i)){
                    case R.styleable.Item_tabBackground:
                        int color = a.getColor(a.getIndex(i), Color.DKGRAY);
                        setBackgroundColor(color);
                        break;
                    case R.styleable.Item_tabText:
                        String text = a.getString(a.getIndex(i));
                        mItemText.setText(text);
                        break;
                    case R.styleable.Item_tabIcon:
                        Drawable icon = a.getDrawable(a.getIndex(i));
                        setBackground(icon);
                        break;
                }
            }
            a.recycle();
        }

        post(new Runnable() {
            @Override
            public void run() {
                final int rotation = getContext().getResources().getConfiguration().orientation;
                if (rotation == Configuration.ORIENTATION_LANDSCAPE) {
                    mItemImage.setVisibility(GONE);
                }
            }
        });

    }

    public void showTopShadow(boolean show){
        mTopShadow.setVisibility(show ? VISIBLE : GONE);
    }

    public void showBottomShadow(boolean show){
        mBottomShadow.setVisibility(show ? VISIBLE : GONE);
    }


    public void showImage(final boolean show){
        final int rotation = getContext().getResources().getConfiguration().orientation;
        if (rotation == Configuration.ORIENTATION_PORTRAIT) {
            mItemImage.animate().alpha(show ? 1 : 0).start();
        } else {
            mItemImage.setVisibility(GONE);
        }
    }

    public void setItemText(String text) {
        this.mItemText.setText(text);
    }

    @Override
    public void setBackgroundColor(@ColorInt int color) {
        if (mContentItem != null)
        mContentItem.setBackgroundColor(color);
    }

    @Override
    public void setBackground(Drawable background) {
        if (mContentItem != null)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mContentItem.setBackground(background);
        } else {
            mContentItem.setBackgroundDrawable(background);
        }
    }

    @Override
    public void setBackgroundResource(@DrawableRes int resid) {
        if (mContentItem != null)
        mContentItem.setBackgroundResource(resid);
    }
}
