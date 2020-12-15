package com.kislichenko.news.entity;

public enum AdBlockTypes {
    Simple ("Simple"),
    Big ("Big"),
    Header ("Header"),
    Footer ("Footer");

    private String code;
    AdBlockTypes(String code){
        this.code = code;
    }
    public String getCode(){ return code;}
}
