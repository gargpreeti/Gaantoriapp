package com.zoptal.gaantori.JsonClasses;


public  class TopAlbum {

         private String id;
         private String url;
         private String title;
         private String artists;
         private String albumsongsplayed;




    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id =id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url= url;
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


    public String getAlbumsongsplayed() {
        return albumsongsplayed;
    }

    public void setAlbumsongsplayed(String albumsongsplayed) {
        this.albumsongsplayed=albumsongsplayed;
    }


}