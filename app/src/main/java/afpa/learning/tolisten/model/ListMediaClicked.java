package afpa.learning.tolisten.model;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import afpa.learning.tolisten.WebViewMedia;


/**
 * Created by Afpa on 13/02/2017.
 */

public class ListMediaClicked implements AdapterView.OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Media media = (Media) parent.getItemAtPosition(position);

        System.out.println("Click Performed on: " + media.getTitle());

        Intent intent = new Intent(parent.getContext(), WebViewMedia.class);
        intent.putExtra("media_title", media.getTitle());
        intent.putExtra("media_genre", media.getGenre());
        intent.putExtra("media_author", media.getAuthor());
        intent.putExtra("media_user", media.getUser());
        intent.putExtra("media_url", media.getUrl());
        intent.putExtra("media_id", media.getId());

        intent.putExtra("media_isViewed", media.isViewed());


        parent.getContext().startActivity(intent);
    }
}
