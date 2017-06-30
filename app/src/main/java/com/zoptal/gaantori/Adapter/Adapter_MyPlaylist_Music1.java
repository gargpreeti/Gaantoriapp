package com.zoptal.gaantori.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zoptal.gaantori.JsonClasses.Model_MyPlaylist;
import com.zoptal.gaantori.R;


public class Adapter_MyPlaylist_Music1 extends BaseAdapter {


	//Varibale declartion
	//initialize view's
	LayoutInflater inflater;
	Context context ;
	Model_MyPlaylist m;

	public Adapter_MyPlaylist_Music1(Context context, Model_MyPlaylist m) {

		this.context=context;
		this.m=m;
		inflater=LayoutInflater.from(context);   // inflate the layout

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return m.getAl_myplaylist().size();
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

		HolderMyPlaylist1 holder=new HolderMyPlaylist1();  // view lookup cache stored in tag
		convertView=inflater.inflate(R.layout.customview_music_myplaylist1, null); // inflate the layout

		// get the TextView and then set the text (item name) and tag (item ID) values

		holder.txt_name=(TextView) convertView.findViewById(R.id.tv_name);
		holder.txt_aname=(TextView) convertView.findViewById(R.id.tv_song);
		holder.img=(ImageView) convertView.findViewById(R.id.img);

		// object item based on the position
		// Populate the data from the data object via the viewHolder object
		// into the template view.

		holder.txt_name.setText(m.getAl_myplaylist().get(position).getName());
		holder.txt_aname.setText(m.getAl_myplaylist().get(position).getNofs());

		// Return the completed view to render on screen
		return convertView;

	}

	public void dialog_addplaylist(){

		final Dialog dialog = new Dialog(context,android.R.style.Theme_Translucent);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCancelable(true);
		dialog.setContentView(R.layout.dialog_addplaylist);


		ImageView img_cross= (ImageView)dialog.findViewById(R.id.img_cross);
		LinearLayout newlist=(LinearLayout)dialog.findViewById(R.id.newlist);
		newlist.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
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
class HolderMyPlaylist1
{
	TextView txt_name;
	TextView txt_aname;
	Button btn;
	ImageView img;
}