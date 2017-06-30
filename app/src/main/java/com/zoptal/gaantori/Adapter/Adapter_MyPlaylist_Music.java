package com.zoptal.gaantori.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zoptal.gaantori.Fragment.Fragment_Home1;
import com.zoptal.gaantori.Fragment.Fragment_PlaylistDetailUser;
import com.zoptal.gaantori.JsonClasses.Json_Saveplaylist;
import com.zoptal.gaantori.JsonClasses.Json_StarSong_add_remove;
import com.zoptal.gaantori.JsonClasses.Json_removeplaylist;
import com.zoptal.gaantori.JsonClasses.Json_renameplaylist;
import com.zoptal.gaantori.JsonClasses.Json_shareapp;
import com.zoptal.gaantori.JsonClasses.MyPlaylist;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.common.NetworkConnection;
import com.zoptal.gaantori.main.MainActivity1;

import java.util.ArrayList;


public class Adapter_MyPlaylist_Music extends BaseAdapter {

	//Varibale declartion
	//initialize view's
	LayoutInflater inflater;
	Context context ;
	ArrayList<MyPlaylist> al_myplaylist;
	public static String pid,listname;
	 Dialog dialog;

	public Adapter_MyPlaylist_Music(Context context,ArrayList<MyPlaylist> al_myplaylist) {

		this.context=context;
		this.al_myplaylist=al_myplaylist;
		inflater=LayoutInflater.from(context);
			}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return al_myplaylist.size();
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

		HolderMyPlaylist holder=new HolderMyPlaylist();  // view lookup cache stored
		convertView=inflater.inflate(R.layout.customview_music_myplaylist, null);  // inflate the layout


		// get the TextView and then set the text (item name) and tag (item ID) values
		holder.txt_name=(TextView) convertView.findViewById(R.id.tv_name);
		holder.txt_aname=(TextView) convertView.findViewById(R.id.tv_song);
		holder.btn=(Button)convertView.findViewById(R.id.btn);
		holder.img=(ImageView) convertView.findViewById(R.id.img);
		holder.linear=(LinearLayout)convertView.findViewById(R.id.linear);
		holder.linr=(LinearLayout)convertView.findViewById(R.id.linr);

		// object item based on the position
		// Populate the data from the data object via the viewHolder object
		// into the template view.

		holder.txt_name.setText(al_myplaylist.get(position).getName());
		holder.txt_aname.setText(al_myplaylist.get(position).getNofs());

		if (NetworkConnection.isConnectedToInternet(context)) {


			new Json_shareapp(context).execute();



		}
		else {
			Toast.makeText(context,"Please Check your internet connection", Toast.LENGTH_SHORT).show();


		}

			holder.linear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


				pid=al_myplaylist.get(position).getId();
				listname=al_myplaylist.get(position).getName();


				Fragment_PlaylistDetailUser fragment_detail = new Fragment_PlaylistDetailUser();
				FragmentManager fragmentManager3 = ((Activity) context).getFragmentManager();
				fragmentManager3.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_detail).commit();
				MainActivity1.Drawer.closeDrawer(Gravity.LEFT);


			}
		});

		holder.linr.setOnClickListener(new View.OnClickListener() {
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

				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setCancelable(true);
				dialog.setContentView(R.layout.dialog_addplaylist5);

    			TextView tv_cncl=(TextView)dialog.findViewById(R.id.tv_cncl);
						RelativeLayout remove_list=(RelativeLayout)dialog.findViewById(R.id.relativeLayout11);
				RelativeLayout rename_list=(RelativeLayout)dialog.findViewById(R.id.relativeLayout113);
				RelativeLayout share = (RelativeLayout) dialog.findViewById(R.id.relativeLayout3);

				remove_list.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();


						if (NetworkConnection.isConnectedToInternet(context)) {


							new Json_removeplaylist(context).execute(Fragment_Home1.user_id,al_myplaylist.get(position).getId());



						}
						else {
							Toast.makeText(context,"Please Check your internet connection", Toast.LENGTH_SHORT).show();


						}


					}
				});

				share.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();

						shareIt();
					}
				});

				rename_list.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();


						dialog_listname1(position);


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

	final Dialog dialog1 = new Dialog(context,android.R.style.Theme_Translucent);
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


	public void dialog_listname1(final int poss){


		final Dialog	dialog1 = new Dialog(context,android.R.style.Theme_Translucent);
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


				if (NetworkConnection.isConnectedToInternet(context)) {


					new Json_renameplaylist(context).execute(Fragment_Home1.user_id,al_myplaylist.get(poss).getId(),name);


				}
				else {
					Toast.makeText(context,"Please Check your internet connection", Toast.LENGTH_SHORT).show();


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


					new Json_StarSong_add_remove(context).execute(Fragment_Home1.user_id,al_myplaylist.get(pos).getId(),"1");

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
// View lookup cache
class HolderMyPlaylist
{
	TextView txt_name;
	TextView txt_aname;
	Button btn;
	ImageView img;
	LinearLayout linear;
	LinearLayout linr;
}