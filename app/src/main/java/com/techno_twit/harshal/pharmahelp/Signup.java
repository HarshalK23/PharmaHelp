package com.techno_twit.harshal.pharmahelp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Signup extends AppCompatActivity {
        private static final String TAG = "SignupActivity";

        EditText _nameText;
        EditText _emailText;
        EditText _passwordText;
        Button _signupButton;
        TextView _loginLink;
        View defaultView;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_signup);

            _nameText = (EditText)findViewById(R.id.input_name);
            _emailText = (EditText)findViewById(R.id.input_email);
            _passwordText = (EditText)findViewById(R.id.input_password);
            _signupButton = (Button)findViewById(R.id.btn_signup);
            _loginLink = (TextView)findViewById(R.id.link_login);

            _signupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signup();
                }
            });

            _loginLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Finish the registration screen and return to the Login activity
                    finish();
                }
            });
        }

        public void signup() {
            Log.d(TAG, "Signup");
            defaultView = findViewById(android.R.id.content);

            if (!validate()) {
                onSignupFailed();
                return;
            }

            _signupButton.setEnabled(false);

            final ProgressDialog progressDialog = new ProgressDialog(Signup.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Creating Account...");
            progressDialog.show();

            String name = _nameText.getText().toString();
            String email = _emailText.getText().toString();
            String password = _passwordText.getText().toString();

            Register register=new Register(name,password,email,"customer",progressDialog);
            register.execute();
        }


        public void onSignupSuccess() {
            _signupButton.setEnabled(true);
            setResult(RESULT_OK, null);
            finish();
        }

        public void onSignupFailed() {
            Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

            _signupButton.setEnabled(true);
        }

        public boolean validate() {
            boolean valid = true;

            String name = _nameText.getText().toString();
            String email = _emailText.getText().toString();
            String password = _passwordText.getText().toString();

            if (name.isEmpty() || name.length() < 3) {
                _nameText.setError("at least 3 characters");
                valid = false;
            } else {
                _nameText.setError(null);
            }

            if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
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
    private class Register extends AsyncTask<Void,Void,String> {

        String username,password,email,role;
        ProgressDialog progressDialog;

        public Register(String username, String password,String email,String role,ProgressDialog progressDialog){
            this.username=username;
            this.password=password;
            this.email=email;
            this.role=role;
            this.progressDialog=progressDialog;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url=new URL("http://"+Connectivity.getIpPort()+"/psr/register.php?username="+username+"&password="+password
                +"&email="+email+"&role="+role);
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

                return result.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public void onPostExecute(String result){
            if(result==null||result.contains("error")){
                Snackbar.make(defaultView, "Error try again", Snackbar.LENGTH_SHORT).show();
                onSignupFailed();
                Log.i("test", "fail");
            }else{
                Snackbar.make(defaultView,"Success",Snackbar.LENGTH_SHORT).show();
                onSignupSuccess();
            }
            progressDialog.dismiss();
        }
    }
    }

