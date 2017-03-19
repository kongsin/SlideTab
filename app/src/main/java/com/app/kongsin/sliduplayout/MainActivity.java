package com.app.kongsin.sliduplayout;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.kongsin.kanimationcontroller.BaseAnimationObject;

public class MainActivity extends AppCompatActivity {

    private RecyclerView    mGridLayout;
    private Drawable        mItemBg;
    private ExampleAdapter  mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGridLayout = (RecyclerView) findViewById(R.id.gridView);
        initialMenu();
    }

    private void initialMenu() {
        mAdapter = new ExampleAdapter(MainActivity.this);
        mAdapter.setItemBackground(mItemBg);
        mGridLayout.setLayoutManager(new GridLayoutManager(this, 2));
        mGridLayout.setAdapter(mAdapter);
        setItemDecoration();

        MenuFragment fragment = new MenuFragment();
        fragment.setOnClickListener(new OnMenuClickedListener() {
            @Override
            public void onClicked(Item item, boolean expand) {
                mItemBg = item.getBackground();
                mAdapter.setItemBackground(mItemBg);
                mAdapter.notifyDataSetChanged();
                BaseAnimationObject baseAnimationObject = new BaseAnimationObject(mGridLayout);
                baseAnimationObject.y(expand ? toDp(300) : toDp(65));
                baseAnimationObject.start();
            }
        });

        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, fragment)
                .commit();
    }

    private void setItemDecoration() {
        mGridLayout.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.left = toDp(12);
                outRect.right = toDp(12);
                outRect.top = toDp(12);
                outRect.bottom = toDp(12);
            }
        });
    }

    private int toDp(int val){
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, val, getResources().getDisplayMetrics());
    }

}
