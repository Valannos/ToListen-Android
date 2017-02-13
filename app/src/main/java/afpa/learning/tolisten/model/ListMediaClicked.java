package afpa.learning.tolisten.model;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by Afpa on 13/02/2017.
 */

public class ListMediaClicked implements AdapterView.OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Media media = (Media) parent.getItemAtPosition(position);

        System.out.println("Click Performed on: " + media.getTitle());

        //Intent intent = new Intent(Activity.this,destinationActivity.class);
        //startActivity(intent);
    }
}
