package com.soushin.cgank.entity;

import java.util.List;

/**
 * SearchResult
 */
public class SearchResult {

    public int count;
    public boolean error;
    public List<ResultsBean> results;

    public static class ResultsBean {

        public String desc;
        public String ganhuo_id;
        public String publishedAt;
        public String readability;
        public String type;
        public String url;
        public String who;
    }
}
