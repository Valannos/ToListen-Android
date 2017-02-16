package afpa.learning.tolisten.model;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import afpa.learning.tolisten.ListActivity;
import afpa.learning.tolisten.Utils;
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
        Utils.fillIntentFromMedia(intent, media);


        ((ListActivity) parent.getContext()).startActivityForResult(intent, 0);
    }
}
