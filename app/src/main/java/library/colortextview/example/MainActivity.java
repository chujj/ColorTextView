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
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.DynamicDrawableSpan;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import library.colortextview.FillHeightImageSpan;
import library.colortextview.LabelSpan;

public class MainActivity extends AppCompatActivity {

    public class SpanStr {
        String str;
        CharacterStyle span;

        public SpanStr(String str, CharacterStyle span) {
            this.str = str;
            this.span = span;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            }
        });


        SpanStr[] strs = new SpanStr[] {
            new SpanStr("中国", new ClickableSpan() {
                @Override
                public void onClick(View widget) {

                }
            }),
            new SpanStr("img", new FillHeightImageSpan(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher))
                .setMargin(10, 0, 15, 0)),
            new SpanStr("人888888888888888888888888888888888888888", null),
            new SpanStr("rerd", new BackgroundColorSpan(0xffff0000)),
            new SpanStr("标签", new LabelSpan(true, 0xffffffff, true, 0xffff8800).setMargin(12, 2, 12, 2).setPadding(6, 0, 6, 0)),
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
        SpannableStringBuilder ssb = new SpannableStringBuilder(getSpansString(strs));
        int start = 0;
        for (int i = 0; i < strs.length; i++) {
            SpanStr ss = strs[i];
            int length = ss.str.length();
            if (ss.span == null) {
                start += length;
                continue;
            }

            ssb.setSpan(ss.span, start, start + length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            start += length;
        }

        ((TextView)this.findViewById(R.id.textview)).setText(ssb);
    }

    private String getSpansString(SpanStr[] spans) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < spans.length; i++) {
            sb.append(spans[i].str);
        }
        return sb.toString();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
