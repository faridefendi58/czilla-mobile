package id.web.jagungbakar.chordzilla.ui.favorite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import id.web.jagungbakar.chordzilla.R;
import id.web.jagungbakar.chordzilla.ui.search.SerializableChord;

public class AdapterListFavorite extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SerializableChord> items = new ArrayList<>();

    private Context ctx;

    private AdapterListFavorite.OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, SerializableChord obj, int position);
    }

    public void setOnItemClickListener(final AdapterListFavorite.OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterListFavorite(Context context, List<SerializableChord> items) {
        this.ctx = context;
        this.items = items;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView artist_name;
        public TextView song_title;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            artist_name = (TextView) v.findViewById(R.id.artist_name);
            song_title = (TextView) v.findViewById(R.id.song_title);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false);
        vh = new AdapterListFavorite.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AdapterListFavorite.OriginalViewHolder) {
            final AdapterListFavorite.OriginalViewHolder view = (AdapterListFavorite.OriginalViewHolder) holder;
            AdapterListFavorite.OriginalViewHolder vwh = (AdapterListFavorite.OriginalViewHolder) holder;

            try {
                SerializableChord chord = items.get(position);
                Map<String, String> chord_to_map = chord.toMap();
                view.artist_name.setText(chord_to_map.get("artist_name"));
                view.song_title.setText(chord_to_map.get("title"));
            } catch (Exception e){}

            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, items.get(position), position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
