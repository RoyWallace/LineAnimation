package etong.lineanimation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by hwt on 2017/7/5.
 */
public class SubTitleView extends AppCompatTextView implements ValueAnimator.AnimatorUpdateListener {

    private Paint paint;

    private float percent = 0;

    private float maxRound;

    private float blr;

    private int stokeWidth = 2;

    private int topLineSize = 0;

    public SubTitleView(Context context) {
        super(context);
    }

    public SubTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
    }

    public SubTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (topLineSize != 0) {
            maxRound = getHeight() * 2 + getWidth() + topLineSize;
        } else {
            maxRound = getHeight() * 2 + getWidth() * 1.5f;
        }
        blr = getWidth() + getHeight() * 2;
    }

    public void setPercent(float f) {
        this.percent = f;
        setTextColor(Color.argb((int) (f * 255), 255, 255, 255));
    }

    public void setMinLine(int length) {
        this.topLineSize = length;
        maxRound = getHeight() * 2 + getWidth() + length;
        postInvalidate();
    }

    public void animate(float f) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setFloatValues(percent, f);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(this);
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float currentRound = maxRound * percent;
        if (currentRound <= stokeWidth) {
            return;
        }
        if (currentRound <= getWidth()) {
            canvas.drawRect((getWidth() - currentRound) / 2, getHeight() - stokeWidth, (getWidth() + currentRound) / 2, getHeight(), paint);
        } else if (currentRound <= blr) {
            canvas.drawRect(0, getHeight() - stokeWidth, getWidth(), getHeight(), paint);
            float y = getHeight() - (currentRound - getWidth()) / 2;
            canvas.drawRect(0, y, stokeWidth, getHeight(), paint);
            canvas.drawRect(getWidth() - stokeWidth, y, getWidth(), getHeight(), paint);
        } else {
            //bottom
            canvas.drawRect(0, getHeight() - stokeWidth, getWidth(), getHeight(), paint);
            //left
            canvas.drawRect(0, 0, stokeWidth, getHeight(), paint);
            //right
            canvas.drawRect(getWidth() - stokeWidth, 0, getWidth(), getHeight(), paint);

            float r = (currentRound - blr) / 2;
            //topLeft
            canvas.drawRect(0, 0, r, stokeWidth, paint);
            //topRight
            canvas.drawRect(getWidth() - r, 0, getWidth(), stokeWidth, paint);
        }
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        percent = (float) animation.getAnimatedValue();
        setTextColor(Color.argb((int) (percent * 255), 255, 255, 255));
        invalidate();
    }

}
