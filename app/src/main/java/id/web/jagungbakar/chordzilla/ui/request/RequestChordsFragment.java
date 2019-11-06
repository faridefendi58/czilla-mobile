package id.web.jagungbakar.chordzilla.ui.request;

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

public class RequestChordsFragment extends Fragment {

    private RequestChordsViewModel requestChordViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        requestChordViewModel =
                ViewModelProviders.of(this).get(RequestChordsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_request_chords, container, false);
        final TextView textView = root.findViewById(R.id.text_chord_request);
        requestChordViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
