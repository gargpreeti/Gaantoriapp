package com.zoptal.gaantori.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.zoptal.gaantori.JsonClasses.Json_SignUp;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.common.NetworkConnection;
import com.zoptal.gaantori.main.LoginSignup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fragment_Signup extends Fragment  implements View.OnClickListener {

    public EditText ed_email,ed_pw1,ed_pw11,ed_username,ed_dob,ed_lname,ed_fname;
    private ImageView img_bck;
    private Button btnsignup;
    private RadioGroup radiogrup;
    private RadioButton radiobtn1,radiobtn2;
    private  Spinner dropdown;
    RelativeLayout rel_date;

    //DatePicker
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog fromDatePickerDialog;

    private String radiovalue;
    public  static String type_value;
   View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

       view = inflater.inflate(R.layout.activity_signup, container, false); //inflate views

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        radiogrup=(RadioGroup)view.findViewById(R.id.radioGroup);
        radiobtn1=(RadioButton)view.findViewById(R.id.radioButton);
        radiobtn2=(RadioButton)view.findViewById(R.id.radioButton2);
        rel_date=(RelativeLayout)view.findViewById(R.id.rel_date);
        dropdown = (Spinner)view.findViewById(R.id.gender1);


        //To find view ids
        ed_email=(EditText)view.findViewById(R.id.email);
        ed_pw1=(EditText)view.findViewById(R.id.pw1);
        ed_pw11=(EditText)view.findViewById(R.id.pw11);
        ed_username=(EditText)view.findViewById(R.id.username);
        ed_dob=(EditText)view.findViewById(R.id.dob);
        ed_fname=(EditText)view.findViewById(R.id.fname);
        ed_lname=(EditText)view.findViewById(R.id.lname);

        img_bck=(ImageView)view.findViewById(R.id.bck);
        btnsignup=(Button)view.findViewById(R.id.btnsignup);


        img_bck.setOnClickListener(this);
        btnsignup.setOnClickListener(this);

        ed_dob.setOnClickListener(this);

        rel_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                fromDatePickerDialog.show();
            }
        });




        dateFormatter = new SimpleDateFormat("yyyy-MM-dd" , Locale.US);
        setDateTimeField();
        radiogrup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.radioButton:

                        int selectedId = radiogrup.getCheckedRadioButtonId();
                        radiobtn1 = (RadioButton) view.findViewById(selectedId);
                        radiovalue= String.valueOf(radiobtn1.getText());
                                    type_value= String.valueOf(radiobtn1.getText());
                        break;

                    case R.id.radioButton2:

                        int selectedId2 = radiogrup.getCheckedRadioButtonId();
                        radiobtn2 = (RadioButton) view.findViewById(selectedId2);
                        radiovalue= String.valueOf(radiobtn2.getText());

                        type_value= String.valueOf(radiobtn2.getText());

                        break;

                }

            }
        });

        String[] items = new String[]{"Male","Female"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                ((TextView) dropdown.getSelectedView()).setTextColor(getResources().getColor(R.color.white));
                     }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        return view;

    }

    private void setDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();

        fromDatePickerDialog = new DatePickerDialog(getActivity(),AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, new DatePickerDialog.OnDateSetListener() {


            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();

                newDate.set(year, monthOfYear, dayOfMonth);
                ed_dob.setText(dateFormatter.format(newDate.getTime()));

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.btnsignup:

              signup();

                break;

            case R.id.bck:

                Intent intent_bck=new Intent(getActivity(),LoginSignup.class);
                startActivity(intent_bck);

                break;

            case R.id.dob:
           final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                fromDatePickerDialog.show();

        }
          }

// For user signup
    public void signup(){

        String email=ed_email.getText().toString().trim().toLowerCase();
        String pw= ed_pw1.getText().toString().trim().toLowerCase();
        String pw1= ed_pw11.getText().toString().trim().toLowerCase();
        String username=ed_username.getText().toString().trim().toLowerCase();
        String dob=ed_dob.getText().toString().trim().toLowerCase();
        String gender=dropdown.getSelectedItem().toString().trim().toLowerCase();
        String fname=ed_fname.getText().toString().trim().toLowerCase();
        String lname=ed_lname.getText().toString().trim().toLowerCase();


        if(username.equals("") ) {
            ed_username.setError("Username should not be empty");
          return;
        }
        if(email.equals("")) {
            ed_email.setError("Email should not be empty");
            return;
        }
        if(pw.equals("")) {
            ed_pw1.setError("Password should not be empty");
            return;
        }
        if(pw1.equals("")) {
            ed_pw11.setError("Password should not be empty");
            return;
        }

        if(!pw1.equals(pw)){
            ed_pw11.setError("Password and confirm password should be same");
            return;
        }
        if(dob.equals("")) {
            ed_dob.setError("DOB should not be empty");
            return;
        }
       if(fname.equals("")){

           ed_fname.setError("First name should not be empty");
           return;
       }
        if(lname.equals("")){

            ed_lname.setError("Last name should not be empty");
            return;
        }

        if (!isValidEmail(email)) {
            ed_email.setError("Invalid Email");

        }


        else {

            if (NetworkConnection.isConnectedToInternet(getActivity())) {
                new Json_SignUp(getActivity()).execute(username,pw,fname,lname,email,gender);

            }
            else {
                Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();
                return;

            }

        }



    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    // validating password with retype password
    private boolean isValidPassword(String password) {
        if (password != null && password.length() > 7) {
            return true;
        }
        return false;
    }




}