package com.example.taskapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;


import java.net.HttpURLConnection;
import java.net.URL;


public class LoginActivity extends AppCompatActivity {

    static final String apiUrl = "https://engine.free.beeceptor.com/api/";
    MaterialButton btnLogin;
    EditText inputUsername;
    EditText inputPassword;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btn_login);
        inputUsername = findViewById(R.id.user_name);
        inputPassword = findViewById(R.id.password);

        btnLogin.setOnClickListener(view -> {
            LoginTask loginTask = new LoginTask();
            loginTask.execute();
        });
    }

    private class LoginTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String responseCode = "";
            try {
                URL url = new URL(apiUrl + "login");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setRequestProperty("username", inputUsername.getText().toString());
                connection.setRequestProperty("password", inputPassword.getText().toString());
                connection.connect();
                responseCode = String.valueOf(connection.getResponseCode());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return responseCode;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            if (s.equals("200") &&
                    inputUsername.getText().toString().equals("test") &&
                    inputPassword.getText().toString().equals("1234")) {
                Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_SHORT).show();
                openMainActivity();
            } else {
                Toast.makeText(getApplicationContext(), "Login not successful!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void openMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}