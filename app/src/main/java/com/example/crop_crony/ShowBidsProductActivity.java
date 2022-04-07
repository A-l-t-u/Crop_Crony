package com.example.crop_crony;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class ShowBidsProductActivity extends AppCompatActivity
{
    Button B;
    EditText ET;
    ImageView IV;
    DrawerLayout D;
    SessionManager SESSION;
    TextView TV, TV2, TV3, TV4, TV5;
    String ProductId, UserName, UserRole;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_bids_product_activity);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        SESSION = new SessionManager(getApplicationContext());
        SESSION.isLoggedIn();
        HashMap<String, String> USER = SESSION.getUserDetails();
        String UserName = USER.get(SessionManager.USER_NAME);
        UserRole = USER.get(SessionManager.USER_ROLE);

        CropCronyDatabase DB = new CropCronyDatabase(ShowBidsProductActivity.this);

        D = (DrawerLayout) findViewById(R.id.showbidDrawer);
        TextView TT = (TextView) findViewById(R.id.drawerTextView);
        TT.setText(UserName);
        ImageView II = (ImageView) findViewById(R.id.drawerImageView);
        Cursor getProfilePic = DB.GetDataById("UsersTable", "Username", UserName);
        while (getProfilePic.moveToNext())
        {
            II.setImageBitmap(UserClass.getImage(getProfilePic.getBlob(getProfilePic.getColumnIndex("UserImage"))));
        }

        B = (Button) findViewById(R.id.showbidButton);

        IV = (ImageView) findViewById(R.id.showbidImageView);

        ET = (EditText) findViewById(R.id.showbidEditText1);

        TV = (TextView) findViewById(R.id.showbidTextView1);
        TV2 = (TextView) findViewById(R.id.showbidTextView2);
        TV3 = (TextView) findViewById(R.id.showbidTextView4);
        TV4 = (TextView) findViewById(R.id.showbidTextView6);
        TV5 = (TextView) findViewById(R.id.showbidTextView8);

        ProductId = getIntent().getStringExtra("ProductId");

        Cursor ProductImage = DB.GetDataById("ProductsTable", "ProductId", ProductId);
        if(ProductImage.getCount() == 0)
        {
            Toast.makeText(ShowBidsProductActivity.this, "Product not found!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            while (ProductImage.moveToNext())
            {
                byte[] productImage = ProductImage.getBlob(ProductImage.getColumnIndex("ProductImage"));
                IV.setImageBitmap(UserClass.getImage(productImage));
            }
        }

        Cursor ProductName = DB.GetDataById("ProductsTable", "ProductId", ProductId);
        if(ProductName.getCount() == 0)
        {
            Toast.makeText(ShowBidsProductActivity.this, "Product not found!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            while (ProductName.moveToNext())
            {
                String productName = ProductName.getString(ProductName.getColumnIndex("ProductName"));
                TV.setText(productName);
            }
        }

        Cursor ProductSeller = DB.GetDataById("ProductsTable", "ProductId", ProductId);
        if(ProductSeller.getCount() == 0)
        {
            Toast.makeText(ShowBidsProductActivity.this, "Product not found!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            while (ProductSeller.moveToNext())
            {
                String productSeller = ProductSeller.getString(ProductSeller.getColumnIndex("ProductSeller"));
                TV2.setText(productSeller);
            }
        }

        Cursor ProductInitialBid = DB.GetDataById("ProductsTable", "ProductId", ProductId);
        if(ProductInitialBid.getCount() == 0)
        {
            Toast.makeText(ShowBidsProductActivity.this, "Product not found!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            while (ProductInitialBid.moveToNext())
            {
                String productInitialBid = ProductInitialBid.getString(ProductInitialBid.getColumnIndex("ProductInitialBid"));
                TV3.setText(productInitialBid);
            }
        }

        Cursor HighestBidding = DB.GetDataById("BiddingsTable", "BiddingProduct", TV.getText().toString());
        if(HighestBidding.getCount() == 0)
        {
            TV4.setText(TV3.getText().toString());
        }
        else
        {
            while (HighestBidding.moveToNext())
            {
                String highestBidding = HighestBidding.getString(HighestBidding.getColumnIndex("HighestBidding"));
                TV4.setText(highestBidding);
            }
        }

        Cursor Status = DB.GetDataById("AuctionedProductTable", "AuctionedProduct", TV.getText().toString());
        if(Status.getCount() == 0)
        {
            TV5.setText("Pending.");
        }
        else
        {
            TV5.setText("Sold.");
        }

        if (TV5.getText().toString().equals("Sold."))
        {
            ET.setEnabled(false);
            B.setEnabled(false);
        }

        B.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int value1 = Integer.parseInt(ET.getText().toString());
                int value2 = Integer.parseInt(TV4.getText().toString());
                if (!(ET.getText().toString().equals("")))
                {
                    if (value1 >= value2)
                    {
                        boolean SetBid = DB.Insert("BiddingsTable", TV.getText().toString(), ET.getText().toString(), UserName);
                        if (SetBid)
                        {
                            Toast.makeText(ShowBidsProductActivity.this, "Successfully Bidded!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ShowBidsProductActivity.this, BuyerMainActivity.class));
                        }
                        else
                        {
                            Toast.makeText(ShowBidsProductActivity.this, "Bidding Failed, Please Try Again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(ShowBidsProductActivity.this, "You must bid higher than the Highest bid, Thank you.", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(ShowBidsProductActivity.this, "Enter you bidding price, Thank you.", Toast.LENGTH_SHORT).show();
                }
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
            startActivity(new Intent(getApplicationContext(), AuctionedProductActivity.class));
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