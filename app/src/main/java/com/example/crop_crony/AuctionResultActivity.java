package com.example.crop_crony;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class AuctionResultActivity extends AppCompatActivity
{
    DrawerLayout D;
    SessionManager SESSION;
    String UserName, UserRole;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auction_result_activity);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        SESSION = new SessionManager(getApplicationContext());
        SESSION.isLoggedIn();
        HashMap<String, String> USER = SESSION.getUserDetails();
        UserName = USER.get(SessionManager.USER_NAME);
        UserRole = USER.get(SessionManager.USER_ROLE);

        CropCronyDatabase DB = new CropCronyDatabase(AuctionResultActivity.this);

        D = (DrawerLayout) findViewById(R.id.auctionDrawer);
        TextView TT = (TextView) findViewById(R.id.drawerTextView);
        TT.setText(UserName);
        ImageView II = (ImageView) findViewById(R.id.drawerImageView);
        Cursor getProfilePic = DB.GetDataById("UsersTable", "Username", UserName);
        while (getProfilePic.moveToNext())
        {
            II.setImageBitmap(UserClass.getImage(getProfilePic.getBlob(getProfilePic.getColumnIndex("UserImage"))));
        }

        RecyclerView RV = (RecyclerView) findViewById(R.id.auctionRecyclerView);

        LinearLayoutManager LLM = new LinearLayoutManager(AuctionResultActivity.this);
        RV.setLayoutManager(LLM);
        ArrayList<ProductsClass> ProductData = new ArrayList<ProductsClass>();
        ProductData.clear();
        Cursor productsData= DB.GetDataWithJoin();
        if (productsData.getCount() == 0)
        {
            Toast.makeText(AuctionResultActivity.this, "Data not found!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            while (productsData.moveToNext())
            {
                String ProductStatus = productsData.getString(productsData.getColumnIndex("ProductStatus"));
                if (!(ProductStatus.equals("Pending")))
                {
                    String ProductId = productsData.getString(productsData.getColumnIndex("ProductId"));
                    String ProductName = productsData.getString(productsData.getColumnIndex("ProductName"));
                    String ProductItem = productsData.getString(productsData.getColumnIndex("ProductItem"));
                    String ProductDescription = productsData.getString(productsData.getColumnIndex("ProductDescription"));
                    String ProductInitialBid = productsData.getString(productsData.getColumnIndex("ProductInitialBid"));
                    String ProductSeller = productsData.getString(productsData.getColumnIndex("ProductSeller"));
                    String username = productsData.getString(productsData.getColumnIndex("HighestBidder"));
                    byte[] ProductImage = productsData.getBlob(productsData.getColumnIndex("ProductImage"));
                    ProductData.add(new ProductsClass(ProductId, ProductName, ProductItem, ProductDescription, ProductInitialBid, ProductSeller, ProductImage,username));
                }
            }
        }
        BuyerMainAdapter productData = new BuyerMainAdapter(AuctionResultActivity.this, ProductData);
        RV.setAdapter(productData);
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    public void MainProductView(View v)
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
        if (this.getClass().getSimpleName().equals("ShowRequestsActivity"))
        {
            DrawerClose(D);
        }
        else
        {
            startActivity(new Intent(getApplicationContext(), ShowRequestsActivity.class));
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