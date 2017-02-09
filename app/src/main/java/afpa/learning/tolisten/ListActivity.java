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

public class ListActivity extends ListMenu {

    Spinner spnGenre;
    ListView lstMedia;
    ListMediaAdapter adpMedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        lstMedia = (ListView) findViewById(R.id.lstMedia);
        spnGenre = (Spinner) findViewById(R.id.spnGenre);

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
        List<String> genre = new ArrayList<>();
        genre.add(this.getString(R.string.filter_genre));

        for (Media m : medias) {
            if (!genre.contains(m.getGenre())) {
                genre.add(m.getGenre());
            }
        }
        ArrayAdapter<String> adptGenre = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genre);
        spnGenre.setAdapter(adptGenre);

        spnGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
    }
}
