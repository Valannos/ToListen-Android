package afpa.learning.tolisten.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import afpa.learning.tolisten.R;

/**
 * Created by Afpa on 08/02/2017.
 */

public class ListMediaAdapter extends ArrayAdapter<Media> implements Filterable {

    private List<Media> medias;
    private Filter filter;
    private Map<String, Bitmap> images = new HashMap<>();

    public ListMediaAdapter(Context context, ArrayList<Media> medias) {
        super(context, 0, medias);
        this.medias = new ArrayList<>(medias);
        this.filter = new MediaFilter(this);
        for (Media media : medias) {
            if (media.getUrl() != null && URLUtil.isValidUrl(media.getUrl())) {
                Uri uri = Uri.parse(media.getUrl());
                String host = uri.getHost();
                if (uri.getPort() != -1) {
                    host += ":" + uri.getPort();
                }
                if (!images.containsKey(host)) {
                    images.put(host, null);
                    System.out.println("Get ICO from: " + host);
                    String[] url = {uri.getScheme(), host};
                    new ImageProvider(this).execute(url);
                    continue;
                }
                System.out.println("ICO from " + host + " is already found!");
                continue;
            }
            System.out.println("\"" + media.getTitle() + "\" has no valid URL: " + media.getUrl());
        }
    }

    public void addImage(String link, Bitmap image) {
        images.put(link, image);
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Media media = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_media, parent, false);
        }

        // Lookup view for data population
        final TextView title = (TextView) convertView.findViewById(R.id.title);
        final TextView author = (TextView) convertView.findViewById(R.id.author);
        final ImageView img = (ImageView) convertView.findViewById(R.id.imgIcon);
        title.setText(media.getTitle());
        author.setText(media.getAuthor());
        author.setGravity(Gravity.END);
        boolean isSetImage = false;
        if (media.getUrl() != null && URLUtil.isValidUrl(media.getUrl())) {
            Uri uri = Uri.parse(media.getUrl());
            String host = uri.getHost();
            if (uri.getPort() != -1) {
                host += ":" + uri.getPort();
            }
            if (images.containsKey(host)) {
                Bitmap image = images.get(host);
                if (image != null) {
                    img.setImageBitmap(image);
                    isSetImage = true;
                }
            }
        }
        if (!isSetImage) {
            img.setImageBitmap(BitmapFactory.decodeResource(this.getContext().getResources(), android.R.drawable.ic_media_play));
        }
        // Return the completed view to render on screen
        return convertView;
    }

    public List<Media> getMedias() {
        return medias;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new MediaFilter(this);
        }
        return filter;
    }

    public class MediaFilter extends Filter {

        private final ListMediaAdapter adp;

        private String genre = getContext().getString(R.string.filter_genre);

        public MediaFilter(ListMediaAdapter adp) {
            this.adp = adp;
        }

        public void setGenre(String genre) {
            this.genre = genre;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List<Media>) results.values);
            if (results.count == 0) {
                adp.notifyDataSetInvalidated();
            } else {
                adp.notifyDataSetChanged();
            }
        }

        @Override
        protected Filter.FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
            List<Media> genreFiltered = new ArrayList<>();
            List<Media> txtFiltered = new ArrayList<>();

            if ((constraint == null || constraint.length() == 0) && genre.equals(getContext().getString(R.string.filter_genre))) {

                // set the Original result to return
                results.count = medias.size();
                results.values = medias;
            } else {

                if (!genre.equals(getContext().getString(R.string.filter_genre))) {
                    genre = genre.toString().toLowerCase();
                    for (int i = medias.size() - 1; i >= 0; i--) {
                        Media data = medias.get(i);
                        if (data.getGenre().toLowerCase().equals(genre)) {
                            genreFiltered.add(data);
                        }
                    }
                } else {
                    genreFiltered = medias;
                }

                if (constraint != null && constraint.length() != 0) {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = genreFiltered.size() - 1; i >= 0; i--) {
                        Media data = genreFiltered.get(i);
                        if (data.getTitle().toLowerCase().contains(constraint) ||
                                data.getAuthor().toLowerCase().contains(constraint) ||
                                data.getUrl().toLowerCase().contains(constraint)) {
                            txtFiltered.add(data);
                        }
                    }
                } else {
                    txtFiltered = genreFiltered;
                }

                // set the Filtered result to return
                results.count = txtFiltered.size();
                results.values = txtFiltered;

            }
            return results;
        }
    }
}
