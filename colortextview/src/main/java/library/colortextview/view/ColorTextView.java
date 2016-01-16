package library.colortextview.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by zhujj on 16-1-16.
 */
public class ColorTextView extends TextView {

    private JSONTextFormot mJSONTextFormot;

    public ColorTextView(Context context) {
        super(context);
    }

    public ColorTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ColorTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void setColorText(JSONTextFormot.BitmapQueryFactory factory, String str, String appendText) {
        if (mJSONTextFormot != null) {
            mJSONTextFormot.stopAsync();
        }
        mJSONTextFormot = new JSONTextFormot(factory, this, str, appendText);
    }
}
