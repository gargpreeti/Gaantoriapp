package com.zoptal.gaantori.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.main.MainActivity1;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

	//Varibale declartion
	//initialize view's

	LayoutInflater inflater;
	List<String> name;
	List<String> image;
	Context context;
	MainActivity1 activity1;

	public CustomAdapter(Context context,  List<String> name,List<String> image) {

		this.name = name;
		this.image = image;
		this.context = context;
		//this.img=img;
		activity1 = (MainActivity1) context;
		inflater = LayoutInflater.from(context);  // inflate the layout

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
	public View getView(final int position, View v, final ViewGroup parent) {
		ViewHolder holder=new ViewHolder();

		if(v==null){

		v = inflater.inflate(R.layout.custom_item_view, null);  //inflate the layout
		holder.txt_name = (TextView) v.findViewById(R.id.text);
		holder.txt_dlt = (TextView) v.findViewById(R.id.text1);
		holder.img = (ImageView) v.findViewById(R.id.image);
		holder.rel=(RelativeLayout)v.findViewById(R.id.rel);
		v.setTag(holder);
		}
		else
		{
			holder =(ViewHolder)v.getTag();
		}
		
		holder.txt_name.setText(name.get(position));
		//holder.img.setImageResource(Integer.parseInt(image.get(position)));
	    Picasso.with(context).load(image.get(position)).into(holder.img);
		holder.txt_dlt.setOnClickListener(new View.OnClickListener() {

		   @Override
	public void onClick(View v) {
		//removeViewAt(position);
      Log.e("clicked on item--","item");

			   activity1.sample.remove(position);
			   activity1.sample1.remove(position);
			   activity1.al_topsong.remove(position);
			   notifyDataSetChanged();


	}
          });
		return v;

	}
	// View lookup cache
	class ViewHolder{
		TextView txt_name;
		TextView txt_dlt;
		ImageView img;
		public RelativeLayout rel;

	}



}
