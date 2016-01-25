package library.colortextview.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import library.colortextview.BoxModelSpan;
import library.colortextview.BoxModelSpanBuilder;
import library.colortextview.FillHeightImageSpan;
import library.colortextview.LabelSpan;
import library.colortextview.R;
import library.colortextview.SpanStr;


/**
 * Created by zhujj on 16-1-16.
 */
public class JSONTextFormot {

    private BoxModelSpan.BoxModelDimensionProvider mDimensionProvider;
    private Holder mHolder;
    public BitmapQueryFactory mBitmapQueryFactory;

    public void stopAsync() {
        mHolder.markStop();
    }

    public static interface BitmapQueryFactory {
        public BitmapQuery create();
    }

    public static abstract class BitmapQuery {
        protected String mUrl;
        private Holder mHolder;
        private BitmapQuery setHolder(Holder holder) {
            mHolder = holder;
            return this;
        }
        private BitmapQuery setUrl (String url) {
            mUrl = url;
            return this;
        }

        public abstract void start(Object obj, int width, int height);


        public void onBitmapGet(Bitmap bitmap) {
            mHolder.setAsyncBitmap(mUrl, bitmap);
        }

    }

    private Bitmap mDefaultBitmap;

    private List<Font> mFontTree;

    private class Holder implements  Runnable {
        TextView tv;
        Handler mHandler;
        boolean finished;
        boolean stop;
        HashMap<String, Bitmap> map;

        public Holder(String text, TextView aTv) {
            finished = false;
            stop = false;
            tv = aTv;
            map = new HashMap<>();
            mHandler = new Handler(Looper.getMainLooper());
        }

        public void setFinish() {
            finished = true;
        }

        public void setAsyncBitmap(String siu, Bitmap bitmap) {
            if (bitmap == null) return;

            if (stop) return;

            map.put(siu, bitmap);
            if (!finished) {
                mHandler.postDelayed(this, 500);
            } else {
                refresh();
            }
        }

        public void refresh() {
            refreshTextView(this, tv, false);
        }

        @Override
        public void run() {
            if (stop) return;

            if (!finished) {
                mHandler.postDelayed(this, 500);
            } else {
                refresh();
            }
        }

        public void setBitmap(String siu, Bitmap mDefaultBitmap) {
            map.put(siu, mDefaultBitmap);
        }

        public Bitmap getBitmap(String url) {
            return map.get(url);
        }

        public void markStop() {
            stop = true;
        }
    }

    public JSONTextFormot(BitmapQueryFactory factory, TextView viewById) {
        this(factory, viewById, null);
    }

    public JSONTextFormot(BitmapQueryFactory factory, final TextView view, String json) {
        this(factory, view, json, null, 0, null);
    }

    protected final String mJsonData;
    protected final String mAppendTextData;
    public JSONTextFormot(BitmapQueryFactory factory, final TextView view, String json, BoxModelSpan.BoxModelDimensionProvider dimensionProvider, int placeHolderRes,String appendText) {
        mJsonData = json;
        mAppendTextData = appendText;

        if (TextUtils.isEmpty(json)) {
            view.setText(appendText);
            return;
        }

        mBitmapQueryFactory = factory;
        mDimensionProvider = dimensionProvider;
        if (placeHolderRes != 0) {
            mDefaultBitmap = BitmapFactory.decodeResource(view.getContext().getResources(), placeHolderRes);
        } else {
            mDefaultBitmap = BitmapFactory.decodeResource(view.getContext().getResources(), R.drawable.ic_launcher);
        }

        String retval = null;
        try {
            mFontTree = new Gson().fromJson(json, new TypeToken<List<Font>>(){}.getType());
            if (!TextUtils.isEmpty(appendText)) {
                Font appendFont = new Font();
                appendFont.type = Font.TEXT;
                appendFont.body = appendText;
                mFontTree.add(appendFont);
            }

        } catch (Exception e) {
            retval = e.getMessage();
            e.printStackTrace();
        }

        if (mFontTree == null) {
            view.setText(retval);
            return;
        }


        mHolder = new Holder(null, view);
        refreshTextView(mHolder, view, true);
        mHolder.setFinish();
    }

    private void refreshTextView(final Holder holder, TextView view, boolean fireBitmapReq) {
        ArrayList<SpanStr> spans = new ArrayList<>();

        // init span
        for(Font font : mFontTree) {
            if (font.type.equals(Font.TEXT)) {
                String colorStr = font.color;
                String bgStr = font.bg_color;

                if (!TextUtils.isEmpty(bgStr) && bgStr.startsWith("#")) {
                    bgStr = bgStr.substring(1);
                }

                if (!TextUtils.isEmpty(colorStr) && colorStr.startsWith("#")) {
                    colorStr = colorStr.substring(1);
                }

                if (!TextUtils.isEmpty(colorStr) || !TextUtils.isEmpty(bgStr)) {
                    LabelSpan unit = new LabelSpan(
                        !TextUtils.isEmpty(colorStr), !TextUtils.isEmpty(colorStr) ? (Integer.parseInt(colorStr, 16) + 0xff000000) : 0xff000000,
                        !TextUtils.isEmpty(bgStr) , !TextUtils.isEmpty(bgStr)  ? (Integer.parseInt(bgStr, 16) + 0xff000000) : 0xffffffff);

                    spans.add(new SpanStr(font.body, unit));
                } else {
                    spans.add(new SpanStr(font.body, null));
                }
            } else if (font.type.equals(Font.IMG)){
                Bitmap bitmap = (fireBitmapReq ? mDefaultBitmap : ((holder.getBitmap(font.url) != null) ? holder.getBitmap(font.url) : mDefaultBitmap));

                spans.add(new SpanStr("img", new FillHeightImageSpan(bitmap)));

                final String url = font.url;
                if (fireBitmapReq && mBitmapQueryFactory != null) {
                    mBitmapQueryFactory.create().setUrl(url).setHolder(holder).start(font, font.width, font.height);
                }
            } else {

            }
        }

        SpanStr[] sss = new SpanStr[spans.size()];
        spans.toArray(sss);

        // set it
        // http://stackoverflow.com/a/26131208/5366967
        view.setText(new BoxModelSpanBuilder().build(mDimensionProvider, sss), TextView.BufferType.SPANNABLE);
    }

    public static class Font {
        public final static String TEXT = "text";
        public final static String IMG = "image";
        public String type;

        // text
        public String body;
        public String color;
        public String bg_color;
        // image
        public String url;
        public int width;
        public int height;
    }
}
