package afpa.learning.tolisten.model;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import afpa.learning.tolisten.APISettings;
import afpa.learning.tolisten.FormActivity;

/**
 * Created by Afpa on 15/02/2017.
 */

public class MediaEditor extends AsyncTask<JSONObject, Void, String> {


    @Override
    protected String doInBackground(JSONObject... params) {



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

            Logger.getLogger(FormActivity.class.getName()).log(Level.INFO, "Connection to " + APISettings.getURI(APISettings.URI.UPDATE));

            DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());
            dos.writeBytes(params[0].toString());
            dos.flush();
            dos.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String line;
            sb = new StringBuilder();

            while ((line = br.readLine()) != null) {

                sb.append(line);

            }
            br.close();
            System.out.println("Response recieved :" + sb.toString());


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) httpURLConnection.disconnect();
        }


        return sb.toString();
    }
}
