package library.colortextview.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import library.colortextview.BoxModelSpan;
import library.colortextview.R;

/**
 * Created by zhujj on 16-1-16.
 */
public class ColorTextView extends TextView {

    private JSONTextFormot mJSONTextFormot;
    private Rect mBoxLableMargin = new Rect();
    private Rect mBoxLablePadding = new Rect();
    private Rect mBoxLabelExtra = new Rect();
    private Rect mBoxImageMargin = new Rect();
    private Rect mBoxImagePadding = new Rect();
    private int mBoxImageMaxHeight;


    public ColorTextView(Context context) {
        super(context);
    }

    public ColorTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ColorTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ColorTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a = this.getContext().getTheme().obtainStyledAttributes(
            attrs,
            R.styleable.ColorTextView,
            0, 0);

        try {
            mBoxLableMargin.left =  a.getDimensionPixelSize(R.styleable.ColorTextView_label_marginLeft,   0);
            mBoxLableMargin.top =   a.getDimensionPixelSize(R.styleable.ColorTextView_label_marginTop,    0);
            mBoxLableMargin.right = a.getDimensionPixelSize(R.styleable.ColorTextView_label_marginRight,  0);
            mBoxLableMargin.bottom =a.getDimensionPixelSize(R.styleable.ColorTextView_label_marginBottom, 0);
            mBoxLablePadding.left = a.getDimensionPixelSize(R.styleable.ColorTextView_label_paddingLeft,  0);
            mBoxLablePadding.right =a.getDimensionPixelSize(R.styleable.ColorTextView_label_paddingRight, 0);
            mBoxLabelExtra.left =   a.getDimensionPixelSize(R.styleable.ColorTextView_label_extraLeft, 0);
            mBoxLabelExtra.top=     a.getDimensionPixelSize(R.styleable.ColorTextView_label_extraTop, 0);

            mBoxImageMargin.left   =a.getDimensionPixelSize(R.styleable.ColorTextView_image_marginLeft, 0);
            mBoxImageMargin.right  =a.getDimensionPixelSize(R.styleable.ColorTextView_image_marginRight, 0);
            mBoxImageMaxHeight =    a.getDimensionPixelSize(R.styleable.ColorTextView_image_maxHeight, -1);
        } finally {
            a.recycle();
        }

    }

    private BoxModelSpan.BoxModelDimensionProvider provider = new BoxModelSpan.BoxModelDimensionProvider() {
        @Override
        public Rect getLableMargin() {
            return mBoxLableMargin;
        }

        @Override
        public Rect getLablePadding() {
            return mBoxLablePadding;
        }

        @Override
        public Rect getLabelExtra() {
            return mBoxLabelExtra;
        }

        @Override
        public Rect getImageMargin() {
            return mBoxImageMargin;
        }

        @Override
        public Rect getImagePadding() {
            return mBoxImagePadding;
        }

        @Override
        public int getImageMaxHeight() {
            return mBoxImageMaxHeight;
        }

    };

    public void setColorText(JSONTextFormot.BitmapQueryFactory factory, String str, String appendText) {
        if (mJSONTextFormot != null) {
            mJSONTextFormot.stopAsync();
        }

        mJSONTextFormot = new JSONTextFormot(factory, this, str, provider, appendText);
    }

    @Override
    public void invalidate(Rect dirty) {
        super.invalidate(dirty);
    }

    @Override
    public void invalidate() {
        super.invalidate();
    }

    @Override
    public void invalidate(int l, int t, int r, int b) {
        super.invalidate(l, t, r, b);
    }

    @Override
    public void postInvalidate() {
        super.postInvalidate();
    }
}

