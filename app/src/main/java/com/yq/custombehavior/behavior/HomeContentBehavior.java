package com.yq.custombehavior.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.OverScroller;

import com.yq.custombehavior.MyApplication;
import com.yq.custombehavior.utils.MathUtils;
import com.yq.custombehavior.utils.Tools;
import com.yq.custombehavior.view.HomeContentLayout;


/**
 * Description ...
 *
 * @author gsz
 * @since V1.0.1
 */
public class HomeContentBehavior extends CoordinatorLayout.Behavior<HomeContentLayout> {
    private static final int DURATION_SHORT = 300;
    private OverScroller mOverScroller;
    private FlingRunnable mFlingRunnable;

    public HomeContentBehavior() {
        init();
    }

    public HomeContentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mOverScroller = new OverScroller(MyApplication.getmContext());
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, HomeContentLayout child, int layoutDirection) {
        child.layout(parent.getLeft(), getSectionHeight(), parent.getRight(), parent.getBottom() + getSectionScrollRange());
        return true;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, HomeContentLayout child, View directTargetChild, View target, int nestedScrollAxes) {
        //拦截垂直方向上的滚动事件
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }


    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, HomeContentLayout child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        if (dy != 0) {
            if (dy < 0) {
                //向下
                if (child.recyclerViewIsTop()) {
                    consumed[1] = scroll(child, dy);
                }
            } else {
                consumed[1] = scroll(child, dy);
            }
        }
    }

    private int scroll(HomeContentLayout child, int dy) {
        int curOffset = (int) child.getTranslationY();
        int maxOffset = 0;
        int minOffset = -getSectionScrollRange();
        int newOffset = (int) (child.getTranslationY() - dy);
        int consumed = 0;
        if (curOffset >= minOffset && curOffset <= maxOffset) {
            // If we have some scrolling range, and we're currently within the min and max
            // offsets, calculate a new offset
            newOffset = MathUtils.constrain(newOffset, minOffset, maxOffset);
            if (curOffset != newOffset) {
                child.setTranslationY(newOffset);
                // Update how much dy we have consumed
                consumed = curOffset - newOffset;
            }
        }
        return consumed;
    }

    /**
     * @return Header偏移量
     */
    public int getSectionScrollRange() {
        return Tools.dip2px(68);
    }

    public int getSectionHeight() {
        return Tools.dip2px(128);
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, HomeContentLayout child, View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);
        float translationY = child.getTranslationY();
        if (translationY < 0 && translationY > -getSectionScrollRange()) {
            //防止还没有滑动时出现抖动
            handleActionUp(coordinatorLayout, child);
        }
    }

    private void handleActionUp(CoordinatorLayout parent, HomeContentLayout child) {
        if (mFlingRunnable != null) {
            child.removeCallbacks(mFlingRunnable);
            mFlingRunnable = null;
        }
        //手指抬起时，header上滑距离超过总距离一半，则整体自动上滑到关闭状态
        float translationY = Math.abs(child.getTranslationY());
        if (translationY < getSectionScrollRange() / 2.0f) {
            scrollToOpen(parent, child, DURATION_SHORT);
        } else {
            scrollToClose(parent, child, DURATION_SHORT);
        }
    }

    private void scrollToClose(CoordinatorLayout parent, HomeContentLayout child, int duration) {
        int curTranslationY = (int) child.getTranslationY();
        int dy = getSectionScrollRange() + curTranslationY;
        mOverScroller.startScroll(0, curTranslationY, 0, -dy, duration);
        start(parent, child);
    }

    private void scrollToOpen(CoordinatorLayout parent, HomeContentLayout child, int duration) {
        float curTranslationY = child.getTranslationY();
        mOverScroller.startScroll(0, (int) curTranslationY, 0, (int) -curTranslationY, duration);
        start(parent, child);
    }

    private void start(CoordinatorLayout parent, HomeContentLayout child) {
        if (mOverScroller.computeScrollOffset()) {
            mFlingRunnable = new FlingRunnable(parent, child);
            ViewCompat.postOnAnimation(child, mFlingRunnable);
        } else {
            onFlingFinished(child);
        }
    }

    private boolean isClosed(View child) {
        return child.getTranslationY() == getSectionScrollRange();
    }


    private void onFlingFinished(View layout) {

    }

    private class FlingRunnable implements Runnable {
        private final CoordinatorLayout mParent;
        private final HomeContentLayout mLayout;

        FlingRunnable(CoordinatorLayout parent, HomeContentLayout layout) {
            mParent = parent;
            mLayout = layout;
        }

        @Override
        public void run() {
            if (mLayout != null && mOverScroller != null) {
                if (mOverScroller.computeScrollOffset()) {
                    mLayout.setTranslationY(mOverScroller.getCurrY());
                    ViewCompat.postOnAnimation(mLayout, this);
                } else {
                    onFlingFinished(mLayout);
                }
            }
        }
    }
}
