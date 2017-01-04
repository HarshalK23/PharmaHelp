package com.techno_twit.harshal.pharmahelp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class Login extends Activity
{
    private static final String TAG = "LoginActivity";

    EditText _emailText;
    EditText _passwordText;
    Button _loginButton;
    TextView _signupLink;
    View defaultView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _emailText = (EditText)findViewById(R.id.input_email);
        _passwordText = (EditText)findViewById(R.id.input_password);
        _loginButton = (Button)findViewById(R.id.btn_login);
        _signupLink = (TextView)findViewById(R.id.link_signup);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(Login.this, Signup.class);
                startActivity(intent);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);
        defaultView = findViewById(android.R.id.content);

        final ProgressDialog progressDialog = new ProgressDialog(Login.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String username = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        Logincheck login=new Logincheck(username,password,progressDialog);
        login.execute();

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
            if (resultCode == RESULT_OK) {
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }

    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);

        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    private class Logincheck extends AsyncTask<Void,Void,String>{

        String username,password;
        ProgressDialog progressDialog;
        public Logincheck(String username, String password,ProgressDialog progressDialog){
            this.username=username;
            this.password=password;
            this.progressDialog=progressDialog;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url=new URL("http://"+Connectivity.getIpPort()+"/psr/login.php?username="+username);
                HttpURLConnection con=(HttpURLConnection)url.openConnection();

                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                con.setConnectTimeout(15000);
                con.setDoInput(true);
                con.setDoOutput(true);

                BufferedReader buff=new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder result=new StringBuilder();
                String line;
                while((line=buff.readLine())!=null){
                    result.append(line);
                }
                buff.close();
                return result.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public void onPostExecute(String result){
            if(result==null||result.contains("error")){
                Snackbar.make(defaultView,"Error try again",Snackbar.LENGTH_SHORT).show();
                Log.i("test", "fail");
            }else if(result.contains(password)){
                onLoginSuccess();
                Intent i = new Intent(Login.this, MainActivity.class);
                startActivity(i);
                Log.i("test","Success");
            }else{
                Snackbar.make(defaultView,"Password incorrect",Snackbar.LENGTH_SHORT).show();
                Log.i("test", "fail1");
            }
            _loginButton.setEnabled(true);
            progressDialog.dismiss();
        }
    }

}