package com.hamad.ali.hotelslist;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String url = "https://api-staging.yamsafer.me/en/api/v3/mobile/cities/625?limit=0&checkin_date=05/13/2015&checkout_date=05/14/2015&api_key=5c75aabcc20a33be13a4466f155d9c7c";


    Hotel[] hotelFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //checks the internet connection
        if (isNetworkConnected()) {

            //if the connection available call fill AsyncTask to fetch hotels data in background
            new FillData().execute();
        } else {

            //if there is no connection available change the background image to no internet connection
            ImageView backgroundImg = (ImageView) findViewById(R.id.loading_temp_img);
            backgroundImg.setImageResource(R.drawable.yamsafer_no_internet);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //made sure nothing left behind
        finish();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    //FillData AsyncTask to fetch hotels data in background
    class FillData extends AsyncTask<Void, Void, Void> {

        CityData response;
        ResponseData responseHotels;
        List<Hotel> list;

        @Override
        protected Void doInBackground(Void... params) {

            //start the connection and fetching hotels data
            InputStream source = retrieveStream(url);
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(source);

            //parsing the json response to extract the hotels list data
            response = gson.fromJson(reader, CityData.class);
            responseHotels = response.getCity();
            list = responseHotels.getData();

            return null;
        }

        @Override

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //initialize the hotels list
            hotelFinal = new Hotel[list.size()];

            //fill the hotels list to pass it to the list view adapter
            int i=0;
            for (Hotel hotel : list) {
                hotelFinal[i] = hotel;
                i++;
            }

            //fill the list view with hotels data
            fillHotelsListView();
        }

        //fetching response stream
        private InputStream retrieveStream(String url) {

            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet getRequest = new HttpGet(url);
            try {
                HttpResponse getResponse = client.execute(getRequest);
                final int statusCode = getResponse.getStatusLine().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    Log.w(getClass().getSimpleName(),
                            "Error " + statusCode + " for URL " + url);
                    return null;
                }
                HttpEntity getResponseEntity = getResponse.getEntity();

                return getResponseEntity.getContent();

            } catch (IOException e) {
                getRequest.abort();
                Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
            }
            return null;
        }
    }

    //fill the list view with hotels data and show it
    private void fillHotelsListView() {
        ListAdapter theAdapter = new MyAdapter(getApplicationContext(), hotelFinal);
        ListView theListView = (ListView) findViewById(R.id.hotels_listView);
        theListView.setAdapter(theAdapter);

        //remove the waiting loading image background after loading hotels
        ImageView loadingImg = (ImageView) findViewById(R.id.loading_temp_img);
        loadingImg.setVisibility(View.GONE);
    }
}
