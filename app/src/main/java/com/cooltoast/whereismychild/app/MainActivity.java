package com.cooltoast.whereismychild.app;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;


public class MainActivity extends Activity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Button b = (Button) findViewById(R.id.button1);
        final LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 600000, 10000, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
               Toast.makeText(MainActivity.this, "Latitude: " + location.getLatitude() + " Longitude: " + location.getLongitude(), Toast.LENGTH_SHORT).show();

               String date = DateUtils.formatElapsedTime(location.getTime());

               final String url = "http://where-is-my-child.herokuapp.com/submit_coordinates?latitude=" + location.getLatitude() + "&longitude=" + location.getLongitude() + "&timestamp=" + date;


               new Thread(new Runnable() {
                       @Override
                       public void run() {

                           try{
                               HttpClient httpclient = new DefaultHttpClient();

                               HttpGet request = new HttpGet();
                               URI website = new URI(url);
                               request.setURI(website);
                               httpclient.execute(request);
                           }
                           catch (Exception e)
                           {
                               e.printStackTrace();
                           }
                       }
               }).start();

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {
                Toast.makeText(MainActivity.this, "GPS enabled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onProviderDisabled(String s) {
                Toast.makeText(MainActivity.this, "GPS disabled", Toast.LENGTH_LONG).show();
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "hello", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
