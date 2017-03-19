package com.app.kongsin.sliduplayout;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.kongsin.kanimationcontroller.AnimationQueue;
import com.kongsin.kanimationcontroller.BaseAnimationObject;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment implements View.OnClickListener {

    private List<Item>              items;
    private RelativeLayout          mRootViewGroup;
    private BaseAnimationObject     mRootObj;
    private boolean                 isCallaps;
    private OnMenuClickedListener   menuClickedListener;
    private int                     mCollapseSize, mExpandSize;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setOnClickListener(OnMenuClickedListener  listener){
        this.menuClickedListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_layout, container, false);
        mRootViewGroup = (RelativeLayout) view.findViewById(R.id.rootViewGroup);

        items = new ArrayList<>();
        items.add((Item) view.findViewById(R.id.mGroup1));
        items.add((Item) view.findViewById(R.id.mGroup2));
        items.add((Item) view.findViewById(R.id.mGroup3));
        items.add((Item) view.findViewById(R.id.mGroup4));

        mRootViewGroup.post(new Runnable() {
            @Override
            public void run() {
                initialItemsSize();
            }
        });
        return view;
    }

    private void initialItemsSize() {
        int h = mRootViewGroup.getHeight();
        mExpandSize = h / mRootViewGroup.getChildCount();
        mCollapseSize = toDp(65);
        for (int i = 0; i < mRootViewGroup.getChildCount(); i++) {
            Item view = (Item) mRootViewGroup.getChildAt(i);
            view.setOnClickListener(this);
            view.getLayoutParams().height = h / mRootViewGroup.getChildCount();
            view.requestLayout();
            view.invalidate();
        }
    }

    private void expand(final Item view){
        isCallaps = false;

        if (menuClickedListener != null){
            menuClickedListener.onClicked(view, false);
        }

        mRootObj = new BaseAnimationObject(mRootViewGroup);
        List<BaseAnimationObject> blist = new ArrayList<>();
        AnimationQueue queue = new AnimationQueue();
        for (int i = 0; i < items.size(); i++) {
            BaseAnimationObject b = new BaseAnimationObject(items.get(i));
            if (i == 0) {
                b.goToTop(mRootObj);
            } else {
                b.stackToBottomOf(blist.get(i-1));
            }
            blist.add(b.height(mExpandSize));
            queue.nextQueue(0, blist.get(i));
        }
        queue.startTogether();
        view.showImage(true, blist.get(0));
    }

    private int getCollapseSize(){
        int oraint = getResources().getConfiguration().orientation;
        if (oraint == Configuration.ORIENTATION_PORTRAIT){
            return -((mRootViewGroup.getHeight() / mRootViewGroup.getChildCount()) / 2);
        } else {
            return 0;
        }
    }

    private void collapse(final Item view){
        isCallaps = true;
        if (menuClickedListener != null){
            menuClickedListener.onClicked(view, false);
        }

        mRootViewGroup.bringChildToFront(view);
        mRootObj = new BaseAnimationObject(mRootViewGroup);
        List<BaseAnimationObject> blist = new ArrayList<>();
        AnimationQueue queue = new AnimationQueue();
        for (int i = 0; i < items.size(); i++) {
            BaseAnimationObject b = new BaseAnimationObject(items.get(i));
            if (i == 0) {
                b.goToTop(mRootObj);
            } else {
                b.y(0);
            }
            blist.add(b.height(mCollapseSize));
            queue.nextQueue(0, blist.get(i));
        }
        queue.startTogether();
        view.showImage(false, blist.get(0));

    }

    @Override
    public void onClick(View v) {
        if (isCallaps){
            if (v instanceof Item){
                expand((Item) v);
            }
        } else {
            if (v instanceof Item){
                collapse((Item) v);
            }
        }
    }

    private int toDp(int val){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, val, getContext().getResources().getDisplayMetrics());
    }

}
