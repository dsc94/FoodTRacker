package com.example.devineni.foodtracker;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by devineni on 6/24/2016.
 */
public class FoodServing extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    TextView textview1;
    TextView textview2;
    EditText editText1;
    EditText editText2;
    String itemselected;
    Toolbar toolbar;
    String readline="";
    String response="";
    RadioGroup radioGroup;
    Spinner spinner;
    dbhelper db;
    String foodname,preference,quantity,units;
    ImageButton imageButton;
    RadioButton radioButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodserving);
        Intent i = getIntent();
        final String foodpart = i.getStringExtra("foodpart");
        textview1 = (TextView) findViewById(R.id.textview1);
        textview2 = (TextView) findViewById(R.id.textview2);
        editText1 = (EditText) findViewById(R.id.edittext1);
        editText2 = (EditText) findViewById(R.id.edittext2);
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter =  ArrayAdapter.createFromResource(getApplicationContext(),R.array.units,android.R.layout.simple_spinner_dropdown_item);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(this);
        toolbar.setTitle("Food Entries");
        toolbar.setTitleTextColor(getResources().getColor(R.color.tabsScrollColor));
        imageButton = (ImageButton) findViewById(R.id.imagebutton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String foodname = editText1.getText().toString();
                String quantity = editText2.getText().toString();
                radioGroup = (RadioGroup) findViewById(R.id.nutrition);
                int nutritiontype = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(nutritiontype);
                CharSequence nutritiontype1 = radioButton.getText();
                db = new dbhelper(getApplicationContext(),"FoodTracker",null,1);
                Date d = new Date();
                String tablename = getIntent().getClass().toString();
                //savetolocaldatabase(foodname, quantity, units, (String) nutritiontype1, d,tablename);
                Intent newintent = new Intent(getApplicationContext(), MainpageActivity.class);
                newintent.putExtra("Foodname", foodname);
                newintent.putExtra("Quantity", quantity);
                newintent.putExtra("nutritiontype",nutritiontype1);
                newintent.putExtra("units",itemselected);
                newintent.putExtra("foodpart",foodpart);
              //  newintent.putExtra("position","breakfast");
                startActivity(newintent);
                //savetodatabase(foodname,quantity,units1);
            }
        });
    }
    private void savetolocaldatabase(String foodname, String quantity, String units, String nutritiontype, Date d,String tablename)
    {
        int todaydate = d.getDate();
        SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd");
        String date = tf.format(todaydate);
        db.initialize("FoodTracker",tablename, 1, "foodname", "quantity", "units", "nutritiontype", "date");
        System.out.println("belowinitilaize");
        SQLiteDatabase database = db.getWritableDatabase();
        db.insert("breakfast",this);
    }
    private void savetodatabase(String foodname, String quantity, String units1) {
        Postclass pc = new Postclass();
        pc.execute(foodname,quantity,units1);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(position)
        {
            case 0:
                itemselected = "Kilograms";
                break;
            case 1:
                itemselected = "Grams";
                break;
            case 2:
                itemselected = "Gallons";
                break;
            case 3:
                itemselected = "Litres";
                break;
            case 4:
                itemselected = "Millilitres";
                break;
            case 5:
                itemselected = "Pcs";
                break;
            case 6:
                itemselected = "Nos";
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private class Postclass extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... params) {
            String params1 = params[0];
            String params2 = params[1];
            String params3 = params[2];
            try
            {
            URL url = new URL("http://192.168.1.33/validuser.php?"+ URLEncoder.encode("usermail", "UTF-8") + "=" + URLEncoder.encode(params1, "UTF-8")
                    + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(params2, "UTF-8"));
            String data = URLEncoder.encode("usermail", "UTF-8") + "=" + URLEncoder.encode(params1, "UTF-8")
                    + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(params2, "UTF-8");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestMethod("GET");
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(data);
            wr.flush();
            InputStream read = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(read,"iso-8859-1"));
            while((readline = br.readLine()) !=null)
            {
                response = response + readline;
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
            return response;
    }

        }
    }
