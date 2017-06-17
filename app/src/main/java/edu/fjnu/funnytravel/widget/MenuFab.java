package edu.sqchen.iubao.widget;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;

import com.gordonwong.materialsheetfab.AnimatedFab;

/**
 * Created by Administrator on 2017/6/9.
 */

public class MenuFab extends FloatingActionButton implements AnimatedFab{

    /**
     * 以下三个构造方法必须全部重写，因为没有在java代码中直接实例化
     * 而是在布局文件中定义，会调用第二个构造方法
     * @param context
     */
    public MenuFab(Context context) {
        super(context);
    }

    public MenuFab(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MenuFab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Shows the FAB.
     */
    @Override
    public void show() {
        show(0, 0);
    }

    /**
     * Shows the FAB and sets the FAB's translation.
     *
     * @param translationX translation X value
     * @param translationY translation Y value
     */
    @Override
    public void show(float translationX, float translationY) {
        // NOTE: Using the parameters is only needed if you want
        // to support moving the FAB around the screen.
        // NOTE: This immediately hides the FAB. An animation can
        // be used instead - see the sample app.
        setVisibility(View.VISIBLE);
    }

    /**
     * Hides the FAB.
     */
    @Override
    public void hide() {
        // NOTE: This immediately hides the FAB. An animation can
        // be used instead - see the sample app.
        setVisibility(View.INVISIBLE);
    }
}
