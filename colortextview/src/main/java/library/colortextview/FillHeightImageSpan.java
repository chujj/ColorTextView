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
    private Rect mDrawRect;
    private int mImageMaxHeight = -1;

    public FillHeightImageSpan(Bitmap bitmap) {
        this(bitmap, 0, 0);
    }

    public FillHeightImageSpan(Bitmap bitmap, int marginLeft, int marginRight) {
        mBitmap = bitmap;
        mRect = new Rect();
        mDrawRect = new Rect();
        setMargin(marginLeft, 0, marginRight, 0);
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        Paint.FontMetricsInt fmi = paint.getFontMetricsInt();
        int height = - fmi.ascent + fmi.bottom;
        if (mImageMaxHeight != -1) {
            height = Math.min(height, mImageMaxHeight);
        }
        int width = (int) (1f * height / mBitmap.getHeight() * mBitmap.getWidth());
        mRect.set(0, 0, width, height);
        mRect.offset(mMargin.left, 0);
        width += (mMargin.left + mMargin.right);
        return width;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        canvas.save();
        canvas.translate(x, top);
        mDrawRect.set(mRect);
        mDrawRect.top = 0;
        mDrawRect.bottom = bottom - top;
        if (mImageMaxHeight != -1 && mDrawRect.height() > mImageMaxHeight) { // align vertical center
            int offsetY = (mDrawRect.height() - mImageMaxHeight) / 2;
            mDrawRect.top = 0;
            mDrawRect.bottom = mImageMaxHeight;
            mDrawRect.offset(0, offsetY);
        }
        canvas.drawBitmap(mBitmap, null, mDrawRect, paint);
        canvas.restore();
    }

    @Override
    public Drawable getDrawable() {
        return null;
    }

    public void setMaxHeight(int imageMaxHeight) {
        mImageMaxHeight = imageMaxHeight;
    }
}
