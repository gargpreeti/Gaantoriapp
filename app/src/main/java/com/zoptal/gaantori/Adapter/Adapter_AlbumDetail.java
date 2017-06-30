package com.zoptal.gaantori.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.zoptal.gaantori.JsonClasses.Json_Saveplaylist_song;
import com.zoptal.gaantori.JsonClasses.Json_StarSong_add_remove;
import com.zoptal.gaantori.JsonClasses.Json_albumdetail;
import com.zoptal.gaantori.JsonClasses.Json_saveplaylistsong;
import com.zoptal.gaantori.JsonClasses.Json_shareapp;
import com.zoptal.gaantori.JsonClasses.Model_AlbumDetail;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.common.DownloadFileFromURL;
import com.zoptal.gaantori.common.NetworkConnection;
import com.zoptal.gaantori.main.MainActivity1;


public class Adapter_AlbumDetail extends BaseAdapter {

	//Varibale declartion
	//initialize view's
	LayoutInflater inflater;
	Context context;
	public static Model_AlbumDetail m;
	Dialog dialog1;
	public static String songurl;
	MainActivity1 activity1;
    public static int testpos;
	Dialog dialog;
	public static String songid;

	public Adapter_AlbumDetail(Context context, Model_AlbumDetail m) {

		this.context = context;
		this.m = m;
		activity1 = (MainActivity1) context;
		inflater = LayoutInflater.from(context); // inflate the layout


	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		//returning total listsize
		return m.getAl_songs().size();

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
	public View getView(final int position, View convertView, final ViewGroup parent) {
		// TODO Auto-generated method stub

				   /*

         * The convertView argument is essentially.
     * It will have a non-null value when ListView is asking you recycle the row layout.

         * So, when convertView is not null, you should simply update its contents instead of inflating a new row layout.
     */

		final HolderAlbumDeatail holder = new HolderAlbumDeatail(); // view lookup cache stored in tag
		convertView = inflater.inflate(R.layout.customview_top_songs, null);  // inflate the layout

		// get the TextView and then set the text (item name) and tag (item ID) values
		holder.txt_name = (TextView) convertView.findViewById(R.id.tv_name);
		holder.txt_aname = (TextView) convertView.findViewById(R.id.tv_aname);
		holder.btn = (Button) convertView.findViewById(R.id.btn);
		holder.img = (ImageView) convertView.findViewById(R.id.img);
		holder.img_downloaded=(ImageView) convertView.findViewById(R.id.img_downloaded);
		holder.rel = (RelativeLayout) convertView.findViewById(R.id.rel);
		holder.menu_linear = (LinearLayout) convertView.findViewById(R.id.menu_linear);
		holder.rel1 = (RelativeLayout) convertView.findViewById(R.id.rel);


		 // object item based on the position
		// Populate the data from the data object via the viewHolder object
		// into the template view.
		holder.txt_name.setText(m.getAl_songs().get(position).getTitle());
		holder.txt_aname.setText(m.getAl_songs().get(position).getArtistname());

//check the network connection
		if (NetworkConnection.isConnectedToInternet(context)) {

				new Json_shareapp(context).execute();
		}

		else {
//just toast it
				Toast.makeText(context,"Please Check your internet connection", Toast.LENGTH_SHORT).show();


		}

		String downloadedvalue=m.getAl_songs().get(position).getDownloaded().toString().trim();

			if(downloadedvalue.equals("1")){

			holder.img_downloaded.setVisibility(View.VISIBLE);
		}
		else{
			holder.img_downloaded.setVisibility(View.GONE);

		}


		if ((activity1.playingTAG.equalsIgnoreCase("Adapter_AlbumDetail")) && (position==activity1.playingPosition)) {

			holder.btn.setBackgroundResource(R.mipmap.current_song);
			holder.txt_name.setTextColor(Color.parseColor("#64ffef"));

		}

		else {
			holder.btn.setBackgroundResource(R.mipmap.song_vlm);
			holder.txt_name.setTextColor(Color.WHITE);
		}



//click listener for item listview to play song
		holder.rel1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				testpos=position;

				if(Fragment_Home1.utype1.equals("Premium")) {

					holder.rel1.setClickable(true);
					activity1.linear_small.setVisibility(View.VISIBLE);
					activity1.playingTAG="Adapter_AlbumDetail";
					activity1.playingPosition=position;
					holder.btn.setBackgroundResource(R.mipmap.current_song);
					notifyDataSetChanged();

					activity1.setListOnPlayer(Json_albumdetail.model_albumDetail.getAl_songs(),position, false,Adapter_AlbumDetail.this);

				}
				else{

					final Toast toast =Toast.makeText(context, "Free Users/Guest Users can only play Shuffle,Please Register", Toast.LENGTH_LONG);
					toast.show();
					new CountDownTimer(1000, 500)
					{
						public void onTick(long millisUntilFinished) {toast.show();}
						public void onFinish() {toast.cancel();}
					}.start();

				}

			}
		});

