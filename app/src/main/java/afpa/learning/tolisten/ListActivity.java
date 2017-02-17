package afpa.learning.tolisten;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import afpa.learning.tolisten.model.ListMediaAdapter;
import afpa.learning.tolisten.model.ListMediaClicked;
import afpa.learning.tolisten.model.Media;
import afpa.learning.tolisten.model.MediaProvider;
import afpa.learning.tolisten.model.GenreAdapter;

public class ListActivity extends ListMenu {

    Spinner spnGenre;
    ListView lstMedia;
    EditText txtSearch;
    ImageView imgSearch;
    CheckBox chkWithViewed;
    ListMediaAdapter adpMedia;
    GenreAdapter adpGenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        txtSearch = (EditText) findViewById(R.id.txtSearch);
        txtSearch.addTextChangedListener(new TextSearchChanged());

        imgSearch = (ImageView) findViewById(R.id.imgSearch);
        imgSearch.setOnClickListener(new ImgClicked());

        chkWithViewed = (CheckBox) findViewById(R.id.chkViewed);
        chkWithViewed.setOnCheckedChangeListener(new CheckWithViewedChanged());

        initMedias();
        initGenre(adpMedia.getMedias());
    }

    @Override
    void onMenuCreated(Menu menu) {
        MenuItem item = menu.findItem(R.id.itmLstMedia);
        item.setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itmUpdate) {
            initMedias();
            initGenre(adpMedia.getMedias());
            adpGenre.notifyDataSetChanged();
            adpMedia.notifyDataSetChanged();
            adpMedia.getFilter().filter(txtSearch.getText());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utils.deleteCache(this);
    }

    // Initialize genre
    private void initGenre(List<Media> medias) {
        spnGenre = (Spinner) findViewById(R.id.spnGenre);

        List<String> genres = new ArrayList<>();
        genres.add(this.getString(R.string.filter_genre));

        for (Media m : medias) {
            if (!genres.contains(m.getGenre())) {
                genres.add(m.getGenre());
            }
        }

        adpGenre = new GenreAdapter(this, android.R.layout.simple_spinner_item, genres);
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        Bundle e = data.getExtras();
        String type = e.getString("method");
        Media m = new Media(e);
        if (m != null) {
            if (type.equals(APISettings.getMethodName(APISettings.URI.POST))) {
                System.out.println("Add media");
                adpMedia.Add(m);
                adpGenre.Add(m.getGenre());
                System.out.println("New media: " + m);
            }
            if (type.equals(APISettings.getMethodName(APISettings.URI.UPDATE))) {
                System.out.println("Update media");
                Media media = adpMedia.getById(m.getId());
                System.out.println("Old media: " + media);
                String oldGenre = media.getGenre();
                media.setAuthor(m.getAuthor());
                media.setGenre(m.getGenre());
                media.setSender(m.getSender());
                media.setTitle(m.getTitle());
                media.setUrl(m.getUrl());
                media.setViewed(m.isViewed());
                if (adpMedia.countGenre(oldGenre) == 0) {
                    adpGenre.remove(oldGenre);
                }
                adpGenre.Add(m.getGenre());
                System.out.println("New media: " + media);
            }
            if (type.equals(APISettings.getMethodName(APISettings.URI.DELETE))) {
                System.out.println("Delete media");
                adpMedia.Remove(m);
                boolean delete = true;
                for (Media media : adpMedia.getMedias()) {
                    if (!media.equals(m) && media.getGenre().equals(m.getGenre())) {
                        delete = false;
                        break;
                    }
                }
                if (delete) {
                    adpGenre.remove(m.getGenre());
                }
                System.out.println("Old media: " + m);
            }
            adpGenre.notifyDataSetChanged();
            adpMedia.notifyDataSetChanged();
            adpMedia.getFilter().filter(txtSearch.getText());
        }
        Logger.getLogger(FormActivity.class.getName()).log(Level.INFO, "TRIGGERED");
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
            String txt = txtSearch.getText().toString();
            mediaFilter.filter(txt);

            if (txt != null && txt.length() > 0) {
                imgSearch.setImageBitmap(BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_delete));
            } else {
                imgSearch.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.search));
            }
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

    private class ImgClicked implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String txt = txtSearch.getText().toString();
            if (txt != null && txt.length() > 0) {
                txtSearch.setText("");
            } else {
                txtSearch.requestFocus();
            }
        }
    }
}
