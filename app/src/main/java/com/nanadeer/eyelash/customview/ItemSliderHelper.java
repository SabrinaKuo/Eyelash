package com.nanadeer.eyelash.customview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Display right menu, delete button.
 *
 * Created by Sabrina Kuo on 2016/5/20. reference by:https://github.com/yjwfn/SlideItem
 */
public class ItemSliderHelper implements RecyclerView.OnItemTouchListener, GestureDetector.OnGestureListener {

    private static final String TAG = "ItemSwipeHelper";
    private static final Boolean DEBUG = false;

    private final int DEFAULT_DURATION = 200;

    private View mTargetView;

    private int mTouchSlop;
    private int mMaxVelocity;
    private int mMinVelocity;
    private int mLastX;
    private int mLastY;


    private boolean mIsDragging;

    private Animator mExpandAndCollapseAnim;

    private GestureDetectorCompat mGestureDetector;

    private Callback mCallback;


    public ItemSliderHelper(Context context, Callback callback) {
        this.mCallback = callback;

        // Gesture for producing fling event
        mGestureDetector = new GestureDetectorCompat(context, this);

        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
        mMaxVelocity = configuration.getScaledMaximumFlingVelocity();
        mMinVelocity = configuration.getScaledMinimumFlingVelocity();
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        if (DEBUG)
            Log.d(TAG, "onInterceptTouchEvent: " + e.getAction());

        int action =  MotionEventCompat.getActionMasked(e);
        int x = (int) e.getX();
        int y = (int) e.getY();

        // if RecyclerView scroll state not idle then targetView is not null
        if(rv.getScrollState() != RecyclerView.SCROLL_STATE_IDLE){
            if(mTargetView != null){
                // hide is active
                smoothHorizontalExpandOrCollapse(DEFAULT_DURATION / 2);
                mTargetView = null;
            }

            return false;
        }

        // if animation is working then intercept and don't do anything.
        if(mExpandAndCollapseAnim != null && mExpandAndCollapseAnim.isRunning()){
            return true;
        }

        boolean needIntercept =  false;
        switch (action) {
            case MotionEvent.ACTION_DOWN:


                int mActivePointerId = MotionEventCompat.getPointerId(e, 0);
                mLastX = (int) e.getX();
                mLastY = (int) e.getY();

                /*
                * if there is a item already open, this click event didn't occur by right side menu then return TRUE
                * if click event occur by right side menu then return FALSE, this section is for menu need to callback Onclick
                * */
                if(mTargetView != null){
                    return !inView(x, y);
                }

                // find out the view that need to display menu 查找需要显示菜单的view;
                mTargetView = mCallback.findTargetView(x, y);


                break;
            case MotionEvent.ACTION_MOVE:

                int deltaX = (x - mLastX);
                int deltaY = (y - mLastY);

                if(Math.abs(deltaY) > Math.abs(deltaX))
                    return false;

                // if move distance is suitable request then intercept
                needIntercept = mIsDragging = mTargetView != null && Math.abs(deltaX) >= mTouchSlop;
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                /*
                * this section meaning didn't occur intercept.
                * */
                if(isExpanded()){

                    if (inView(x, y)) {
                        // occur by right side menu.
                        if (DEBUG)
                            Log.d(TAG, "click item");
                        mCallback.onSlideItemClick((Integer) mTargetView.getTag());
                    }else{
                        //intercept, prevent targetView active onClick event.拦截事件,防止targetView执行onClick事件
                        needIntercept = true;
                    }

                    // collapse menu
                    smoothHorizontalExpandOrCollapse(DEFAULT_DURATION / 2);
                } else {
                    mCallback.onItemClick((Integer) mTargetView.getTag());
                }

                mTargetView = null;
                break;
        }

        return  needIntercept ;
    }



    private boolean isExpanded() {
        return mTargetView != null &&  mTargetView.getScrollX() == getHorizontalRange();
    }

    private boolean isCollapsed() {

        return mTargetView != null && mTargetView.getScrollX() == 0;
    }

