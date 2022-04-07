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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class SellerMainActivity extends AppCompatActivity
{
    DrawerLayout D;
    SessionManager SESSION;
    String UserName, UserRole;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_main_activity);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        SESSION = new SessionManager(getApplicationContext());
        SESSION.isLoggedIn();
        HashMap<String, String> USER = SESSION.getUserDetails();
        UserName = USER.get(SessionManager.USER_NAME);
        UserRole = USER.get(SessionManager.USER_ROLE);

        CropCronyDatabase DB = new CropCronyDatabase(SellerMainActivity.this);

        Button B = (Button) findViewById(R.id.sellerButton);

        D = (DrawerLayout) findViewById(R.id.sellerDrawer);
        TextView TT = (TextView) findViewById(R.id.drawerTextView);
        TT.setText(UserName);
        ImageView II = (ImageView) findViewById(R.id.drawerImageView);
        Cursor getProfilePic = DB.GetDataById("UsersTable", "Username", UserName);
        while (getProfilePic.moveToNext())
        {
            II.setImageBitmap(UserClass.getImage(getProfilePic.getBlob(getProfilePic.getColumnIndex("UserImage"))));
        }

        RecyclerView RV = (RecyclerView) findViewById(R.id.sellerRecyclerView);

        LinearLayoutManager LLM = new LinearLayoutManager(SellerMainActivity.this);
        RV.setLayoutManager(LLM);
        ArrayList<ProductsClass> ProductData = new ArrayList<ProductsClass>();
        ProductData.clear();
        Cursor Products = DB.GetData("ProductsTable");
        if (Products.getCount() == 0) {
            Toast.makeText(SellerMainActivity.this, "Data not found!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            while (Products.moveToNext())
            {
                String ProductStatus = Products.getString(Products.getColumnIndex("ProductStatus"));
                if (!(ProductStatus.equals("Sold")))
                {
                    String ProductId = Products.getString(Products.getColumnIndex("ProductId"));
                    String ProductName = Products.getString(Products.getColumnIndex("ProductName"));
                    String ProductItem = Products.getString(Products.getColumnIndex("ProductItem"));
                    String ProductDescription = Products.getString(Products.getColumnIndex("ProductDescription"));
                    String ProductInitialBid = Products.getString(Products.getColumnIndex("ProductInitialBid"));
                    String ProductSeller = Products.getString(Products.getColumnIndex("ProductSeller"));
                    byte[] ProductImage = Products.getBlob(Products.getColumnIndex("ProductImage"));
                    ProductData.add(new ProductsClass(ProductId, ProductName, ProductItem, ProductDescription, ProductInitialBid, ProductSeller, ProductImage));
                }
            }
        }
        SellerMainAdapter productData = new SellerMainAdapter(SellerMainActivity.this, ProductData);
        RV.setAdapter(productData);

        B.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(SellerMainActivity.this, ProductInsertActivity.class));
            }
        });
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