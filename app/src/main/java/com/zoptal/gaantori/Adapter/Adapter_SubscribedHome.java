package com.zoptal.gaantori.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.zoptal.gaantori.Fragment.Fragment_Home1;
import com.zoptal.gaantori.JsonClasses.Json_artistdetailAll;
import com.zoptal.gaantori.JsonClasses.Model_Home;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.common.NetworkConnection;


public class Adapter_SubscribedHome extends BaseAdapter {

	//Varibale declartion
	//initialize view's
	LayoutInflater inflater;
	Context context ;
	Model_Home m;
	public static String id,artistname,img,fcount,fstat;

	public Adapter_SubscribedHome(Context context, Model_Home m) {

		this.context=context;
		this.m=m;

	inflater=LayoutInflater.from(context); // inflate the layout

	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return m.getAl_homeartist().size();
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

		Holdersubhome holder=new Holdersubhome();  // view lookup cache stored in tag
		convertView=inflater.inflate(R.layout.customview_albums_browse, null); // inflate the layout

		// get the TextView and then set the text (item name) and tag (item ID) values
		holder.txt_name=(TextView) convertView.findViewById(R.id.name);
		holder.txt_singer=(TextView) convertView.findViewById(R.id.singer);
		holder.img=(ImageView) convertView.findViewById(R.id.img);

		// object item based on the position
		// Populate the data from the data object via the viewHolder object
		// into the template view.

		holder.txt_name.setText(m.getAl_homeartist().get(position).getName());
		holder.txt_singer.setText(m.getAl_homeartist().get(position).getFcount());
		Picasso.with(context).load(m.getAl_homeartist().get(position).getImage()).into(holder.img);


		holder.img.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				id=m.getAl_homeartist().get(position).getId();
				artistname=m.getAl_homeartist().get(position).getName();
				img=m.getAl_homeartist().get(position).getImage();
				fcount=m.getAl_homeartist().get(position).getFcount();


				if (NetworkConnection.isConnectedToInternet(context)) {
					new Json_artistdetailAll(context).execute(Fragment_Home1.user_id,m.getAl_homeartist().get(position).getId());
				}
				else {
					Toast.makeText(context,"Please Check your internet connection", Toast.LENGTH_SHORT).show();


				}







			}
		});

		return convertView;

	}

}
// View lookup cache
class Holdersubhome
{
	TextView txt_name;
	TextView txt_singer;
	ImageView img;
}