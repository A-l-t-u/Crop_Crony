package com.example.crop_crony;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class UserClass
{
    String UserId, UserName, Username, UserEmail, UserPassword, UserCNICNumber, UserAddress, UserCity, UserPhoneNumber, UserRole;
    byte[] UserImage;

    public UserClass(String userId, String userName, String username, String userEmail, String userPassword, String userCNICNumber, String userAddress, String userCity, String userPhoneNumber, String userRole, byte[] userImage)
    {
        UserId = userId;
        UserName = userName;
        Username = username;
        UserEmail = userEmail;
        UserPassword = userPassword;
        UserCNICNumber = userCNICNumber;
        UserAddress = userAddress;
        UserCity = userCity;
        UserPhoneNumber = userPhoneNumber;
        UserRole = userRole;
        UserImage = userImage;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

    public String getUserCNICNumber() {
        return UserCNICNumber;
    }

    public void setUserCNICNumber(String userCNICNumber) {
        UserCNICNumber = userCNICNumber;
    }

    public String getUserAddress() {
        return UserAddress;
    }

    public void setUserAddress(String userAddress) {
        UserAddress = userAddress;
    }

    public String getUserCity() {
        return UserCity;
    }

    public void setUserCity(String userCity) {
        UserCity = userCity;
    }

    public String getUserPhoneNumber() {
        return UserPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        UserPhoneNumber = userPhoneNumber;
    }

    public String getUserRole() {
        return UserRole;
    }

    public void setUserRole(String userRole) {
        UserRole = userRole;
    }

    public byte[] getUserImage() {
        return UserImage;
    }

    public void setUserImage(byte[] userImage) {
        UserImage = userImage;
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