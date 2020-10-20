package com.kislichenko.news.entity;

public enum AdBlockTypes {
    Simple ("SIMPLE"),
    Big ("BIG"),
    Header ("HEADER"),
    Footer ("FOOTER");

    private String code;
    AdBlockTypes(String code){
        this.code = code;
    }
    public String getCode(){ return code;}
}