    /*
    * according the scrollX of targetView to calculate targetView offset, that can know this point is in right side menu.
    * */
    private boolean inView(int x, int y) {

        if (mTargetView == null)
            return false;

        int scrollX = mTargetView.getScrollX();
        int left = mTargetView.getWidth() - scrollX;
        int top = mTargetView.getTop();
        int right = left + getHorizontalRange() ;
        int bottom = mTargetView.getBottom();
        Rect rect = new Rect(left, top, right, bottom);
        return rect.contains(x, y);
    }



    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        if (DEBUG)
            Log.d(TAG, "onTouchEvent: " + e.getAction());

        if(mExpandAndCollapseAnim != null && mExpandAndCollapseAnim.isRunning() || mTargetView == null)
            return;

        // if wanna callback fling event, then set mIsDragging to false.
        if (mGestureDetector.onTouchEvent(e)) {
            mIsDragging = false;
            return;
        }

        int x = (int) e.getX();
//        int y = (int) e.getY();
        int action =  MotionEventCompat.getActionMasked(e);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //RecyclerView wont sent this down event

                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = (int) (mLastX - e.getX());
                if(mIsDragging) {
                    horizontalDrag(deltaX);
                }
                mLastX = x;
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

                if(mIsDragging){
                    if(!smoothHorizontalExpandOrCollapse(0) && isCollapsed())
                        mTargetView = null;

                    mIsDragging = false;
                }

                break;
        }


    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    /**
     * according to touch event to scroll the scrollX of View
     *
     * @param delta delta of drag
     */
    private void horizontalDrag(int delta) {
        int scrollX = mTargetView.getScrollX();
        int scrollY = mTargetView.getScrollY();
        if ((scrollX + delta) <= 0) {
            mTargetView.scrollTo(0, scrollY);
            return;
        }


        int horRange = getHorizontalRange();
        scrollX += delta;
        if (Math.abs(scrollX) < horRange) {
            mTargetView.scrollTo(scrollX, scrollY);
        } else {
            mTargetView.scrollTo(horRange, scrollY);
        }


    }


    /**
     * according to current scrollX's position to determine status is expand or collapse.
     *
     * @param velocityX if != 0 , this is a fling event, otherwise is ACTION_UP or ACTION_CANCEL
     */
    private boolean smoothHorizontalExpandOrCollapse(float velocityX) {

        int scrollX = mTargetView.getScrollX();
        int scrollRange = getHorizontalRange();

        if (mExpandAndCollapseAnim != null)
            return false;


        int to = 0;
        int duration = DEFAULT_DURATION;

        if (velocityX == 0) {
            // if already expand half, then smooth expand all.
            if (scrollX > scrollRange / 2) {
                to = scrollRange;
            }
        } else {


            if (velocityX > 0)
                to = 0;
            else
                to = scrollRange;

            duration = (int) ((1.f - Math.abs(velocityX) / mMaxVelocity) * DEFAULT_DURATION);
        }

        if(to == scrollX)
            return false;

        mExpandAndCollapseAnim = ObjectAnimator.ofInt(mTargetView, "scrollX", to);
        mExpandAndCollapseAnim.setDuration(duration);
        mExpandAndCollapseAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mExpandAndCollapseAnim = null;
                if (isCollapsed())
                    mTargetView = null;

                if (DEBUG)
                    Log.d(TAG, "onAnimationEnd");
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                //onAnimationEnd(animation);
                mExpandAndCollapseAnim = null;

                if (DEBUG)
                    Log.d(TAG, "onAnimationCancel");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mExpandAndCollapseAnim.start();

        return true;
    }

    private int getHorizontalRange() {
        RecyclerView.ViewHolder viewHolder = mCallback.getChildViewHolder(mTargetView);
        return mCallback.getHorizontalRange(viewHolder);
    }


    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        if(Math.abs(velocityX) > mMinVelocity && Math.abs(velocityX) < mMaxVelocity) {
            if(!smoothHorizontalExpandOrCollapse(velocityX) ) {
                if(isCollapsed())
                    mTargetView = null;
                return true;
            }
        }
        return false;
    }


    public interface Callback {

        int getHorizontalRange(RecyclerView.ViewHolder holder);

        RecyclerView.ViewHolder getChildViewHolder(View childView);

        View findTargetView(float x, float y);

        void onSlideItemClick(int position); // for callback delete icon click.

        void onItemClick(int position);
    }
}
