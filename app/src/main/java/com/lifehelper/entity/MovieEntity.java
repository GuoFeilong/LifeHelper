package com.lifehelper.entity;

import java.util.List;

/**
 * Created by jsion on 16/3/29.
 */
public class MovieEntity {
    private String reason;
    private ResultBean result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        private String title;
        private String tag;
        private String act;
        private String year;
        private Object rating;
        private String area;
        private String dir;
        private String desc;
        private String cover;
        private String vdo_status;


        private PlaylinksBean playlinks;

        private List<VideoRecBean> video_rec;


        private List<ActSBean> act_s;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getAct() {
            return act;
        }

        public void setAct(String act) {
            this.act = act;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public Object getRating() {
            return rating;
        }

        public void setRating(Object rating) {
            this.rating = rating;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getDir() {
            return dir;
        }

        public void setDir(String dir) {
            this.dir = dir;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getVdo_status() {
            return vdo_status;
        }

        public void setVdo_status(String vdo_status) {
            this.vdo_status = vdo_status;
        }

        public PlaylinksBean getPlaylinks() {
            return playlinks;
        }

        public void setPlaylinks(PlaylinksBean playlinks) {
            this.playlinks = playlinks;
        }

        public List<VideoRecBean> getVideo_rec() {
            return video_rec;
        }

        public void setVideo_rec(List<VideoRecBean> video_rec) {
            this.video_rec = video_rec;
        }

        public List<ActSBean> getAct_s() {
            return act_s;
        }

        public void setAct_s(List<ActSBean> act_s) {
            this.act_s = act_s;
        }

        public static class PlaylinksBean {
            private String qiyi;

            public String getQiyi() {
                return qiyi;
            }

            public void setQiyi(String qiyi) {
                this.qiyi = qiyi;
            }

        }

        public static class VideoRecBean {
            private String cover;
            private String detail_url;
            private String title;

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getDetail_url() {
                return detail_url;
            }

            public void setDetail_url(String detail_url) {
                this.detail_url = detail_url;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

        }

        public static class ActSBean {
            private String name;
            private String url;
            private String image;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

        }
    }
}

