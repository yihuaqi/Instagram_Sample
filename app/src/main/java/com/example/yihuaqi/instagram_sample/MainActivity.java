package com.example.yihuaqi.instagram_sample;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;


import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {

    private String tag = "Instagram";
    private Button loginBtn;
    private Button getPictureBtn;
    private InstagramApp mApp;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mApp = new InstagramApp(this,ApplicationData.CLIENT_ID,ApplicationData.CLIENT_SECRET,ApplicationData.CALLBACKURL);
        mApp.setListener(listener);


        loginBtn = (Button) findViewById(R.id.LoginBtn);
        getPictureBtn = (Button) findViewById(R.id.GetPictureBtn);
        listView = (ListView) findViewById(R.id.listView);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mApp.hasAccessToken()){
                    final AlertDialog.Builder builder = new AlertDialog.Builder(
                            MainActivity.this);
                    builder.setMessage("Logout from Instagram?")
                            .setCancelable(false)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog, int id) {
                                            mApp.resetAccessToken();
                                            loginBtn.setText("Login");

                                        }
                                    })
                            .setNegativeButton("No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                    final AlertDialog alert = builder.create();
                    alert.show();
                } else {
                    mApp.authorize();
                }
            }
        });

        getPictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(tag,"Click on button getPicture");
                new GetPictureTask().execute();
            }
        });

        if(mApp.hasAccessToken()){
            loginBtn.setText("Logout");
        }
    }

    public class GetPictureTask extends AsyncTask<Void,Void,ArrayList<ImageURL>> {

        @Override
        protected ArrayList<ImageURL> doInBackground(Void... params) {
            return mApp.getImages();

        }

        @Override
        protected void onPostExecute(ArrayList<ImageURL> images) {
            listView.setAdapter(new ImageAdapter(MainActivity.this,images));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ImageURL imageUrl = (ImageURL)listView.getItemAtPosition(position);
                    ImageView imageView = ((ImageAdapter.ViewHolder)view.getTag()).imageView;
                    new ImageDownloaderTask(imageView,false).execute(imageUrl.getImageUrl());
                }
            });

        }
    }




    InstagramApp.OAuthAuthenticationListener listener = new InstagramApp.OAuthAuthenticationListener() {
        @Override
        public void onSuccess() {
            Log.d(tag,"Connected as "+mApp.getName());
            loginBtn.setText("Logout");
        }

        @Override
        public void onFail(String error) {
            Log.d(tag,"Failed: "+error);
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
