package afpa.learning.tolisten;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import afpa.learning.tolisten.model.Media;
import afpa.learning.tolisten.model.MediaEditor;
import afpa.learning.tolisten.model.MediaFormHandler;

/**
 * Created by Afpa on 08/02/2017.
 */

public class FormActivity extends ListMenu {

    private TextView appTitle;
    private EditText inputTitle;
    private EditText inputGenre;
    private EditText inputAuthor;
    private EditText inputURL;
    private EditText inputSender;
    private Button btnValid;
    private Button btnEdit;
    private Media editedMedia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        if (intent.getExtras().getBoolean("edition")) {


            appTitle = (TextView) findViewById(R.id.AppTitle);
            appTitle.setText(R.string.editTitleForm);

            btnValid = (Button) findViewById(R.id.btnValid);
            btnValid.setVisibility(View.INVISIBLE);
            btnValid.setEnabled(false);

            btnEdit = (Button) findViewById(R.id.editButton);
            btnEdit.setVisibility(View.VISIBLE);
            btnEdit.setEnabled(true);

            inputTitle = (EditText) findViewById(R.id.inputTitle);
            inputGenre = (EditText) findViewById(R.id.inputGenre);
            inputAuthor = (EditText) findViewById(R.id.inputAuthor);
            inputURL = (EditText) findViewById(R.id.inputURL);
            inputSender = (EditText) findViewById(R.id.inputSender);

            editedMedia = new Media(getIntent().getExtras());

            inputTitle.setText(editedMedia.getTitle());
            inputGenre.setText(editedMedia.getGenre());
            inputAuthor.setText(editedMedia.getAuthor());
            inputSender.setText(editedMedia.getUser());
            inputURL.setText(editedMedia.getUrl());


        }


    }

    /**
     * Get and check media formular fields and send it to server throw POST request
     *
     * @param view
     */
    private void sendForm(View view) throws JSONException {

        inputTitle = (EditText) findViewById(R.id.inputTitle);
        inputGenre = (EditText) findViewById(R.id.inputGenre);
        inputAuthor = (EditText) findViewById(R.id.inputAuthor);
        inputURL = (EditText) findViewById(R.id.inputURL);
        inputSender = (EditText) findViewById(R.id.inputSender);
        String result = "";
        Intent returnIntent;


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
            try {
                result = mfh.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            //   Logger.getLogger(FormActivity.class.getName()).log(Level.INFO, "Done");
            Toast.makeText(this, R.string.toastForm, Toast.LENGTH_LONG).show();

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            returnIntent = new Intent();
            returnIntent.putExtra("id", jsonObject.getInt("id"));
            returnIntent.putExtra("url", jsonObject.getString("url"));
            returnIntent.putExtra("sender", jsonObject.getString("sender"));
            returnIntent.putExtra("genre", jsonObject.getString("genre"));
            returnIntent.putExtra("author", jsonObject.getString("author"));
            returnIntent.putExtra("title", jsonObject.getString("title"));

            Integer viewedInt = jsonObject.getInt("isViewed");

            if (viewedInt == 0) {
                returnIntent.putExtra("isViewed", Boolean.FALSE);
            } else {
                returnIntent.putExtra("isViewed", Boolean.TRUE);

            }

            this.setResult(RESULT_OK, returnIntent);
            this.finish();


        } else {

            Logger.getLogger(FormActivity.class.getName()).log(Level.INFO, "Some fields are empty");
            Toast.makeText(this, R.string.toastEmptyFields, Toast.LENGTH_LONG).show();

        }


    }

    public void quitForm(View view) {

        this.finish();

    }

    public void sendEditForm(View view) throws JSONException {

        inputTitle = (EditText) findViewById(R.id.inputTitle);
        inputGenre = (EditText) findViewById(R.id.inputGenre);
        inputAuthor = (EditText) findViewById(R.id.inputAuthor);
        inputURL = (EditText) findViewById(R.id.inputURL);
        inputSender = (EditText) findViewById(R.id.inputSender);
        String result = "";
        Intent returnIntent;


        Media media = new Media(inputTitle.getText().toString(), inputURL.getText().toString(), inputAuthor.getText().toString(), inputGenre.getText().toString(), inputSender.getText().toString());
        media.setId(editedMedia.getId());
        if (!media.getAuthor().equals("") && !media.getGenre().equals("") && !media.getTitle().equals("") && !media.getUrl().equals("") && !media.getUser().equals("")) {

            JSONObject json = new JSONObject();
            try {
                json.put("id", editedMedia.getId());
                json.put("title", media.getTitle());
                json.put("url", media.getUrl());
                json.put("genre", media.getGenre());
                json.put("sender", media.getUser());
                json.put("author", media.getAuthor());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Logger.getLogger(FormActivity.class.getName()).log(Level.SEVERE, json.toString());
            MediaEditor editor = new MediaEditor();
            editor.execute(json);
            try {
                result = editor.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            Logger.getLogger(FormActivity.class.getName()).log(Level.INFO, "Update Done");
            Toast.makeText(this, R.string.toastEditOK, Toast.LENGTH_LONG).show();

            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(result);
                returnIntent = new Intent();
                returnIntent.putExtra("id", jsonObject.getInt("id"));
                returnIntent.putExtra("url", jsonObject.getString("url"));
                returnIntent.putExtra("sender", jsonObject.getString("sender"));
                returnIntent.putExtra("genre", jsonObject.getString("genre"));
                returnIntent.putExtra("author", jsonObject.getString("author"));
                returnIntent.putExtra("title", jsonObject.getString("title"));

                Integer viewedInt = jsonObject.getInt("isViewed");

                returnIntent.putExtra("isViewed", viewedInt != 0 ? true : false);
                Logger.getLogger(FormActivity.class.getName()).log(Level.SEVERE, json.toString());


                this.setResult(RESULT_OK, returnIntent);
                this.finish();
            } catch (JSONException e) {
                e.printStackTrace();
            }



        } else {

            Logger.getLogger(FormActivity.class.getName()).log(Level.INFO, "Some fields are empty");
            Toast.makeText(this, R.string.toastEmptyFields, Toast.LENGTH_LONG).show();

        }


    }

    @Override
    void onMenuCreated(Menu menu) {
        MenuItem item = menu.findItem(R.id.itmAddMedia);
        item.setVisible(false);
    }
}


