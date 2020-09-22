package com.advance.libcommon.bean.respone;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wangqi on 2019/8/15.
 */

public class NewExpressEnetity {

    private String content="";
    private int currentPage;
    private int id;
    private int important;
    @SerializedName("new")
    private boolean newX;
    private int pageSize;
    private int pageStart;
    private long pubTime;
    private String remarkTitle;
    private String remarkType;
    private String sourceId;
    private long sourceTime;
    private int status;
    private String url;
    public int maxLines = 5;
    private String pic;

    public int getMaxLines() {
        return maxLines;
    }

    public void setMaxLines(int maxLines) {
        this.maxLines = maxLines;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImportant() {
        return important;
    }

    public void setImportant(int important) {
        this.important = important;
    }

    public boolean isNewX() {
        return newX;
    }

    public void setNewX(boolean newX) {
        this.newX = newX;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageStart() {
        return pageStart;
    }

    public void setPageStart(int pageStart) {
        this.pageStart = pageStart;
    }

    public long getPubTime() {
        return pubTime;
    }

    public void setPubTime(long pubTime) {
        this.pubTime = pubTime;
    }

    public String getRemarkTitle() {
        return remarkTitle;
    }

    public void setRemarkTitle(String remarkTitle) {
        this.remarkTitle = remarkTitle;
    }

    public String getRemarkType() {
        return remarkType;
    }

    public void setRemarkType(String remarkType) {
        this.remarkType = remarkType;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public long getSourceTime() {
        return sourceTime;
    }

    public void setSourceTime(long sourceTime) {
        this.sourceTime = sourceTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
