package edu.sqchen.iubao.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.sqchen.iubao.R;

public class WeatherActivity extends AppCompatActivity {

    @BindView(R.id.tool_bar_weather)
    Toolbar mToolBarWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);
        initToolbar();
    }

    private void initToolbar() {
        mToolBarWeather.setTitle("福州");
        mToolBarWeather.setTitleTextColor(Color.WHITE);
        mToolBarWeather.setNavigationIcon(R.drawable.ic_back);
        mToolBarWeather.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
