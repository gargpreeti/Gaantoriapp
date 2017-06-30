package com.zoptal.gaantori.JsonClasses;

import java.util.ArrayList;

public class Model_AlbumDetail {

	private String albumid;
	private String albumname;
	private String albumartist;
	private String albumartistid;


	private ArrayList<TopSongs> al_songs = new ArrayList<TopSongs>();


	public String getAlbumid() {

		return albumid;
	}

	public void setAlbumid(String albumid) {

		this.albumid=albumid;
	}



	public String getAlbumname() {

		return albumname;
	}

	public void setAlbumname(String albumname) {

		this.albumname=albumname;
	}


	public String getAlbumartist() {

		return albumartist;
	}

	public void setAlbumartist(String albumartist) {

		this.albumartist=albumartist;
	}


	public String getAlbumartistid() {

		return albumartistid;
	}

	public void setAlbumartistid(String albumartistid) {

		this.albumartistid=albumartistid;
	}

	public ArrayList<TopSongs> getAl_songs() {
		return al_songs;
	}

	public void setAl_songs(ArrayList<TopSongs> al_songs) {
		this.al_songs= al_songs;
	}

}





