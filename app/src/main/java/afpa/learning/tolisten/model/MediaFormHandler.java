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
 * Created by Afpa on 13/02/2017.
 */

public class MediaFormHandler extends AsyncTask<JSONObject, Void, String> {

    protected JSONObject jsonMedia;

    public MediaFormHandler(JSONObject jsonMedia) {
        this.jsonMedia = jsonMedia;
    }

    @Override
    protected String doInBackground(JSONObject... params) {
        HttpURLConnection connection = null;
        StringBuffer response = null;

        try {


            URL url = new URL(APISettings.getURI(APISettings.URI.POST));
            connection = (HttpURLConnection) url.openConnection();

            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            Logger.getLogger(FormActivity.class.getName()).log(Level.INFO, "Connection to " + APISettings.getURI(APISettings.URI.POST));


            DataOutputStream dos = new DataOutputStream(connection.getOutputStream());

            dos.writeBytes(this.jsonMedia.toString());
            dos.flush();


            dos.close();


            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            response = new StringBuffer();
            while ((line = br.readLine()) != null) {

                response.append(line);
                response.append('\r');
            }
            br.close();


            System.out.println(response.toString());


        } catch (MalformedURLException e) {

            e.getStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
        }

        return response.toString();
    }
}



