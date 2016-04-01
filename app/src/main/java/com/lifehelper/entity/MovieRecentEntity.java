package com.lifehelper.entity;

import java.util.List;

/**
 * Created by jsion on 16/3/29.
 */
public class MovieRecentEntity {
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
        private String url;
        private String m_url;
        private String date;
        private String morelink;
        private MovieDataType data;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getM_url() {
            return m_url;
        }

        public void setM_url(String m_url) {
            this.m_url = m_url;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getMorelink() {
            return morelink;
        }

        public void setMorelink(String morelink) {
            this.morelink = morelink;
        }

        public MovieDataType getData() {
            return data;
        }

        public void setData(MovieDataType data) {
            this.data = data;
        }
    }

    public static class MovieDataType {
        private String link;
        private String name;
        private List<MovieDesc> data;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<MovieDesc> getData() {
            return data;
        }

        public void setData(List<MovieDesc> data) {
            this.data = data;
        }
    }

    public static class MovieDesc {
        private String grade;
        private String gradeNum;
        private String iconaddress;
        private String iconlinkUrl;
        private String m_iconlinkUrl;
        private String subHead;
        private String tvTitle;

        private MovieType type;
        private MovieStory story;
        private MoviePlayDate playDate;
        private MovieStar star;
        private MovieMore more;
        private MovieDirector director;

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getGradeNum() {
            return gradeNum;
        }

        public void setGradeNum(String gradeNum) {
            this.gradeNum = gradeNum;
        }

        public String getIconaddress() {
            return iconaddress;
        }

        public void setIconaddress(String iconaddress) {
            this.iconaddress = iconaddress;
        }

        public String getIconlinkUrl() {
            return iconlinkUrl;
        }

        public void setIconlinkUrl(String iconlinkUrl) {
            this.iconlinkUrl = iconlinkUrl;
        }

        public String getM_iconlinkUrl() {
            return m_iconlinkUrl;
        }

        public void setM_iconlinkUrl(String m_iconlinkUrl) {
            this.m_iconlinkUrl = m_iconlinkUrl;
        }

        public String getSubHead() {
            return subHead;
        }

        public void setSubHead(String subHead) {
            this.subHead = subHead;
        }

        public String getTvTitle() {
            return tvTitle;
        }

        public void setTvTitle(String tvTitle) {
            this.tvTitle = tvTitle;
        }

        public MovieType getType() {
            return type;
        }

        public void setType(MovieType type) {
            this.type = type;
        }

        public MovieStory getStory() {
            return story;
        }

        public void setStory(MovieStory story) {
            this.story = story;
        }

        public MoviePlayDate getPlayDate() {
            return playDate;
        }

        public void setPlayDate(MoviePlayDate playDate) {
            this.playDate = playDate;
        }

        public MovieStar getStar() {
            return star;
        }

        public void setStar(MovieStar star) {
            this.star = star;
        }

        public MovieMore getMore() {
            return more;
        }

        public void setMore(MovieMore more) {
            this.more = more;
        }

        public MovieDirector getDirector() {
            return director;
        }

        public void setDirector(MovieDirector director) {
            this.director = director;
        }
    }


    public static class MovieType {
        private String showname;
        private List<MovieTypeData> data;

        public String getShowname() {
            return showname;
        }

        public void setShowname(String showname) {
            this.showname = showname;
        }

        public List<MovieTypeData> getData() {
            return data;
        }

        public void setData(List<MovieTypeData> data) {
            this.data = data;
        }
    }

    public static class MovieStory {
        private String showname;
        private Story data;

        public String getShowname() {
            return showname;
        }

        public void setShowname(String showname) {
            this.showname = showname;
        }

        public Story getData() {
            return data;
        }

        public void setData(Story data) {
            this.data = data;
        }
    }

    public static class MoviePlayDate {
        private String showname;
        private String data;
        private String data2;

        public String getShowname() {
            return showname;
        }

        public void setShowname(String showname) {
            this.showname = showname;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getData2() {
            return data2;
        }

        public void setData2(String data2) {
            this.data2 = data2;
        }
    }

    public static class MovieStar {
        private String showname;
        private Star data;

        public String getShowname() {
            return showname;
        }

        public void setShowname(String showname) {
            this.showname = showname;
        }

        public Star getData() {
            return data;
        }

        public void setData(Star data) {
            this.data = data;
        }
    }


    public static class MovieMore {
        private String showname;
        private List<More> data;

        public String getShowname() {
            return showname;
        }

        public void setShowname(String showname) {
            this.showname = showname;
        }

        public List<More> getData() {
            return data;
        }

        public void setData(List<More> data) {
            this.data = data;
        }
    }

    public static class MovieDirector {
        private String showname;
        private Director data;

        public String getShowname() {
            return showname;
        }

        public void setShowname(String showname) {
            this.showname = showname;
        }

        public Director getData() {
            return data;
        }

        public void setData(Director data) {
            this.data = data;
        }
    }

    public static class MovieTypeData {
        private String name;
        private String link;

        public MovieTypeData(String name, String link) {
            this.name = name;
            this.link = link;
        }
    }

    public static class Director {
        private String name;
        private String link;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }

    public static class Story {
        private String storyBrief;
        private String storyMoreLink;

        public String getStoryBrief() {
            return storyBrief;
        }

        public void setStoryBrief(String storyBrief) {
            this.storyBrief = storyBrief;
        }

        public String getStoryMoreLink() {
            return storyMoreLink;
        }

        public void setStoryMoreLink(String storyMoreLink) {
            this.storyMoreLink = storyMoreLink;
        }
    }

    public static class Star {
        private String link;
        private String name;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class More {
        private String link;
        private String name;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
