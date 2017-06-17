package edu.sqchen.iubao.model.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/5/2.
 */

public class Ticket {


    /**
     * error_code : 0
     * reason : 成功
     * result : [{"name":"翡翠谷成人票","price_mk":"90","price":"60"},{"name":"翡翠谷+九龙瀑成人票","price_mk":"175","price":"120"},{"name":"手机导游(不含门票，短信发送)","price_mk":"40","price":"20"},{"name":"翡翠谷儿童票","price_mk":"45","price":"45"},{"name":"翡翠谷优待票","price_mk":"45","price":"45"},{"name":"翡翠谷家庭票[2大1小]","price_mk":"225","price":"165"},{"name":"翡翠谷家庭票[1大1小]","price_mk":"135","price":"105"},{"name":"【黄山2日游】自选黄山醉温泉国际度假酒店，黄山醉温泉/西递/安徽宏村等","price_mk":"1","price":"479"},{"name":"【黄山2日游】自选黄山温泉度假酒店，翡翠谷/黄山温泉/黄山风景区等","price_mk":"1","price":"749"},{"name":"【黄山3日游】自选黄山狮林大酒店/黄山北海宾馆等，翡翠谷/黄山温泉/安徽宏村等","price_mk":"462","price":"2020"}]
     */

    private int error_code;
    private String reason;
    private List<ResultBean> result;



    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * name : 翡翠谷成人票
         * price_mk : 90
         * price : 60
         */

        private String name;
        private String price_mk;
        private String price;

        public ResultBean(String name, String price_mk, String price) {
            this.name = name;
            this.price_mk = price_mk;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice_mk() {
            return price_mk;
        }

        public void setPrice_mk(String price_mk) {
            this.price_mk = price_mk;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
