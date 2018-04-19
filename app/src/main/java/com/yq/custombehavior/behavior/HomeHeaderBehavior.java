package com.yq.custombehavior.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.yq.custombehavior.view.HomeContentLayout;
import com.yq.custombehavior.view.HomeHeaderLayout;


/**
 * Description ...
 *
 * @author gsz
 * @since V1.0.1
 */
public class HomeHeaderBehavior extends CoordinatorLayout.Behavior<HomeHeaderLayout> {
    private float mCurrentOffset = -1;

    public HomeHeaderBehavior() {

    }

    public HomeHeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, HomeHeaderLayout child, View dependency) {
        return dependency instanceof HomeContentLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, HomeHeaderLayout child, View dependency) {
        float translationY = dependency.getTranslationY();
        dispatchOffsetUpdates(child, translationY);
        return super.onDependentViewChanged(parent, child, dependency);
    }

    private void dispatchOffsetUpdates(HomeHeaderLayout child, float offset) {
        if (mCurrentOffset != offset) {
            child.onOffsetChanged(offset);
            mCurrentOffset = offset;
        }
    }
}
