package com.wq.android.lib.verticaltablayout;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by qwang on 2016/8/10.
 */
public class VerticalTabLayout extends ScrollView implements View.OnClickListener {

    private static final int INVALID_NUM = -1;
    private static final int SHADOW_COLOR = 0x22000000;
    private static final int ARROW_TYPE_INNER = 1;
    private static final int ARROW_TYPE_OUTER = 2;

    private TabStrip mTabStrip;
    private int mIndicatorColor = Color.GRAY;
    private Context mContext;
    private int mIndicatorWidth = Integer.MAX_VALUE;
    private int mIndicatorLeft = 0;
    private float mIndicatorY = 0;
    private int mIndicatorRight = 0;
    private int mIndicatorCorners = 0;
    private int mDividerHeight = 0;
    private int mDividerColor = Color.GRAY;
    private int mIndicatorGravity = Gravity.RIGHT;
    private int mTabHeight = INVALID_NUM;
    private int mDividerPadding = 0;
    private int mArrowColor = Color.TRANSPARENT;
    private int mArrowGravity = Gravity.RIGHT;
    private int mTabTextColor = Color.DKGRAY;
    private int mTabSelectedTextColor = Color.DKGRAY;
    private int mTabTextAppearance = INVALID_NUM;
    private int mTabTextSize = INVALID_NUM;
    private int mTabPadding = INVALID_NUM;
    private int mTabPaddingLeft = 0;
    private int mTabPaddingRight = 0;
    private int mTabPaddingTop = 0;
    private int mTabPaddingBottom = 0;
    private int mTabGravity = Gravity.CENTER_VERTICAL;
    private OnTabSelectedListener mOnTabSelectedListener;
    private int mArrowSize = 0;
    private int mArrowType = ARROW_TYPE_INNER;
    private int mTabDrawablePadding = 0;

    public interface OnTabSelectedListener {
        public void onTabSelected(Tab tab, int position);

        public void onTabReleased(Tab tab, int position);
    }

    public static class OnTabSelectedAdapter implements OnTabSelectedListener {

        @Override
        public void onTabSelected(Tab tab, int position) {

        }

        @Override
        public void onTabReleased(Tab tab, int position) {

        }
    }

    public VerticalTabLayout(Context context) {
        super(context);
        init(context, null);
    }

