package com.example.sda.warehouse.ui;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

public class ScrollAwareFABBehavior extends FloatingActionButton.Behavior {

    public ScrollAwareFABBehavior(Context context, AttributeSet attrs) {
        super();
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout,
                                       FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL ||
                super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target,
                        nestedScrollAxes);
    }

    /*Runnable showRunnable = new Runnable() {
        @Override
        public void run(FloatingActionButton fab) {
            fab.show();
        }
    };*/

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,
                               View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed,
                dyUnconsumed);

        if (dyUnconsumed>0 || dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
            child.hide(new FloatingActionButton.OnVisibilityChangedListener() {
                           @Override
                           public void onHidden(FloatingActionButton fab) {
                               super.onHidden(fab);
                               fab.setVisibility(View.INVISIBLE);
                           }
                       }
            );
          /*  child.show(new FloatingActionButton.OnVisibilityChangedListener() {
                @Override
                public void onShown(FloatingActionButton fab) {
                    super.onShown(fab);

                    fab.setVisibility(View.VISIBLE);
                }
            });*/
        } else if (dyUnconsumed<0 ||dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
            child.show();



            /*child.show(new FloatingActionButton.OnVisibilityChangedListener() {
                @Override
                public void onShown(final FloatingActionButton fab) {
                    super.onShown(fab);
                    Runnable showRunnable = new Runnable() {
                        @Override
                        public void run() {
                            fab.hide();
                        }
                    };



                    //fab.setVisibility(View.VISIBLE);
                }
            });*/
        }
    }
    /*@Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,
                               View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        if (dyConsumed > 0) {
            // User scrolled down -> hide the FAB
            List<View> dependencies = coordinatorLayout.getDependencies(child);
            for (View view : dependencies) {
                if (view instanceof FloatingActionButton) {
                    ((FloatingActionButton) view).hide();
                }
            }
        } else if (dyConsumed < 0) {
            // User scrolled up -> show the FAB
            List<View> dependencies = coordinatorLayout.getDependencies(child);
            for (View view : dependencies) {
                if (view instanceof FloatingActionButton) {
                    ((FloatingActionButton) view).show();
                }
            }
        }
    }*/

}