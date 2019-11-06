package id.web.jagungbakar.chordzilla.ui.mysong;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import id.web.jagungbakar.chordzilla.R;

public class MySongsFragment extends Fragment {

    private MySongsViewModel mySongsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mySongsViewModel =
                ViewModelProviders.of(this).get(MySongsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_my_songs, container, false);
        final TextView textView = root.findViewById(R.id.text_my_songs);
        mySongsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}

