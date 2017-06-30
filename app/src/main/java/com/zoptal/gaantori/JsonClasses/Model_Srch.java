package com.zoptal.gaantori.JsonClasses;

import java.util.ArrayList;

public class Model_Srch {


	private ArrayList<SrchAlbum> al_srchalbum = new ArrayList<SrchAlbum>();
	private ArrayList<SrchArtist> al_srchartist = new ArrayList<SrchArtist>();
	private ArrayList<Playlist> al_playlist = new ArrayList<Playlist>();

	private ArrayList<TopSongs> al_srchsong = new ArrayList<TopSongs>();


	public ArrayList<SrchAlbum> getAl_srchalbum() {
		return al_srchalbum;
	}

	public void setAl_srchalbum(ArrayList<SrchAlbum> al_srchalbum) {
		this.al_srchalbum= al_srchalbum;
	}



	public ArrayList<SrchArtist> getAl_srchartist() {
		return al_srchartist;
	}

	public void setAl_srchartist(ArrayList<SrchArtist> al_srchartist) {
		this.al_srchartist= al_srchartist;
	}


	public ArrayList<Playlist> getAl_playlist() {
		return al_playlist;
	}

	public void setAl_playlist(ArrayList<Playlist> al_playlist) {
		this.al_playlist= al_playlist;
	}


	public ArrayList<TopSongs> getAl_srchsong() {
		return al_srchsong;
	}

	public void setAl_srchsong(ArrayList<TopSongs> al_srchsong) {
		this.al_srchsong= al_srchsong;
	}

}





