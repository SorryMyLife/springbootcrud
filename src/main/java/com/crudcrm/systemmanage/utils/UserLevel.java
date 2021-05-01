package com.crudcrm.systemmanage.utils;

public enum  UserLevel {

    admin(1,"一级管理"),root(0,"root"),admin1(2,"二级管理"),admin2(3,"三级管理"),user(4,"普通用户"),tmpuser(5,"临时用户(游客)");

    private Integer Level;
    private String LevelName;
    UserLevel(Integer Level,String LevelName){
        this.Level = Level;
        this.LevelName = LevelName;
    }

    public Integer getLevel() {
        return Level;
    }

    public void setLevel(Integer level) {
        Level = level;
    }

    public String getLevelName() {
        return LevelName;
    }

    public void setLevelName(String levelName) {
        LevelName = levelName;
    }
}
