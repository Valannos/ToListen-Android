package afpa.learning.tolisten;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import afpa.learning.tolisten.model.Media;
import afpa.learning.tolisten.model.MediaSwitchViewState;

/**
 * Created by Afpa on 13/02/2017.
 */

public class WebViewMedia extends Activity {

    private Switch switchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        Intent currentIntent = getIntent();
        setContentView(R.layout.activity_webviewmedia);

        switchView = (Switch) findViewById(R.id.switchViewState);

        final Media media = new Media(

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



                }



            }
        });


    }
}
