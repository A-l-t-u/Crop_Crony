package com.example.crop_crony;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CropCronyDatabase extends SQLiteOpenHelper
{
    SQLiteDatabase db = this.getWritableDatabase();
    static final String DB_NAME = "CropCrony.db";
    static final int VER = 1;

    static final String TB1 = "UsersTable";
    static final String TB1_COL1 = "UserId";
    static final String TB1_COL2 = "UserFullName";
    static final String TB1_COL3 = "Username";
    static final String TB1_COL4 = "UserEmail";
    static final String TB1_COL5 = "UserPassword";
    static final String TB1_COL6 = "UserCNICNumber";
    static final String TB1_COL7 = "UserAddress";
    static final String TB1_COL8 = "UserCity";
    static final String TB1_COL9 = "UserPhoneNumber";
    static final String TB1_COL10 = "UserRole";
    static final String TB1_COL11 = "UserImage";

    static final String TB2 = "RolesTable";
    static final String TB2_COL1 = "RoleId";
    static final String TB2_COL2 = "RoleName";

    static final String TB3 = "ItemsTable";
    static final String TB3_COL1 = "ItemId";
    static final String TB3_COL2 = "ItemName";

    static final String TB4 = "ProductsTable";
    static final String TB4_COL1 = "ProductId";
    static final String TB4_COL2 = "ProductName";
    static final String TB4_COL3 = "ProductItem";
    static final String TB4_COL4 = "ProductDescription";
    static final String TB4_COL5 = "ProductInitialBid";
    static final String TB4_COL6 = "ProductSeller";
    static final String TB4_COL7 = "ProductStatus";
    static final String TB4_COL8 = "ProductFinalDate";
    static final String TB4_COL9 = "ProductImage";

    static final String TB5 = "BiddingsTable";
    static final String TB5_COL1 = "BiddingId";
    static final String TB5_COL2 = "BiddingProduct";
    static final String TB5_COL3 = "HighestBidding";
    static final String TB5_COL4 = "HighestBidder";

    static final String TB6 = "AuctionedProductTable";
    static final String TB6_COL1 = "AuctionedProductId";
    static final String TB6_COL2 = "AuctionedProduct";
    static final String TB6_COL3 = "AuctionedProductBuyer";
    static final String TB6_COL4 = "AuctionedProductPrice";
    static final String TB6_COL5 = "AuctionedProductFeedback";

    static final String TB7 = "ProductRequestTable";
    static final String TB7_COL1 = "RequestId";
    static final String TB7_COL2 = "RequestedBy";
    static final String TB7_COL3 = "RequestedProduct";
    static final String TB7_COL4 = "ProductDescription";

    static final String TB8 = "AdvertisementsTable";
    static final String TB8_COL1 = "AdId";
    static final String TB8_COL2 = "AdName";
    static final String TB8_COL3 = "AdImage";

    static final String TB9 = "GovernmentSchemesTable";
    static final String TB9_COL1 = "SchemeId";
    static final String TB9_COL2 = "SchemeName";
    static final String TB9_COL3 = "SchemeImage";

    static final String TB10 = "CitiesTable";
    static final String TB10_COL1 = "CityId";
    static final String TB10_COL2 = "CityName";

    static final String CREATE_USER_TABLE = "CREATE TABLE " + TB1 + "(" + TB1_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TB1_COL2 + " TEXT, " + TB1_COL3 + " TEXT UNIQUE, " + TB1_COL4 + " TEXT UNIQUE, " + TB1_COL5 + " TEXT, " + TB1_COL6 + " TEXT UNIQUE, " + TB1_COL7 + " TEXT, " + TB1_COL8 + " INTEGER, " + TB1_COL9 + " TEXT, " + TB1_COL10 + " INTEGER, " + TB1_COL11 + " BLOB, FOREIGN KEY (" + TB1_COL8 + ") REFERENCES " + TB10 + "(" + TB10_COL1 + "), FOREIGN KEY (" + TB1_COL10 + ") REFERENCES " + TB2 + "(" + TB2_COL1 + "))";
    static final String CREATE_ROLE_TABLE = "CREATE TABLE " + TB2 + "(" + TB2_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TB2_COL2 + " TEXT UNIQUE)";
    static final String CREATE_ITEM_TABLE = "CREATE TABLE " + TB3 + "(" + TB3_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TB3_COL2 + " TEXT UNIQUE)";
    static final String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TB4 + "(" + TB4_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TB4_COL2 + " TEXT, " + TB4_COL3 + " INTEGER, " + TB4_COL4 + " TEXT, " + TB4_COL5 + " INTEGER, " + TB4_COL6 + " INTEGER, " + TB4_COL7 + " TEXT, " + TB4_COL8 + " DATE, " + TB4_COL9 + " BLOB, FOREIGN KEY (" + TB4_COL3 + ") REFERENCES " + TB3 + "(" + TB3_COL1 + "), FOREIGN KEY (" + TB4_COL6 + ") REFERENCES " + TB1 + "(" + TB1_COL1 + "))";
    static final String CREATE_BIDDING_TABLE = "CREATE TABLE " + TB5 + "(" + TB5_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TB5_COL2 + " INTEGER, " + TB5_COL3 + " INTEGER, " + TB5_COL4 + " INTEGER, FOREIGN KEY (" + TB5_COL2 + ") REFERENCES " + TB4 + "(" + TB4_COL1 + "), FOREIGN KEY (" + TB5_COL4 + ") REFERENCES " + TB1 + "(" + TB1_COL1 + "))";
    static final String CREATE_AUCTIONED_PRODUCT_TABLE = "CREATE TABLE " + TB6 + "(" + TB6_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TB6_COL2 + " INTEGER, " + TB6_COL3 + " INTEGER, " + TB6_COL4 + " INTEGER, " + TB6_COL5 + " TEXT, FOREIGN KEY (" + TB6_COL2 + ") REFERENCES " + TB4 + "(" + TB4_COL1 + "), FOREIGN KEY (" + TB6_COL3 + ") REFERENCES " + TB1 + "(" + TB1_COL1 + "))";
    static final String CREATE_REQUEST_TABLE = "CREATE TABLE " + TB7 + "(" + TB7_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TB7_COL2 + " INTEGER, " + TB7_COL3 + " INTEGER, " + TB7_COL4 + " TEXT, FOREIGN KEY (" + TB7_COL2 + ") REFERENCES " + TB1 + "(" + TB1_COL1 + "), FOREIGN KEY (" + TB7_COL3 + ") REFERENCES " + TB3 + "(" + TB3_COL1 + "))";
    static final String CREATE_ADVERTISEMENT_TABLE = "CREATE TABLE " + TB8 + "(" + TB8_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TB8_COL2 + " TEXT, " + TB8_COL3 + " BLOB)";
    static final String CREATE_SCHEME_TABLE = "CREATE TABLE " + TB9 + "(" + TB9_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TB9_COL2 + " TEXT, " + TB9_COL3 + " BLOB)";
    static final String CREATE_CITY_TABLE = "CREATE TABLE " + TB10 + "(" + TB10_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TB10_COL2 + " TEXT UNIQUE)";

    static final String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TB1;
    static final String DROP_ROLE_TABLE = "DROP TABLE IF EXISTS " + TB2;
    static final String DROP_ITEM_TABLE = "DROP TABLE IF EXISTS " + TB3;
    static final String DROP_PRODUCT_TABLE = "DROP TABLE IF EXISTS " + TB4;
    static final String DROP_BIDDING_TABLE = "DROP TABLE IF EXISTS " + TB5;
    static final String DROP_AUCTIONED_PRODUCT_TABLE = "DROP TABLE IF EXISTS " + TB6;
    static final String DROP_REQUEST_TABLE = "DROP TABLE IF EXISTS " + TB7;
    static final String DROP_ADVERTISEMENT_TABLE = "DROP TABLE IF EXISTS " + TB8;
    static final String DROP_SCHEME_TABLE = "DROP TABLE IF EXISTS " + TB9;
    static final String DROP_CITY_TABLE = "DROP TABLE IF EXISTS " + TB10;

    static final String INSERT_ADMIN_ROLE = "INSERT INTO RolesTable (RoleName) VALUES ('Admin')";
    static final String INSERT_BUYER_ROLE = "INSERT INTO RolesTable (RoleName) VALUES ('Buyer')";
    static final String INSERT_SELLER_ROLE = "INSERT INTO RolesTable (RoleName) VALUES ('Seller')";
    static final String INSERT_CITY1 = "INSERT INTO CitiesTable (CityName) VALUES ('Karachi')";
    static final String INSERT_CITY2 = "INSERT INTO CitiesTable (CityName) VALUES ('Lahore')";
    static final String INSERT_CITY3 = "INSERT INTO CitiesTable (CityName) VALUES ('Islamabad')";
    static final String INSERT_ITEMS1 = "INSERT INTO ItemsTable (ItemName) VALUES ('Wheat')";
    static final String INSERT_ITEMS2 = "INSERT INTO ItemsTable (ItemName) VALUES ('Cotton')";
    static final String INSERT_ITEMS3 = "INSERT INTO ItemsTable (ItemName) VALUES ('Maize')";
    static final String INSERT_ITEMS4 = "INSERT INTO ItemsTable (ItemName) VALUES ('Fruits')";
    static final String INSERT_ITEMS5 = "INSERT INTO ItemsTable (ItemName) VALUES ('Vegetables')";
    static final String INSERT_ADMIN = "INSERT INTO UsersTable (UserFullName, Username, UserEmail, UserPassword, UserCNICNumber, UserAddress, UserCity, UserPhoneNumber, UserRole) VALUES ('Admin', 'admin', 'admin@gmail.com', 'admin', '', '', 'Karachi', '', 'Admin')";

    public CropCronyDatabase(@Nullable Context context)
    {
        super(context, DB_NAME, null, VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_ROLE_TABLE);
        db.execSQL(CREATE_CITY_TABLE);
        db.execSQL(CREATE_ITEM_TABLE);
        db.execSQL(CREATE_PRODUCT_TABLE);
        db.execSQL(CREATE_BIDDING_TABLE);
        db.execSQL(CREATE_AUCTIONED_PRODUCT_TABLE);
        db.execSQL(CREATE_REQUEST_TABLE);
        db.execSQL(CREATE_ADVERTISEMENT_TABLE);
        db.execSQL(CREATE_SCHEME_TABLE);
        db.execSQL(CREATE_USER_TABLE);

        db.execSQL(INSERT_ADMIN_ROLE);
        db.execSQL(INSERT_BUYER_ROLE);
        db.execSQL(INSERT_SELLER_ROLE);
        db.execSQL(INSERT_CITY1);
        db.execSQL(INSERT_CITY2);
        db.execSQL(INSERT_CITY3);
        db.execSQL(INSERT_ITEMS1);
        db.execSQL(INSERT_ITEMS2);
        db.execSQL(INSERT_ITEMS3);
        db.execSQL(INSERT_ITEMS4);
        db.execSQL(INSERT_ITEMS5);
        db.execSQL(INSERT_ADMIN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_ROLE_TABLE);
        db.execSQL(DROP_ITEM_TABLE);
        db.execSQL(DROP_PRODUCT_TABLE);
        db.execSQL(DROP_BIDDING_TABLE);
        db.execSQL(DROP_AUCTIONED_PRODUCT_TABLE);
        db.execSQL(DROP_REQUEST_TABLE);
        db.execSQL(DROP_ADVERTISEMENT_TABLE);
        db.execSQL(DROP_SCHEME_TABLE);
        db.execSQL(DROP_CITY_TABLE);
        onCreate(db);
    }

    public Cursor Login(String Name, String Password)
    {
        Cursor Result = db.rawQuery("SELECT * FROM " + TB1 + " WHERE " + TB1_COL3 + " = ? AND " + TB1_COL5 + " = ?", new String[] {Name, Password});
        return Result;
    }

    public Cursor GetData(String Table)
    {
        Cursor Result = db.rawQuery("SELECT * FROM " + Table,null);
        return Result;
    }

    public Cursor GetDataWithJoin()
    {
        Cursor Result = db.rawQuery("SELECT * FROM ProductsTable PT Left JOIN BiddingsTable BT ON PT.ProductName = BT.BiddingProduct", null);
        return Result;
    }

    public Cursor GetDataById(String Table, String ColumnName, String Value)
    {
        Cursor Result = db.rawQuery("SELECT * FROM " + Table + " WHERE " + ColumnName + "=?",new String[]{Value});
        return Result;
    }

    public Cursor GetDataById(String Table, String ColumnName, String Value, String val)
    {
        Cursor Result = db.rawQuery("SELECT MAX(HighestBidding) FROM " + Table + " WHERE " + ColumnName + "=?",new String[]{Value});
//        Cursor Result = db.rawQuery("SELECT * FROM " + Table + " WHERE " + ColumnName + "=? and HighestBidding=?",new String[]{Value,val});
        return Result;
    }

    public boolean Delete(String Table, String Value1)
    {
        long Result = db.delete(Table, "Id=?", new String[]{Value1});
        if (Result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean Insert(String Table, String Value1)
    {
        ContentValues CV = new ContentValues();
        if (Table == TB2)
        {
            CV.put(TB2_COL2, Value1);
        }
        if (Table == TB3)
        {
            CV.put(TB3_COL2, Value1);
        }
        if (Table == TB10)
        {
            CV.put(TB10_COL2, Value1);
        }

        long Result = db.insert(Table,null, CV);

        if (Result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean Insert(String Table, String Value1, byte[] Value2)
    {
        ContentValues CV = new ContentValues();
        if (Table == TB8)
        {
            CV.put(TB8_COL2, Value1);
            CV.put(TB8_COL3, Value2);
        }
        if (Table == TB9)
        {
            CV.put(TB9_COL2, Value1);
            CV.put(TB9_COL3, Value2);
        }

        long Result = db.insert(Table,null, CV);

        if (Result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean Insert(String Table, String Value1, String Value2, String Value3)
    {
        ContentValues CV = new ContentValues();
        if (Table == TB5)
        {
            CV.put(TB5_COL2, Value1);
            CV.put(TB5_COL3, Value2);
            CV.put(TB5_COL4, Value3);
        }
        if (Table == TB7)
        {
            CV.put(TB7_COL2, Value1);
            CV.put(TB7_COL3, Value2);
            CV.put(TB7_COL4, Value3);
        }

        long Result = db.insert(Table,null, CV);

        if (Result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean Insert(String Table, String Value1, String Value2, String Value3, String Value4)
    {
        ContentValues CV = new ContentValues();
        CV.put(TB6_COL2, Value1);
        CV.put(TB6_COL3, Value2);
        CV.put(TB6_COL4, Value3);
        CV.put(TB6_COL5, Value4);

        long Result = db.insert(TB6,null, CV);

        if (Result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean Insert(String Value1, String Value2, String Value3, String Value4, String Value5, String Value6, String Value7, byte[] Value8)
    {
        ContentValues CV = new ContentValues();
        CV.put(TB4_COL2, Value1);
        CV.put(TB4_COL3, Value2);
        CV.put(TB4_COL4, Value3);
        CV.put(TB4_COL5, Value4);
        CV.put(TB4_COL6, Value5);
        CV.put(TB4_COL7, Value6);
        CV.put(TB4_COL8, Value7);
        CV.put(TB4_COL9, Value8);

        long Result = db.insert(TB4,null, CV);

        if (Result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean Insert(String Value1, String Value2, String Value3, String Value4, String Value5, String Value6, String Value7, String Value8, String Value9, byte[] Value10)
    {
        ContentValues CV = new ContentValues();
        CV.put(TB1_COL2, Value1);
        CV.put(TB1_COL3, Value2);
        CV.put(TB1_COL4, Value3);
        CV.put(TB1_COL5, Value4);
        CV.put(TB1_COL6, Value5);
        CV.put(TB1_COL7, Value6);
        CV.put(TB1_COL8, Value7);
        CV.put(TB1_COL9, Value8);
        CV.put(TB1_COL10, Value9);
        CV.put(TB1_COL11, Value10);

        long Result = db.insert(TB1,null, CV);

        if (Result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean Update(String Table, String Value, String Value1)
    {
        long Result = 0;
        ContentValues CV = new ContentValues();
        if (Table == TB2)
        {
            CV.put(TB2_COL1, Value);
            CV.put(TB2_COL2, Value1);
            Result = db.update(Table, CV, TB2_COL1 + "=?", new String[]{Value});
        }
        if (Table == TB3)
        {
            CV.put(TB3_COL1, Value);
            CV.put(TB3_COL2, Value1);
            Result = db.update(Table, CV, TB3_COL1 + "=?", new String[]{Value});
        }
        if (Table == TB10)
        {
            CV.put(TB10_COL1, Value);
            CV.put(TB10_COL2, Value1);
            Result = db.update(Table, CV, TB10_COL1 + "=?", new String[]{Value});
        }

        if (Result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean Update(String Table, String Value, String Value1, String Value2)
    {
        long Result = 0;
        ContentValues CV = new ContentValues();
        if (Table == TB8)
        {
            CV.put(TB8_COL1, Value);
            CV.put(TB8_COL2, Value1);
            CV.put(TB8_COL3, Value2);
            Result = db.update(Table, CV, TB8_COL1 + "=?", new String[]{Value});
        }
        if (Table == TB9)
        {
            CV.put(TB9_COL1, Value);
            CV.put(TB9_COL2, Value1);
            CV.put(TB9_COL3, Value2);
            Result = db.update(Table, CV, TB9_COL1 + "=?", new String[]{Value});
        }

        if (Result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean Update(String Table, String Value, String Value1, String Value2, String Value3)
    {
        long Result = 0;
        ContentValues CV = new ContentValues();
        if (Table == TB5)
        {
            CV.put(TB5_COL1, Value);
            CV.put(TB5_COL2, Value1);
            CV.put(TB5_COL3, Value2);
            CV.put(TB5_COL4, Value3);
            Result = db.update(Table, CV, TB5_COL1 + "=?", new String[]{Value});
        }
        if (Table == TB7)
        {
            CV.put(TB5_COL1, Value);
            CV.put(TB7_COL2, Value1);
            CV.put(TB7_COL3, Value2);
            CV.put(TB7_COL4, Value3);
            Result = db.update(Table, CV, TB7_COL1 + "=?", new String[]{Value});
        }

        if (Result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean Update(String Table, String Value, String Value1, String Value2, String Value3, String Value4)
    {
        long Result = 0;
        ContentValues CV = new ContentValues();
        CV.put(TB6_COL1, Value);
        CV.put(TB6_COL2, Value1);
        CV.put(TB6_COL3, Value2);
        CV.put(TB6_COL4, Value3);
        CV.put(TB6_COL5, Value4);
        Result = db.update(Table, CV, TB6_COL1 + "=?", new String[]{Value});

        if (Result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean Update(String Value, String Value1)
    {
        long Result = 0;
        ContentValues CV = new ContentValues();
        CV.put(TB4_COL1, Value);
        CV.put(TB4_COL7, Value1);
        Result = db.update("ProductsTable", CV, TB4_COL1 + "=?", new String[]{Value});

        if (Result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean Update(String Table, String Value, String Value1, String Value2, String Value3, String Value4, String Value5, String Value6, String Value7, byte[] Value8)
    {
        long Result = 0;
        ContentValues CV = new ContentValues();
        CV.put(TB4_COL1, Value);
        CV.put(TB4_COL2, Value1);
        CV.put(TB4_COL3, Value2);
        CV.put(TB4_COL4, Value3);
        CV.put(TB4_COL5, Value4);
        CV.put(TB4_COL6, Value5);
        CV.put(TB4_COL7, Value6);
        CV.put(TB4_COL8, Value7);
        CV.put(TB4_COL9, Value8);
        Result = db.update(Table, CV, TB4_COL1 + "=?", new String[]{Value});

        if (Result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean Update(String Table, String Value, String Value1, String Value2, String Value3, String Value4, String Value5, String Value6, String Value7, String Value8, String Value9, byte[] Value10)
    {
        long Result = 0;
        ContentValues CV = new ContentValues();
        CV.put(TB1_COL1, Value);
        CV.put(TB1_COL2, Value1);
        CV.put(TB1_COL3, Value2);
        CV.put(TB1_COL4, Value3);
        CV.put(TB1_COL5, Value4);
        CV.put(TB1_COL6, Value5);
        CV.put(TB1_COL7, Value6);
        CV.put(TB1_COL8, Value7);
        CV.put(TB1_COL9, Value8);
        CV.put(TB1_COL10, Value9);
        CV.put(TB1_COL11, Value10);
        Result = db.update(Table, CV, TB1_COL1 + "=?", new String[]{Value});

        if (Result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}