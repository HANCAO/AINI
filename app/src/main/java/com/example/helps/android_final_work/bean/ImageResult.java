package com.example.helps.android_final_work.bean;

public class ImageResult {
    private String date;
    private String url;
    private String copyright;
    private String title;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 7.定义一个Map对象来存储返回结果
     {
     "images": [
     {
     "startdate": "20181221",
     "fullstartdate": "201812211600",
     "enddate": "20181222",
     "url": "/az/hprichbg/rb/ColdMoonRising_ZH-CN6131399146_1920x1080.jpg",
     "urlbase": "/az/hprichbg/rb/ColdMoonRising_ZH-CN6131399146",
     "copyright": "黄石国家公园里正在升起的月亮  (© Tom Murphy/Getty Images)",
     "copyrightlink": "http://www.bing.com/search?q=%E6%AD%A3%E5%9C%A8%E5%8D%87%E8%B5%B7%E7%9A%84%E6%9C%88%E4%BA%AE+&form=hpcapt&mkt=zh-cn",
     "title": "",
     "quiz": "/search?q=Bing+homepage+quiz&filters=WQOskey:%22HPQuiz_20181221_ColdMoonRising%22&FORM=HPQUIZ",
     "wp": false,
     "hsh": "43b5340ea94cb318335dc721fa92c1ac",
     "drk": 1,
     "top": 1,
     "bot": 1,
     "hs": []
     }
     ],
     "tooltips": {
     "loading": "正在加载...",
     "previous": "上一个图像",
     "next": "下一个图像",
     "walle": "此图片不能下载用作壁纸。",
     "walls": "下载今日美图。仅限用作桌面壁纸。"
     }
     }*/
}
