package com.example.crop_crony;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SingleColumnInsertActivity extends AppCompatActivity
{
    DrawerLayout D;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_column_insert_activity);

        CropCronyDatabase dataBase = new CropCronyDatabase(SingleColumnInsertActivity.this);

        EditText ET = (EditText) findViewById(R.id.singleEditText1);
        Button Btn = (Button) findViewById(R.id.singleButton);
        D = (DrawerLayout) findViewById(R.id.drawer);

        Btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                boolean Result = dataBase.Insert("ItemsTable", ET.getText().toString());
                if(Result)
                {
                    Toast.makeText(SingleColumnInsertActivity.this, "Insert Successfully!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(SingleColumnInsertActivity.this, "Insertion Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void MainProductView(View v)
    {

    }

    public void AuctionResult(View v)
    {

    }

    public void CreateAuctionProduct(View v)
    {

    }

    public void ProductRequests(View v)
    {

    }

    public void Logout(View v)
    {

    }

//    public void DrawerOpen(View v)
//    {
//        D.openDrawer(GravityCompat.START);
//    }

    public static void DrawerClose(DrawerLayout d)
    {
       d.closeDrawer(GravityCompat.START);
    }
}