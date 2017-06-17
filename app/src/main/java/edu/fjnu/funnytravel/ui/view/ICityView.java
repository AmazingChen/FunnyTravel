package edu.sqchen.iubao.ui.view;

import java.util.List;

import edu.sqchen.iubao.model.entity.City;

/**
 * Created by Administrator on 2017/4/24.
 */

public interface ICityView extends IBaseView{


    /**
     * 获取城市数据
     * @return
     */
    List<City> getCityList();
}
