package library.colortextview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * Created by zhujj on 16-1-16.
 */
public class FillHeightImageSpan extends BoxModelSpan<FillHeightImageSpan> {
    private Bitmap mBitmap;
    private Rect mRect;

    public FillHeightImageSpan(Bitmap bitmap) {
        this(bitmap, 0, 0);
    }

    public FillHeightImageSpan(Bitmap bitmap, int marginLeft, int marginRight) {
        mBitmap = bitmap;
        mRect = new Rect();
        setMargin(marginLeft, 0, marginRight, 0);
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        Paint.FontMetricsInt fmi = paint.getFontMetricsInt();
        int height = - fmi.top + fmi.bottom;
        int width = (int) (1f * height / mBitmap.getHeight() * mBitmap.getWidth());
        mRect.set(0, 0, width, height);
        mRect.offset(mMargin.left, 0);
        width += (mMargin.left + mMargin.right);
        return width;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        canvas.save();
        canvas.translate(x, y + paint.getFontMetricsInt().top);
        canvas.drawBitmap(mBitmap, null, mRect, paint);
        canvas.restore();
    }

    @Override
    public Drawable getDrawable() {
        return null;
    }
}
