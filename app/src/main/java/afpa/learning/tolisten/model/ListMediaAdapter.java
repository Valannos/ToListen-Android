package afpa.learning.tolisten.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import afpa.learning.tolisten.R;

/**
 * Created by Afpa on 08/02/2017.
 */

public class ListMediaAdapter extends ArrayAdapter<Media> implements Filterable {

    private List<Media> medias;
    private Filter filter;

    public ListMediaAdapter(Context context, ArrayList<Media> medias) {
        super(context, 0, medias);
        this.medias = new ArrayList<>(medias) ;
        this.filter = new MediaFilter(this);
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
        title.setText(media.getTitle());

        // Return the completed view to render on screen
        return convertView;
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

        public MediaFilter(ListMediaAdapter adp) {
            this.adp = adp;
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
            List<Media> filteredArrList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0 || constraint.equals(getContext().getString(R.string.filter_genre))) {
                // set the Original result to return
                results.count = medias.size();
                results.values = medias;
            } else {
                constraint = constraint.toString().toLowerCase();
                for (int i = medias.size() -1; i >= 0; i--) {
                    Media data = medias.get(i);
                    if (data.getGenre().toLowerCase().equals(constraint)) {
                        filteredArrList.add(data);
                    }
                }
                // set the Filtered result to return
                results.count = filteredArrList.size();
                results.values = filteredArrList;
            }
            return results;
        }
    }
}
