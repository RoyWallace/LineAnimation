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

    private float value = 0;

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

    public void setValue(float f) {
        this.value = f;
        setTextColor(Color.argb((int) (f * 255), 255, 255, 255));
    }

    public void setMinLine(int length) {
        this.topLineSize = length;
        maxRound = getHeight() * 2 + getWidth() + length;
        postInvalidate();
    }

    public void animate(float f) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setFloatValues(value, f);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(this);
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float round = maxRound * value;
        if (round <= stokeWidth) {
            return;
        }
        if (round <= getWidth()) {
            canvas.drawRect((getWidth() - round) / 2, getHeight() - stokeWidth, (getWidth() + round) / 2, getHeight(), paint);
        } else if (round <= blr) {
            canvas.drawRect(0, getHeight() - stokeWidth, getWidth(), getHeight(), paint);
            float y = getHeight() - (round - getWidth()) / 2;
            canvas.drawRect(0, y, stokeWidth, getHeight(), paint);
            canvas.drawRect(getWidth() - stokeWidth, y, getWidth(), getHeight(), paint);
        } else {
            canvas.drawRect(0, getHeight() - stokeWidth, getWidth(), getHeight(), paint);
            canvas.drawRect(0, 0, stokeWidth, getHeight(), paint);
            canvas.drawRect(getWidth() - stokeWidth, 0, getWidth(), getHeight(), paint);

            float r = (round - blr) / 2;
            canvas.drawRect(0, 0, r, stokeWidth, paint);
            canvas.drawRect(getWidth() - r, 0, getWidth(), stokeWidth, paint);
        }
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        value = (float) animation.getAnimatedValue();
        setTextColor(Color.argb((int) (value * 255), 255, 255, 255));
        invalidate();
    }
}
