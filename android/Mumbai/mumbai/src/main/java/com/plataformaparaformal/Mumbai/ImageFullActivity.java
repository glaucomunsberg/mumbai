package com.plataformaparaformal.Mumbai;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;


public class ImageFullActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_full);

        String anddress = "";
        Bundle extras;
        URL newurl = null;
        Bitmap mIcon_val = null;

        if(savedInstanceState == null){
            extras = getIntent().getExtras();
            if(extras== null){
                anddress = null;
            }else{
                anddress = extras.getString("urlAnddress");
            }
        }else{
            anddress = (String) savedInstanceState.getSerializable("urlAnddress");
        }

        if(anddress != null){
            try {
                newurl = new URL(anddress);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            ImageView img = (ImageView) findViewById(R.id.image_img);
            img.setImageBitmap(mIcon_val);
        }

        getActionBar().setTitle(R.string.imageFull_title);
        getActionBar().setSubtitle(R.string.imageFull_subTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.image_full, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

}
