package com.comiyun.weixin.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.ArrayList;
import java.util.List;

public class ReplyNewsMsg extends BaseReplyMsg {

    public ReplyNewsMsg(String fromUserName, String toUserName) {
        this.setFromUserName(fromUserName);
        this.setToUserName(toUserName);
        this.setMsgType("news");
    }

    @XStreamAlias("ArticleCount")
    private int articleCount;

    @XStreamAlias("Articles")
    private List<NewsItem> articles = new ArrayList<NewsItem>();

    public void addNewsItem(NewsItem item) {
        if (articles.size() < 10) {
            articles.add(item);
            setArticleCount(articles.size());
        }
    }

    public int getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(int articleCount) {
        this.articleCount = articleCount;
    }

    public List<NewsItem> getArticles() {
        return articles;
    }

    public void setArticles(List<NewsItem> articles) {
        this.articles = articles;
    }
}
