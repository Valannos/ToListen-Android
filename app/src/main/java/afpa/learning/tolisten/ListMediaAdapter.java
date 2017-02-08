package afpa.learning.tolisten;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import afpa.learning.tolisten.model.Media;

/**
 * Created by Afpa on 08/02/2017.
 */

public class ListMediaAdapter extends ArrayAdapter<Media> {

    public ListMediaAdapter(Context context, ArrayList<Media> medias) {
        super(context, 0, medias);
    }
}
