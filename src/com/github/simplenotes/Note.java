/*
 * This POJO is based on the JSON document in the API2.
 * {
 key         :  (string, note identifier, created by server),
 deleted     :  (bool, whether or not note is in trash),
 modifyDate  :  (last modified date, in seconds since epoch),
 createDate  :  (note created date, in seconds since epoch),
 syncNum     :  (integer, number set by server, track note changes),
 version     :  (integer, number set by server, track note content changes),
 minVersion  :  (integer, number set by server, minimum version available for note),
 shareKey    :  (string, shared note identifier),
 publishKey  :  (string, published note identifier),
 systemTags  :  [ (Array of strings, some set by server) ],
 tags        :  [ (Array of strings) ],
 content     :  (string, data content)
 }
*/
package com.github.simplenotes;

import java.util.List;
import java.util.ArrayList;

import com.google.gson.Gson;

public class Note {
    private Long id;
    private String key;
    private boolean deleted;
    private int modifyDate;
    private int createDate;
    private int syncNum;
    private int minVersion;
    private int version;
    private String shareKey;
    private String publishKey;
    private List<String> systemTags;
    private List<String> tags;
    private String content;
    
    public Note() {
        this.deleted = false;
        this.tags = null;
        this.systemTags = null;

    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getKey() {
        return key;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    public boolean isDeleted() {
        return deleted;
    }
    public void setModifyDate(int modifyDate) {
        this.modifyDate = modifyDate;
    }
    public int getModifyDate() {
        return this.modifyDate;
    }
    public void setCreateDate(int createDate) {
        this.createDate = createDate;
    }
    public int getCreateDate() {
        return createDate;
    }
    public void setSyncNum(int syncNum) {
        this.syncNum = syncNum;
    }
    public int getSyncNum() {
        return syncNum;
    }
    public void setMinVersion(int minVersion) {
        this.minVersion = minVersion;
    }
    public int getMinVersion() {
        return minVersion;
    }
    public void setVersion(int version) {
        this.version = version;
    }
    public int getVersion() {
        return version;
    }
    public void setShareKey(String shareKey) {
        this.shareKey = shareKey;
    }
    public String getShareKey() {
        return shareKey;
    }
    public void setPublishKey(String publishKey) {
        this.publishKey = publishKey;
    }
    public String getPublishKey() {
        return publishKey;
    }
    public void setSystemTags(List<String> systemTags) {
        this.systemTags = systemTags;
    }
    public List<String> getSystemTags() {
        if (this.systemTags == null) {
            this.systemTags = new ArrayList<String>();
        }

        return this.systemTags;
    }
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    public List<String> getTags() {
        if (this.tags == null) {
            this.tags = new ArrayList<String>();
        }

        return this.tags;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }

    public static Note fromJSON(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Note.class);
    }

    public String toJSON()
    {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
