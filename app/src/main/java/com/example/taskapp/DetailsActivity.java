package com.example.taskapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class DetailsActivity extends AppCompatActivity {

    String detailsUrl = "https://engine.free.beeceptor.com/api/getSportDetails?sportId=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        String id ="";
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            id = extras.getString("id");
        }
        detailsUrl = detailsUrl + id;

        DetailsTask detailsTask = new DetailsTask();
        detailsTask.execute();
    }

    private class DetailsTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String responseText = "";
            try {
                URL url = new URL(detailsUrl);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.connect();
                responseText = getResponseText(connection);
                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return responseText;
        }

        @Override
        protected void onPostExecute(String s) {
            String name = "";
            String address = "";
            String phone = "";
            String price = "";
            String currency ="";

            super.onPostExecute(s);
            if (!s.isEmpty()) {
                JSONObject responseObject = null;
                try {
                    responseObject = new JSONObject(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    if (responseObject != null) {
                        name = responseObject.getString("name");
                        address = responseObject.getString("address");
                        phone = responseObject.getString("phone");
                        price = responseObject.getString("price");
                        currency = responseObject.getString("currency");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                TextView textViewName = findViewById(R.id.textViewName);
                TextView textViewAddress = findViewById(R.id.textViewAddress);
                TextView textViewPhone = findViewById(R.id.textViewPhone);
                TextView textViewPrice = findViewById(R.id.textViewPrice);

                textViewName.setText(name);
                textViewAddress.setText(address);
                textViewPhone.setText(phone);
                String priceCurrency = price + " " + currency;
                textViewPrice.setText(priceCurrency);
            }
        }
    }

    @NonNull
    private String getResponseText(HttpURLConnection connection) throws IOException {
        String responseText;
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            responseText = response.toString();
        }
        return responseText;
    }

}