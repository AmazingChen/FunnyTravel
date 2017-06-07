package edu.fjnu.funnytravel.util;

import java.util.Comparator;

import edu.fjnu.funnytravel.entity.SortCity;


/**
 * 用于将ListView里的内容根据ABCD...XYZ的顺序进行排序
 * Created by Administrator on 2017/4/22.
 */

public class SpellComparator implements Comparator<SortCity> {

    public int compare(SortCity sortCity1,SortCity sortCity2) {
        if(sortCity1.getSortLetter().equals("#")
                || sortCity1.getSortLetter().equals("@")) {
            return -1;
        } else if(sortCity2.getSortLetter().equals("#")
                || sortCity2.getSortLetter().equals("@")){
            return -1;
        } else {
            return sortCity1.getSortLetter().compareTo(sortCity2.getSortLetter());
        }
    }
}
