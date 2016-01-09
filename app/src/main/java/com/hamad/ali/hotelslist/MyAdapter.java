package com.hamad.ali.hotelslist;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Ali on 1/9/2016.
 */
public class MyAdapter extends ArrayAdapter<Hotel> {

    Hotel[] hotelValues;

    public MyAdapter(Context context, Hotel[] values) {

        super(context, R.layout.hotel_layout,values);
        hotelValues=values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater theInflater= LayoutInflater.from(getContext());

        View theView=theInflater.inflate(R.layout.hotel_layout, parent, false);


        ImageView hotelImage=(ImageView)theView.findViewById(R.id.image_hotel);
        TextView name=(TextView)theView.findViewById(R.id.name_hotel_textView);
        TextView avgPriceRating=(TextView)theView.findViewById(R.id.avgRating_hotel_textView);
        TextView distance=(TextView)theView.findViewById(R.id.distance_hotel_textView);
        RatingBar rating=(RatingBar)theView.findViewById(R.id.ratingBar);
        TextView payment=(TextView)theView.findViewById(R.id.payment_textView);

        //nothing
        TextView avgNightStatement=(TextView)theView.findViewById(R.id.avgStatement_hotel_textView);


        name.setText(hotelValues[position].getName().toString());
        avgPriceRating.setText(hotelValues[position].getAvgRate() + " $");
        distance.setText(hotelValues[position].getDistance().toString());
        rating.setNumStars(hotelValues[position].getStars());

        hotelImage.setImageURI(Uri.parse(hotelValues[position].getCoverImage().toString()));

        if(hotelValues[position].is_cardLess()){
            payment.setText("card less");
            payment.setBackgroundColor(Color.parseColor("#00FF99"));
        }else if(hotelValues[position].isPost_paid()){
            payment.setText("Pay at hotel");
            payment.setBackgroundColor(Color.parseColor("#0033FF"));
        }
        else if(hotelValues[position].isPre_paid()){
            payment.setText("Pay Now");
            payment.setBackgroundColor(0xF00000);
        }


        new DownloadImageTask(hotelImage)
                .execute(hotelValues[position].getCoverImage().toString());
        return theView;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}