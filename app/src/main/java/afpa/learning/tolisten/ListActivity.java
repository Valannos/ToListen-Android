package afpa.learning.tolisten;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import afpa.learning.tolisten.model.Media;

public class ListActivity extends ListMenu {

    Spinner spnGenre;
    ListView lstMedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        lstMedia = (ListView) findViewById(R.id.lstMedia);
        spnGenre = (Spinner) findViewById(R.id.spnGenre);

        ArrayList<Media> medias = new ArrayList<>();

        ListMediaAdapter adptMedia = new ListMediaAdapter(this, medias);

        lstMedia.setAdapter(adptMedia);

        List<String> genre = new ArrayList<>();

        genre.add(this.getString(R.string.filter_genre));

        for (Media m : medias) {
            if (!genre.contains(m.getGenre())) {
                genre.add(m.getGenre());
            }
        }

        ArrayAdapter<String> adptGenre = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genre);

        spnGenre.setAdapter(adptGenre);

    }
}