    public VerticalTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public VerticalTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public VerticalTabLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        mTabStrip = new TabStrip(mContext);
        super.addView(mTabStrip, 0, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        setFillViewport(true);

        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.VerticalTabLayout);
        mIndicatorColor = typedArray.getColor(R.styleable.VerticalTabLayout_tabIndicatorColor, mIndicatorColor);
        mArrowColor = typedArray.getColor(R.styleable.VerticalTabLayout_tabArrowColor, mArrowColor);
        mArrowGravity = typedArray.getInteger(R.styleable.VerticalTabLayout_tabArrowGravity, mArrowGravity);
        mIndicatorWidth = (int) typedArray.getDimension(R.styleable.VerticalTabLayout_tabIndicatorWidth, dpToPx(5));
        mIndicatorCorners = (int) typedArray.getDimension(R.styleable.VerticalTabLayout_tabIndicatorCorners, mDividerHeight);
        mDividerHeight = (int) typedArray.getDimension(R.styleable.VerticalTabLayout_dividerHeight, mDividerHeight);
        mDividerColor = typedArray.getColor(R.styleable.VerticalTabLayout_dividerColor, Color.TRANSPARENT);
        mDividerPadding = (int) typedArray.getDimension(R.styleable.VerticalTabLayout_dividerPadding, mDividerPadding);
        mIndicatorGravity = typedArray.getInteger(R.styleable.VerticalTabLayout_tabIndicatorGravity, mIndicatorGravity);
        mTabHeight = (int) typedArray.getDimension(R.styleable.VerticalTabLayout_tabHeight, LayoutParams.WRAP_CONTENT);
        mTabTextColor = typedArray.getColor(R.styleable.VerticalTabLayout_tabTextColor, mTabTextColor);
        mTabSelectedTextColor = typedArray.getColor(R.styleable.VerticalTabLayout_tabSelectedTextColor, mTabSelectedTextColor);
        mTabTextAppearance = typedArray.getResourceId(R.styleable.VerticalTabLayout_tabTextAppearance, mTabTextAppearance);
        mTabTextSize = (int) typedArray.getDimension(R.styleable.VerticalTabLayout_tabTextSize, mTabTextSize);
        mTabPadding = (int) typedArray.getDimension(R.styleable.VerticalTabLayout_tabPadding, mTabPadding);
        mTabPaddingLeft = (int) typedArray.getDimension(R.styleable.VerticalTabLayout_tabPaddingLeft, mTabPaddingLeft);
        mTabPaddingRight = (int) typedArray.getDimension(R.styleable.VerticalTabLayout_tabPaddingRight, mTabPaddingRight);
        mTabPaddingTop = (int) typedArray.getDimension(R.styleable.VerticalTabLayout_tabPaddingTop, mTabPaddingTop);
        mTabPaddingBottom = (int) typedArray.getDimension(R.styleable.VerticalTabLayout_tabPaddingBottom, mTabPaddingBottom);
        mTabGravity = typedArray.getInteger(R.styleable.VerticalTabLayout_tabViewGravity, mTabGravity);
        mArrowType = typedArray.getInteger(R.styleable.VerticalTabLayout_tabArrowType, mArrowType);
        mTabDrawablePadding = (int) typedArray.getDimension(R.styleable.VerticalTabLayout_tabDrawablePadding, mTabDrawablePadding);
        typedArray.recycle();
        initLayout();
    }

    private void initLayout() {
        post(new Runnable() {
            @Override
            public void run() {
                initBackgroundForArrow();
                initDivider();
                initIndicatorGravity();
            }
        });
    }

    public void setOnTabSelectedListener(@Nullable OnTabSelectedListener listener) {
        mOnTabSelectedListener = listener;
    }

    public void addTab(Tab tab) {
        TabView tabView = new TabView(mContext, tab);
        mTabStrip.addView(tabView);
        tabView.setOnClickListener(this);
    }

    /**
     * Create and return a new {@link Tab}. You need to manually add this using
     * {@link #addTab(Tab)} or a related method.
     *
     * @return A new Tab
     * @see #addTab(Tab)
     */
    @NonNull
    public Tab newTab() {
        return new Tab();
    }

    @Override
    public void onClick(View view) {
        setTabSelected(view);
    }

    public void setTabSelected(int position) {
        setTabSelected(mTabStrip.getChildAt(position));
    }

    private View mSelectedTab = null;

    private void setTabSelected(View view) {
        if (view == null) {
            return;
        }
        if (view != mSelectedTab) {
            if (mSelectedTab != null) {
                mSelectedTab.setSelected(false);
            }
            view.setSelected(true);
            mSelectedTab = view;
            scrollTab(mSelectedTab);
            mTabStrip.moveIndicator(view);
        }
    }

    private void scrollTab(final View tabView) {
        tabView.post(new Runnable() {
            @Override
            public void run() {
                int tabTop = tabView.getTop() + tabView.getHeight() / 2 - getScrollY();
                int target = getHeight() / 2;
                if (tabTop > target) {
                    smoothScrollBy(0, tabTop - target);
                } else if (tabTop < target) {
                    smoothScrollBy(0, tabTop - target);
                }
            }
        });
    }

    public void setTabHeight(int px) {
        mTabHeight = px;
        mTabStrip.postInvalidate();
    }

    public void setArrowColor(int color) {
        mArrowColor = color;
    }

    public void removeAllTabs() {
        mTabStrip.removeAllViews();
    }

    public void initBackgroundForArrow() {
        if (mArrowColor != Color.TRANSPARENT) {
            mArrowSize = mTabHeight / 6;
            if (mArrowType == ARROW_TYPE_OUTER) {
                LayerDrawable d = new LayerDrawable(new Drawable[]{getBackground()});
                if (mArrowGravity == Gravity.RIGHT) {
                    d.setLayerInset(0, 0, 0, mArrowSize, 0);
                } else if (mArrowGravity == Gravity.LEFT) {
                    d.setLayerInset(0, mArrowSize, 0, 0, 0);
                }
                setBackground(d);
            }
        }
    }

    public void setDivider(int height, int color, int padding) {
        mDividerColor = color;
        mDividerHeight = height;
        mDividerPadding = padding;
    }

    private void initDivider() {
        if (mDividerHeight > 0) {
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(mDividerColor);
            drawable.setSize(1, mDividerHeight);
            mTabStrip.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            if (mArrowType == ARROW_TYPE_OUTER && mArrowSize > 0 && mArrowColor != Color.TRANSPARENT) {
                LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{drawable});
                if (mArrowGravity == Gravity.LEFT) {
                    layerDrawable.setLayerInset(0, mArrowSize, 0, 0, 0);
                } else {
                    layerDrawable.setLayerInset(0, 0, 0, mArrowSize, 0);
                }
                mTabStrip.setDividerDrawable(layerDrawable);
            } else {
                mTabStrip.setDividerDrawable(drawable);
            }
            mTabStrip.setDividerPadding(mDividerPadding);
            mTabStrip.postInvalidate();
        }
    }

    private void initIndicatorGravity() {
        if (mArrowType == ARROW_TYPE_OUTER && mArrowColor != Color.TRANSPARENT) {
            calcIndicatorSizeByArrowOuter();
        } else {
            calcIndicatorSizeByArrowInner();
        }
    }

    private void calcIndicatorSizeByArrowOuter() {
        int width = mTabStrip.getWidth();
        if (mIndicatorGravity == Gravity.FILL || mIndicatorWidth >= width) {
            mIndicatorLeft = 0 + mArrowGravity == Gravity.LEFT ? mArrowSize : 0;
            mIndicatorRight = width - (mArrowGravity == Gravity.RIGHT ? mArrowSize : 0);
        } else if (mIndicatorGravity == Gravity.LEFT) {
            mIndicatorLeft = 0 + mArrowSize;
            mIndicatorRight = mIndicatorWidth;
        } else {
            mIndicatorLeft = width - mIndicatorWidth - mArrowSize;
            mIndicatorRight = width - mArrowSize;
        }
        mTabStrip.invalidate();
    }

    private void calcIndicatorSizeByArrowInner() {
        int width = mTabStrip.getWidth();
        if (mIndicatorGravity == Gravity.FILL || mIndicatorWidth >= width) {
            mIndicatorLeft = 0;
            mIndicatorRight = width;
        } else if (mIndicatorGravity == Gravity.LEFT) {
            mIndicatorLeft = 0;
            mIndicatorRight = mIndicatorWidth;
        } else {
            mIndicatorLeft = width - mIndicatorWidth;
            mIndicatorRight = width;
        }
        mTabStrip.invalidate();
    }

    @Override
    public void addView(View child) {
        addViewInternal(child);
    }

    @Override
    public void addView(View child, int index) {
        addViewInternal(child);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        addViewInternal(child);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        addViewInternal(child);
    }

    private void addViewInternal(final View child) {
        if (child instanceof TabItem) {
            addTabFromItemView((TabItem) child);
        } else {
            throw new IllegalArgumentException("Only TabItem instances can be added to TabLayout");
        }
    }

    private void addTabFromItemView(@NonNull TabItem item) {
        Tab tab = newTab();
        tab.setText(item.getText().toString());
        tab.setDrawable(item.getIcon());
        tab.setDrawableGravity(item.getIconGravity());
        tab.setContentDescription(item.getContentDescription() != null ? item.getContentDescription().toString() : null);
        addTab(tab);
    }

    @Override
    public void removeView(View view) {
    }

    @Override
    public void removeAllViews() {
    }

    @Override
    public void removeViewAt(int index) {
    }

    private class TabStrip extends LinearLayout {
        private final Paint mPaint;
        private ValueAnimator mAnimator;
        private RectF mRect = new RectF();
        private Path mArrowPath = new Path();


        TabStrip(Context context) {
            super(context);
            setWillNotDraw(false);
            setOrientation(LinearLayout.VERTICAL);
            mPaint = new Paint();
        }

        public void moveIndicator(View view) {
            float moveTo = view.getTop();
            if (mIndicatorY == moveTo) {
                return;
            }

            if (mAnimator != null && mAnimator.isRunning()) {
                mAnimator.cancel();
            }
            mAnimator = ValueAnimator.ofFloat(mIndicatorY, moveTo);
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float value = Float.parseFloat(valueAnimator.getAnimatedValue().toString());
                    mIndicatorY = value;
                    invalidate();
                }
            });
            mAnimator.setDuration(200).start();
        }

        @Override
        public void addView(View child) {
            super.addView(child, createLayoutParamsForTabs());
        }

        private LayoutParams createLayoutParamsForTabs() {
            final LayoutParams lp = new LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            lp.height = mTabHeight <= 0 ? FrameLayout.LayoutParams.MATCH_PARENT : mTabHeight;
            return lp;
        }

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if(getChildCount() <= 0) {
                return;
            }
            int width = getWidth();
            mPaint.reset();
            //drawShadow(canvas, width);
            drawIndicator(canvas);
            drawArrow(canvas, width);
        }

        private void drawShadow(Canvas canvas, int width) {
            if (mIndicatorGravity != Gravity.FILL && mIndicatorWidth < width) {
                mPaint.setColor(SHADOW_COLOR);
                mRect.set(0, mIndicatorY, width, mIndicatorY + mTabHeight);
                canvas.drawRect(mRect, mPaint);
            }
        }

        private void drawIndicator(Canvas canvas) {
            mRect.set(mIndicatorLeft, mIndicatorY, mIndicatorRight, mIndicatorY + mTabHeight);
            mPaint.setColor(mIndicatorColor);
            if (mIndicatorCorners > 0) {
                canvas.drawRoundRect(mRect, mIndicatorCorners, mIndicatorCorners, mPaint);
            } else {
                canvas.drawRect(mRect, mPaint);
            }
        }

        private void drawArrow(Canvas canvas, int width) {
            if (mArrowColor == Color.TRANSPARENT || mArrowSize <= 0) {
                return;
            }
            if (mArrowType == ARROW_TYPE_OUTER) {
                drawArrowOuter(canvas, width);
            } else {
                drawArrowInner(canvas, width);
            }
        }

        private void drawArrowInner(Canvas canvas, int width) {
            mArrowPath.reset();
            if (mArrowGravity == Gravity.RIGHT) {
                mArrowPath.moveTo(width - mArrowSize, mIndicatorY + mTabHeight / 2);
                mArrowPath.lineTo(width + mArrowSize, mIndicatorY + mTabHeight / 2 - mArrowSize * 2);
                mArrowPath.lineTo(width + mArrowSize, mIndicatorY + mTabHeight / 2 + mArrowSize * 2);
                mArrowPath.close();
            } else {
                mArrowPath.moveTo(mArrowSize, mIndicatorY + mTabHeight / 2);
                mArrowPath.lineTo(0, mIndicatorY + mTabHeight / 2 - mArrowSize);
                mArrowPath.lineTo(0, mIndicatorY + mTabHeight / 2 + mArrowSize);
                mArrowPath.close();
            }
            mPaint.setColor(mArrowColor);
            canvas.drawPath(mArrowPath, mPaint);
        }

        private void drawArrowOuter(Canvas canvas, int width) {
            mArrowPath.reset();
            if (mArrowGravity == Gravity.RIGHT) {
                mArrowPath.moveTo(width, mIndicatorY + mTabHeight / 2);
                mArrowPath.lineTo(width - mArrowSize, mIndicatorY + mTabHeight / 2 - mArrowSize);
                mArrowPath.lineTo(width - mArrowSize, mIndicatorY + mTabHeight / 2 + mArrowSize);
                mArrowPath.close();
            } else {
                mArrowPath.moveTo(0, mIndicatorY + mTabHeight / 2);
                mArrowPath.lineTo(mArrowSize, mIndicatorY + mTabHeight / 2 - mArrowSize);
                mArrowPath.lineTo(mArrowSize, mIndicatorY + mTabHeight / 2 + mArrowSize);
                mArrowPath.close();
            }
            mPaint.setColor(mArrowColor);
            canvas.drawPath(mArrowPath, mPaint);
        }
    }

    private int dpToPx(int dps) {
        return Math.round(getResources().getDisplayMetrics().density * dps);
    }

    public final static class Tab {
        private Drawable drawable;
        private int drawableRes = INVALID_NUM;
        private int drawableGravity = Gravity.LEFT;
        private String text;
        private String contentDescription = "";
        private int drawableWidth = 0;
        private int drawableHeight = 0;
        private View mCustomView;
        private Object mTag;

        private Tab() {
            // Private constructor
        }

        public String getContentDescription() {
            return contentDescription;
        }

        public Tab setContentDescription(String contentDescription) {
            this.contentDescription = contentDescription;
            return this;
        }

        public Drawable getDrawable() {
            return drawable;
        }

        public Tab setDrawable(Drawable drawable) {
            this.drawable = drawable;
            return this;
        }

        public int getDrawableRes() {
            return drawableRes;
        }

        public Tab setDrawable(int drawableRes) {
            this.drawableRes = drawableRes;
            return this;
        }

        public int getDrawableGravity() {
            return drawableGravity;
        }

        public Tab setDrawableGravity(int drawableGravity) {
            this.drawableGravity = drawableGravity;
            return this;
        }

        public Tab setDrawableSize(int drawableWidth, int drawableHeight) {
            this.drawableWidth = drawableWidth;
            this.drawableHeight = drawableHeight;
            return this;
        }

        public int getDrawableHeight() {
            return drawableHeight;
        }

        public int getDrawableWidth() {
            return drawableWidth;
        }

        public String getText() {
            return text;
        }

        public Tab setText(String text) {
            this.text = text;
            return this;
        }

        public View getCustomView() {
            return mCustomView;
        }

        public Tab setCustomView(View mCustomView) {
            this.mCustomView = mCustomView;
            return this;
        }

        public Object getTag() {
            return mTag;
        }

        public Tab setTag(Object mTag) {
            this.mTag = mTag;
            return this;
        }
    }

    private class TabView extends FrameLayout {

        private TextView mTabItem;
        private Tab mTab;

        public TabView(Context context, Tab tab) {
            super(context);
            mTab = tab;
            setContentDescription(mTab.getContentDescription());
            if (tab.getCustomView() != null) {
                addView(tab.getCustomView());
                return;
            }
            mTabItem = new TextView(mContext);
            mTabItem.setText(mTab.getText());
            mTabItem.setTextColor(mTabTextColor);
            mTabItem.setGravity(Gravity.CENTER_VERTICAL);
            setPaddings();
            setDrawable();
            addView(mTabItem, createLayoutParams());
        }

        private void setPaddings() {
            if (mTabPadding > 0) {
                mTabItem.setPadding(mTabPadding, mTabPadding, mTabPadding, mTabPadding);
            } else if (mTabPaddingLeft > 0 || mTabPaddingTop > 0 || mTabPaddingRight > 0 || mTabPaddingBottom > 0) {
                mTabItem.setPadding(mTabPaddingLeft, mTabPaddingTop, mTabPaddingRight, mTabPaddingBottom);
            }
        }

        private LayoutParams createLayoutParams() {
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = mTabGravity | Gravity.CENTER_VERTICAL;
            return params;
        }

        private void setDrawable() {
            Drawable d = mTab.getDrawable();
            if (d == null && mTab.getDrawableRes() != INVALID_NUM) {
                d = getResources().getDrawable(mTab.getDrawableRes());
            }
            if (d == null) {
                return;
            }
            if (mTab.getDrawableHeight() > 0 && mTab.getDrawableWidth() > 0) {
                d.setBounds(0, 0, mTab.getDrawableWidth(), mTab.getDrawableHeight());
            } else {
                float width = d.getIntrinsicWidth();
                float height = d.getIntrinsicHeight();
                float rate = width / height;
                float bottom = mTabHeight / 2;
                if (bottom > height) bottom = height;
                float right = bottom * rate;
                d.setBounds(0, 0, (int) right, (int) bottom);
            }
            switch (mTab.getDrawableGravity()) {
                case Gravity.LEFT:
                    mTabItem.setCompoundDrawables(d, null, null, null);
                    break;
                case Gravity.RIGHT:
                    mTabItem.setCompoundDrawables(null, null, d, null);
                    break;
                case Gravity.TOP:
                    mTabItem.setCompoundDrawables(null, d, null, null);
                    break;
                case Gravity.BOTTOM:
                    mTabItem.setCompoundDrawables(null, null, null, d);
                    break;
            }

            mTabItem.setCompoundDrawablePadding(mTabDrawablePadding);
        }

        @Override
        public void setSelected(boolean selected) {
            super.setSelected(selected);
            if (mTabItem != null) {
                mTabItem.setTextColor(selected ? mTabSelectedTextColor : mTabTextColor);
            }
            if (mOnTabSelectedListener != null) {
                if (selected) {
                    mOnTabSelectedListener.onTabSelected(mTab, mTabStrip.indexOfChild(this));
                } else {
                    mOnTabSelectedListener.onTabReleased(mTab, mTabStrip.indexOfChild(this));
                }
            }
        }
    }
}
