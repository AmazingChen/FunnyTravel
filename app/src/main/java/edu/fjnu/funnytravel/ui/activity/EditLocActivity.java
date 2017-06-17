package edu.sqchen.iubao.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import edu.sqchen.iubao.R;
import edu.sqchen.iubao.adapter.SortAdapter;
import edu.sqchen.iubao.app.MyApplication;
import edu.sqchen.iubao.model.entity.City;
import edu.sqchen.iubao.model.entity.SortCity;
import edu.sqchen.iubao.presenter.CityPresenter;
import edu.sqchen.iubao.ui.common.BaseActivity;
import edu.sqchen.iubao.ui.view.ICityView;
import edu.sqchen.iubao.ui.util.CharacterParseUtil;
import edu.sqchen.iubao.ui.util.SpellComparator;
import edu.sqchen.iubao.widget.ClearEditText;
import edu.sqchen.iubao.widget.SideBar;

public class EditLocActivity extends BaseActivity<ICityView,CityPresenter> implements ICityView {

    @BindView(R.id.tool_bar_edit_loc)
    Toolbar mToolBarEditLoc;

    @BindView(R.id.filter_edit)
    ClearEditText mFilterEdit;

    @BindView(R.id.country_lvcountry)
    ListView mCountryLvcountry;

    @BindView(R.id.dialog)
    TextView mDialog;

    @BindView(R.id.sidrbar)
    SideBar mSidrbar;

    private SortAdapter mSortAdapter;

    private CharacterParseUtil mCharacterParseUtil;

    private List<SortCity> mSortCityList = new ArrayList<SortCity>();

    private SpellComparator mSpellComparator;

    private SweetAlertDialog mLoadingDialog;

    private MyApplication locApp;

    private List<City> mCityList = new ArrayList<City>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_loc);
        ButterKnife.bind(this);
        locApp = (MyApplication) getApplication();
        initToolbar();
        initViews();
    }

    private void initToolbar() {
        mToolBarEditLoc.setTitle(locApp.getCuurentCity().getCityName());
        mToolBarEditLoc.setTitleTextColor(Color.WHITE);
        mToolBarEditLoc.setNavigationIcon(R.drawable.ic_back);
        mToolBarEditLoc.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initViews() {

        initLoadingDialog();

        //实例化汉字转拼音类
        mCharacterParseUtil = CharacterParseUtil.getInstance();
        mSpellComparator = new SpellComparator();
        mSidrbar.setTextDialog(mDialog);
        mSidrbar.setOnTouchingLetterListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String letterStr) {
                int position = mSortAdapter.getPositionForSection(letterStr.charAt(0));
                if (position != -1) {
                    mCountryLvcountry.setSelection(position);
                }
            }
        });
        mCountryLvcountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //修改LocationApplication中的全局变量currentCity
                locApp.setCuurentCity(((SortCity) mSortAdapter.getItem(position)).getCity());
                Log.d("cityId","城市：" + ((SortCity) mSortAdapter.getItem(position)).getCity().getCityName() +
                "ID：" + ((SortCity) mSortAdapter.getItem(position)).getCity().getCityId());
                Intent intent = new Intent(EditLocActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        //加载城市数据并初始化适配器，绑定给ListView
        getCities();

        //编辑框的监听
        mFilterEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 初始化进度对话框
     */
    private void initLoadingDialog() {
        mLoadingDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        mLoadingDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.blue));
        mLoadingDialog.setTitleText("Loading");
        mLoadingDialog.setCancelable(true);
    }

    /**
     * 得到经过排序后的数组
     * @param cities    所有城市数据
     * @return
     */
    private List<SortCity> getSortCityData(List<City> cities) {
        List<SortCity> mSortList = new ArrayList<SortCity>();
        for (int i = 0; i < cities.size(); i++) {
            SortCity sortCity = new SortCity();
            //将得到的城市名传入，用于排序和显示
            sortCity.setCityName(cities.get(i).getCityName());
            //将得到的整个城市实体对象传入，用于在点击item时修改当前城市这个全局变量
            sortCity.setCity(cities.get(i));
            //汉字转换成拼音
            String pinyin = mCharacterParseUtil.getSelling(cities.get(i).getCityName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            //正则表达式，判断首字母是否为英文字母
            if (sortString.matches("[A-Z]")) {
                sortCity.setSortLetter(sortString.toUpperCase());
            } else {
                sortCity.setSortLetter("#");
            }

            mSortList.add(sortCity);
        }

        return mSortList;
    }


    /**
     * 根据输入框中输入的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<SortCity> filterDataList = new ArrayList<SortCity>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDataList = mSortCityList;
        } else {
            filterDataList.clear();
            for (SortCity sortCity : mSortCityList) {
                String cityName = sortCity.getCityName();
                if (cityName.toUpperCase().indexOf(filterStr.toString().toUpperCase()) != -1
                        || mCharacterParseUtil.getSelling(cityName).toUpperCase()
                        .startsWith(filterStr.toString().toUpperCase())) {
                    filterDataList.add(sortCity);
                }
            }
        }

        //根据A-Z的顺序进行排序
        Collections.sort(filterDataList, mSpellComparator);
        mSortAdapter.updateListView(filterDataList);
    }

    @Override
    protected CityPresenter createPresenter() {
        return new CityPresenter(this);
    }

    @Override
    public void showLoading() {
        mLoadingDialog.show();
    }

    @Override
    public void hideLoading() {
        mLoadingDialog.hide();
    }

    @Override
    public List<City> getCityList() {
        mPresenter = new CityPresenter(this);
        mCityList = mPresenter.getCities(getApplicationContext());

        return mCityList;
    }

    private void getCities() {
        //将从Model层获得的城市数据传入getSortCityData()方法中
        mSortCityList = getSortCityData(getCityList());
        //根据A-Z进行排序
        Collections.sort(mSortCityList, mSpellComparator);
        mSortAdapter = new SortAdapter(this, mSortCityList);
        mCountryLvcountry.setAdapter(mSortAdapter);
    }


}
