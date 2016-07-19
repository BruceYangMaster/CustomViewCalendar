package com.deepblue.customviewcalendar.CalendarView;

/**
 * Created by Administrator on 2016/7/19 0019.
 */

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.deepblue.customviewcalendar.R;

/**
 * 边角带标记的TextView
 * 文字大小在dimen文件里抽取了
 * 因为主要在代码里动态设置，所以暂时不抽取自定义属性吧。
 *
 * @author cjj
 * @time 2015-12-24
 */
public class BadgeTextView extends TextView {

    /**
     * 动画持续时间.
     */
    private static final int ALPHA_ANIM_DURATION = 3000;

    /**
     * 指定标记的方向为左上.
     */
    public static final int DIRECTION_TOP_LEFT = 1;

    /**
     * 指定标记的方向为右上.
     */
    public static final int DIRECTION_TOP_RIGHT = 2;

    /**
     * 指定标记的方向为左下.
     */
    public static final int DIRECTION_BOTTOM_LEFT = 3;

    /**
     * 指定标记的方向为右下.
     */
    public static final int DIRECTION_BOTTOM_RIGHT = 4;

    /**
     * The Direction.
     */
    private int mDirection = DIRECTION_TOP_RIGHT;

    /**
     * The Badge color.
     */
    private int mBadgeColor;

    /**
     * The Badge circle color.
     */
    private int mBadgeCircleColor;

    /**
     * The Badge size.
     */
    private float mBadgeSize;

    /**
     * The Badge string.
     */
    private String mBadgeString;

    /**
     * The Show background circle.
     */
    private boolean mShowBackgroundCircle;
    /**
     * The Show background circle2.
     */
    private boolean mShowBackgroundCircle2;

    /**
     * Instantiates a new badge text view.
     *
     * @param context      the context
     * @param attrs        the attrs
     * @param defStyleAttr the def style attr
     */
    public BadgeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    /**
     * Instantiates a new badge text view.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public BadgeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Instantiates a new badge text view.
     *
     * @param context the context
     */
    public BadgeTextView(Context context) {
        this(context, null);
    }

    /**
     * Instantiates a new badge text view.
     *
     * @param context   the context
     * @param direction the direction
     */
    public BadgeTextView(Context context, int direction) {
        this(context);

        this.mDirection = direction;
    }

    /**
     * 初始化.
     *
     * @return void
     * @author cjj
     * @time 2015-12-24 下午4:19:24
     */
    private void init() {
        mBadgeColor = Color.RED;
        // 紫罗兰
        mBadgeCircleColor = 0x66EE82EE;
        //右上角显示的数字的字体大小
        mBadgeSize = 20;
    }

    /**
     * Sets the Badge string.
     * 这里默认设置显示了，不显示再调用setChecked
     *
     * @param badgeStr the new Badge string
     */
    public void setBadgeString(String badgeStr) {
        this.mBadgeString = badgeStr;
        this.mShowBackgroundCircle = true;
//		this.invalidate();
    }

    public void setChecked(boolean checked) {
        this.mShowBackgroundCircle2 = checked;
        this.invalidate();
    }

    /**
     * 设置标记的位置：左上、右上、左下、右下
     * 默认为右上
     *
     * @param direction the new badge direction
     */
    public void setBadgeDirection(int direction) {
        this.mDirection = direction;
        this.invalidate();
    }

    /**
     * Draw.
     *
     * @param canvas the canvas
     */
    /* （非 Javadoc）
     * @see android.view.View#draw(android.graphics.Canvas)
	 */
    @Override
    public void draw(Canvas canvas) {
        // 需求是中心点换背景圆，就直接写了啊
        TextPaint paint = getPaint();
        int oriColor = paint.getColor();
        paint.setColor(mBadgeCircleColor);
        // 拿字体高度做半径
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float radius = (float) (-fontMetrics.ascent * 0.7);
        int circleX = getWidth() / 2;
        int cirlceY = getHeight() / 2;
        //
        if (mShowBackgroundCircle) {
            //画圆圈
            canvas.drawCircle(circleX, cirlceY, radius, paint);
            paint.setColor(oriColor);
        }
        if (mShowBackgroundCircle2) {
            canvas.drawCircle(circleX, cirlceY, radius, paint);
            paint.setColor(oriColor);
        }
        super.draw(canvas);
    }

