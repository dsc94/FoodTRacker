package com.example.devineni.foodtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by devineni on 8/17/2016.
 */
public class dbhelper extends SQLiteOpenHelper {
    private String databasename;
    public String tablename;
    public int databaseversion;
    public String Column1;
    public String Column2;
    public String Column3;
    public String Column4;
    public String Column5;
    SQLiteDatabase db;
    public dbhelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);

    }

    public void initialize(String databasename, String tablename, int i, String Column1, String Column2, String Column3, String Column4, String Column5)
    {
        this.tablename = tablename;
        this.databaseversion = i;
        this.Column1 = Column1;
        this.Column2 = Column2;
        this.Column3 = Column3;
        this.Column4 = Column4;
        this.Column5 = Column5;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
           System.out.println("oncreate");
           System.out.println(tablename);
           System.out.println(Column1);
           System.out.println(Column2);
           System.out.println(Column3);
           System.out.println(Column4);
           System.out.println(Column5);

           String createquery = "CREATE TABLE if not exists " + tablename + "(" + Column1 + "  TEXT," + Column2 + " TEXT," + Column3 + " TEXT,"
                  + Column4 + " TEXT," + Column5 + " TEXT" + ");";
           db.execSQL(createquery);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
       System.out.println("on upgrade");
        db.needUpgrade(newVersion);
    }

    public void insert(String foodpart,Object o)
    {
       System.out.println(foodpart);
      if(foodpart.equals("Breakfast"))
      {
          System.out.println("breakfast insert");
          String foodname = ((Breakfast) o).getFoodname();
          String quantity = ((Breakfast) o).getQuantity();
          String units = ((Breakfast) o).getUnits();
          String nutritiontype = ((Breakfast) o).getNutritiontype();
          String date = ((Breakfast) o).getDate();
          ContentValues cv = new ContentValues();
          cv.put(this.Column1,foodname);
          cv.put(this.Column2,quantity);
          cv.put(this.Column3,units);
          cv.put(this.Column4,nutritiontype);
          cv.put(this.Column5,date);
          System.out.println("dscdate");
          System.out.println(date);
          System.out.println(foodname);
          System.out.println(quantity);
          System.out.println(units);
          System.out.println(nutritiontype);
          System.out.println(date);
          db = getWritableDatabase();
          db.insert("Breakfast", null, cv);
          db.close();
      }
      if(foodpart.equals("dinner"))
      {
          System.out.println("Dinner insert");
          String foodname = ((Breakfast) o).getFoodname();
          String quantity = ((Breakfast) o).getQuantity();
          String units = ((Breakfast) o).getUnits();
          String nutritiontype = ((Breakfast) o).getNutritiontype();
          String date = ((Breakfast) o).getDate();
          ContentValues cv = new ContentValues();
          cv.put(this.Column1,foodname);
          cv.put(this.Column2,quantity);
          cv.put(this.Column3,units);
          cv.put(this.Column4,nutritiontype);
          cv.put(this.Column5,date);
          System.out.println("dscdate");
          System.out.println(date);
          System.out.println(foodname);
          System.out.println(quantity);
          System.out.println(units);
          System.out.println(nutritiontype);
          System.out.println(date);
          db = getWritableDatabase();
          db.insert("Dinner", null, cv);
          db.close();
      }
      if(foodpart.equals("Lunch"))
      {
          System.out.println("Lunch insert");
          String foodname = ((Breakfast) o).getFoodname();
          String quantity = ((Breakfast) o).getQuantity();
          String units = ((Breakfast) o).getUnits();
          String nutritiontype = ((Breakfast) o).getNutritiontype();
          String date = ((Breakfast) o).getDate();
          ContentValues cv = new ContentValues();
          cv.put(this.Column1,foodname);
          cv.put(this.Column2,quantity);
          cv.put(this.Column3,units);
          cv.put(this.Column4,nutritiontype);
          cv.put(this.Column5,date);
          System.out.println("dscdate");
          System.out.println(date);
          System.out.println(foodname);
          System.out.println(quantity);
          System.out.println(units);
          System.out.println(nutritiontype);
          System.out.println(date);
          db = getWritableDatabase();
          db.insert("Lunch", null, cv);
          db.close();
      }
      if(foodpart.equals("Snacks"))
      {
          System.out.println("snacks insert");
          String foodname = ((Breakfast) o).getFoodname();
          String quantity = ((Breakfast) o).getQuantity();
          String units = ((Breakfast) o).getUnits();
          String nutritiontype = ((Breakfast) o).getNutritiontype();
          String date = ((Breakfast) o).getDate();
          ContentValues cv = new ContentValues();
          cv.put(this.Column1,foodname);
          cv.put(this.Column2,quantity);
          cv.put(this.Column3,units);
          cv.put(this.Column4,nutritiontype);
          cv.put(this.Column5,date);
          System.out.println("dscdate");
          System.out.println(date);
          System.out.println(foodname);
          System.out.println(quantity);
          System.out.println(units);
          System.out.println(nutritiontype);
          System.out.println(date);
          db = getWritableDatabase();
          db.insert("Snacks", null, cv);
          db.close();
      }
    }
    public ArrayList<String> retrieve(Object o)
    {
        ArrayList<String> al = new ArrayList<String>();
        if(o instanceof Breakfast)
        {
            System.out.println("breakfast retreive");
            SQLiteDatabase db;
            Date d = new Date();
            SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd");
            String date = tf.format(d);
            System.out.println(date);
            String rawquery = "SELECT * FROM Breakfast where date="+"'"+date+"'";
            db = getWritableDatabase();
            Cursor c = db.rawQuery(rawquery, null);
            System.out.println(c.getCount());
            if(c == null)
            {
              System.out.println("breakfast cursor is null");
            }
            else {
                if (c.moveToFirst()) {
                    do {
                            String foodname = c.getString(0).toString();
                            System.out.println(foodname);
                            al.add(foodname);
                    } while (c.moveToNext() && c.getString(0) != null);
                }
            }
            db.close();
        }
        if(o instanceof Dinner)
        {
            System.out.println("dinner retreive");
            SQLiteDatabase db;
            Date d = new Date();
            SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd");
            String date = tf.format(d);
            System.out.println(date);
            String rawquery = "SELECT * FROM Dinner where date="+"'"+date+"'";
            db = getWritableDatabase();
            Cursor c = db.rawQuery(rawquery, null);
            System.out.println(c.getCount());
            if (c == null) {
                System.out.println("dinner cursor is null");
                } else {
                    if (c.moveToFirst()) {
                        do {

                            String foodname = c.getString(0).toString();
                            System.out.println(foodname);
                            al.add(foodname);

                        } while (c.moveToNext() && c.getString(0) != null);
                    }
                }


            db.close();
        }
        if(o instanceof Lunch)
        {
            System.out.println("lunch retreive");
            SQLiteDatabase db;
            Date d = new Date();
            SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd");
            String date = tf.format(d);
            System.out.println(date);
            String rawquery = "SELECT * FROM Lunch where date ="+"'"+date+"'";
            db = getWritableDatabase();
            Cursor c = db.rawQuery(rawquery, null);
            System.out.println(c.getCount());
            if(c == null)
            {
                System.out.println("lunch cursor is null");
            }
            else {
                if (c.moveToFirst()) {
                    do {
                            String foodname = c.getString(0).toString();
                            System.out.println(foodname);
                            al.add(foodname);
                    } while (c.moveToNext() && c.getString(0) != null);
                }
            }
            db.close();
        }
        if(o instanceof Snacks)
        {
            System.out.println("snacks retreive");
            SQLiteDatabase db;
            Date d = new Date();
            SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd");
            String date = tf.format(d);
            System.out.println(date);
            String rawquery = "SELECT * FROM Snacks where date ="+"'"+date+"'";
            db = getWritableDatabase();
            Cursor c = db.rawQuery(rawquery, null);
            System.out.println(c.getCount());
            if(c == null)
            {
                System.out.println("snacks cursor is null");
            }
            else {
                if (c.moveToFirst()) {
                    do {

                            String foodname = c.getString(0).toString();
                            al.add(foodname);
                    } while (c.moveToNext() && c.getString(0) != null);
                }
            }
            db.close();
        }
        return al;
    }

    public ArrayList<String> retrievedates(Object o)
    {
        ArrayList<String> dates = new ArrayList<String>();
        if(o instanceof Breakfast)
        {
            SQLiteDatabase db;
            String rawquery = "select date from Breakfast";
            db = getWritableDatabase();
            Cursor c = db.rawQuery(rawquery,null);
            if(c == null)
            {

            }
            else
            {
                if (c.moveToFirst()) {
                    do {

                        String date = c.getString(0).toString();
                        dates.add(date);
                    } while (c.moveToNext() && c.getString(0) != null);
                }
            }
        }
        if(o instanceof Lunch)
        {
            SQLiteDatabase db;
            String rawquery = "select date from Lunch";
            db = getWritableDatabase();
            Cursor c = db.rawQuery(rawquery,null);
            if(c == null)
            {

            }
            else
            {
                if (c.moveToFirst()) {
                    do {

                        String date = c.getString(0).toString();
                        dates.add(date);
                    } while (c.moveToNext() && c.getString(0) != null);
                }
            }
        }
        if(o instanceof Snacks)
        {
            SQLiteDatabase db;
            String rawquery = "select date from Snacks";
            db = getWritableDatabase();
            Cursor c = db.rawQuery(rawquery,null);
            if(c == null)
            {

            }
            else
            {
                if (c.moveToFirst()) {
                    do {
                        String date = c.getString(0).toString();
                        dates.add(date);
                    } while (c.moveToNext() && c.getString(0) != null);
                }
            }
        }
        if(o instanceof Dinner)
        {
            SQLiteDatabase db;
            String rawquery = "select date from Dinner";
            db = getWritableDatabase();
            Cursor c = db.rawQuery(rawquery,null);
            if(c == null)
            {

            }
            else
            {
                if (c.moveToFirst()) {
                    do {

                        String date = c.getString(0).toString();
                        dates.add(date);
                    } while (c.moveToNext() && c.getString(0) != null);
                }
            }
        }
        return dates;
    }

    public ArrayList<String> retriveparticulardate(Object o,String date)
    {
        ArrayList<String> fooditems = new ArrayList<String>();
        if(o instanceof Breakfast)
        {
            System.out.println("breakfast retreive");
            SQLiteDatabase db;
            Date d = new Date();
            System.out.println(date);
            String rawquery = "SELECT * FROM Breakfast where date="+"'"+date+"'";
            db = getWritableDatabase();
            Cursor c = db.rawQuery(rawquery, null);
            System.out.println(c.getCount());
            if(c == null)
            {
                System.out.println("breakfast cursor is null");
            }
            else {
                if (c.moveToFirst()) {
                    do {
                        String foodname = c.getString(0).toString();
                        System.out.println(foodname);
                        fooditems.add(foodname);
                    } while (c.moveToNext() && c.getString(0) != null);
                }
            }
            db.close();
        }
        if(o instanceof Dinner)
        {
            System.out.println("dinner retreive");
            SQLiteDatabase db;
            Date d = new Date();
            System.out.println(date);
            String rawquery = "SELECT * FROM Dinner where date="+"'"+date+"'";
            db = getWritableDatabase();
            Cursor c = db.rawQuery(rawquery, null);
            System.out.println(c.getCount());
            if (c == null) {
                System.out.println("dinner cursor is null");
            } else {
                if (c.moveToFirst()) {
                    do {

                        String foodname = c.getString(0).toString();
                        System.out.println(foodname);
                        fooditems.add(foodname);
                    } while (c.moveToNext() && c.getString(0) != null);
                }
            }


            db.close();
        }
        if(o instanceof Lunch)
        {
            System.out.println("lunch retreive");
            SQLiteDatabase db;
            Date d = new Date();
            System.out.println(date);
            String rawquery = "SELECT * FROM Lunch where date ="+"'"+date+"'";
            db = getWritableDatabase();
            Cursor c = db.rawQuery(rawquery, null);
            System.out.println(c.getCount());
            if(c == null)
            {
                System.out.println("lunch cursor is null");
            }
            else {
                if (c.moveToFirst()) {
                    do {
                        String foodname = c.getString(0).toString();
                        System.out.println(foodname);
                        fooditems.add(foodname);
                    } while (c.moveToNext() && c.getString(0) != null);
                }
            }
            db.close();
        }
        if(o instanceof Snacks)
        {
            System.out.println("snacks retreive");
            SQLiteDatabase db;
            Date d = new Date();
            System.out.println(date);
            String rawquery = "SELECT * FROM Snacks where date ="+"'"+date+"'";
            db = getWritableDatabase();
            Cursor c = db.rawQuery(rawquery, null);
            System.out.println(c.getCount());
            if(c == null)
            {
                System.out.println("snacks cursor is null");
            }
            else {
                if (c.moveToFirst()) {
                    do {

                        String foodname = c.getString(0).toString();
                        fooditems.add(foodname);
                    } while (c.moveToNext() && c.getString(0) != null);
                }
            }
            db.close();
        }
        return fooditems;
    }
}
