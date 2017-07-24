package com.ylz.newapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 两端对齐的text view
 */
public class AlignTextView extends android.support.v7.widget.AppCompatTextView {
    private static final String TAG = "AlignTextView";
    /**
     * 文字
     */
    private String text;
    /**
     * 后缀
     */
    private String suffixStr;

    public AlignTextView(Context context) {
        this(context, null);
    }

    public AlignTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AlignTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        text = getText().toString();
        setText(null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(getTextSize());
        paint.setColor(getTextColors().getDefaultColor());
        Rect targetRect = new Rect(0, 0, getWidth(), getHeight());

        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int baseline = (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        //Textview控件总宽度
        int maxTextViewWidth = getWidth();
        if (TextUtils.isEmpty(text) || text.length() < 1) {
            super.onDraw(canvas);
            return;
        }
        //后缀字符
        suffixStr = text.substring(text.length() - 1, text.length());
        // 情况一:有后缀
        if (suffixStr.equals(":") || suffixStr.equals("：")) {
            //后缀宽度
            int suffixWidth = getSuffixWidth(paint, suffixStr);// suffixMode后缀的：
            int textWidth = getSuffixWidth(paint, text.substring(0, text.length() - 1));//内容的长度
            // 中间字符-1及减冒号
            int spec = (int) ((maxTextViewWidth - textWidth - suffixWidth) * 1.0 / (text.length() - 2));
            //spec是每个字符串之间的空格
            int x = 0;
            for (int i = 0; i < text.length() - 1; i++) {
                canvas.drawText(String.valueOf(text.charAt(i)), x, baseline, paint);//
                x += spec + getSuffixWidth(paint, String.valueOf(text.charAt(i)));
            }
            canvas.drawText(suffixStr, maxTextViewWidth - suffixWidth, baseline, paint);
        } else {// 情况二:无后缀
            char[] chars = text.toCharArray();
            int spec = (int) ((maxTextViewWidth - getSuffixWidth(paint, text)) * 1.0 / (chars.length - 1));
            int x = 0;
            for (int i = 0; i < chars.length; i++) {
                canvas.drawText(String.valueOf(chars[i]), x, baseline, paint);
                x += spec + getSuffixWidth(paint, String.valueOf(text.charAt(i)));
            }
        }
        super.onDraw(canvas);
    }

    /**
     * 计算后缀的宽度
     */
    private int getSuffixWidth(Paint paint, String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        if (str.equals(":")) {
            str = "a";
        } else if (str.equals("：")) {
            str = "啊";
        }
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);//计算出一个全角字符的宽度
        return rect.width();
    }

    public void setAlingText(String text) {
        this.text = text;
        invalidate();
    }
}