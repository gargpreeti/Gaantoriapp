package com.zoptal.gaantori.Adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zoptal.gaantori.Fragment.Fragment_PlaylistDetail;
import com.zoptal.gaantori.JsonClasses.Model_Home;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.main.MainActivity1;


public class Adapter_HomeFeatured extends BaseAdapter {

	//Varibale declartion
	//initialize view's

	LayoutInflater inflater;
	Context context ;
	Model_Home m;
	public static String pid;

	public Adapter_HomeFeatured(Context context, Model_Home m) {

		this.context=context;
		this.m = m;

		inflater=LayoutInflater.from(context); // inflate the layout
			}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub

		return m.getAl_homeplaylist().size();
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

		Holderfeat holder=new Holderfeat();   // view lookup cache stored
		convertView=inflater.inflate(R.layout.customview_albums_browse, null);  // inflate the layout

		// get the TextView and then set the text (item name) and tag (item ID) values
		holder.txt_name=(TextView) convertView.findViewById(R.id.name);
		holder.img=(ImageView) convertView.findViewById(R.id.img);


		// object item based on the position
		// Populate the data from the data object via the viewHolder object
		// into the template view.
		holder.txt_name.setText(m.getAl_homeplaylist().get(position).getAlbumname());
			Picasso.with(context).load(m.getAl_homeplaylist().get(position).getAlbumimage()).into(holder.img);

//
		holder.img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				pid=m.getAl_homeplaylist().get(position).getId();

				Fragment_PlaylistDetail fragment_detail = new Fragment_PlaylistDetail();
				FragmentManager fragmentManager3 = ((Activity) context).getFragmentManager();
				fragmentManager3.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_detail).addToBackStack(null).commit();
				MainActivity1.Drawer.closeDrawer(Gravity.LEFT);




			}
		});

		// Return the completed view to render on screen
		return convertView;

	}

}
// View lookup cache
class Holderfeat
{
	TextView txt_name;
	TextView txt_singer;
	ImageView img;
}