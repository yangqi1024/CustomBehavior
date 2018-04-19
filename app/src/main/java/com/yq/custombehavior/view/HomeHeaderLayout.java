package com.yq.custombehavior.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.yq.custombehavior.R;
import com.yq.custombehavior.behavior.HomeHeaderBehavior;
import com.yq.custombehavior.utils.Tools;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Description ...
 *
 * @author gsz
 * @since V1.0.1
 */
@CoordinatorLayout.DefaultBehavior(HomeHeaderBehavior.class)
public class HomeHeaderLayout extends ConstraintLayout {

    @BindView(R.id.view1) View mView1;
    @BindView(R.id.view2) View mView2;
    @BindView(R.id.view3) View mView3;
    @BindView(R.id.view4) View mView4;
    @BindView(R.id.view5) View mView5;
    @BindView(R.id.view6) View mView6;
    @BindView(R.id.view7) View mView7;
    @BindView(R.id.view8) View mView8;
    @BindView(R.id.view9) View mView9;
    @BindView(R.id.view10) View mView10;
    @BindView(R.id.view11) View mView11;
    /**
     * 临界值
     */
    private int criticalValue = Tools.dip2px(40);
    private int smallTranY = Tools.dip2px(14);
    /**
     * 视差系数
     */
    private float parallax = 0.3f;


    public HomeHeaderLayout(Context context) {
        this(context, null);
    }

    public HomeHeaderLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeHeaderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = View.inflate(context, R.layout.layout_home_section, this);
        ButterKnife.bind(this, view);
    }


    /**
     * @return Header偏移量
     */
    public int getSectionScrollRange() {
        return Tools.dip2px(68);
    }

    public void onOffsetChanged(float verticalOffset) {
        handleSection(verticalOffset);
        handleAlpha(verticalOffset);
        handleTranYAndAlpha(verticalOffset);
    }

    private void handleSection(float verticalOffset) {
        float tvSearchAlpha = 1 + verticalOffset * 1.0f / getSectionScrollRange();
        if (tvSearchAlpha > 1) {
            tvSearchAlpha = 1;
        } else if (tvSearchAlpha < 0) {
            tvSearchAlpha = 0;
        }
        float transY = verticalOffset * parallax;
        mView1.setTranslationY(transY);
        mView2.setTranslationY(transY);
        mView3.setTranslationY(transY);
        mView4.setTranslationY(transY);
        mView1.setAlpha(tvSearchAlpha);
        mView2.setAlpha(tvSearchAlpha);
        mView3.setAlpha(tvSearchAlpha);
        mView4.setAlpha(tvSearchAlpha);
        mView1.setVisibility(tvSearchAlpha > 0 ? View.VISIBLE : View.INVISIBLE);
        mView2.setVisibility(tvSearchAlpha > 0 ? View.VISIBLE : View.INVISIBLE);
        mView3.setVisibility(tvSearchAlpha > 0 ? View.VISIBLE : View.INVISIBLE);
        mView4.setVisibility(tvSearchAlpha > 0 ? View.VISIBLE : View.INVISIBLE);
    }


    private void handleTranYAndAlpha(float verticalOffset) {
        //从40dp到最大值时逐渐开始显示
        float alpha = 0;
        float tranY = 0;
        if (Math.abs(verticalOffset) > criticalValue) {
            alpha = -(verticalOffset + criticalValue) * 1.0f / (getSectionScrollRange() - criticalValue);
            tranY = -(verticalOffset + criticalValue) * smallTranY * 1.0f / (getSectionScrollRange() - criticalValue);
        }
        if (alpha > 1) {
            alpha = 1;
        } else if (alpha < 0) {
            alpha = 0;
        }
        if (tranY < 0) {
            tranY = 0;
        } else if (tranY > smallTranY) {
            tranY = smallTranY;
        }
        mView6.setAlpha(alpha);
        mView7.setAlpha(alpha);
        mView8.setAlpha(alpha);
        mView10.setAlpha(alpha);
        mView6.setVisibility(alpha > 0 ? View.VISIBLE : View.INVISIBLE);
        mView7.setVisibility(alpha > 0 ? View.VISIBLE : View.INVISIBLE);
        mView8.setVisibility(alpha > 0 ? View.VISIBLE : View.INVISIBLE);
        mView10.setVisibility(alpha > 0 ? View.VISIBLE : View.INVISIBLE);

        mView6.setTranslationY(tranY);
        mView7.setTranslationY(tranY);
        mView8.setTranslationY(tranY);
        mView10.setTranslationY(tranY);
    }

    private void handleAlpha(float verticalOffset) {
        //40dp的时候隐藏掉
        float tvSearchAlpha = 1 + verticalOffset * 1.0f / criticalValue;
        if (tvSearchAlpha > 1) {
            tvSearchAlpha = 1;
        } else if (tvSearchAlpha < 0) {
            tvSearchAlpha = 0;
        }
        mView5.setAlpha(tvSearchAlpha);
        mView9.setAlpha(tvSearchAlpha);
        mView11.setAlpha(tvSearchAlpha);
        mView5.setVisibility(tvSearchAlpha > 0 ? View.VISIBLE : View.INVISIBLE);
        mView9.setVisibility(tvSearchAlpha > 0 ? View.VISIBLE : View.INVISIBLE);
        mView11.setVisibility(tvSearchAlpha > 0 ? View.VISIBLE : View.INVISIBLE);
    }
}
