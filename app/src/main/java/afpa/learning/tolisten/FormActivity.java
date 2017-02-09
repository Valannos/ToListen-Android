package afpa.learning.tolisten;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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

    public void sendForm(View view) {

        inputTitle = (EditText) findViewById(R.id.inputTitle);
        inputGenre = (EditText) findViewById(R.id.inputGenre);
        inputAuthor = (EditText) findViewById(R.id.inputAuthor);
        inputURL = (EditText) findViewById(R.id.inputURL);
        inputSender = (EditText) findViewById(R.id.inputSender);

        Media media = new Media(inputTitle.getText().toString(), inputURL.getText().toString(), inputAuthor.getText().toString(), inputGenre.getText().toString(), inputSender.getText().toString());

        if (!media.getAuthor().equals("") && !media.getGenre().equals("") && !media.getTitle().equals("") && !media.getUrl().equals("") && !media.getUser().equals("")) {


            try {

                URL url = new URL(APISettings.getURI(APISettings.URI.POST));
                HttpURLConnection client = (HttpURLConnection) url.openConnection();
                client.setReadTimeout(3000);
                client.setConnectTimeout(3000);
                client.setRequestMethod("POST");
                ContentValues cv = new ContentValues();
                cv.put("title", media.getTitle());
                cv.put("genre", media.getGenre());
                cv.put("author", media.getUrl());
              //  cv.put();


            } catch (MalformedURLException e) {

                e.getStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }


        }


    }


}
