package com.example.crop_crony;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class ProductInsertActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener
{
    Uri ImgUri;
    byte[] Img6;
    ImageView IV;
    Spinner SP2;
    DrawerLayout D;
    Button Btn, Btn6;
    int day, month, year;
    SessionManager SESSION;
    String UserName, UserRole;
    EditText ET, ET3, ET4, ET5, ET6;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_insert_activity);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        SESSION = new SessionManager(getApplicationContext());
        SESSION.isLoggedIn();
        HashMap<String, String> USER = SESSION.getUserDetails();
        UserName = USER.get(SessionManager.USER_NAME);
        UserRole = USER.get(SessionManager.USER_ROLE);

        CropCronyDatabase dataBase = new CropCronyDatabase(ProductInsertActivity.this);

        D = (DrawerLayout) findViewById(R.id.productDrawer);
        TextView TT = (TextView) findViewById(R.id.drawerTextView);
        TT.setText(UserName);
        ImageView II = (ImageView) findViewById(R.id.drawerImageView);
        Cursor getProfilePic = dataBase.GetDataById("UsersTable", "Username", UserName);
        while (getProfilePic.moveToNext())
        {
            II.setImageBitmap(UserClass.getImage(getProfilePic.getBlob(getProfilePic.getColumnIndex("UserImage"))));
        }


        ET = (EditText) findViewById(R.id.productEditText1);
        ET3 = (EditText) findViewById(R.id.productEditText3);
        ET4 = (EditText) findViewById(R.id.productEditText4);
        ET5 = (EditText) findViewById(R.id.productEditText5);
        ET6 = (EditText) findViewById(R.id.productEditText6);

        SP2 = (Spinner) findViewById(R.id.productSpinner2);

        Btn = (Button) findViewById(R.id.productButton);
        Btn6 = (Button) findViewById(R.id.productButton6);

        IV = (ImageView) findViewById(R.id.productImageView);

        ArrayList<String> items = new ArrayList<String>();
        Cursor ItemsData = dataBase.GetData("ItemsTable");
        if(ItemsData.getCount() == 0)
        {
            Toast.makeText(ProductInsertActivity.this, "No data found!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            items.add("Select Item...");
            while (ItemsData.moveToNext())
            {
                String ItemName = ItemsData.getString(ItemsData.getColumnIndex("ItemName"));

                items.add(ItemName);
            }
            ArrayAdapter<String> Items = new ArrayAdapter<String>(ProductInsertActivity.this, R.layout.support_simple_spinner_dropdown_item, items);
            SP2.setAdapter(Items);
        }

        ET5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(ProductInsertActivity.this,ProductInsertActivity.this, year, month, day);
                datePickerDialog.show();
            }
        });

        ET6.setText(UserName);

        Btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    InputStream IS =getContentResolver().openInputStream(ImgUri);

                    Img6 = UserClass.getImageByte(IS);
                    boolean Result = dataBase.Insert(ET.getText().toString(), SP2.getSelectedItem().toString(), ET3.getText().toString(), ET4.getText().toString(), ET6.getText().toString(), "Pending", ET5.getText().toString(), Img6);
                    if(Result)
                    {
                        Toast.makeText(ProductInsertActivity.this, "Insert Successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ProductInsertActivity.this, SellerMainActivity.class));
                    }
                    else
                    {
                        Toast.makeText(ProductInsertActivity.this, "Insertion Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        Btn6.setOnClickListener(new View.OnClickListener()
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
                    Img6 = UserClass.getImageByte(IS);
                    IV.setImageBitmap(UserClass.getImage(Img6));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
    {
        month ++;
        ET5.setText(dayOfMonth + "-" + month + "-" + year);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    public void UserProfile(View v)
    {
        if (this.getClass().getSimpleName().equals("UserProfileActivity"))
        {
            DrawerClose(D);
        }
        else
        {
            startActivity(new Intent(getApplicationContext(), SellerProfileActivity.class));
        }
    }

    public void MainProductView(View v)
    {
        if (UserRole.equals("Admin"))
        {
            if (this.getClass().getSimpleName().equals("AdminMainActivity"))
            {
                DrawerClose(D);
            }
            else
            {
                startActivity(new Intent(getApplicationContext(), AdminMainActivity.class));
            }
        }
        if (UserRole.equals("Buyer"))
        {
            if (this.getClass().getSimpleName().equals("BuyerMainActivity"))
            {
                DrawerClose(D);
            }
            else
            {
                startActivity(new Intent(getApplicationContext(), BuyerMainActivity.class));
            }
        }
        if (UserRole.equals("Seller"))
        {
            if (this.getClass().getSimpleName().equals("SellerMainActivity"))
            {
                DrawerClose(D);
            }
            else
            {
                startActivity(new Intent(getApplicationContext(), SellerMainActivity.class));
            }
        }
    }

    public void MyProductView(View v)
    {
        if (UserRole.equals("Buyer"))
        {
            if (this.getClass().getSimpleName().equals("BuyerBiddenProductActivity"))
            {
                DrawerClose(D);
            }
            else
            {
                startActivity(new Intent(getApplicationContext(), BuyerBiddenProductActivity.class));
            }
        }
        if (UserRole.equals("Seller"))
        {
            if (this.getClass().getSimpleName().equals("SellerMyProductsActivity"))
            {
                DrawerClose(D);
            }
            else
            {
                startActivity(new Intent(getApplicationContext(), SellerMyProductsActivity.class));
            }
        }
    }

    public void AuctionResult(View v)
    {
        if (this.getClass().getSimpleName().equals("AuctionResultActivity"))
        {
            DrawerClose(D);
        }
        else
        {
            startActivity(new Intent(getApplicationContext(), AuctionResultActivity.class));
        }
    }

    public void CreateAuctionProduct(View v)
    {
        if (this.getClass().getSimpleName().equals("ProductInsertActivity"))
        {
            DrawerClose(D);
        }
        else
        {
            startActivity(new Intent(getApplicationContext(), ProductInsertActivity.class));
        }
    }

    public void ProductRequests(View v)
    {
        if (UserRole.equals("Buyer"))
        {
            if (this.getClass().getSimpleName().equals("ProductRequestActivity"))
            {
                DrawerClose(D);
            }
            else
            {
                startActivity(new Intent(getApplicationContext(), ProductRequestActivity.class));
            }
        }
        if (UserRole.equals("Seller"))
        {
            if (this.getClass().getSimpleName().equals("ShowRequestsActivity"))
            {
                DrawerClose(D);
            }
            else
            {
                startActivity(new Intent(getApplicationContext(), ShowRequestsActivity.class));
            }
        }
    }

    public void Logout(View v)
    {
        SESSION.logoutUser();
    }

    public void DrawerOpen(View v)
    {
        D.openDrawer(GravityCompat.START);
    }

    public static void DrawerClose(DrawerLayout d)
    {
        d.closeDrawer(GravityCompat.START);
    }
}