/*

 * Here you can control when we have to open popup

 */

		holder.menu_linear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

                 dialog = new Dialog(context, android.R.style.Theme_Translucent){
					@Override
					public boolean onTouchEvent(MotionEvent event) {
						// Tap anywhere to close dialog.
						dialog.dismiss();
						return true;
					}
				};

				dialog.setCanceledOnTouchOutside(true);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setCancelable(true);
				dialog.setContentView(R.layout.dialog_addplaylist2);


				TextView tv_cncl = (TextView) dialog.findViewById(R.id.tv_cncl);
				RelativeLayout add_list = (RelativeLayout) dialog.findViewById(R.id.relativeLayout1);
				RelativeLayout saveoffline=(RelativeLayout)dialog.findViewById(R.id.relativeLayout);
				RelativeLayout playnxt=(RelativeLayout)dialog.findViewById(R.id.relativeLayout2);
				RelativeLayout share = (RelativeLayout) dialog.findViewById(R.id.relativeLayout3);

				// To add song in playlist
				add_list.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();

						if(Fragment_Home1.user_id.equals("null")) {
							// just toast it

							Toast.makeText(context, "Guest users cannot add songs to playlist", Toast.LENGTH_LONG).show();
						}
						else{

							dialog_addplaylist(position);
						}

					}
				});
				//To PLay Next song.
				playnxt.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {



						if(Fragment_Home1.utype1.equals("Premium")) {
							Toast.makeText(context, "Song added to queue list", Toast.LENGTH_SHORT).show();
							activity1.addSongToQueue(Json_albumdetail.model_albumDetail.getAl_songs().get(position));
						}
						else{

							Toast.makeText(context, "Please Upgrade", Toast.LENGTH_LONG).show();
						}


					}
				});

				//For save offline song
				saveoffline.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {


						if(Fragment_Home1.utype1.equals("Premium")) {

							new DownloadFileFromURL(Json_albumdetail.model_albumDetail.getAl_songs().get(position).getUrl(),Json_albumdetail.model_albumDetail.getAl_songs().get(position).getTitle()).execute(Json_albumdetail.model_albumDetail.getAl_songs().get(position).getUrl());
							new Json_OfflineSongs(context).execute(Fragment_Home1.user_id, Json_albumdetail.model_albumDetail.getAl_songs().get(position).getId(),"1");


						}
						else{

							Toast.makeText(context, "Free user cannot save offline songs", Toast.LENGTH_LONG).show();
						}

								dialog.dismiss();


					}
				});

				//To share song
				share.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();

						Intent sharingIntent = new Intent(Intent.ACTION_SEND);
						sharingIntent.setType("text/plain");
						sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Gaantori App");
						sharingIntent.putExtra(Intent.EXTRA_TEXT, Json_shareapp.userlink);
						sharingIntent.putExtra(Intent.EXTRA_TEXT,"Played Album: "+ m.getAl_songs().get(position).getAlbum() +" Song: "+m.getAl_songs().get(position).getTitle()
			+ " by Artist: " + m.getAl_songs().get(position).getArtistname()+"   "+Json_shareapp.userlink);

				context.startActivity(Intent.createChooser(sharingIntent, "Share via"));


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
		// Return the completed view to render on screen
		return convertView;

	}
// Open dialog to create new playlist
	public void dialog_listname(final int poss) {

		dialog1 = new Dialog(context, android.R.style.Theme_Translucent);
		dialog1.setCanceledOnTouchOutside(true);
		dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);

		dialog1.setCancelable(true);
		dialog1.setContentView(R.layout.dialog_listname);

		dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

		TextView text_cncl = (TextView) dialog1.findViewById(R.id.cncl);
		TextView text_create = (TextView) dialog1.findViewById(R.id.create);
		final EditText ed_name = (EditText) dialog1.findViewById(R.id.name);
			text_cncl.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog1.dismiss();
			}
		});

		//playlist name
		text_create.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String name = ed_name.getText().toString().toLowerCase().trim();

				if (Fragment_Home1.user_id == null) {

					Toast.makeText(context, "Please Login to create playlist", Toast.LENGTH_LONG).show();
				} else {
					Log.e("song id homee----", "" +m.getAl_songs().get(poss).getId());
			       	songid=m.getAl_songs().get(poss).getId();
					new Json_Saveplaylist_song(context).execute(name, Fragment_Home1.user_id);
				}
//To cancel dialog
				dialog1.dismiss();
			}
		});
//To show dialog
		dialog1.show();
	}

// Open dialog to create new playlist
	public void dialog_addplaylist(final int pos) {


		final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		dialog.setCancelable(true);
		dialog.setContentView(R.layout.dialog_addplaylist);


		ImageView img_cross = (ImageView) dialog.findViewById(R.id.img_cross);
		RelativeLayout rel = (RelativeLayout) dialog.findViewById(R.id.star_id);
		final ListView list = (ListView) dialog.findViewById(R.id.list);

		new Json_MyPlaylist1(context, list).execute(Fragment_Home1.user_id);

		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				new Json_saveplaylistsong(context).execute(Fragment_Home1.user_id, Json_MyPlaylist1.model_myPlaylist.getAl_myplaylist().get(position).getId(), m.getAl_songs().get(pos).getId(),"1");
				dialog.dismiss();
			}
		});


		LinearLayout newlist = (LinearLayout) dialog.findViewById(R.id.newlist);

		//create newlist
		newlist.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();

				dialog_listname(pos);

			}
		});

		rel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// call Json class to add song in started list

				new Json_StarSong_add_remove(context).execute(Fragment_Home1.user_id, m.getAl_songs().get(pos).getId(), "1");


				dialog.dismiss();

			}
		});

// To cancel dialog
		img_cross.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				songurl = m.getAl_songs().get(pos).getUrl(); // get songurl accroding to position

				dialog.dismiss();
			}
		});


		dialog.show();
	}


}

// View lookup cache
class HolderAlbumDeatail
{
      TextView txt_name;
	TextView txt_aname;
	Button btn;
	ImageView img;
	ImageView img_downloaded;
	RelativeLayout rel;
	LinearLayout menu_linear;
	RelativeLayout rel1;
}