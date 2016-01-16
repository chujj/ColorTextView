package library.colortextview;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

/**
 * Created by zhujj on 16-1-16.
 */
public class BoxModelSpanBuilder {

    public static SpannableStringBuilder build(BoxModelSpan.BoxModelDimensionProvider provider, SpanStr ... strs) {

        SpannableStringBuilder ssb = new SpannableStringBuilder(getSpansString(strs));
        int start = 0;
        for (int i = 0; i < strs.length; i++) {
            SpanStr ss = strs[i];
            int length = ss.str.length();
            if (ss.span == null) {
                start += length;
                continue;
            }

            if (provider != null && ss.span instanceof BoxModelSpan) {
                if (ss.span instanceof LabelSpan) {
                    ((LabelSpan)ss.span).setMargin(provider.getLableMargin());
                    ((LabelSpan)ss.span).setPadding(provider.getLablePadding());
                } else if (ss.span instanceof FillHeightImageSpan) {
                    ((FillHeightImageSpan)ss.span).setMargin(provider.getImageMargin());
                    ((FillHeightImageSpan)ss.span).setPadding(provider.getImagePadding());
                }
            }

            ssb.setSpan(ss.span, start, start + length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            start += length;
        }
        return ssb;
    }


    private static String getSpansString(SpanStr[] spans) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < spans.length; i++) {
            sb.append(spans[i].str);
        }
        return sb.toString();
    }
}
