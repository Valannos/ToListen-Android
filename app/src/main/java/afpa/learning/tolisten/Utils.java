package afpa.learning.tolisten;

import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import afpa.learning.tolisten.model.Media;



public class Utils {


    /**
     * Taken from http://stackoverflow.com/questions/9767952/how-to-add-parameters-to-httpurlconnection-using-post
     */

    public static String stringifyPostData(HashMap<String, Integer> map) throws UnsupportedEncodingException {

        StringBuilder sb = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, Integer> entry : map.entrySet()) {


            if (first) {
                first = false;
            } else {
                sb.append("&");
            }
            sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            sb.append("=");
            sb.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));


        }


        System.out.print(sb.toString());
        return sb.toString();
    }

    public static Intent fillIntentFromMedia(Intent intent, Media media) {

        intent.putExtra("id", media.getId());
        intent.putExtra("url", media.getUrl());
        intent.putExtra("sender", media.getSender());
        intent.putExtra("genre", media.getGenre());
        intent.putExtra("author", media.getAuthor());
        intent.putExtra("title", media.getTitle());
        intent.putExtra("isViewed", media.isViewed());

        return intent;

    }

    public static Intent fillIntentFormJSON(Intent intent, JSONObject json) throws JSONException {

        intent.putExtra("id", json.getInt("id"));
        intent.putExtra("url", json.getString("url"));
        intent.putExtra("sender", json.getString("sender"));
        intent.putExtra("genre", json.getString("genre"));
        intent.putExtra("author", json.getString("author"));
        intent.putExtra("title", json.getString("title"));
        Integer viewedInt = json.getInt("isViewed");
        intent.putExtra("isViewed", viewedInt != 0 ? true : false);


        return intent;
    }

    public static Media setMediaWithIntent(Intent intent, Media media){

        media.setId(intent.getExtras().getInt("id"));
        media.setTitle(intent.getExtras().getString("title"));
        media.setUrl(intent.getExtras().getString("url"));
        media.setAuthor(intent.getExtras().getString("author"));
        media.setGenre(intent.getExtras().getString("genre"));
        media.setSender(intent.getExtras().getString("sender"));
        media.setViewed(intent.getExtras().getBoolean("isViewed"));


        return media;
    }


}
