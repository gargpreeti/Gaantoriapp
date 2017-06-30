package com.zoptal.gaantori.JsonClasses;


public  class SrchAlbum {

         private String id;
         private String image;
         private String title;
         private String artists;
         private String singer;




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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public String getArtists() {
        return artists;
    }

    public void setArtists(String artists) {
        this.artists= artists;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer=singer;
    }



}