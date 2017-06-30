package com.zoptal.gaantori.Fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.soundcloud.android.crop.Crop;
import com.zoptal.gaantori.JsonClasses.Json_ChangePw;
import com.zoptal.gaantori.JsonClasses.Json_EditProfile;
import com.zoptal.gaantori.JsonClasses.ModelProfileData;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.main.MainActivity1;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class Fragment_EditProfile extends Fragment  implements View.OnClickListener {

    //Variable declration
    EditText ed_name2,ed_name44,ed_male;
    Button btn_updt,btn_cncl;
    ImageView img_profile,img_add,img_edit;
    TextView tv_name,tv_email;
    RelativeLayout rel_change;

    Bitmap bitmap;
    String username,dob,gender;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.activity_profile, container, false); //inflate the view

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        MainActivity1.mDrawerToggle.setDrawerIndicatorEnabled(false);
        MainActivity1.mDrawerToggle.setHomeAsUpIndicator(null);
        MainActivity1.img_srch.setVisibility(View.VISIBLE);


        MainActivity1.img_srch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity1.ed_srch.setVisibility(View.VISIBLE);

                MainActivity1.textToolHeader.setVisibility(View.INVISIBLE);
                MainActivity1.img_srch.setVisibility(View.INVISIBLE);

            }
        });

        MainActivity1.ed_srch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    Fragment_Srch fragment_srch = new Fragment_Srch();
                    FragmentManager fragmentManager1 =getFragmentManager();
                    fragmentManager1.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_srch).commit();
                    return true;
                }
                return false;
            }

        });

        MainActivity1.textToolHeader.setVisibility(View.VISIBLE);
        MainActivity1.ed_srch.setVisibility(View.INVISIBLE);
        ed_name2=(EditText)view.findViewById(R.id.name2);
        ed_name44=(EditText)view.findViewById(R.id.name44);
        ed_male=(EditText)view.findViewById(R.id.male);
        img_edit=(ImageView)view.findViewById(R.id.img_edit);
        img_profile=(ImageView)view.findViewById(R.id.user_img);
        img_add=(ImageView)view.findViewById(R.id.img_add);
        tv_name=(TextView)view.findViewById(R.id.name);
        tv_email=(TextView)view.findViewById(R.id.email);
        btn_updt=(Button)view.findViewById(R.id.btn_updt);
        btn_cncl=(Button)view.findViewById(R.id.btn_cncl);

        Bundle bundle = getArguments();
        ModelProfileData m=(ModelProfileData)bundle.getSerializable("profiledata");

        ed_name2.setText(m.getUsername());
        ed_name44.setText(m.getDob());
        ed_male.setText(m.getGender());
        tv_name.setText(m.getUsername());
        tv_email.setText(m.getEmail());


        MainActivity1.tv_bck.setVisibility(View.VISIBLE);

        rel_change=(RelativeLayout)view.findViewById(R.id.rel_change);

        MainActivity1.tv_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager1 = getFragmentManager();
                Fragment_Settings fragment_postad = new Fragment_Settings();
                fragmentManager1.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_postad).commit();

            }
        });

        ed_name2.setEnabled(false);
        ed_name44.setEnabled(false);
        ed_male.setEnabled(false);


        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Crop.pickImage(getActivity(),1);

            }
        });


        rel_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(getActivity(),android.R.style.Theme_Translucent);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialog);

                ImageView img_cross= (ImageView)dialog.findViewById(R.id.img_cross);
                final EditText  oldpw=(EditText)dialog.findViewById(R.id.oldpw);
                final EditText  newpw=(EditText)dialog.findViewById(R.id.newpw);
                final EditText  confirmpw=(EditText)dialog.findViewById(R.id.confirmpw);
                Button btn_submit=(Button)dialog.findViewById(R.id.submit);

                btn_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String opw=oldpw.getText().toString().trim();
                        String npw=newpw.getText().toString().trim();
                        String cpw=confirmpw.getText().toString().trim();

                        new Json_ChangePw(getActivity()).execute(Fragment_Home1.user_id,opw,npw,cpw);


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
        });

        img_edit.setOnClickListener(this);
        btn_updt.setOnClickListener(this);
        btn_cncl.setOnClickListener(this);

        return view;

    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG,20, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getActivity().getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(getActivity(),1);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode ==getActivity().RESULT_OK) {

            img_profile.setImageURI(null);

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),Crop.getOutput(result));
            } catch (IOException e) {
                e.printStackTrace();
            }
                   img_profile.setImageURI(Crop.getOutput(result));
        }
        else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(getActivity(), Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void onActivityResult1(int requestCode, int resultCode, Intent data) {

        try {
            if (requestCode ==1) {
                beginCrop(data.getData());

            }
            if (requestCode == 1) {

                handleCrop(resultCode, data);
            }

        } catch (Exception e) {

        }

    }



    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.img_edit:

                ed_name2.setEnabled(true);
                ed_name44.setEnabled(true);
                ed_male.setEnabled(true);

                break;

            case R.id.btn_updt:

                username=ed_name2.getText().toString().trim();
                dob=ed_name44.getText().toString().trim();
                gender=ed_male.getText().toString().trim();

                new Json_EditProfile(getActivity()).execute(Fragment_Home1.user_id,username,dob,gender);

                break;

            case R.id.btn_cncl:
                ed_name2.setEnabled(false);
                ed_name44.setEnabled(false);
                ed_male.setEnabled(false);

                break;

        }
    }


}