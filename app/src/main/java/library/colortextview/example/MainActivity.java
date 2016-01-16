package library.colortextview.example;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.DynamicDrawableSpan;
import android.view.View;
import android.widget.TextView;

import library.colortextview.BoxModelSpanBuilder;
import library.colortextview.FillHeightImageSpan;
import library.colortextview.LabelSpan;
import library.colortextview.SpanStr;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SpanStr[] strs = new SpanStr[] {
            new SpanStr("标签", new LabelSpan(true, 0xffffffff, true, 0xffff8800).setMargin(12, 2, 12, 2).setPadding(6, 0, 6, 0)),
//            new SpanStr("clickspan 中文", new ClickableSpan() {
//                @Override
//                public void onClick(View widget) {
//
//                }
//            }),
            new SpanStr("img", new FillHeightImageSpan(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher))
                .setMargin(10, 0, 15, 0)),
            new SpanStr("人888888888888888888888888888888888888888", null),
            new SpanStr("rerd", new BackgroundColorSpan(0xffff0000)),
//            new SpanStr("clickspan 中文", new ClickableSpan() {
//                @Override
//                public void onClick(View widget) {
//
//                }
//            }),
            new SpanStr("中文", null),
            new SpanStr("hello, worldjJ", new LabelSpan(true, 0xffff0000, true, 0xff000000).setMargin(10, 0, 15, 0).setPadding(15, 0, 10, 0)),
            new SpanStr("read", new DynamicDrawableSpan() {

                Paint mPaint = new Paint();
                int width;

                @Override
                public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
                    width = (int) paint.measureText(text, start, end);
                    return width;
                }

                @Override
                public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
                    Paint.FontMetricsInt fmi = paint.getFontMetricsInt();
                    mPaint.setColor(0xff00ff00);


                    canvas.translate(x, y);
                    canvas.drawText(text.subSequence(start, end).toString(), 0, 0, paint);

//                    canvas.drawLine();
                    canvas.drawLine(0, 0, width, 0, mPaint); // baseline
                    canvas.restore();

                }

                @Override
                public void updateDrawState(TextPaint ds) {
//                    super.updateDrawState(ds);
                    ds.bgColor = 0xffff0000;
                }

                @Override
                public Drawable getDrawable() {
                    return null;
                }
            }),
            new SpanStr("img", new FillHeightImageSpan(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher))),

            new SpanStr("888", null),
        };

        // init spans
        SpannableStringBuilder ssb = BoxModelSpanBuilder.build(strs);

            ((TextView) this.findViewById(R.id.textview)).setText(ssb);
    }

}
