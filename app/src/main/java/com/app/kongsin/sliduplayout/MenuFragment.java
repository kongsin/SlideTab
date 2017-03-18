package com.app.kongsin.sliduplayout;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.kanimationcontroller.AnimationQueue;
import com.example.kanimationcontroller.BaseAnimationControl;

public class MenuFragment extends Fragment implements View.OnClickListener {

    private Item                    mGroup1, mGroup2, mGroup3, mGroup4;
    private RelativeLayout          mRootViewGroup;
    private boolean                 isCallaps;
    private OnMenuClickedListener   menuClickedListener;

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
        for (int i = 0; i < mRootViewGroup.getChildCount(); i++) {
            View view = mRootViewGroup.getChildAt(i);
            view.getLayoutParams().height = h / mRootViewGroup.getChildCount();
            view.requestLayout();
            view.invalidate();
        }
    }

    private void expand(){
        isCallaps = false;
        BaseAnimationControl b1 = new BaseAnimationControl(mGroup1);
        b1.goToTop(mRootViewGroup);
        BaseAnimationControl b2 = new BaseAnimationControl(mGroup2);
        b2.stackToBottomOf(b1);
        BaseAnimationControl b3 = new BaseAnimationControl(mGroup3);
        b3.stackToBottomOf(b2);
        BaseAnimationControl b4 = new BaseAnimationControl(mGroup4);
        b4.stackToBottomOf(b3);

        AnimationQueue queue = new AnimationQueue(0, b1);
        queue.nextQueue(0, b2);
        queue.nextQueue(0, b3);
        queue.nextQueue(0, b4);
        queue.startTogether();
    }

    private int getCallapsSize(){
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
            menuClickedListener.onClicked(view.getId());
        }

        mRootViewGroup.bringChildToFront(view);

        BaseAnimationControl b1 = new BaseAnimationControl(mGroup1);
        b1.y(getCallapsSize());
        BaseAnimationControl b2 = new BaseAnimationControl(mGroup2);
        b2.y(getCallapsSize());
        BaseAnimationControl b3 = new BaseAnimationControl(mGroup3);
        b3.y(getCallapsSize());
        BaseAnimationControl b4 = new BaseAnimationControl(mGroup4);
        b4.y(getCallapsSize());

        AnimationQueue queue = new AnimationQueue(0, b1);
        queue.nextQueue(0, b2);
        queue.nextQueue(0, b3);
        queue.nextQueue(0, b4);
        queue.startTogether();
    }

    @Override
    public void onClick(View v) {
        if (isCallaps){
            if (v instanceof Item){
                ((Item) v).showImage(true);
                expand();
            }
        } else {
            if (v instanceof Item){
                ((Item) v).showImage(false);
                collapse((Item) v);
            }
        }
    }

}
