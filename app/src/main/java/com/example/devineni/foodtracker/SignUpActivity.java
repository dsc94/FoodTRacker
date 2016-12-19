package com.example.devineni.foodtracker;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
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

/**
 * Created by devineni on 6/13/2016.
 */
public class SignUpActivity extends AppCompatActivity{
    EditText name;
    EditText email;
    EditText pass;
    Button signup;
    String username,usermail,password;
    ProgressDialog progress;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        name = (EditText) findViewById(R.id.signupnameid);
        email = (EditText) findViewById(R.id.signupemailid);
        pass = (EditText) findViewById(R.id.signuppasswordid);
        signup = (Button) findViewById(R.id.signupbuttonid);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = name.getText().toString();
                usermail = email.getText().toString();
                password = pass.getText().toString();
                boolean check = checkvalid(usermail, password);
                if (check) {
                    //System.out.println("dsc1");
                    savetodatabase(username, usermail, password);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Password or Email id is not matching the rules",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    public void onPause() {
        super.onPause();
        if(progress != null){
            progress.dismiss();
        }
    }

    private void savetodatabase(String username, String usermail, String password) {
       // System.out.println("dsc2");
        sendPostRequest(username, usermail, password);
    }

    public void sendPostRequest (String username,String usermail,String password) {
        //System.out.println("dsc4");
       PostClass pc =  new PostClass(this);
        pc.execute(username,usermail,password);
    }

    private  boolean checkvalid(String usermail, String password)
    {
       String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

       if(Patterns.EMAIL_ADDRESS.matcher(usermail).matches())
       {
           System.out.println("dsc1");
           if (password.length() > 8)
           {
               System.out.println("dsc1");
               if (password.matches(pattern))
               {
                   System.out.println("dsc1");
                   return true;
               }
               else
               {
                   return false;
               }
           }
           else
           {
               return false;
           }
       }
        else
       {
           return false;
       }
    }

    private class PostClass  extends AsyncTask<String, Void, String> {
        private Context context;
        PostClass(Context c)
        {
            context = c;
        }
        protected void onPreExecute()
        {
           // System.out.println("dsc6");
            progress= new ProgressDialog(this.context);
            progress.setMessage("Registering..");
            progress.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String check = "successfully registered";
            String check1 = "username or email already exist";
            String check2 = "please fill all values";
            if(s.equals(check))
            {
                progress.dismiss();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(),"successfully registered",Toast.LENGTH_LONG).show();

            }
            else if(s.equals(check1))
            {
                progress.dismiss();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(),"username or email already exist",Toast.LENGTH_LONG).show();
                Log.e("STATUS",s);
                //progress.dismiss();
            }
            else if(s.equals(check2))
            {
                progress.dismiss();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(),"Please Fill all values...",Toast.LENGTH_LONG).show();
            }
            else
            {
                progress.dismiss();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected String doInBackground(String... params)
        {
            String param1 = params[0];
            String param2 = params[1];
            String param3 = params[2];
            String readline ="";
            String url2 ="";
            String response ="";
            //System.out.println("dsc5");
            try {
                URL url = new URL("http://localhost/register.php?"+URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(param1,"UTF-8")
                        + "&" + URLEncoder.encode("usermail", "UTF-8") + "=" + URLEncoder.encode(param2, "UTF-8")
                        + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(param3, "UTF-8"));
                String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(param1, "UTF-8")
                        + URLEncoder.encode("usermail", "UTF-8") + "=" + URLEncoder.encode(param2, "UTF-8")
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(param3, "UTF-8");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setDoOutput(true);
                con.setUseCaches(false);
                con.setDoInput(true);
                URL url1 = con.getURL();
                url2 =  url1.toString();
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
            catch (IOException e)
            {
                e.printStackTrace();
            }
           return response;
        }
    }
}