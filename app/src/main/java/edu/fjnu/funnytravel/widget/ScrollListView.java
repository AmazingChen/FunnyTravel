package edu.sqchen.iubao.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Administrator on 2017/4/24.
 */

public class ScrollListView extends ListView {


    public ScrollListView(Context context) {
        super(context);
    }

    public ScrollListView(Context context, AttributeSet attr) {
        super(context,attr);
    }

    public ScrollListView(Context context,AttributeSet attr,int defStyle) {
        super(context,attr,defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec,expandSpec);
    }

}
