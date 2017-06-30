package com.zoptal.gaantori.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zoptal.gaantori.Fragment.Fragment_Home1;
import com.zoptal.gaantori.Fragment.Fragment_MyMusic;
import com.zoptal.gaantori.JsonClasses.Json_OfflineSongsRemove;
import com.zoptal.gaantori.JsonClasses.Json_shareapp;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.main.MainActivity1;

import java.io.File;
import java.util.ArrayList;

public class Adapter_offlinesongs extends BaseAdapter {

	//Varibale declartion
	//initialize view's
	LayoutInflater inflater;
	ArrayList<String> name;
	Context context;
	Dialog dialog;
	MainActivity1 activity1;

	public Adapter_offlinesongs(Context context, ArrayList<String>  name) {
		this.name = name;
		this.context = context;
		activity1 = (MainActivity1) context;
		inflater = LayoutInflater.from(context); // inflate the layout

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return name.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View v, ViewGroup parent) {

			ViewHolder holder=new ViewHolder();
		if(v==null){
		v = inflater.inflate(R.layout.song_layout, null);

			// get the TextView and then set the text (item name) and tag (item ID) values
		holder.txt_name = (TextView) v.findViewById(R.id.textView1);
		holder.rel=(RelativeLayout)v.findViewById(R.id.rel);
		holder.menu_linear=(LinearLayout) v.findViewById(R.id.menu_linear);
		v.setTag(holder);

			}
		else
		{
			holder =(ViewHolder)v.getTag();
			
		}
		// object item based on the position
		// Populate the data from the data object via the viewHolder object
		// into the template view.
		holder.txt_name.setText(name.get(position));

		holder.rel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				activity1.linear_small.setVisibility(View.VISIBLE);
		      	activity1.setListOnPlayer(Fragment_MyMusic.items, position, true, null);

			}
		});

		holder.menu_linear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				dialog_offline(position);
			}
		});
		return v;

	}

	class ViewHolder{
		TextView txt_name;
		RelativeLayout rel;
		LinearLayout  menu_linear;
		
	}

	public void dialog_offline(final int position){

		dialog= new Dialog(context, android.R.style.Theme_Translucent){
			@Override
			public boolean onTouchEvent(MotionEvent event) {
				// Tap anywhere to close dialog.
				dialog.dismiss();
				return true;
			}
		};

		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCancelable(true);
		dialog.setContentView(R.layout.dialog_addplaylist6);

		TextView tv_cncl=(TextView)dialog.findViewById(R.id.tv_cncl);
		final RelativeLayout play=(RelativeLayout)dialog.findViewById(R.id.relativeLayout11);
		RelativeLayout removes=(RelativeLayout)dialog.findViewById(R.id.relativeLayout);
		RelativeLayout playnxt=(RelativeLayout)dialog.findViewById(R.id.relativeLayout2);
		RelativeLayout share=(RelativeLayout)dialog.findViewById(R.id.relativeLayout3);


		removes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String filePath = Environment.getExternalStorageDirectory() + "/.GaantoriApp/" +Fragment_MyMusic.items.remove(position).getTitle() + ".mp3";


				File file = new File(filePath);
				if (file.exists()) {
					file.delete();
				}
				Fragment_MyMusic.items1.remove(position);
						new Json_OfflineSongsRemove(context).execute(Fragment_Home1.user_id,Fragment_MyMusic.itemsID.get(position),"0");

				notifyDataSetChanged();

						dialog.dismiss();

			}
		});
		playnxt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


				if(Fragment_Home1.utype1.equals("Premium")) {

					final Toast toast = Toast.makeText(context,"Song added to queue list",Toast.LENGTH_SHORT);
					toast.show();
							new CountDownTimer(1000, 500)
					{
						public void onTick(long millisUntilFinished) {toast.show();}
						public void onFinish() {toast.cancel();}
					}.start();
					activity1.addSongToQueue(Fragment_MyMusic.items.get(position));
					dialog.dismiss();
				}
				else{

					Toast.makeText(context, "Please Register to enjoy Premium Functions", Toast.LENGTH_LONG).show();
				}

			}
		});
		play.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


				activity1.linear_small.setVisibility(View.VISIBLE);
              activity1.setListOnPlayer(Fragment_MyMusic.items, position, true, null);
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

	private void shareIt() {
//sharing implementation here
		Intent sharingIntent = new Intent(Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Gaantori App");
		sharingIntent.putExtra(Intent.EXTRA_TEXT, Json_shareapp.userlink);
		context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
	}
	
}
