package com.example.taskapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerviewAdapter.RecyclerViewClickListener listener;

    String mainUrl = "https://engine.free.beeceptor.com/api/getServices";

    ArrayList<String> nameList = new ArrayList<>();
    ArrayList<String> idList = new ArrayList<>();
    int[] img = {R.drawable.ic_baseline_sports_rugby_24_white,
            R.drawable.ic_baseline_sports_basketball_24_white,
            R.drawable.ic_baseline_sports_cricket_24_white,
            R.drawable.ic_baseline_sports_mma_24_white,
            R.drawable.ic_baseline_sports_rugby_24_white,
            R.drawable.ic_baseline_sports_soccer_24_white};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainTask mainTask = new MainTask();
        mainTask.execute();

        recyclerView =findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private class MainTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String responseText = "";
            try {
                URL url = new URL(mainUrl);
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
            super.onPostExecute(s);
            if (!s.isEmpty()) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                nameList.clear();
                idList.clear();
                try {
                    assert jsonArray != null;
                    jsonArrayToList(jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            setOnClickListener();
            RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder> adapter = new RecyclerviewAdapter(MainActivity.this, nameList, idList, img, listener);
            recyclerView.setAdapter(adapter);
                   }
    }

    private void jsonArrayToList(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject items = jsonArray.getJSONObject(i);
            String name = items.getString("name");
            nameList.add(name);
            String id = items.getString("id");
            idList.add(id);
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

    private void setOnClickListener() {
        listener = (view, position) -> {
            Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
            intent.putExtra("id", idList.get(position));
            startActivity(intent);
        };
    }
}