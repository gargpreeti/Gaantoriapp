package com.zoptal.gaantori.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zoptal.gaantori.Fragment.Fragment_Srch;
import com.zoptal.gaantori.JsonClasses.Model_Srch;
import com.zoptal.gaantori.R;

public class Adapter_SrchPlaylist extends BaseAdapter {

	//Varibale declartion
	//initialize view's

	LayoutInflater inflater;
	Context context ;
    Model_Srch m;
	public static String id,albumname,artistname,img;

	public Adapter_SrchPlaylist(Context c, Model_Srch m) {

		context = c;
		this.m = m;
		inflater=LayoutInflater.from(context);// inflate the layout
			}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub

		if(m.getAl_playlist().size()==0){

			Fragment_Srch.tv_playlist.setVisibility(View.GONE);

		}
		return m.getAl_playlist().size();

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

		HolderSrchplaylist holder=new HolderSrchplaylist(); // view lookup cache stored in tag
		convertView=inflater.inflate(R.layout.customview_albumssrch, null);  // inflate the layout

		// get the TextView and then set the text (item name) and tag (item ID) values

		holder.txt_name=(TextView) convertView.findViewById(R.id.name);
		holder.txt_singer=(TextView) convertView.findViewById(R.id.singer);
		holder.img=(ImageView) convertView.findViewById(R.id.img);
		holder.relativeLayout=(RelativeLayout)convertView.findViewById(R.id.relativeLayout);

		// object item based on the position
		// Populate the data from the data object via the viewHolder object
		// into the template view.
    	holder.txt_name.setText(m.getAl_playlist().get(position).getAlbumname());
		holder.txt_singer.setText("");

		Picasso.with(context).load(m.getAl_playlist().get(position).getAlbumimage()).into(holder.img);

		return convertView;

	}

}
// View lookup cache
class HolderSrchplaylist
{
	TextView txt_name;
TextView txt_singer;

	ImageView img;
	RelativeLayout relativeLayout;


}