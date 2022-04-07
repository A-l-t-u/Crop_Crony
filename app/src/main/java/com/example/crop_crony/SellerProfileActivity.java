package com.example.crop_crony;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.HashMap;

public class SellerProfileActivity extends AppCompatActivity
{
    Button B;
    Uri ImgUri;
    byte[] Img;
    ImageView IV;
    DrawerLayout D;
    SessionManager SESSION;
    String Id, Password, UserName, UserRole;
    EditText ET, ET2, ET3, ET4, ET5, ET6, ET7;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_profile_activity);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        CropCronyDatabase dataBase = new CropCronyDatabase(SellerProfileActivity.this);

        SESSION = new SessionManager(getApplicationContext());
        SESSION.isLoggedIn();
        HashMap<String, String> USER = SESSION.getUserDetails();
        UserName = USER.get(SessionManager.USER_NAME);
        UserRole = USER.get(SessionManager.USER_ROLE);

        D = (DrawerLayout) findViewById(R.id.userProfileDrawer);
        TextView TT = (TextView) findViewById(R.id.drawerTextView);
        TT.setText(UserName);
        ImageView II = (ImageView) findViewById(R.id.drawerImageView);
        Cursor getProfilePic = dataBase.GetDataById("UsersTable", "Username", UserName);
        while (getProfilePic.moveToNext())
        {
            II.setImageBitmap(UserClass.getImage(getProfilePic.getBlob(getProfilePic.getColumnIndex("UserImage"))));
        }

        B = (Button) findViewById(R.id.userProfileButton);

        IV = (ImageView) findViewById(R.id.userProfileImageView);

        ET = (EditText) findViewById(R.id.userProfileEditText1);
        ET2 = (EditText) findViewById(R.id.userProfileEditText2);
        ET3 = (EditText) findViewById(R.id.userProfileEditText3);
        ET4 = (EditText) findViewById(R.id.userProfileEditText4);
        ET5 = (EditText) findViewById(R.id.userProfileEditText5);
        ET6 = (EditText) findViewById(R.id.userProfileEditText6);
        ET7 = (EditText) findViewById(R.id.userProfileEditText7);

        Cursor getUserData = dataBase.GetDataById("UsersTable", "Username", UserName);
        while (getUserData.moveToNext())
        {
            Id = getUserData.getString(getUserData.getColumnIndex("UserId"));
            ET.setText(getUserData.getString(getUserData.getColumnIndex("UserFullName")));
            ET2.setText(getUserData.getString(getUserData.getColumnIndex("Username")));
            ET3.setText(getUserData.getString(getUserData.getColumnIndex("UserEmail")));
            Password = getUserData.getString(getUserData.getColumnIndex("UserPassword"));
            ET4.setText(getUserData.getString(getUserData.getColumnIndex("UserCNICNumber")));
            ET5.setText(getUserData.getString(getUserData.getColumnIndex("UserAddress")));
            ET6.setText(getUserData.getString(getUserData.getColumnIndex("UserCity")));
            ET7.setText(getUserData.getString(getUserData.getColumnIndex("UserPhoneNumber")));
            IV.setImageBitmap(UserClass.getImage(getUserData.getBlob(getUserData.getColumnIndex("UserImage"))));
        }

        B.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (B.getText().toString().equals("Edit."))
                {
                    ET.setEnabled(true);
                    ET2.setEnabled(true);
                    ET3.setEnabled(true);
                    ET4.setEnabled(true);
                    ET5.setEnabled(true);
                    ET6.setEnabled(true);
                    ET7.setEnabled(true);
                    IV.setOnClickListener(new View.OnClickListener()
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
                    B.setText("Update.");
                }
                else
                {
                    boolean Result = dataBase.Update("UsersTable", Id, ET.getText().toString(), ET2.getText().toString(), ET3.getText().toString(), Password, ET4.getText().toString(), ET5.getText().toString(), ET6.getText().toString(), ET7.getText().toString(), UserRole, Img);
                    if (Result)
                    {
                        Toast.makeText(SellerProfileActivity.this, "Updated.", Toast.LENGTH_SHORT).show();
                        ET.setEnabled(false);
                        ET2.setEnabled(false);
                        ET3.setEnabled(false);
                        ET4.setEnabled(false);
                        ET5.setEnabled(false);
                        ET6.setEnabled(false);
                        ET7.setEnabled(false);
                        B.setText("Edit.");
                    }
                    else
                    {
                        Toast.makeText(SellerProfileActivity.this, "Update Failed, Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
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

                    Img = UserClass.getImageByte(IS);
                    IV.setImageBitmap(UserClass.getImage(Img));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
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