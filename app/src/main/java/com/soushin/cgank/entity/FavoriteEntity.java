package com.soushin.cgank.entity;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Favorite
 */

public class FavoriteEntity extends DataSupport implements Serializable {
    private String title;
    private String type;
    private String author;
    private String data;
    private String url;
    private String gankID;
    private long createtime;
    private int favorite_position;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGankID() {
        return gankID;
    }

    public void setGankID(String gankID) {
        this.gankID = gankID;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }


    public int getFavorite_position() {
        return favorite_position;
    }

    public void setFavorite_position(int favorite_position) {
        this.favorite_position = favorite_position;
    }
}
