package afpa.learning.tolisten;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import afpa.learning.tolisten.model.Media;
import afpa.learning.tolisten.model.MediaRemover;
import afpa.learning.tolisten.model.MediaSwitchViewState;

/**
 * Created by Afpa on 13/02/2017.
 */

public class WebViewMedia extends Activity {

    private Switch switchView;

    private Media media;
    private TextView webViewTitle;

    String method = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_webviewmedia);

        switchView = (Switch) findViewById(R.id.switchViewState);

        media = new Media(getIntent().getExtras());

        WebView browser = (WebView) findViewById(R.id.webViewMedia);
        WebSettings webSettings = browser.getSettings();
        browser.setWebViewClient(new WebViewClient());
        webSettings.setJavaScriptEnabled(true);
        // browser.loadUrl("http://www.html5rocks.com/");

        browser.loadUrl(media.getUrl());
        webViewTitle = (TextView) findViewById(R.id.webViewTitle);

        webViewTitle.setText(media.getAuthor() + " - " + media.getTitle());

        System.out.println(media.isViewed());
        System.out.println(media.getId());


        switchView.setChecked(media.isViewed());

        switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                MediaSwitchViewState mediaSwitchViewState = new MediaSwitchViewState(media.getId());

                mediaSwitchViewState.execute();


                if (media.isViewed()) {

                    media.setViewed(false);
                    Toast.makeText(buttonView.getContext(), R.string.setToNotViewed, Toast.LENGTH_SHORT).show();


                } else {


                    media.setViewed(true);
                    Toast.makeText(buttonView.getContext(), R.string.setToViewed, Toast.LENGTH_SHORT).show();

                }


            }
        });


    }

    /**
     * Return media object through intent to ListActivity
     * If no DELETE nor PUT request has been sent, "method" value is set to APISettings.URI.UPDATE
     * @param view
     */
    public void exitWebView(View view) {

        Intent intent = new Intent();

        intent.putExtra("edition", true);
        intent.putExtra("id", media.getId());
        intent.putExtra("url", media.getUrl());
        intent.putExtra("sender", media.getSender());
        intent.putExtra("genre", media.getGenre());
        intent.putExtra("author", media.getAuthor());
        intent.putExtra("title", media.getTitle());
        intent.putExtra("isViewed", media.isViewed());
        if (this.method.equals("")) this.method = APISettings.getMethodName(APISettings.URI.UPDATE);
        intent.putExtra("method", method);



        setResult(RESULT_OK, intent);
        this.finish();

    }

    /**
     * Send a DELETE request of media via JSON.
     * A JSON response containing same media is returned
     * method value is set to APISettings.URI.DELETE
     * @param view
     */
    public void deleteMedia(View view) {

        Intent intentMedia = null;
        MediaRemover remover = new MediaRemover();
        remover.execute(media.getId());
        try {
            String response = remover.get();
            System.out.println("Deleted : " + response);
            JSONObject jsonObject = new JSONObject(response);
            intentMedia = new Intent();
            intentMedia.putExtra("id", jsonObject.getInt("id"));
            intentMedia.putExtra("url", jsonObject.getString("url"));
            intentMedia.putExtra("sender", jsonObject.getString("sender"));
            intentMedia.putExtra("genre", jsonObject.getString("genre"));
            intentMedia.putExtra("author", jsonObject.getString("author"));
            intentMedia.putExtra("title", jsonObject.getString("title"));

            method = APISettings.getMethodName(APISettings.URI.DELETE);
            intentMedia.putExtra("method", method);
            Integer viewedInt = jsonObject.getInt("isViewed");


            intentMedia.putExtra("isViewed", (viewedInt == 0) ? Boolean.FALSE : Boolean.TRUE);


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Toast.makeText(this, R.string.toastConfirmSuppression, Toast.LENGTH_SHORT).show();
        this.setResult(RESULT_OK, intentMedia);

        this.finish();


    }

    /**
     * Call FormActivity for edition with media data in intent
     * A boolean is also sent to set FormActivity to edition mode (i.e. pre-filled fields, appropriate button...)
     * A result is awaited, got through onActivityResult event
     * @param view
     */

    public void editMedia(View view) {


        Intent intent = new Intent(this, FormActivity.class);

        intent.putExtra("edition", true);
        intent.putExtra("id", media.getId());
        intent.putExtra("url", media.getUrl());
        intent.putExtra("sender", media.getSender());
        intent.putExtra("genre", media.getGenre());
        intent.putExtra("author", media.getAuthor());
        intent.putExtra("title", media.getTitle());
        intent.putExtra("isViewed", media.isViewed());

        startActivityForResult(intent, 0);
        //this.finish();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {


            if (resultCode == Activity.RESULT_OK) {

                media.setId(data.getExtras().getInt("id"));
                media.setTitle(data.getExtras().getString("title"));
                media.setUrl(data.getExtras().getString("url"));
                media.setAuthor(data.getExtras().getString("author"));
                media.setGenre(data.getExtras().getString("genre"));
                media.setSender(data.getExtras().getString("sender"));
                media.setViewed(data.getExtras().getBoolean("isViewed"));

                webViewTitle = (TextView) findViewById(R.id.webViewTitle);

                webViewTitle.setText(media.getAuthor() + " - " + media.getTitle());
                method = APISettings.getMethodName(APISettings.URI.UPDATE);
                System.out.println("RECIEVED TITLE = " + media.getTitle());


            }
        }


    }
}




