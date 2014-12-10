package com.example.yihuaqi.instagram_sample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import java.io.InputStream;
import java.util.Random;

/**
 * Created by yihuaqi on 2014/12/10.
 */
public class ImageDownloaderTask extends AsyncTask<String,Void,Bitmap> {
    private ImageView imageView;
    private boolean scaleDown;
    public ImageDownloaderTask(ImageView imageView, boolean scaleDown){
        this.imageView = imageView;
        this.scaleDown = scaleDown;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if(isCancelled()){
            bitmap = null;
        }
        if(imageView !=null){
            if(bitmap!=null){
                imageView.setImageBitmap(bitmap);

            } else {

            }
        }
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        return downloadBitmap(params[0]);
    }

    Bitmap downloadBitmap(String url){
        final AndroidHttpClient client = AndroidHttpClient.newInstance("Android");
        final HttpGet getRequest = new HttpGet(url);
        try{
            HttpResponse response = client.execute(getRequest);
            final int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode!= HttpStatus.SC_OK){
                Log.d("Instagram","Error "+statusCode+" while retrieving bitmap from "+url);
                return null;
            }
            final HttpEntity entity = response.getEntity();
            if(entity!=null){
                InputStream inputStream = null;
                try{
                    inputStream = entity.getContent();
                    BitmapFactory.Options options = new BitmapFactory.Options();

                    if(scaleDown){
                        options.inSampleSize = 2;
                    } else {
                        options.inSampleSize = 1;
                    }

                    final Bitmap bitmap = BitmapFactory.decodeStream(inputStream,null,options);
                    return bitmap;
                } finally {
                    if(inputStream!=null){
                        inputStream.close();
                    }
                    entity.consumeContent();
                }
            }

        } catch (Exception e ){
            getRequest.abort();

        } finally {
            if(client !=null){
                client.close();
            }
        }
        return null;
    }
}
