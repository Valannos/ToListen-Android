package afpa.learning.tolisten.model;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import afpa.learning.tolisten.APISettings;

/**
 * Created by Afpa on 14/02/2017.
 */

public class MediaRemover extends AsyncTask<Integer, Void, Boolean> {

    private Integer media_id;

    @Override
    protected Boolean doInBackground(Integer... params) {

        URL url;
        HttpURLConnection httpURLConnection = null;

        try {
            this.media_id = params[0];

            url = new URL(APISettings.getURI(APISettings.URI.DELETE) + media_id);
            System.out.println(url);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("DELETE");
            httpURLConnection.setDoOutput(false);
            httpURLConnection.setDoInput(false);

            httpURLConnection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            httpURLConnection.getResponseCode();
            System.out.println(httpURLConnection.getResponseCode());


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }


        return true;
    }
}
