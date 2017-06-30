package com.zoptal.gaantori.JsonClasses;

import java.util.ArrayList;

public class Model_Home {


	private ArrayList<HomeData> al_homenewreleases = new ArrayList<HomeData>();
	private ArrayList<HomeDataPlaylist> al_homeplaylist = new ArrayList<HomeDataPlaylist>();
	private ArrayList<HomeDataArtists> al_homeartist = new ArrayList<HomeDataArtists>();

	public ArrayList<HomeData> getAl_homenewreleases() {
		return al_homenewreleases;
	}

	public void setAl_homenewreleases(ArrayList<HomeData> al_homenewreleases) {
		this.al_homenewreleases= al_homenewreleases;
	}



	public ArrayList<HomeDataPlaylist> getAl_homeplaylist() {
		return al_homeplaylist;
	}

	public void setAl_homeplaylist(ArrayList<HomeDataPlaylist> al_homeplaylist) {
		this.al_homeplaylist= al_homeplaylist;
	}


	public ArrayList<HomeDataArtists> getAl_homeartist() {
		return al_homeartist;
	}

	public void setAl_homeartist(ArrayList<HomeDataArtists> al_homeartist) {
		this.al_homeartist= al_homeartist;
	}

}





