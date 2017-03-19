package com.app.kongsin.sliduplayout;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private RecyclerView            mGridLayout;
    private Drawable                mItemBg;
    private ExampleAdapter          mAdapter;

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
        fragment.injectRecyclerView(mGridLayout);
        fragment.setOnClickListener(new OnMenuClickedListener() {
            @Override
            public void onClicked(Item item, boolean expand) {
                mItemBg = item.getBackground();
                mAdapter.setItemBackground(mItemBg);
                mAdapter.notifyDataSetChanged();
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
                GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();

                int pos = parent.getChildAdapterPosition(view);
                if (pos % layoutManager.getSpanCount() == 0) {
                    outRect.left = toDp(12);
                    outRect.right = toDp(6);
                } else {
                    outRect.left = toDp(6);
                    outRect.right = toDp(12);
                }
                if (pos < layoutManager.getSpanCount()) {
                    outRect.top = toDp(12);
                    outRect.bottom = toDp(6);
                } else if (pos < (layoutManager.getItemCount() - layoutManager.getSpanCount())){
                    outRect.top = toDp(6);
                    outRect.bottom = toDp(6);
                } else {
                    outRect.top = toDp(6);
                    outRect.bottom = toDp(12);
                }

            }
        });
    }

    private int toDp(int val){
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, val, getResources().getDisplayMetrics());
    }

}
