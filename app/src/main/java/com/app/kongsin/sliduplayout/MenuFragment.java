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

public class MenuFragment extends Fragment implements View.OnClickListener {

    private Item                    mGroup1, mGroup2, mGroup3, mGroup4;
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
        mGroup1 = (Item) view.findViewById(R.id.mGroup1);
        mGroup1.setOnClickListener(this);
        mGroup1.showTopShadow(false);
        mGroup2 = (Item) view.findViewById(R.id.mGroup2);
        mGroup2.setOnClickListener(this);
        mGroup3 = (Item) view.findViewById(R.id.mGroup3);
        mGroup3.setOnClickListener(this);
        mGroup4 = (Item) view.findViewById(R.id.mGroup4);
        mGroup4.setOnClickListener(this);

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
        BaseAnimationObject b1 = new BaseAnimationObject(mGroup1);
        b1.goToTop(mRootObj).height(mExpandSize);
        BaseAnimationObject b2 = new BaseAnimationObject(mGroup2);
        b2.stackToBottomOf(b1).height(mExpandSize);
        BaseAnimationObject b3 = new BaseAnimationObject(mGroup3);
        b3.stackToBottomOf(b2).height(mExpandSize);
        BaseAnimationObject b4 = new BaseAnimationObject(mGroup4);
        b4.stackToBottomOf(b3).height(mExpandSize);

        AnimationQueue queue = new AnimationQueue(0, b1);
        queue.nextQueue(0, b2);
        queue.nextQueue(0, b3);
        queue.nextQueue(0, b4);
        queue.startTogether();
        view.showImage(true, b1);
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
        BaseAnimationObject b1 = new BaseAnimationObject(mGroup1);
        b1.goToTop(mRootObj).height(mCollapseSize);
        BaseAnimationObject b2 = new BaseAnimationObject(mGroup2);
        b2.y(0).height(mCollapseSize);
        BaseAnimationObject b3 = new BaseAnimationObject(mGroup3);
        b3.y(0).height(mCollapseSize);
        BaseAnimationObject b4 = new BaseAnimationObject(mGroup4);
        b4.y(0).height(mCollapseSize);

        AnimationQueue queue = new AnimationQueue(0, b1);
        queue.nextQueue(0, b2);
        queue.nextQueue(0, b3);
        queue.nextQueue(0, b4);
        queue.startTogether();
        view.showImage(false, b1);

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
