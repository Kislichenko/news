package com.kislichenko.news.entity;

public enum ReportageStatus {
    Created("Created"),
    Inwork ("Inwork"),
    Fixing("Fixing"),
    Closed("Closed");

    private String code;
    ReportageStatus (String code){
        this.code = code;
    }
    public String getCode(){ return code;}
}
