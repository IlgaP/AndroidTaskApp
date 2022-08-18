package com.example.taskapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialButton btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(view -> {


                   EditText inputUsername = findViewById(R.id.user_name);
                   EditText inputPassword = findViewById(R.id.password);

                   String username = String.valueOf(inputUsername.getText());
                   String password = String.valueOf(inputPassword.getText());

                   try {
                       URL url = new URL("https://engine.free.beeceptor.com/api/login");
                       HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                       connection.setRequestMethod("POST");
                       connection.addRequestProperty("username", username);
                       connection.addRequestProperty("password", password);
                       connection.connect();
                       int responseCode = connection.getResponseCode();
                       Toast.makeText(getApplicationContext(), "code: " + responseCode, Toast.LENGTH_LONG).show();
                   } catch (Exception e) {
                       e.printStackTrace();
                   }




//            if(username.equals("test") && password.equals("1234")){
//                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
//            }else {
//                Toast.makeText(getApplicationContext(),"Failure" , Toast.LENGTH_LONG).show();
//            }

        });



    }
}