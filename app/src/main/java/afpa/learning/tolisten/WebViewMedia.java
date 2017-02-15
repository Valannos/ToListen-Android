package afpa.learning.tolisten;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
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
    private MenuItem menuItem;
    private Button deleteBtn;
    private Media media;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        Intent currentIntent = getIntent();
        setContentView(R.layout.activity_webviewmedia);

        switchView = (Switch) findViewById(R.id.switchViewState);

        media = new Media(

                (Integer) currentIntent.getExtras().get("media_id"),
                (String) currentIntent.getExtras().get("media_title"),
                (String) currentIntent.getExtras().get("media_url"),
                (String) currentIntent.getExtras().get("media_author"),
                (String) currentIntent.getExtras().get("media_genre"),
                (String) currentIntent.getExtras().get("media_user"),
                (Boolean) currentIntent.getExtras().get("media_isViewed")

        );

        WebView browser = (WebView) findViewById(R.id.webViewMedia);
        WebSettings webSettings = browser.getSettings();
        browser.setWebViewClient(new WebViewClient());
        webSettings.setJavaScriptEnabled(true);
        // browser.loadUrl("http://www.html5rocks.com/");

        browser.loadUrl(media.getUrl());
        TextView webViewTitle = (TextView) findViewById(R.id.webViewTitle);

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

            Integer viewedInt = jsonObject.getInt("isViewed");

            if (viewedInt == 0) {
                intentMedia.putExtra("isViewed", Boolean.FALSE);
            } else {
                intentMedia.putExtra("isViewed", Boolean.TRUE);

            }


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

    public void editMedia(View view) {







    }
}
