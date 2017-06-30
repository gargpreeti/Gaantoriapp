package com.zoptal.gaantori.JsonClasses;


public  class Playlist {

         private String id;
         private String albumname;
         private String albumimage;


    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id =id;
    }

    public String getAlbumname() {
        return albumname;
    }

    public void setAlbumname(String albumname) {
        this.albumname= albumname;
    }


    public String getAlbumimage() {
        return albumimage;
    }

    public void setAlbumimage(String albumimage) {
        this.albumimage= albumimage;
    }


}