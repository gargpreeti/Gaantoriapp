package com.zoptal.gaantori.JsonClasses;


public  class BrowseArtist {

         private String id;
         private String name;
         private String desc;
         private String fol;
         private String image;
    private String fcount;



    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id =id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name= name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc= desc;
    }

    public String getFol() {
        return fol;
    }

    public void setFol(String fol) {
        this.fol= fol;
    }


    public String getFcount() {
        return fcount;
    }

    public void setFcount(String fcount) {
        this.fcount= fcount;
    }



}