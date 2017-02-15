package afpa.learning.tolisten.model;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import afpa.learning.tolisten.APISettings;

/**
 * Created by Afpa on 15/02/2017.
 */

public class MediaEditor extends AsyncTask<JSONObject, Void, String> {


    @Override
    protected String doInBackground(JSONObject... params) {

        String response = "";

        StringBuilder sb = null;
        URL url;
        HttpURLConnection httpURLConnection = null;

        try {
            url = new URL(APISettings.getURI(APISettings.URI.UPDATE));
            httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setReadTimeout(3000);
            httpURLConnection.setConnectTimeout(3000);

            httpURLConnection.setRequestMethod("PUT");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return response;
    }
}
