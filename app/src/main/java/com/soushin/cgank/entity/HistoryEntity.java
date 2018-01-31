package com.soushin.cgank.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2018/1/8.
 *
 */

public class HistoryEntity extends DataSupport{
    private long createTimeMill;
    private String content;

    public long getCreateTimeMill() {
        return createTimeMill;
    }

    public void setCreateTimeMill(long createTimeMill) {
        this.createTimeMill = createTimeMill;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
