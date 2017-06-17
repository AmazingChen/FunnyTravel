package edu.sqchen.iubao.ui.view;

import java.util.List;

import edu.sqchen.iubao.model.entity.Attraction;

/**
 * Created by Administrator on 2017/4/24.
 */

public interface IAttractionView extends IBaseView{

    /**
     * 获取景点数据
     * @return
     */
    List<Attraction> getAttractionList();
}
