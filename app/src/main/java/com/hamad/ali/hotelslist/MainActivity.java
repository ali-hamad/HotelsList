package com.hamad.ali.hotelslist;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new fillData().execute();

        }

    class fillData extends AsyncTask<Void, Void, Void> {

        CityData response;
        ResponseData responseHotels;
        List<Hotel> list;

        String str="wait";

        @Override
        protected Void doInBackground(Void... params) {
            InputStream source = retrieveStream(url);
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(source);

            response=gson.fromJson(reader, CityData.class);

            responseHotels=response.getCity();

            list=responseHotels.getData();


            return null;
        }

        @Override

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            int count=0,i=0;
            for(Hotel hotel:list){
                count++;
            }
            hotelFinal=new Hotel[count];

            for(Hotel hotel:list){
                hotelFinal[i]=hotel;
                i++;
            }
            ListAdapter theAdapter = new MyAdapter(getApplicationContext(), hotelFinal);
            ListView theListView = (ListView) findViewById(R.id.hotels_listView);
            theListView.setAdapter(theAdapter);
            ImageView loadingImg=(ImageView)findViewById(R.id.loading_temp_img);
            loadingImg.setVisibility(View.GONE);


        }

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

            }

            catch (IOException e) {

                getRequest.abort();

                Log.w(getClass().getSimpleName(), "Error for URL " + url, e);

            }

            return null;

        }


    }
}
