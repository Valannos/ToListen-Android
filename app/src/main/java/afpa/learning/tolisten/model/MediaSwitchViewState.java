package afpa.learning.tolisten.model;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import afpa.learning.tolisten.APISettings;

/**
 * Created by Afpa on 14/02/2017.
 */

public class MediaSwitchViewState extends AsyncTask<Integer, Void, Boolean> {

    protected Integer mediaId;

    public MediaSwitchViewState(Integer mediaId) {

        this.mediaId = mediaId;
    }

    @Override
    protected Boolean doInBackground(Integer... params) {

        HttpURLConnection urlConnection = null;
        StringBuffer response = null;
        DataOutputStream dos;

        try {


            URL url = new URL(APISettings.getURI(APISettings.URI.SWITCH_VIEW_STATE));
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(3000);
            urlConnection.setConnectTimeout(3000);

            urlConnection.setRequestMethod("PUT");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("mediaId", mediaId);

            dos = new DataOutputStream(urlConnection.getOutputStream());


            dos.writeBytes(jsonObject.toString());
            dos.flush();
            dos.close();

           // urlConnection.getInputStream();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }


        return true;
    }
}
