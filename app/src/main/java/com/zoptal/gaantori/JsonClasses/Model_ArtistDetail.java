package com.zoptal.gaantori.JsonClasses;

import java.util.ArrayList;

public class Model_ArtistDetail {

	private String artistid;
	private String artistame;
	private String artistimages;
	private String fcount;
	private String fstat;


	private ArrayList<Album> al_album = new ArrayList<Album>();
	private ArrayList<TopAlbum> al_topalbum = new ArrayList<TopAlbum>();


	public String getArtistid() {

		return artistid;
	}

	public void setArtistid(String artistid) {

		this.artistid=artistid;
	}



	public String getArtistame() {

		return artistame;
	}

	public void setArtistame(String artistame) {

		this.artistame=artistame;
	}


	public String getArtistimages() {

		return artistimages;
	}

	public void setArtistimages(String artistimages) {

		this.artistimages=artistimages;
	}

	public String getFcount() {

		return fcount;
	}

	public void setFcount(String fcount) {

		this.fcount=fcount;
	}
	public String getFstat() {

		return fstat;
	}

	public void setFstat(String fstat) {

		this.fstat=fstat;
	}
	public ArrayList<Album> getAl_album() {
		return al_album;
	}

	public void setAl_album(ArrayList<Album> al_album) {
		this.al_album= al_album;
	}

	public ArrayList<TopAlbum> getAl_topalbum() {
		return al_topalbum;
	}

	public void setAl_topalbum(ArrayList<TopAlbum> al_topalbum) {
		this.al_topalbum= al_topalbum;
	}
}





