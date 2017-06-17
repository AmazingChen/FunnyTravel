package edu.sqchen.iubao.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/4.
 */

public class AttractionDetail implements Serializable{

    private static final long serialVersionUID = -7060210544600464481L;

    /**
     * error_code : 0
     * reason : 成功
     * result : [{"title":"翡翠谷","referral":"翡翠谷是黄山脚下的一道峡谷，谷中之溪名为\u201c碧玉溪\u201d，源自炼丹、始信、天女诸峰。翡翠谷中怪岩耸立，流水潺潺，气势非凡。谷中分布着一百多个形态各异、大小不同的彩池，或晶明、或翠绿，变化多端，绚丽多彩，仿佛一颗颗色彩缤纷的翡翠，故名翡翠谷。","img":"http://pic4.40017.cn/scenery/destination/2016/07/31/17/Tahj0I.jpg"},{"title":"人间瑶池仙境","referral":"翡翠谷的特色在于水，谷中有大大小小美如翡翠，丽若彩珠，流光溢彩，灵秀绝伦的潭池达一百多个，被人们赞为\u201c人间瑶池仙境\u201d。并被称赞曰\u201c未游过翡翠谷彩池者，不能称到过黄山。黄山之雄奇，见于莲花、天都，黄山的美梦必从翡翠谷彩池中浮出\u201d 。","img":"http://pic4.40017.cn/scenery/destination/2016/07/31/17/SvWfMv.jpg"},{"title":"","referral":"翡翠谷素有小九寨沟之称，当真正看到的时候，会特别惊讶水色竟然蓝绿相间相溶，与平时看到的完全不一样。这是由于彩池随着周遍景物，彩池的颜色随着太阳角度和光线明暗的变化而变化，或晶莹，或翠绿，绚丽多彩，变化多端，像不像一颗颗晶莹剔透的翡翠散落谷中？","img":"http://pic4.40017.cn/scenery/destination/2016/07/31/17/YGD3SY.jpg"},{"title":"","referral":"翡翠谷的石头都是干干净净的石头，没有泥沙，所以水流的冲刷形成一汪清潭，正是看起来是浅滩，实际上有几米深。温润的碧水，在崎岖的石头上前行，也变得激情起来。在与石头的撞击中，清澈的水变成纯白的浪花，如瀑如雾，另有一番美景。","img":"http://pic4.40017.cn/scenery/destination/2016/07/31/17/QNfjEt.jpg"},{"title":"","referral":"置身谷中，看竹林片片，听泉声潺潺，观岩石之奇瑰，水色之亮丽，令人心旷神怡。","img":"http://pic4.40017.cn/scenery/destination/2016/07/31/17/jmZ20K.jpg"},{"title":"卧虎藏龙之蛟龙打斗","referral":"翡翠谷彩池群大小不同，形状各异，清澈透明，由于倒映两岸青山，多青碧如玉，也有因池底岩石的颜色而五彩缤纷，池水深浅不一，色彩更富变幻。彩池群中著名的有：龙凤池、花镜池、绿珠池、玉环池、白鹿池、雷雨池、天池、天鹅池。《卧虎藏龙》的拍摄地就在这里，花镜池则是李慕白和玉蛟龙打斗的地方。远远望去郁郁葱葱，竹枝摇旖，一股清凉的感觉悠然而生。","img":"http://pic4.40017.cn/scenery/destination/2016/07/31/17/bAosX1.jpg"},{"title":"竹海乐悠悠","referral":"翡翠谷的上坡和下坡时截然不同的景致。上坡是一水的清泉碧潭，下坡则是一路的巍巍竹海。遮天蔽日，凉意无限。行走其中，竹子轻轻摇晃，仿佛在友善的打着招呼，竹叶与竹叶不断的相磨，似乎在亲密的嬉戏，沙沙声不绝于耳，大自然的声音，总有令人惬意的魔力。","img":"http://pic4.40017.cn/scenery/destination/2016/07/31/17/rgWDRI.jpg"},{"title":"","referral":"翡翠谷在黄山脚，虽有高山流水的意境，却没有爬山的疲惫。不管你是否爬黄山，都不要错过翡翠谷哦，慢悠悠的走在翡翠谷里，看一看不一样的黄山，看一看美丽的彩池，再看一看绿意盎然的竹林，乐悠悠的亲近大自然吧。","img":"http://pic4.40017.cn/scenery/destination/2016/07/31/17/ApJuOa.jpg"},{"title":"出游贴士","referral":"1.翡翠谷已实现全区域WIFI覆盖，拍美景发照片，再也不用担心流量了。","img":"http:"}]
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

    public static class ResultBean implements Serializable{
        /**
         * title : 翡翠谷
         * referral : 翡翠谷是黄山脚下的一道峡谷，谷中之溪名为“碧玉溪”，源自炼丹、始信、天女诸峰。翡翠谷中怪岩耸立，流水潺潺，气势非凡。谷中分布着一百多个形态各异、大小不同的彩池，或晶明、或翠绿，变化多端，绚丽多彩，仿佛一颗颗色彩缤纷的翡翠，故名翡翠谷。
         * img : http://pic4.40017.cn/scenery/destination/2016/07/31/17/Tahj0I.jpg
         */

        private String title;
        private String referral;
        private String img;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getReferral() {
            return referral;
        }

        public void setReferral(String referral) {
            this.referral = referral;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
