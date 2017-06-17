package edu.sqchen.iubao.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import edu.sqchen.iubao.R;

/**
 * Created by Administrator on 2017/4/22.
 */

public class SideBar extends View {

    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;

    private static String[] LETTERS = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O",
            "P","Q","R","S","T","U","V","W","X","Y","Z","#"};

    private Paint mPaint = new Paint();

    //选中项
    private int choosed = -1;

    private TextView mTextDialog;

    public void setTextDialog(TextView textView) {
        this.mTextDialog = textView;
    }

    public SideBar(Context context) {
        super(context);
    }

    public SideBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();
        int singleHeight = height / LETTERS.length;
        for(int i = 0;i < LETTERS.length;i++) {
            mPaint.setColor(Color.rgb(33,65,98));
            mPaint.setTypeface(Typeface.DEFAULT_BOLD);
            mPaint.setAntiAlias(true);
            mPaint.setTextSize(40);
            //选中的状态
            if(i == choosed) {
                mPaint.setColor(Color.parseColor("#3399ff"));
                mPaint.setFakeBoldText(true);
            }
            //x坐标等于中间-字符串宽的的一半
            float xPosition = width / 2 - mPaint.measureText(LETTERS[i]) /2;
            float yPosition = singleHeight * i + singleHeight;
            canvas.drawText(LETTERS[i],xPosition,yPosition,mPaint);
            mPaint.reset();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoosed = choosed;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        //得到所点击项是第几个
        final int selectedCount = (int)( y / getHeight() * LETTERS.length);

        switch(action) {
            case MotionEvent.ACTION_UP:
                setBackgroundDrawable(new ColorDrawable(0x00000000));
                choosed = -1;
                invalidate();
                if(mTextDialog != null) {
                    mTextDialog.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                setBackgroundResource(R.drawable.sidebar_background);
                if(oldChoosed != selectedCount) {
                    if( selectedCount > 0 && selectedCount < LETTERS.length) {
                        if(listener != null) {
                            listener.onTouchingLetterChanged(LETTERS[selectedCount]);
                        }
                        if(mTextDialog != null) {
                            mTextDialog.setText(LETTERS[selectedCount]);
                            mTextDialog.setVisibility(View.VISIBLE);
                        }
                        choosed = selectedCount;
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }

    public void setOnTouchingLetterListener(OnTouchingLetterChangedListener listener) {
        this.onTouchingLetterChangedListener = listener;
    }

    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(String letterStr);
    }

}
