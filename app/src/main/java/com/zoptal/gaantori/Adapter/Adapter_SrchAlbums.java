package com.zoptal.gaantori.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.zoptal.gaantori.Fragment.Fragment_Home1;
import com.zoptal.gaantori.Fragment.Fragment_Srch;
import com.zoptal.gaantori.JsonClasses.Json_albumdetailAll;
import com.zoptal.gaantori.JsonClasses.Json_artistdetailAll;
import com.zoptal.gaantori.JsonClasses.Model_Srch;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.common.NetworkConnection;

import java.util.Arrays;
import java.util.List;

public class Adapter_SrchAlbums extends BaseAdapter {

	//Varibale declartion
	//initialize view's
	LayoutInflater inflater;
	Context context ;
    Model_Srch m;
	public static String id,albumname,artistname,img;

	public Adapter_SrchAlbums(Context c, Model_Srch m) {

		context = c;
		this.m = m;
		inflater=LayoutInflater.from(context); // inflate the layout
			}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub

		if(m.getAl_srchalbum().size()==0){

			Fragment_Srch.tv_album.setVisibility(View.GONE);

		}
		return m.getAl_srchalbum().size();

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

		HolderSrchAlbum holder=new HolderSrchAlbum();
		convertView=inflater.inflate(R.layout.customview_albumssrch, null);

		// get the TextView and then set the text (item name) and tag (item ID) values

		holder.txt_name=(TextView) convertView.findViewById(R.id.name);
			holder.img=(ImageView) convertView.findViewById(R.id.img);
		holder.relativeLayout=(RelativeLayout)convertView.findViewById(R.id.relativeLayout);

		holder.listSinger = (ListView) convertView.findViewById(R.id.listSinger);

		ViewGroup.LayoutParams listh = (ViewGroup.LayoutParams) holder.listSinger.getLayoutParams();
		listh.height =80;
		holder.listSinger.setLayoutParams(listh);


		artistname=m.getAl_srchalbum().get(position).getSinger();
		List<String> alistname = Arrays.asList(artistname.split(","));

		id = m.getAl_srchalbum().get(position).getArtists();


		final List<String> alistid = Arrays.asList(id.split(","));

		for(int i=0;i<alistname.size();i++){

			int totalh=alistname.size();
			listh.height =totalh*80;
			holder.listSinger.setLayoutParams(listh);

		}

		final ArrayAdapter<String> stringArrayAdapter=new ArrayAdapter<String>(context,R.layout.list_itemsinger,alistname);
		stringArrayAdapter.setNotifyOnChange(true);
		holder.listSinger.setAdapter(stringArrayAdapter);




		holder.txt_name.setText(m.getAl_srchalbum().get(position).getTitle());
			Picasso.with(context).load(m.getAl_srchalbum().get(position).getImage()).into(holder.img);


		holder.txt_name.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				id=m.getAl_srchalbum().get(position).getId();
				albumname=m.getAl_srchalbum().get(position).getTitle();
				artistname=m.getAl_srchalbum().get(position).getSinger();
				img=m.getAl_srchalbum().get(position).getImage();


				String user_id;
				if(Fragment_Home1.user_id.equals("null")){
					user_id="0";
				}
				else{

					user_id=Fragment_Home1.user_id;
				}

				if (NetworkConnection.isConnectedToInternet(context)) {

					new Json_albumdetailAll(context).execute(m.getAl_srchalbum().get(position).getId(), user_id);
				}
			else {
					Toast.makeText(context,"Please Check your internet connection", Toast.LENGTH_SHORT).show();


				}





			}
		});

		holder.img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				id=m.getAl_srchalbum().get(position).getId();
				albumname=m.getAl_srchalbum().get(position).getTitle();
				artistname=m.getAl_srchalbum().get(position).getSinger();
				img=m.getAl_srchalbum().get(position).getImage();

				String user_id;
				if(Fragment_Home1.user_id.equals("null")){
					user_id="0";
				}
				else{

					user_id=Fragment_Home1.user_id;
				}


				if (NetworkConnection.isConnectedToInternet(context)) {

					new Json_albumdetailAll(context).execute(m.getAl_srchalbum().get(position).getId(),user_id);


				}
				else {
					Toast.makeText(context,"Please Check your internet connection", Toast.LENGTH_SHORT).show();


				}





			}
		});

		holder.listSinger.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
							new Json_artistdetailAll(context).execute(Fragment_Home1.user_id, alistid.get(position));

			}
		});



		return convertView;

	}

}
// View lookup cache
class HolderSrchAlbum
{
	TextView txt_name;
	ListView listSinger;
	ImageView img;
	RelativeLayout relativeLayout;


}