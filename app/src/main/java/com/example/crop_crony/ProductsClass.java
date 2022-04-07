package com.example.crop_crony;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ProductsClass
{
    String ProductId, ProductName, ProductItem, ProductDescription, ProductInitialBid, ProductSeller, BidderName;
    byte[] ProductImage;

    public ProductsClass(String productId, String productName, String productItem, String productDescription, String productInitialBid, String productSeller, byte[] productImage,String bidderName)
    {
        ProductId = productId;
        ProductName = productName;
        ProductItem = productItem;
        ProductDescription = productDescription;
        ProductInitialBid = productInitialBid;
        ProductSeller = productSeller;
        ProductImage = productImage;
        BidderName = bidderName;
    }

    public ProductsClass(String productId, String productName, String productItem, String productDescription, String productInitialBid, String productSeller, byte[] productImage)
    {
        ProductId = productId;
        ProductName = productName;
        ProductItem = productItem;
        ProductDescription = productDescription;
        ProductInitialBid = productInitialBid;
        ProductSeller = productSeller;
        ProductImage = productImage;
    }

    public String getBidderName() {
        return BidderName;
    }

    public void setBidderName(String bidderName) {
        BidderName = bidderName;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductItem() {
        return ProductItem;
    }

    public void setProductItem(String productItem) {
        ProductItem = productItem;
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }

    public String getProductInitialBid() {
        return ProductInitialBid;
    }

    public void setProductInitialBid(String productInitialBid) {
        ProductInitialBid = productInitialBid;
    }

    public String getProductSeller() {
        return ProductSeller;
    }

    public void setProductSeller(String productSeller) {
        ProductSeller = productSeller;
    }

    public byte[] getProductImage() {
        return ProductImage;
    }

    public void setProductImage(byte[] productImage) {
        ProductImage = productImage;
    }

    public static Bitmap getImage(byte[] Image) {
        return BitmapFactory.decodeByteArray(Image, 0, Image.length);
    }

    public static byte[] getImageByte(InputStream IS) throws IOException {
        int bufferSize = 1024;
        int Length = 0;
        ByteArrayOutputStream byteBuffer =new ByteArrayOutputStream();
        byte[] buffer = new byte[bufferSize];

        while ((Length = IS.read(buffer)) != -1)
        {
            byteBuffer.write(buffer, 0, Length);
        }

        return byteBuffer.toByteArray();
    }
}
