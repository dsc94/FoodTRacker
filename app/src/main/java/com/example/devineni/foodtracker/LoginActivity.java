package com.example.devineni.foodtracker;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class LoginActivity extends AppCompatActivity {
    EditText mailid;
    EditText password;
    Button login;
    Button signup;
    String mail;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mailid = (EditText) findViewById(R.id.foodtrackeremailid);
        mail = mailid.getText().toString();
        password = (EditText) findViewById(R.id.foodtrackerpasswordid);
        login = (Button) findViewById(R.id.foodtrackerloginbuttonid);
        signup = (Button) findViewById(R.id.foodtrackersignupid);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String mail = mailid.getText().toString();
                String pass = password.getText().toString();
                Intent i = new Intent(getApplicationContext(),MainpageActivity.class);
                i.putExtra("username", mail);
                startActivity(i);
                //checkvalid(mail, pass);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signuppage = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(signuppage);
            }
        });
    }

    private void checkvalid(String mail, String pass) {
          getClass gc =new getClass(getApplicationContext());
          gc.execute(mail,pass);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class getClass extends AsyncTask<String,Void,String>{
        public Context context;
        getClass(Context c)
        {
            context = c;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String check = "valid user";
            String check2 = "invalid user";
            if(s.equals(check)) {
                dialog.dismiss();
                System.out.println("Valid user");

            }
            else if(s.equals(check2))
            {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(),"Are you a Registered user?Please check Email id and Password!! or You can register by clicking the SIGN UP button", Toast.LENGTH_LONG).show();
            }
            else
            {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(LoginActivity.this);
            dialog.setMessage("Please Wait Authenticating...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String param1 = params[0];
            String param2 = params[1];
            String readline="";
            String response ="";
            try {

                URL url = new URL("http://localhost/validuser.php?" + URLEncoder.encode("usermail", "UTF-8") + "=" + URLEncoder.encode(param1, "UTF-8")
                        + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(param2, "UTF-8"));
                String data = URLEncoder.encode("usermail", "UTF-8") + "=" + URLEncoder.encode(param1, "UTF-8")
                        + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(param2, "UTF-8");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                if (con.getResponseCode() == 200)
                {
                    con.setDoInput(true);
                    con.setDoOutput(true);
                    con.setRequestMethod("GET");
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
                    response = "Check Your internet connection";
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
