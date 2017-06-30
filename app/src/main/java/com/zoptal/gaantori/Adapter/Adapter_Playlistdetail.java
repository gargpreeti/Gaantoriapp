package com.zoptal.gaantori.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zoptal.gaantori.Fragment.Fragment_Home1;
import com.zoptal.gaantori.JsonClasses.Json_MyPlaylist1;
import com.zoptal.gaantori.JsonClasses.Json_OfflineSongs;
import com.zoptal.gaantori.JsonClasses.Json_PlaylistDetail;
import com.zoptal.gaantori.JsonClasses.Json_Saveplaylist;
import com.zoptal.gaantori.JsonClasses.Json_StarSong_add_remove;
import com.zoptal.gaantori.JsonClasses.Json_albumdetail;
import com.zoptal.gaantori.JsonClasses.Json_saveplaylistsong;
import com.zoptal.gaantori.JsonClasses.Json_shareapp;
import com.zoptal.gaantori.JsonClasses.TopSongs;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.common.DownloadFileFromURL;
import com.zoptal.gaantori.common.NetworkConnection;
import com.zoptal.gaantori.main.MainActivity1;

import java.util.ArrayList;


public class Adapter_Playlistdetail extends BaseAdapter {

	//Varibale declartion
	//initialize view's
	LayoutInflater inflater;
	Context context ;
	ArrayList<TopSongs> al_playlistsong;
	MainActivity1 activity1;
	Dialog dialog1;

