package com.yq.custombehavior.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;

import com.yq.custombehavior.utils.Tools;
import com.yq.custombehavior.view.HomeContentLayout;


/**
 * 首页fab Behavior
 *
 * @author gsz
 * @since V1.0.1
 */
public class TranslationFabBehavior extends FloatingActionButton.Behavior {
    private int fabScrollDistance = Tools.dip2px(95);

    public TranslationFabBehavior() {
    }

    public TranslationFabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton fab, View dependency) {
        return dependency instanceof HomeContentLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        if (dependency instanceof HomeContentLayout) {
            float translationY = dependency.getTranslationY();
            float ratio = translationY / getSectionScrollRange();
            child.setTranslationY(fabScrollDistance * ratio);
            child.setScaleX(0.5f - 0.5f * ratio);
            child.setScaleY(0.5f - 0.5f * ratio);
        }
        return super.onDependentViewChanged(parent, child, dependency);
    }

    /**
     * @return Header偏移量
     */
    public int getSectionScrollRange() {
        return Tools.dip2px(68);
    }
}
