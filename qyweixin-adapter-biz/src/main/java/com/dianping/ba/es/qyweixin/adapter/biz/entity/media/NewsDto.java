package com.dianping.ba.es.qyweixin.adapter.biz.entity.media;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangyuchen on 7/22/14.
 */
public class NewsDto extends MediaDto{
    public static class Article implements Serializable {
        private String title;
        private String description;
        private String url;
        private String picurl;

        public Article(String title, String description, String url, String picurl) {
            this.title = title;
            this.description = description;
            this.url = url;
            this.picurl = picurl;
        }

        public Article() {

        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPicurl() {
            return picurl;
        }

        public void setPicurl(String picurl) {
            this.picurl = picurl;
        }
    }
    private List<Article> articles;

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public void addArticle(Article article){
        if(articles==null){
            articles = new ArrayList<Article>();
        }
        articles.add(article);
    }

    @Override
    protected void injectMap(Map params){
        if(params==null){
            params = new HashMap();
        }
        params.put("msgtype", "news");
        Map content = new HashMap();
        content.put("articles", this.getArticles());
        params.put("news", content);
    }

    @Override
    public String toString() {
        return "{msgtype=news}";
    }
}