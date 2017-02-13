package afpa.learning.tolisten.model;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import afpa.learning.tolisten.APISettings;

/**
 * Created by Afpa on 08/02/2017.
 */

public class MediaProvider extends AsyncTask<String, Void, ArrayList<Media>> {


    @Override
    protected void onPostExecute(ArrayList<Media> medias) {
        super.onPostExecute(medias);
    }

    @Override
    protected ArrayList<Media> doInBackground(String... params) {

        ArrayList<Media> medias = null;

        StringBuilder sb = new StringBuilder();
        HttpURLConnection urlConnection;


        try {

            URL url = new URL(APISettings.getURI(APISettings.URI.GET_ALL));
            System.out.println(url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(3000);
            urlConnection.setConnectTimeout(3000);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.connect();


            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println("Connection established, retrieving data");
                InputStream inst = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(inst));


                String line;
                while ((line = br.readLine()) != null) {

                    sb.append(line);

                }
                br.close();

            } else {

            System.out.println("Error connecting server");

            }
            urlConnection.disconnect();


            JSONArray jsonArray = new JSONArray(sb.toString());
            medias = new ArrayList<>(jsonArray.length());

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int media_id = jsonObject.getInt("id");
                String media_url = jsonObject.getString("url");
                String media_sender = jsonObject.getString("sender");
                String media_genre = jsonObject.getString("genre");
                String media_author = jsonObject.getString("author");
                String media_title = jsonObject.getString("title");
                boolean media_isViewed = jsonObject.getInt("isViewed") == 1 ? true : false;

                Media media = new Media(media_id, media_title, media_url, media_author, media_genre, media_sender, media_isViewed);
                medias.add(media);

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return medias;
    }
}