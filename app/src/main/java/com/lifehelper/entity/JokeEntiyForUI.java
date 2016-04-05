package com.lifehelper.entity;

/**
 * Created by jsion on 16/4/5.
 */
public class JokeEntiyForUI {
    private String content;
    private String hashId;
    private int unixtime;
    private String updatetime;
    private int jokeType;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId;
    }

    public int getUnixtime() {
        return unixtime;
    }

    public void setUnixtime(int unixtime) {
        this.unixtime = unixtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public int getJokeType() {
        return jokeType;
    }

    public void setJokeType(int jokeType) {
        this.jokeType = jokeType;
    }
}
