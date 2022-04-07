package com.example.crop_crony;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class BuyerMainAdapter extends RecyclerView.Adapter
{
    SessionManager SESSION;
    Context context;
    ArrayList<ProductsClass> productData;
    String UserName;

    public BuyerMainAdapter(Context context, ArrayList<ProductsClass> productData)
    {
        this.context = context;
        this.productData = productData;
        SESSION = new SessionManager(context);
        HashMap<String, String> USER = SESSION.getUserDetails();
        UserName = USER.get(SessionManager.USER_NAME);
    }

    public class viewHolder extends RecyclerView.ViewHolder
    {
        ImageView IV1, IV2;
        Button B;
        TextView TV2, TV3, TV4,TV5;

        public viewHolder(@NonNull View itemView)
        {
            super(itemView);
            IV1 = (ImageView) itemView.findViewById(R.id.productViewImageView1);
            IV2 = (ImageView) itemView.findViewById(R.id.productViewImageView2);
            TV2 = (TextView) itemView.findViewById(R.id.productViewTextView2);
            TV3 = (TextView) itemView.findViewById(R.id.productViewTextView3);
            TV4 = (TextView) itemView.findViewById(R.id.productViewTextView4);
            TV5 = (TextView) itemView.findViewById(R.id.productViewTextView5);
            B = (Button) itemView.findViewById(R.id.productViewButton);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_view_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        ProductsClass productsClass = productData.get(position);

        ((viewHolder) holder).IV1.setImageBitmap(UserClass.getImage(productsClass.getProductImage()));
        ((viewHolder) holder).TV2.setText(productsClass.getProductName());
        ((viewHolder) holder).TV3.setText(productsClass.getProductItem());
        ((viewHolder) holder).TV4.setText(productsClass.getProductInitialBid());
        ((viewHolder) holder).TV5.setText(productsClass.getBidderName());
      //  String UN = productsClass.getBidderName();
        if (((viewHolder) holder).TV5.getText().toString().equals(UserName))
        {
            ((viewHolder) holder).IV2.setVisibility(View.VISIBLE);
        }
        ((viewHolder) holder).B.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent MoveToProduct = new Intent(context, ShowBidsProductActivity.class);
                MoveToProduct.putExtra("ProductId", productsClass.getProductId());
                context.startActivity(MoveToProduct);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productData.size();
    }
}
