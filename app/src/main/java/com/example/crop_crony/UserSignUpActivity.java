package com.example.crop_crony;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;

public class UserSignUpActivity extends AppCompatActivity
{
    Uri ImgUri;
    byte[] Img10;
    String Role;
    Spinner SP7;
    ImageView IV;
    Button Btn, Btn10;
    RadioButton RBtn, RBtn2;
    EditText ET, ET2, ET3, ET4, ET5, ET6, ET8;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_sign_up_activity);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        CropCronyDatabase dataBase = new CropCronyDatabase(UserSignUpActivity.this);

        ET = (EditText) findViewById(R.id.userEditText1);
        ET2 = (EditText) findViewById(R.id.userEditText2);
        ET3 = (EditText) findViewById(R.id.userEditText3);
        ET4 = (EditText) findViewById(R.id.userEditText4);
        ET5 = (EditText) findViewById(R.id.userEditText5);
        ET6 = (EditText) findViewById(R.id.userEditText6);
        ET8 = (EditText) findViewById(R.id.userEditText8);

        SP7 = (Spinner) findViewById(R.id.userSpinner7);

        Btn = (Button) findViewById(R.id.userButton);
        Btn10 = (Button) findViewById(R.id.userButton10);

        RBtn = (RadioButton) findViewById(R.id.userRadioButton1);
        RBtn2 = (RadioButton) findViewById(R.id.userRadioButton2);

        IV = (ImageView) findViewById(R.id.userImageView);

        ArrayList<String> cities = new ArrayList<String>();
        Cursor CitiesData = dataBase.GetData("CitiesTable");
        if(CitiesData.getCount() == 0)
        {
            Toast.makeText(UserSignUpActivity.this, "No data found!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            cities.add("Select City...");
            while (CitiesData.moveToNext()) {
                String CityName = CitiesData.getString(CitiesData.getColumnIndex("CityName"));

                cities.add(CityName);
            }
            ArrayAdapter<String> Cities = new ArrayAdapter<String>(UserSignUpActivity.this, R.layout.support_simple_spinner_dropdown_item, cities);
            SP7.setAdapter(Cities);
        }

        Cursor RolesData1 = dataBase.GetDataById("RolesTable", "RoleId", "2");
        while (RolesData1.moveToNext())
        {
            String RoleName1 = RolesData1.getString(RolesData1.getColumnIndex("RoleName"));
            RBtn.setText(RoleName1);
        }
        Cursor RolesData2 = dataBase.GetDataById("RolesTable", "RoleId", "3");
        while (RolesData2.moveToNext())
        {
            String RoleName2 = RolesData2.getString(RolesData2.getColumnIndex("RoleName"));
            RBtn2.setText(RoleName2);
        }

        ET5.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                String str =  s.toString();
                if(s.length() == 5 || s.length() == 13)
                {
                    str += "-";
                    ET5.setText(str);
                    ET5.setSelection(str.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        Btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (RBtn.isChecked()) { Role = RBtn.getText().toString();}
                if (RBtn2.isChecked()) { Role = RBtn2.getText().toString();}

                try
                {
                    InputStream IS =getContentResolver().openInputStream(ImgUri);
                    Img10 = UserClass.getImageByte(IS);

                    boolean Result = dataBase.Insert(ET.getText().toString(), ET2.getText().toString(), ET3.getText().toString(), ET4.getText().toString(), ET5.getText().toString(), ET6.getText().toString(), SP7.getSelectedItem().toString(), ET8.getText().toString(), Role, Img10);
                    if(Result)
                    {
                        Toast.makeText(UserSignUpActivity.this, "Insert Successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UserSignUpActivity.this, LoginActivity.class));
                    }
                    else
                    {
                        Toast.makeText(UserSignUpActivity.this, "Insertion Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        Btn10.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent Select = new Intent();
                Select.setType("image/*");
                Select.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(Select, "Select Picture"), 200);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200)
        {
            if (resultCode == RESULT_OK)
            {
                ImgUri = data.getData();
                try
                {
                    InputStream IS =getContentResolver().openInputStream(ImgUri);

                    Img10 = UserClass.getImageByte(IS);
                    IV.setImageBitmap(UserClass.getImage(Img10));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus)
        {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}