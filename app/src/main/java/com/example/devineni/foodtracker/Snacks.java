package  com.example.devineni.foodtracker;
import android.annotation.TargetApi;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
public class Snacks extends Fragment {
    ImageButton imagebutton;
    ListView listview;
    Intent listviewintent;
    SQLiteDatabase localdatabase;
    ArrayList<String> foodlist = new ArrayList<String>();
    String foodname;
    String quantity;
    String units;
    String nutritiontype;
    String date;
    String foodpart;
    dbhelper db;
    private static String DB_PATH = "/data/data/com.example.devineni.foodtracker/databases/";
    private static String DB_NAME = "FoodTracker.db";
    public Snacks()
    {
        listviewintent = null;
    }
    public Snacks(String foodpart)
    {

    }
    public Snacks(Intent getintent)
    {
        listviewintent = getintent;
        this.quantity = listviewintent.getStringExtra("Quantity");
        this.foodname = listviewintent.getStringExtra("Foodname");
        this.units = listviewintent.getStringExtra("units");
        this.nutritiontype = listviewintent.getStringExtra("nutritiontype");
        this.foodpart = listviewintent.getStringExtra("foodpart");
        System.out.println(foodpart);
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void savetolocaldatabase(String foodname, String quantity, String units, String nutritiontype, Date d)
    {
       // int todaydate = d.getDate();
        SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd");
        date = tf.format(d);
        System.out.println(date);
        db.initialize("FoodTracker", foodpart, 1, "foodname", "quantity", "units", "nutritiontype", "date");
        System.out.println("belowinitilaize");
        SQLiteDatabase database = db.getWritableDatabase();
        db.insert(foodpart,this);
    }
    public String getFoodname()
    {
        return this.foodname;
    }
    public String getQuantity()
    {
        return this.quantity;
    }
    public String getUnits()
    {
        return this.units;
    }
    public String getNutritiontype()
    {
        return this.nutritiontype;
    }
    public String getDate()
    {
        return  this.date;
    }
    @Override
    public void onResume()
    {
        super.onResume();
        System.out.println("Activity resumed");
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v;
        if(listviewintent != null )
        {
            System.out.println("dsc123");
            db = new dbhelper(this.getContext(),"Snacks",null,2);
            Date d = new Date();
            savetolocaldatabase(foodname, quantity, units, nutritiontype, d);
            //savetodatabase(foodname, quantity, units, nutritiontype, d);
            v = inflater.inflate(R.layout.commonlayout1, container, false);
            ArrayList<String> snacks = db.retrieve(this);
            listview = (ListView) v.findViewById(R.id.listview);
            ArrayAdapter adapter = new ArrayAdapter(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,snacks);
            listview.setAdapter(adapter);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent listclickintent = new Intent(getContext(), FoodServing.class);
                    listclickintent.putExtra("position", position);
                }
            });
        }
        else
        {
            System.out.println("dsc234");
            v = inflater.inflate(R.layout.commonlayout, container, false);
            db = new dbhelper(this.getContext(),"FoodTracker",null,2);
            db.initialize("FoodTracker","Snacks", 1, "foodname", "quantity", "units", "nutritiontype", "date");
            Date d = new Date();
            SQLiteDatabase db1 = db.getWritableDatabase();
            db.onCreate(db1);
            ArrayList<String> breakfast = db.retrieve(this);
            listview = (ListView) v.findViewById(R.id.listview);
            ArrayAdapter adapter = new ArrayAdapter(getActivity().getApplicationContext(),android.R.layout.simple_expandable_list_item_1,breakfast);
            listview.setAdapter(adapter);
        }
        return v;
    }
    private void savetodatabase(String foodname, String quantity, String units,String nutritiontype,Date d)
    {
        PostClass pc = new PostClass();
        String date = d.toString();
        pc.execute(foodname,quantity,units,nutritiontype,date);
    }
    class PostClass extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params) {
            String foodname = params[0];
            String quantity = params[1];
            String units = params[2];
            String nutritiontype = params[3];
            String date = params[4];
            String readline ="";
            String url2 ="";
            String response ="";
            try {
                URL url = new URL("http://localhost/insertintobreakfast.php?" + URLEncoder.encode("foodname", "UTF-8") + "=" + URLEncoder.encode(foodname, "UTF-8")
                        + "&" + URLEncoder.encode("quantity", "UTF-8") + "=" + URLEncoder.encode(quantity, "UTF-8")
                        + "&" + URLEncoder.encode("units", "UTF-8") + "=" + URLEncoder.encode(units, "UTF-8")
                        + "&" + URLEncoder.encode("nutritiontype", "UTF-8") + "=" + URLEncoder.encode(nutritiontype, "UTF-8")
                        + "&" + URLEncoder.encode("dates", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8"));
                String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(foodname, "UTF-8")
                        + URLEncoder.encode("usermail", "UTF-8") + "=" + URLEncoder.encode(quantity, "UTF-8")
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(units, "UTF-8");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                if (con.getResponseCode() == 200)
                {
                    con.setRequestMethod("GET");
                    con.setDoOutput(true);
                    con.setUseCaches(false);
                    con.setDoInput(true);
                    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                    wr.write(data);
                    wr.flush();
                    InputStream read = con.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(read, "iso-8859-1"));
                    while ((readline = br.readLine()) != null) {
                        response = response + readline;
                    }
                }
                else
                {
                    response = "Check your Internet Connection";
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return response;
        }
    }
}