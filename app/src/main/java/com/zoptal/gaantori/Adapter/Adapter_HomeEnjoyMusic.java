package com.zoptal.gaantori.Adapter;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zoptal.gaantori.Fragment.Fragment_Browse;
import com.zoptal.gaantori.Fragment.Fragment_Discover;
import com.zoptal.gaantori.Fragment.Fragment_TopLists;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.main.MainActivity1;


public class Adapter_HomeEnjoyMusic extends BaseAdapter {

	LayoutInflater inflater;
	Context context ;
	String singer[];
	int img[];

	public Adapter_HomeEnjoyMusic(Context context, String singer[], int img[]) {

		this.context=context;
		this.singer=singer;
		this.img=img;
		inflater=LayoutInflater.from(context);
			}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return img.length;
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

		final HolderHomeRelease holder=new HolderHomeRelease();   // view lookup cache stored
		convertView=inflater.inflate(R.layout.customview_enjoy_music, null);  // inflate the layout

		// get the TextView and then set the text (item name) and tag (item ID) values
		holder.txt_singer=(TextView) convertView.findViewById(R.id.singer);
		holder.img=(ImageView) convertView.findViewById(R.id.img1);

// object item based on the position
		// Populate the data from the data object via the viewHolder object
		// into the template view.
		try {

			holder.txt_singer.setText(singer[position]);
			holder.img.setBackgroundResource(img[position]);
		}
		catch (ArrayIndexOutOfBoundsException e){}



		holder.img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (position == 0) {


					Fragment fragment_toplist = new Fragment_TopLists();
					FragmentManager fragmentManager2 =((Activity) context). getFragmentManager();
					fragmentManager2.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_toplist).commit();
					MainActivity1.Drawer.closeDrawer(Gravity.LEFT);


				}
				if (position == 1) {

					Fragment fragment_browse = new Fragment_Browse();
					FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
					fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_browse).commit();
					MainActivity1.Drawer.closeDrawer(Gravity.LEFT);

				}
				if (position == 2) {

					Fragment_Discover fragment_discover = new Fragment_Discover();
					FragmentManager fragmentManager5 = ((Activity) context).getFragmentManager();
					fragmentManager5.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_discover).commit();
					MainActivity1.Drawer.closeDrawer(Gravity.LEFT);


				}
			}
		});




		// Return the completed view to render on screen
		return convertView;

	}

}
