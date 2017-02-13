package afpa.learning.tolisten;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import afpa.learning.tolisten.model.ListMediaAdapter;
import afpa.learning.tolisten.model.Media;
import afpa.learning.tolisten.model.MediaProvider;
import afpa.learning.tolisten.model.ListMediaClicked;

public class ListActivity extends ListMenu {

    Spinner spnGenre;
    ListView lstMedia;
    ListMediaAdapter adpMedia;
    ArrayAdapter<String> adpGenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initMedias();
        initGenre(adpMedia.getMedias());
    }

    // Initialize genre
    private void initGenre(List<Media> medias) {
        spnGenre = (Spinner) findViewById(R.id.spnGenre);

        List<String> genre = new ArrayList<>();
        genre.add(this.getString(R.string.filter_genre));

        for (Media m : medias) {
            if (!genre.contains(m.getGenre())) {
                genre.add(m.getGenre());
            }
        }

        adpGenre = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genre);
        spnGenre.setAdapter(adpGenre);

        spnGenre.setOnItemSelectedListener(new ComboGenreSelected());
    }

    // Initialize medias
    private void initMedias() {
        lstMedia = (ListView) findViewById(R.id.lstMedia);

        ArrayList<Media> medias = new ArrayList<>();
        MediaProvider data = new MediaProvider();
        data.execute();
        try {
            medias = data.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        adpMedia = new ListMediaAdapter(this, medias);
        lstMedia.setAdapter(adpMedia);

        lstMedia.setOnItemClickListener(new ListMediaClicked());
    }

    class ComboGenreSelected implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            adpMedia.getFilter().filter(adpGenre.getItem(position));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            adpMedia.getFilter().filter("");
        }
    }
}
