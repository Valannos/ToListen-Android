package afpa.learning.tolisten.model;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Afpa on 15/02/2017.
 */

public class GenreAdapter extends ArrayAdapter<String> {

    public GenreAdapter(Context context, int resource, List<String> genres) {
        super(context, resource, genres);
    }

    public boolean contains(String genre) {
        for (int i = getCount() -1; i >= 0; i--) {
            String g = getItem(i);
            if (g.equals(genre)) {
                return true;
            }
        }
        return false;
    }

    public void Add(String genre) {
        if (!contains(genre)) {
            add(genre);
        }
    }
}