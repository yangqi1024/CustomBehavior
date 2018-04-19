package com.yq.custombehavior.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.yq.custombehavior.R;
import com.yq.custombehavior.RecyAdapter;
import com.yq.custombehavior.behavior.HomeContentBehavior;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Description ...
 *
 * @author gsz
 * @since V1.0.1
 */
@CoordinatorLayout.DefaultBehavior(HomeContentBehavior.class)
public class HomeContentLayout extends LinearLayout {

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ArrayList<String> data = new ArrayList<>();


    public HomeContentLayout(Context context) {
        this(context, null);
    }

    public HomeContentLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeContentLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = View.inflate(context, R.layout.layout_home_content, this);
        ButterKnife.bind(this, view);
        initData();
        mLinearLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        RecyAdapter recyAdapter = new RecyAdapter(data);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.HORIZONTAL));
        mRecyclerView.setAdapter(recyAdapter);
    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            data.add(String.valueOf(i));
        }
    }

    /**
     * recyclerview 是否已经到顶点
     *
     * @return true :到顶点
     */
    public boolean recyclerViewIsTop() {
        int firstVisibleItemPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
        View viewByPosition = mLinearLayoutManager.findViewByPosition(firstVisibleItemPosition);
        int top = 0;
        if(viewByPosition != null) {
             top = viewByPosition.getTop();
        }
        return firstVisibleItemPosition == 0 && top == 0;
    }

}
