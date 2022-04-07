package com.example.crop_crony;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductRequestAdapter extends RecyclerView.Adapter
{
    Context context;
    ArrayList<ProductRequestClass> req;

    public ProductRequestAdapter(Context context, ArrayList<ProductRequestClass> req)
    {
        this.context = context;
        this.req = req;
    }

    public class viewHolder extends RecyclerView.ViewHolder
    {
        TextView TV, TV2, TV3;

        public viewHolder(@NonNull View itemView)
        {
            super(itemView);
            TV = (TextView) itemView.findViewById(R.id.reqViewTextView);
            TV2 = (TextView) itemView.findViewById(R.id.reqViewTextView2);
            TV3 = (TextView) itemView.findViewById(R.id.reqViewTextView3);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_view_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        ProductRequestClass Req = req.get(position);

        ((viewHolder) holder).TV.setText(Req.getRequestedProduct());
        ((viewHolder) holder).TV2.setText(Req.getRequestedBy());
        ((viewHolder) holder).TV3.setText(Req.getRequestDescription());
    }

    @Override
    public int getItemCount() {
        return req.size();
    }
}
