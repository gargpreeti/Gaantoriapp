package com.zoptal.gaantori.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.zoptal.gaantori.Fragment.Fragment_Home1;
import com.zoptal.gaantori.JsonClasses.Json_FollowUnfollow;
import com.zoptal.gaantori.JsonClasses.Json_artistdetailAll;
import com.zoptal.gaantori.JsonClasses.Model_Top;
import com.zoptal.gaantori.JsonClasses.NewRelease;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.common.NetworkConnection;

import java.util.ArrayList;


public class Adapter_TopArtists extends BaseAdapter {

	//Varibale declartion
	//initialize view's
	LayoutInflater inflater;
	Context context ;
	Model_Top m;
	public static String id,artistname,img,fcount,fstat;

	public Adapter_TopArtists(Context context,Model_Top m) {

		this.context=context;
		this.m=m;

		inflater=LayoutInflater.from(context);  // inflate the layout
			}


	public void setAddList(ArrayList<NewRelease> list)
	{

		this.m.getAl_newrelease().addAll(list);

		notifyDataSetChanged();


	}
	//get current arraylist
	public ArrayList<NewRelease> getCurrentList()
	{
		return m.getAl_newrelease();
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub

		return m.getAl_newrelease().size();


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

		final HolderTopArtists holder=new HolderTopArtists(); // view lookup cache stored in tag
		convertView=inflater.inflate(R.layout.customview_top_artists, null); // inflate the layout

		// get the TextView and then set the text (item name) and tag (item ID) values
		holder.txt_name=(TextView) convertView.findViewById(R.id.tv_name);
		holder.txt_aname=(TextView) convertView.findViewById(R.id.tv_user);
		holder.btn=(Button)convertView.findViewById(R.id.btn);
		holder.btn1=(Button)convertView.findViewById(R.id.btn1);
		holder.img=(ImageView) convertView.findViewById(R.id.img);
		holder.rel=(RelativeLayout)convertView.findViewById(R.id.rel);

		// object item based on the position
		// Populate the data from the data object via the viewHolder object
		// into the template view.
		holder.txt_name.setText(m.getAl_newrelease().get(position).getName());
		holder.txt_aname.setText(m.getAl_newrelease().get(position).getFcount());

		Picasso.with(context).load(m.getAl_newrelease().get(position).getImage()).into(holder.img);


		holder.img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			id=m.getAl_newrelease().get(position).getId();

				if (NetworkConnection.isConnectedToInternet(context)) {

					new Json_artistdetailAll(context).execute(Fragment_Home1.user_id, id);
				}
				else {
					Toast.makeText(context,"Please Check your internet connection", Toast.LENGTH_SHORT).show();


				}





			}
		});



		holder.txt_name.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				id=m.getAl_newrelease().get(position).getId();



				if (NetworkConnection.isConnectedToInternet(context)) {

					new Json_artistdetailAll(context).execute(Fragment_Home1.user_id, id);

				}
				else {
					Toast.makeText(context,"Please Check your internet connection", Toast.LENGTH_SHORT).show();


				}





			}
		});
		if(m.getAl_newrelease().get(position).getFstat().equals("follow")){

			holder.btn.setVisibility(View.VISIBLE);
			holder.btn1.setVisibility(View.INVISIBLE);
		}
		else {
			holder.btn1.setVisibility(View.VISIBLE);
			holder.btn.setVisibility(View.INVISIBLE);
		}
		if(Fragment_Home1.user_id.equals("null")) {
			holder.btn.setEnabled(false);
			holder.btn1.setEnabled(false);

		}
		else{
			holder.btn1.setEnabled(true);
			holder.btn.setEnabled(true);
		}

		holder.btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				holder.btn.setVisibility(View.INVISIBLE);
				holder.btn1.setVisibility(View.VISIBLE);


				if (NetworkConnection.isConnectedToInternet(context)) {

					new Json_FollowUnfollow(context).execute(Fragment_Home1.user_id,m.getAl_newrelease().get(position).getId(),"1");
					int previouscount= Integer.parseInt(holder.txt_aname.getText().toString());

					int newvalue=1;
					newvalue+=previouscount;

					holder.txt_aname.setText(String.valueOf(newvalue));

				}
				else {
					Toast.makeText(context,"Please Check your internet connection", Toast.LENGTH_SHORT).show();


				}



			}
		});

		holder.btn1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				holder.btn.setVisibility(View.VISIBLE);
				holder.btn1.setVisibility(View.INVISIBLE);


				if (NetworkConnection.isConnectedToInternet(context)) {

					new Json_FollowUnfollow(context).execute(Fragment_Home1.user_id,m.getAl_newrelease().get(position).getId(),"0");


					int previouscount= Integer.parseInt(holder.txt_aname.getText().toString());
							int newvalue=1;
					newvalue=previouscount-newvalue;
							if(newvalue<0) {
						holder.txt_aname.setText("0");
					}
					else {
						holder.txt_aname.setText(String.valueOf(newvalue));
					}
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
class HolderTopArtists
{
	TextView txt_name;
	TextView txt_aname;
	Button btn,btn1;
	ImageView img;
	RelativeLayout rel;

}