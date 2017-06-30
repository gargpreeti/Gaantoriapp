package com.zoptal.gaantori.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.zoptal.gaantori.Fragment.Fragment_Home1;
import com.zoptal.gaantori.JsonClasses.BrowseArtist;
import com.zoptal.gaantori.JsonClasses.Json_FollowUnfollow;
import com.zoptal.gaantori.JsonClasses.Json_artistdetailAll;
import com.zoptal.gaantori.JsonClasses.Model_BrowseArtist;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.common.NetworkConnection;

import java.util.ArrayList;


public class Adapter_AritistBrowse extends BaseAdapter {

	//Varibale declartion
	//initialize view's
	LayoutInflater inflater;
	Context context ;
	Model_BrowseArtist m;
	private ArrayList<BrowseArtist> al_browseartist = new ArrayList<BrowseArtist>();
	public static String id,artistname,img,fcount,fstat;

	public Adapter_AritistBrowse(Context context,Model_BrowseArtist m) {
		this.context=context;
		this.m = m;
		this.al_browseartist = new ArrayList<>();
		al_browseartist.addAll(m.getAl_browseartist());
		inflater = LayoutInflater.from(context);  // inflate the layout

			}

	// Filter Class
	public void filter(String charText) {

		//charText = charText.toLowerCase(Locale.getDefault());
		//  m.getAl_newrelease().clear();
		al_browseartist = new ArrayList<>();

		if(charText.trim().equals("All")){
			al_browseartist.addAll(m.getAl_browseartist());
		}
		if (charText.trim().isEmpty()) {
			al_browseartist.addAll(m.getAl_browseartist());
		} else {

			for (int i = 0; i < m.getAl_browseartist().size(); i++) {
				if (m.getAl_browseartist().get(i).getName().startsWith(charText)) {
					al_browseartist.add(m.getAl_browseartist().get(i));

				}
			}
		}

		notifyDataSetChanged();
	}


	public void setAddList(ArrayList<BrowseArtist> list)
	{

		m.getAl_browseartist().addAll(list);
		this.al_browseartist.addAll(list);
		notifyDataSetChanged();


	}
	public ArrayList<BrowseArtist> getCurrentList()
	{
		return al_browseartist;
	}



	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		//returning total listsize
		return al_browseartist.size();
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

		final HolderAritist holder=new HolderAritist();  // view lookup cache stored in tag
		convertView=inflater.inflate(R.layout.customview_aritist_browse, null); // inflate the layout


		// get the TextView and then set the text (item name) and tag (item ID) values
		holder.txt_name=(TextView) convertView.findViewById(R.id.name);
		holder.txt_follow=(TextView) convertView.findViewById(R.id.follow);
		holder.txt_btn=(Button)convertView.findViewById(R.id.btn);
		holder.txt_btn1=(Button)convertView.findViewById(R.id.btn1);
		holder.img=(ImageView) convertView.findViewById(R.id.img);


		// object item based on the position
		// Populate the data from the data object via the viewHolder object
		// into the template view.
		holder.txt_name.setText("    "+al_browseartist.get(position).getName());
		holder.txt_follow.setText(al_browseartist.get(position).getFcount());
			Picasso.with(context).load(al_browseartist.get(position).getImage()).into(holder.img);

		holder.txt_btn.setTextColor(Color.parseColor("#ffffff"));
		holder.txt_btn1.setTextColor(Color.parseColor("#ffffff"));

             if(al_browseartist.get(position).getFol().equals("follow")){

				 holder.txt_btn.setVisibility(View.VISIBLE);
				 holder.txt_btn1.setVisibility(View.INVISIBLE);
			 }
		else {

				 holder.txt_btn.setVisibility(View.INVISIBLE);
				 holder.txt_btn1.setVisibility(View.VISIBLE);
			 }

		//check condition for userid
		if(Fragment_Home1.user_id.equals("null")) {
			holder.txt_btn1.setEnabled(false);
			holder.txt_btn.setEnabled(false);

			holder.txt_btn.setTextColor(Color.parseColor("#ffffff")); //set the button text color
			holder.txt_btn1.setTextColor(Color.parseColor("#ffffff"));//set the button text color


		}
		else{
			holder.txt_btn1.setEnabled(true);
			holder.txt_btn.setEnabled(true);

			holder.txt_btn.setTextColor(Color.parseColor("#ffffff"));//set the button text color
			holder.txt_btn1.setTextColor(Color.parseColor("#ffffff"));//set the button text color

		}
		//click to unfollow  to artist
		holder.txt_btn1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				holder.txt_btn1.setVisibility(View.INVISIBLE);
				holder.txt_btn.setVisibility(View.VISIBLE);


				if (NetworkConnection.isConnectedToInternet(context)) {
					new Json_FollowUnfollow(context).execute(Fragment_Home1.user_id,al_browseartist.get(position).getId(),"0");
					int previouscount= Integer.parseInt(holder.txt_follow.getText().toString());
						int newvalue=1;
					newvalue=previouscount-newvalue;
					if(newvalue<0) {
						holder.txt_follow.setText("0");
					}
					else {
						holder.txt_follow.setText(String.valueOf(newvalue));
					}

				}
				else {
					//just toast it
					Toast.makeText(context,"Please Check your internet connection", Toast.LENGTH_SHORT).show();


				}


			}
		});

		//click to follow  to artist
		holder.txt_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				holder.txt_btn.setVisibility(View.INVISIBLE);
				holder.txt_btn1.setVisibility(View.VISIBLE);



				if (NetworkConnection.isConnectedToInternet(context)) {

					// follow artist
					new Json_FollowUnfollow(context).execute(Fragment_Home1.user_id,al_browseartist.get(position).getId(),"1");
					int previouscount= Integer.parseInt(holder.txt_follow.getText().toString());
								int newvalue=1;
					newvalue+=previouscount;
						holder.txt_follow.setText(String.valueOf(newvalue));

				}
				else {
					//just toast it
					Toast.makeText(context,"Please Check your internet connection", Toast.LENGTH_SHORT).show();


				}




			}
		});

         //clicklistener to open artist detail

		holder.img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				id=al_browseartist.get(position).getId();
				artistname=al_browseartist.get(position).getName();
				img=al_browseartist.get(position).getImage();
				fcount=al_browseartist.get(position).getFcount();
				fstat=al_browseartist.get(position).getFol();

						if (NetworkConnection.isConnectedToInternet(context)) {

					new Json_artistdetailAll(context).execute(Fragment_Home1.user_id,al_browseartist.get(position).getId());


				}
				else {
							//just toast it
					Toast.makeText(context,"Please Check your internet connection", Toast.LENGTH_SHORT).show();


				}




			}
		});
		// Return the completed view to render on screen
		return convertView;

	}

}
// View lookup cache
class HolderAritist
{
	TextView txt_name;
	TextView txt_follow;
	Button txt_btn;
	Button txt_btn1;
	ImageView img;
}