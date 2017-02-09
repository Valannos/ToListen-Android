package afpa.learning.tolisten;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import afpa.learning.tolisten.model.Media;

/**
 * Created by Afpa on 08/02/2017.
 */

public class FormActivity extends Activity {

    private EditText inputTitle;
    private EditText inputGenre;
    private EditText inputAuthor;
    private EditText inputURL;
    private EditText inputSender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

    }

    /**
     * Get and check media formular fields and send it to server throw POST request
     *
     * @param view
     */
    public void sendForm(View view) {

        inputTitle = (EditText) findViewById(R.id.inputTitle);
        inputGenre = (EditText) findViewById(R.id.inputGenre);
        inputAuthor = (EditText) findViewById(R.id.inputAuthor);
        inputURL = (EditText) findViewById(R.id.inputURL);
        inputSender = (EditText) findViewById(R.id.inputSender);

        Media media = new Media(inputTitle.getText().toString(), inputURL.getText().toString(), inputAuthor.getText().toString(), inputGenre.getText().toString(), inputSender.getText().toString());

        if (!media.getAuthor().equals("") && !media.getGenre().equals("") && !media.getTitle().equals("") && !media.getUrl().equals("") && !media.getUser().equals("")) {



       /*     try {
                data = URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(media.getTitle(), "UTF-8");
                data += "&" + URLEncoder.encode("url", "UTF-8") + "=" + URLEncoder.encode(media.getUrl(), "UTF-8");
                data += "&" + URLEncoder.encode("sender", "UTF-8") + "=" + URLEncoder.encode((media.getUser()), "UTF-8");
                data += "&" + URLEncoder.encode("genre", "UTF-8") + "=" + URLEncoder.encode(media.getGenre(), "UTF-8");
                data += "&" + URLEncoder.encode("author", "UTF-8") + "=" + URLEncoder.encode(media.getAuthor(), "UTF-8");

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
*/


            JSONObject json = new JSONObject();
            try {
                json.put("title", media.getTitle());
                json.put("url", media.getUrl());
                json.put("genre", media.getGenre());
                json.put("sender", media.getUser());
                json.put("author", media.getAuthor());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Logger.getLogger(FormActivity.class.getName()).log(Level.INFO, json.toString());

            BufferedReader br;
            try {


                URL url = new URL(APISettings.getURI(APISettings.URI.POST));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(3000);
                connection.setConnectTimeout(3000);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

                OutputStreamWriter otw = new OutputStreamWriter(connection.getOutputStream());
                otw.write(json.toString());
                otw.flush();


                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {

                    sb.append(line + "\n");

                }


                br.close();
                Logger.getLogger(FormActivity.class.getName()).log(Level.INFO, "Response : " + sb.toString());

                if (sb.toString() == "200") {

                    Toast.makeText(getBaseContext(), sb.toString(), Toast.LENGTH_LONG).show();
                    this.finish();


                }

            } catch (MalformedURLException e) {

                e.getStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }


        }


    }

    public void quitForm(View view) {

        this.finish();

    }

}


