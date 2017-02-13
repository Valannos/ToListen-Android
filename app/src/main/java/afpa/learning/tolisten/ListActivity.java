package afpa.learning.tolisten;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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
    EditText txtSearch;
    CheckBox chkWithViewed;
    ListMediaAdapter adpMedia;
    ArrayAdapter<String> adpGenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        txtSearch = (EditText) findViewById(R.id.txtSearch);
        txtSearch.addTextChangedListener(new TextSearchChanged());

        chkWithViewed = (CheckBox) findViewById(R.id.chkViewed);
        chkWithViewed.setOnCheckedChangeListener(new CheckWithViewedChanged());

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

    public Spinner getSpnGenre() {
        return spnGenre;
    }

    public ListView getLstMedia() {
        return lstMedia;
    }

    public EditText getTxtSearch() {
        return txtSearch;
    }

    public CheckBox getChkWithViewed() {
        return chkWithViewed;
    }

    private class ComboGenreSelected implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ListMediaAdapter.MediaFilter mediaFilter = (ListMediaAdapter.MediaFilter) adpMedia.getFilter();
            mediaFilter.filter(txtSearch.getText());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            adpMedia.getFilter().filter("");
        }
    }

    private class TextSearchChanged implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            ListMediaAdapter.MediaFilter mediaFilter = (ListMediaAdapter.MediaFilter) adpMedia.getFilter();
            mediaFilter.filter(txtSearch.getText());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private class CheckWithViewedChanged implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            ListMediaAdapter.MediaFilter mediaFilter = (ListMediaAdapter.MediaFilter) adpMedia.getFilter();
            mediaFilter.filter(txtSearch.getText());
        }
    }
}
