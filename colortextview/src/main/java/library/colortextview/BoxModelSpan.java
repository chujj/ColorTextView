package library.colortextview;

import android.graphics.Rect;
import android.text.style.DynamicDrawableSpan;

/**
 * Created by zhujj on 16-1-16.
 */
public abstract class BoxModelSpan<T extends BoxModelSpan> extends DynamicDrawableSpan {

    protected Rect mMargin, mPadding;

    public BoxModelSpan() {
        mMargin = new Rect(0, 0, 0, 0);
        mPadding = new Rect(0, 0, 0, 0);
    }

    public T setMargin(int left, int top, int right, int bottom) {
        mMargin.set(left, top, right, bottom);
        return (T) this;
    }

    public T setPadding(int l, int t, int r, int b) {
        mPadding.set(l, t, r, b);
        return (T) this;
    }
}
