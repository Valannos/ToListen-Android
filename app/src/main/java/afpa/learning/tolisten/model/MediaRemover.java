package afpa.learning.tolisten.model;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import afpa.learning.tolisten.APISettings;

/**
 * Created by Afpa on 14/02/2017.
 */

public class MediaRemover extends AsyncTask<Integer, Void, String> {

    private Integer media_id;

    @Override
    protected String doInBackground(Integer... params) {
        StringBuilder sb = null;
        URL url;
        HttpURLConnection httpURLConnection = null;

        try {
            this.media_id = params[0];

            url = new URL(APISettings.getURI(APISettings.URI.DELETE) + media_id);
            System.out.println(url);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("DELETE");
            httpURLConnection.setDoOutput(false);
            httpURLConnection.setDoInput(true);

            httpURLConnection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            httpURLConnection.getResponseCode();
            System.out.println(httpURLConnection.getResponseCode());

            BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {


                sb.append(line);

            }
            br.close();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }


        return sb.toString();
    }
}
