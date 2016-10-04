package koster.kosterpset3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RetrievePoster extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView;

    public RetrievePoster(ImageView imageView)
    {
        this.imageView = imageView;
    }


    /* Retrieves poster image from the API and stores it into a bitmap */
    protected Bitmap doInBackground(String... urls) {
        String imageurl = urls[0];

        try {
            URL url = new URL(imageurl);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            return bmp;
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    protected void onPostExecute(Bitmap result)
    {
        imageView.setImageBitmap(result);
    }
}
