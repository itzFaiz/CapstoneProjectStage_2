package com.rexontechnologies.popularmovies.utils;

import android.text.TextUtils;

public class Trailer {

    private String id;
    private String iso_639_1;
    private String iso_3166_1;
    private String key;
    private String name;
    private String site;
    private String size;
    private String type;

    public Trailer(){
        this.id = id;
        this.iso_639_1 = iso_639_1;
        this.iso_3166_1 = iso_3166_1;
        this.key = key;
        this.name = name;
        this.site = site;
        this.size = size;
        this.type = type;
    }

    public String getId() {
        return TextUtils.isEmpty(id)? "N/A" : id;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getIso_639_1() {
        return TextUtils.isEmpty(iso_639_1)? "N/A" : iso_639_1;
    }
    public void setIso_639_1(String iso_639_1){
        this.iso_639_1 = iso_639_1;
    }
    public String getIso_3166_1() {
        return TextUtils.isEmpty(iso_3166_1)? "N/A" : iso_3166_1;
    }
    public void setIso_3166_1(String iso_3166_1){
        this.iso_3166_1 = iso_3166_1;
    }
    public String getKey() {
        return TextUtils.isEmpty(key)? "N/A" : key;
    }
    public void setKey(String key){
        this.key = key;
    }
    public String getName() {
        return TextUtils.isEmpty(name)? "N/A" : name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getSite() {
        return TextUtils.isEmpty(site)? "N/A" : site;
    }
    public void setSite(String site){
        this.site = site;
    }
    public String getSize() {
        return TextUtils.isEmpty(size)? "N/A" : size;
    }
    public void setSize(String size){
        this.size = size;
    }
    public String getType() {
        return TextUtils.isEmpty(type)? "N/A" : type;
    }
    public void setType(String type){
        this.type = type;
    }
}

