package com.lxyer.base.common.utils;

public enum FileType {

    IMAGE("图片",1),
    DOCUMENT("文档",2),
    VIDEO("视频",3),
    MUSIC("音乐",4),
    OTHER("其他",5);

    private String name;
    private int index;

    FileType(String name, int index){
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public static String getName(int index){
        for(FileType fileType : FileType.values()){
            if(fileType.getIndex() == index){
                return fileType.name;
            }
        }
        return null;
    }
}
