package yt.com.easylife.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import yt.com.easylife.R;

/**
 * author  : LSM
 * time    : 2018/04/27
 * function:
 * e-mail  : lsmloveu@126.com
 * github  : https://github.com/lsmloveu
 * csdn    : http://blog.csdn.net/csdn_android_lsm
 * 简书    : http://www.jianshu.com/u/644036b17b6f
 */

public class VerticalTextView extends View {

    private String text;
    private int size;
    private int color;
    private  String font;
    private  Context mContext;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public VerticalTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.VerticalTextView);
        text = typedArray.getNonResourceString(R.styleable.VerticalTextView_text);
        size = typedArray.getDimensionPixelSize(R.styleable.VerticalTextView_size,30);
        color = typedArray.getColor(R.styleable.VerticalTextView_color,getResources().getColor(R.color.white));
        font = typedArray.getNonResourceString(R.styleable.VerticalTextView_font);
        typedArray.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        //获取字数
        int textSize = text.length();
        //获取字数应该占据的高度
        int maxTextHeight = size*textSize+(textSize-1)*10;
        //行数
        int textline0 = maxTextHeight/getHeight();
        //余数
        int textline1 = maxTextHeight%getHeight();
        if(textline1!=0) {
            textline0+=1;
        }
        //一行最多绘制的字数
        int textnum=getHeight()/(size+10);
        //绘制字体起始坐标
        float baseLineX = getWidth()/2.0f + ((textline0-2)/2.0f)*size+((textline0-1)/2.0f)*10;  //
        float baseLineY = (size+10)*2+size;
        paint.setColor(color);
        paint.setTextSize(size);
        paint.setFakeBoldText(false);
        paint.setTypeface(Typeface.createFromAsset(mContext.getAssets(),font));
        for (int i=0; i<text.length(); i++) {
            canvas.drawText(String.valueOf(text.toCharArray()[i]), baseLineX, baseLineY, paint);
            baseLineY+=(size+10);
            if(baseLineY>getHeight()&&textline0>=0) {
                textline0--;
                baseLineX -=(size+10);
                baseLineY=size;
            }
        }
    }
}
