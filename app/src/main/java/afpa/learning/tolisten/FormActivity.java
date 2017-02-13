package afpa.learning.tolisten;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

import afpa.learning.tolisten.model.Media;
import afpa.learning.tolisten.model.MediaFormHandler;

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
            MediaFormHandler mfh = new MediaFormHandler(json);
            mfh.execute();


        } else {

            Logger.getLogger(FormActivity.class.getName()).log(Level.INFO, "Some fields are empty");

        }

        Logger.getLogger(FormActivity.class.getName()).log(Level.INFO, "Done");
        Toast.makeText(this, R.string.toastForm, Toast.LENGTH_LONG).show();
        this.finish();


    }

    public void quitForm(View view) {

        this.finish();

    }

}


