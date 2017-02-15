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
        intent.putExtra("title", media.getTitle());
        intent.putExtra("genre", media.getGenre());
        intent.putExtra("author", media.getAuthor());
        intent.putExtra("user", media.getUser());
        intent.putExtra("url", media.getUrl());
        intent.putExtra("id", media.getId());

        intent.putExtra("isViewed", media.isViewed());


        parent.getContext().startActivity(intent);
    }
}
