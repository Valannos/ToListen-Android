package afpa.learning.tolisten.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Afpa on 13/02/2017.
 */

public class ImageProvider extends AsyncTask<String, Void, Bitmap> {

    private ListMediaAdapter parent;
    private String host;

    public ImageProvider(ListMediaAdapter parent) {
        this.parent = parent;
        this.host = "";
    }

    protected Bitmap doInBackground(String... urls) {
        Bitmap mIcon11 = null;
        try {
            String type = urls[0];
            this.host = urls[1];

            URL url = new URL(type, host, "/favicon.ico");

            URLConnection connection = url.openConnection();
            connection.connect();

            InputStream in = connection.getInputStream();
            BitmapFactory.Options options = new BitmapFactory.Options();
            mIcon11 = BitmapFactory.decodeStream(in, null, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        parent.addImage(host, result);
    }
}
