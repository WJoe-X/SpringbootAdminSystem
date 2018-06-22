package com.lenovo.bootstrap.po;

import java.util.Date;

public class Picture {
    private Integer id;

    private String title;

    private String originPicUrl;

    private String fixedPicUrl;

    private Date createAt;

    private Date updateAt;

    private String uploaderName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getOriginPicUrl() {
        return originPicUrl;
    }

    public void setOriginPicUrl(String originPicUrl) {
        this.originPicUrl = originPicUrl == null ? null : originPicUrl.trim();
    }

    public String getFixedPicUrl() {
        return fixedPicUrl;
    }

    public void setFixedPicUrl(String fixedPicUrl) {
        this.fixedPicUrl = fixedPicUrl == null ? null : fixedPicUrl.trim();
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getUploaderName() {
        return uploaderName;
    }

    public void setUploaderName(String uploaderName) {
        this.uploaderName = uploaderName == null ? null : uploaderName.trim();
    }
}