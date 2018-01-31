package com.soushin.cgank.entity;

/**
 * Created by Administrator on 2017/8/16.
 * 菜单
 */

public class MenuEntity {
    private String name;
    private int imgID;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;
    public MenuEntity(String name, int imgID){
        this.name = name;
        this.imgID = imgID;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgID() {
        return imgID;
    }

    public void setImgID(int imgID) {
        this.imgID = imgID;
    }

    @Override
    public String toString() {
        return "MenuEntity{" +
                "name='" + name + '\'' +
                ", imgID=" + imgID +
                '}';
    }
}
