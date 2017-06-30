package com.zoptal.gaantori.JsonClasses;


public  class SrchArtist {

         private String id;
         private String image;
         private String name;
         private String fcount;
         private String fstatus;


    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id =id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image=image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }



    public String getFcount() {
        return fcount;
    }

    public void setFcount(String fcount) {
        this.fcount=fcount;
    }

    public String getFstatus() {
        return fstatus;
    }

    public void setFstatus(String fstatus) {
        this.fstatus=fstatus;
    }


}