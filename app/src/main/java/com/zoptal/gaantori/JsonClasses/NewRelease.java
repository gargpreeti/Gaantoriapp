package com.zoptal.gaantori.JsonClasses;


public  class NewRelease {

         private String id;
         private String name;
         private String image;
         private String artists;
         private String fcount;
         private String fstat;
        private String artistids;


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



    public String getArtists() {
        return artists;
    }

    public void setArtists(String artists) {
        this.artists= artists;
    }


    public String getFcount() {
        return fcount;
    }

    public void setFcount(String fcount) {
        this.fcount= fcount;
    }

    public String getFstat() {
        return fstat;
    }

    public void setFstat(String fstat) {
        this.fstat= fstat;
    }
    public String getArtistids() {
        return artistids;
    }

    public void setArtistids(String artistids) {
        this.artistids=artistids;
    }
}