    /**
     * On draw.
     *
     * @param canvas the canvas
     */
    @SuppressLint("DrawAllocation")
    /* （非 Javadoc）
     * @see android.widget.TextView#onDraw(android.graphics.Canvas)
	 */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBadgeString != null) {
            TextPaint paint = getPaint();
            int oriColor = paint.getColor();
            float oriTextSize = paint.getTextSize();

            paint.setColor(mBadgeColor);
            paint.setTextSize(mBadgeSize);

            float badgeWidth = paint.measureText(mBadgeString);
            // 参考去掉了一个leading的高度，我只显示数字那个没卵用
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            float badgeHeight = -fontMetrics.ascent;
//			float badgeHeight = paint.descent() - paint.ascent();
            /**
             * 右上角数字的图标
             */
            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            switch (mDirection) {
                case DIRECTION_TOP_LEFT:

                    canvas.drawText(mBadgeString, 0, badgeHeight, paint);
//					canvas.drawBitmap(bm,0, badgeHeight, paint);
                    break;

                case DIRECTION_TOP_RIGHT:

                    // 感觉有时候换显示不全啊就差一点，再让一个像素
                    canvas.drawText(mBadgeString, getWidth() - badgeWidth - 1, badgeHeight, paint);
//					canvas.drawBitmap(bm, getWidth() - badgeWidth - 1, badgeHeight, paint);
                    break;

                case DIRECTION_BOTTOM_LEFT:

                    // drawText是从baseLine开始画的
                    canvas.drawText(mBadgeString, 0, getHeight() - 1, paint);
//					canvas.drawBitmap(bm, 0, getHeight() - 1, paint);
                    break;

                case DIRECTION_BOTTOM_RIGHT:

                    canvas.drawText(mBadgeString, getWidth() - badgeWidth - 1, getHeight() - 1, paint);
//					canvas.drawBitmap(bm, getWidth() - badgeWidth - 1, getHeight() - 1, paint);
                    break;

                default:

                    canvas.drawText(mBadgeString, getWidth() - badgeWidth - 1, badgeHeight, paint);
//					canvas.drawBitmap(bm, getWidth() - badgeWidth - 1, badgeHeight, paint);
                    break;
            }

            // 还原Paint
            paint.setColor(oriColor);
            paint.setTextSize(oriTextSize);
        }

    }

    public int getBadgeColor() {
        return mBadgeColor;
    }

    public void setBadgeColor(int badgeColor) {
        mBadgeColor = badgeColor;
    }

    public int getBadgeCircleColor() {
        return mBadgeCircleColor;
    }

    /**
     * Sets the Badge circle color.
     *
     * @param badgeCircleColor the new Badge circle color
     */
    public void setBadgeCircleColor(int badgeCircleColor) {
        mBadgeCircleColor = badgeCircleColor;
    }

    /**
     * 开始动画
     */
    public void startDayAnim() {
        final int badgeColor = this.getBadgeColor();
        final int badgeCircleColor = this.getBadgeCircleColor();
        ValueAnimator valueAnimator1 = ValueAnimator.ofInt(0, 1);
        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedFraction = animation.getAnimatedFraction();
                int colorEvaluate = colorEvaluate(animatedFraction, badgeColor);
                int colorEvaluate2 = colorEvaluate(animatedFraction, badgeCircleColor);
                BadgeTextView.this.setBadgeColor(colorEvaluate);
                BadgeTextView.this.setBadgeCircleColor(colorEvaluate2);
                BadgeTextView.this.invalidate();
            }
        });

        valueAnimator1.setDuration(ALPHA_ANIM_DURATION);
        valueAnimator1.start();
    }

    /**
     * 删减版的Evaluate只改变颜色的透明度.
     *
     * @param fraction the fraction
     * @param endColor the end color
     * @return the int
     */
    private int colorEvaluate(float fraction, int endColor) {
        int startA = 0;
        int endA = (endColor >> 24) & 0xff;
        int endR = (endColor >> 16) & 0xff;
        int endG = (endColor >> 8) & 0xff;
        int endB = endColor & 0xff;
        return (int) (((startA + (int) (fraction * (endA - startA))) << 24) | (int) (endR << 16) | (int) (endG << 8) | endB);
    }

}
