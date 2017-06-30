package com.zoptal.gaantori.Adapter;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.zoptal.gaantori.Fragment.Fragment_Home1;
import com.zoptal.gaantori.Fragment.Fragment_Srch;
import com.zoptal.gaantori.JsonClasses.Json_Srch;
import com.zoptal.gaantori.JsonClasses.Model_Srch;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.main.MainActivity1;

public class Adapter_SrchSong extends BaseAdapter {

	//Varibale declartion
	//initialize view's
	LayoutInflater inflater;
	Context context;
	Model_Srch m;
	public static String id, albumname, artistname, img;
	MainActivity1 activity1;

	public Adapter_SrchSong(Context c, Model_Srch m) {

		context = c;
		this.m = m;
		activity1 = (MainActivity1) context;
		inflater = LayoutInflater.from(context); // inflate the layout
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub

		if (m.getAl_srchsong().size() == 0) {

			Fragment_Srch.tv_song.setVisibility(View.GONE);

		}
		return m.getAl_srchsong().size();


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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		final HolderSrchSong holder = new HolderSrchSong();  // view lookup cache stored in tag
		convertView = inflater.inflate(R.layout.customview_songsrch, null); // inflate the layout

		// get the TextView and then set the text (item name) and tag (item ID) values
  	holder.txt_name = (TextView) convertView.findViewById(R.id.name);
			holder.img = (ImageView) convertView.findViewById(R.id.img);
		holder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.relativeLayout);
		holder.img_downloaded = (ImageView) convertView.findViewById(R.id.img_downloaded);

		// object item based on the position
		// Populate the data from the data object via the viewHolder object
		// into the template view.
		holder.txt_name.setText(m.getAl_srchsong().get(position).getTitle());
			Picasso.with(context).load(m.getAl_srchsong().get(position).getImage()).into(holder.img);


		String downloadedvalue=m.getAl_srchsong().get(position).getDownloaded().toString().trim();
		if(downloadedvalue.equals("1")){

			holder.img_downloaded.setVisibility(View.VISIBLE);
		}
		else{
			holder.img_downloaded.setVisibility(View.GONE);

		}


		holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (Fragment_Home1.utype1.equals("Premium")) {

				} else {

					final Toast toast = Toast.makeText(context, "Free Users/Guest Users can only play Shuffle,Please Register", Toast.LENGTH_SHORT);
					toast.show();
					new CountDownTimer(1000, 500) {
						public void onTick(long millisUntilFinished) {
							toast.show();
						}

						public void onFinish() {
							toast.cancel();
						}
					}.start();


				}
				activity1.linear_small.setVisibility(View.VISIBLE);
				activity1.setListOnPlayer(Json_Srch.model_srch.getAl_srchsong(), position, false, Adapter_SrchSong.this);
			}
		});

		return convertView;

	}

}
// View lookup cache
class HolderSrchSong
{
	TextView txt_name;
	ImageView img;
	RelativeLayout relativeLayout;
	ImageView img_downloaded;

}