	public Adapter_Playlistdetail(Context context,ArrayList<TopSongs> al_playlistsong) {

		this.context=context;
		this.al_playlistsong=al_playlistsong;
		activity1 = (MainActivity1) context;
		inflater=LayoutInflater.from(context);  // inflate the layout

	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return al_playlistsong.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView( final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		final HolderPlaylist holder=new HolderPlaylist();
		convertView=inflater.inflate(R.layout.customview_playlist, null);


		// get the TextView and then set the text (item name) and tag (item ID) values
		holder.txt_name=(TextView) convertView.findViewById(R.id.tv_name);
		holder.txt_aname=(TextView) convertView.findViewById(R.id.tv_aname);
		holder.btn=(Button)convertView.findViewById(R.id.btn);
		holder.img=(ImageView) convertView.findViewById(R.id.img);
		holder.menu_linear=(LinearLayout)convertView.findViewById(R.id.menu_linear);
		holder.rel=(RelativeLayout)convertView.findViewById(R.id.rel);


		// object item based on the position
		// Populate the data from the data object via the viewHolder object
		// into the template view.

		holder.txt_name.setText(al_playlistsong.get(position).getTitle());
		holder.txt_aname.setText(al_playlistsong.get(position).getAlbum());

		if ((activity1.playingTAG.equalsIgnoreCase("Adapter_Playlistdetail")) && (position==activity1.playingPosition)) {
			holder.btn.setBackgroundResource(R.mipmap.current_song);
			holder.txt_name.setTextColor(Color.parseColor("#64ffef"));
		} else {
			holder.btn.setBackgroundResource(R.mipmap.song_vlm);
			holder.txt_name.setTextColor(Color.WHITE);
		}

		holder.rel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


				if(Fragment_Home1.utype1.equals("Premium")) {

				}
				else{

					final Toast toast = Toast.makeText(context,"Free Users/Guest Users can only play Shuffle,Please Register",Toast.LENGTH_SHORT);
					toast.show();
					new CountDownTimer(1000, 500)
					{
						public void onTick(long millisUntilFinished) {toast.show();}
						public void onFinish() {toast.cancel();}
					}.start();


								}
				activity1.linear_small.setVisibility(View.VISIBLE);
				activity1.playingTAG="Adapter_Playlistdetail";
				activity1.playingPosition=position;
				holder.btn.setBackgroundResource(R.mipmap.current_song);
				notifyDataSetChanged();
				activity1.setListOnPlayer(Json_albumdetail.model_albumDetail.getAl_songs(), position,false,Adapter_Playlistdetail.this);

			}
		});


		if (NetworkConnection.isConnectedToInternet(context)) {

			new Json_shareapp(context).execute();


		}
		else {
			Toast.makeText(context,"Please Check your internet connection", Toast.LENGTH_SHORT).show();


		}





					holder.menu_linear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				final Dialog dialog = new Dialog(context,android.R.style.Theme_Translucent);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

				dialog.setCancelable(true);
				dialog.setContentView(R.layout.dialog_addplaylist);


				TextView tv_cncl=(TextView)dialog.findViewById(R.id.tv_cncl);
				RelativeLayout add_list=(RelativeLayout)dialog.findViewById(R.id.relativeLayout1);
				RelativeLayout saveoffline=(RelativeLayout)dialog.findViewById(R.id.relativeLayout);
						RelativeLayout share=(RelativeLayout)dialog.findViewById(R.id.relativeLayout3);

				add_list.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();

						if(Fragment_Home1.user_id.equals("null")) {

							final Toast toast = Toast.makeText(context,"Guest users cannot add songs to playlist",Toast.LENGTH_SHORT);
							toast.show();
							new CountDownTimer(1000, 500)
							{
								public void onTick(long millisUntilFinished) {toast.show();}
								public void onFinish() {toast.cancel();}
							}.start();



											}

						else{
							dialog_addplaylist(position);

						}



					}
				});

				saveoffline.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						if(Fragment_Home1.utype1.equals("Premium")) {

							new DownloadFileFromURL( Json_PlaylistDetail.al_playlistsong.get(position).getUrl(), Json_PlaylistDetail.al_playlistsong.get(position).getTitle()).execute( Json_PlaylistDetail.al_playlistsong.get(position).getUrl());

							if (NetworkConnection.isConnectedToInternet(context)) {


								new Json_OfflineSongs(context).execute(Fragment_Home1.user_id,Json_PlaylistDetail.al_playlistsong.get(position).getId(),"1");


							}
							else {
								Toast.makeText(context,"Please Check your internet connection", Toast.LENGTH_SHORT).show();


							}



											}
						else{


							final Toast toast = Toast.makeText(context,"Free user cannot save offline songs",Toast.LENGTH_SHORT);
							toast.show();
							new CountDownTimer(1000, 500)
							{
								public void onTick(long millisUntilFinished) {toast.show();}
								public void onFinish() {toast.cancel();}
							}.start();

						}


						dialog.dismiss();


					}
				});


				share.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();

						shareIt();
					}
				});

				tv_cncl.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

				dialog.show();

			}
		});


		return convertView;

	}

	private void shareIt() {
//sharing implementation here
		Intent sharingIntent = new Intent(Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Gaantori App");
		sharingIntent.putExtra(Intent.EXTRA_TEXT, Json_shareapp.userlink);
		context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
	}

	public void dialog_listname(){


		dialog1 = new Dialog(context,android.R.style.Theme_Translucent);
		dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);

		dialog1.setCancelable(true);
		dialog1.setContentView(R.layout.dialog_listname);


		TextView text_cncl=(TextView)dialog1.findViewById(R.id.cncl);
		TextView text_create=(TextView)dialog1.findViewById(R.id.create);
		final EditText ed_name=(EditText)dialog1.findViewById(R.id.name);

		text_cncl.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog1.dismiss();
			}
		});


		text_create.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String name=ed_name.getText().toString().toLowerCase().trim();

				if(Fragment_Home1.user_id.equals("null")){

					final Toast toast = Toast.makeText(context,"Please Login to create playlist",Toast.LENGTH_SHORT);
					toast.show();
					new CountDownTimer(1000, 500)
					{
						public void onTick(long millisUntilFinished) {toast.show();}
						public void onFinish() {toast.cancel();}
					}.start();

				}

				else {


					if (NetworkConnection.isConnectedToInternet(context)) {


						new Json_Saveplaylist(context).execute(name,Fragment_Home1.user_id);
					}
					else {
						Toast.makeText(context,"Please Check your internet connection", Toast.LENGTH_SHORT).show();


					}

				}

				dialog1.dismiss();
			}
		});



		dialog1.show();
	}


	public void dialog_addplaylist(final int pos){


		final Dialog dialog = new Dialog(context,android.R.style.Theme_Translucent);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		dialog.setCancelable(true);
		dialog.setContentView(R.layout.dialog_addplaylist);


		ImageView img_cross= (ImageView)dialog.findViewById(R.id.img_cross);
		RelativeLayout rel=(RelativeLayout)dialog.findViewById(R.id.star_id);
		ListView list=(ListView)dialog.findViewById(R.id.list);

		if (NetworkConnection.isConnectedToInternet(context)) {


			new Json_MyPlaylist1(context,list).execute(Fragment_Home1.user_id);
		}
		else {
			Toast.makeText(context,"Please Check your internet connection", Toast.LENGTH_SHORT).show();


		}

		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


				if (NetworkConnection.isConnectedToInternet(context)) {


					new Json_saveplaylistsong(context).execute(Fragment_Home1.user_id, Json_MyPlaylist1.model_myPlaylist.getAl_myplaylist().get(pos).getId(),al_playlistsong.get(position).getId(),"1");
				}
				else {
					Toast.makeText(context,"Please Check your internet connection", Toast.LENGTH_SHORT).show();


				}




			}
		});

		LinearLayout newlist=(LinearLayout)dialog.findViewById(R.id.newlist);

				newlist.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();

						dialog_listname();

					}
				});

		rel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


				if (NetworkConnection.isConnectedToInternet(context)) {


					new Json_StarSong_add_remove(context).execute(Fragment_Home1.user_id,al_playlistsong.get(pos).getId(),"1");

				}
				else {
					Toast.makeText(context,"Please Check your internet connection", Toast.LENGTH_SHORT).show();


				}

							dialog.dismiss();

			}
		});



		img_cross.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});




		dialog.show();
	}

}
class HolderPlaylist
{
	TextView txt_name;
	TextView txt_aname;
	Button btn;
	ImageView img;
	LinearLayout menu_linear;
	ImageView img_downloaded;
	RelativeLayout rel;
}