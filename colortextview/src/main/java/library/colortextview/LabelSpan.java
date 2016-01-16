package library.colortextview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;

/**
 * Created by zhujj on 16-1-16.
 */
public class LabelSpan extends BoxModelSpan {
    private boolean isFg;
    private int mFgColor;
    private boolean isBg;
    private int mBgColor;
    private Rect mBgRect;
    private Paint mBgPaint;

    public LabelSpan(boolean fg, int fgColor, boolean bg, int bgColor) {
        isFg = fg;
        mFgColor = fgColor;
        isBg = bg;
        mBgColor = bgColor;
        mBgRect = new Rect();
        mBgPaint = new Paint();
        mBgPaint.setColor(bgColor);
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        Paint.FontMetricsInt fmi = paint.getFontMetricsInt();
        int height = - fmi.top + fmi.bottom;

        float originSize = paint.getTextSize();
        if (mMargin.top != 0 || mMargin.bottom != 0) {
            paint.setTextSize(originSize - mMargin.top - mMargin.bottom);
        }
        int width = (int) (paint.measureText(text, start, end)) + mPadding.left + mPadding.right;
        if (mMargin.top != 0 || mMargin.bottom != 0) {
            paint.setTextSize(originSize);
        }

        mBgRect.set(0, 0 + mMargin.top, width, height - mMargin.bottom);
        mBgRect.offset(mMargin.left, 0);
        width += (mMargin.left + mMargin.right);
        return width;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        canvas.save();
        canvas.translate(x, y);

        int originFg = 0;

        float originSize = paint.getTextSize();
        float originAscent = paint.getFontMetrics().top;
        if (mMargin.top != 0 || mMargin.bottom != 0) {
            paint.setTextSize(originSize - mMargin.top - mMargin.bottom);
        }
        if (isFg) {
            originFg = paint.getColor();
            paint.setColor(mFgColor);
        }

        if (isBg) {
            Rect cache = new Rect(mBgRect);
            cache.bottom = paint.getFontMetricsInt().bottom;
            cache.top = paint.getFontMetricsInt().top;
            canvas.drawRect(cache, mBgPaint);
        }

        canvas.drawText(text.subSequence(start, end).toString(), mBgRect.left + mPadding.left, mMargin.top + originAscent - paint.getFontMetrics().top, paint);
        if (isFg) {
            paint.setColor(originFg);
        }
        if (mMargin.top != 0 || mMargin.bottom != 0) {
            paint.setTextSize(originSize);
        }

        canvas.restore();
    }

    @Override
    public Drawable getDrawable() {
        return null;
    }
}
