package id.web.jagungbakar.chordzilla.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import id.web.jagungbakar.chordzilla.MainActivity;
import id.web.jagungbakar.chordzilla.R;
import id.web.jagungbakar.chordzilla.utils.ViewAnimation;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;
    private View root;
    private Context context;

    private EditText et_search;
    private ImageButton bt_clear, bt_back;

    private ProgressBar progress_bar;
    private LinearLayout lyt_no_result;

    private RecyclerView recyclerSuggestion;
    private AdapterSuggestionSearch mAdapterSuggestion;
    private LinearLayout lyt_suggestion;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                ViewModelProviders.of(this).get(SearchViewModel.class);
        root = inflater.inflate(R.layout.fragment_search, container, false);
        context = container.getContext();

        initComponent();
        return root;
    }

    private void initComponent() {
        progress_bar = (ProgressBar) root.findViewById(R.id.progress_bar);
        lyt_no_result = (LinearLayout) root.findViewById(R.id.lyt_no_result);

        lyt_suggestion = (LinearLayout) root.findViewById(R.id.lyt_suggestion);
        et_search = (EditText) root.findViewById(R.id.et_search);
        et_search.addTextChangedListener(textWatcher);

        bt_clear = (ImageButton) root.findViewById(R.id.bt_clear);
        bt_back = (ImageButton) root.findViewById(R.id.bt_back);
        bt_clear.setVisibility(View.GONE);
        recyclerSuggestion = (RecyclerView) root.findViewById(R.id.recyclerSuggestion);

        recyclerSuggestion.setLayoutManager(new LinearLayoutManager(context));
        recyclerSuggestion.setHasFixedSize(true);

        //set data and list adapter suggestion
        mAdapterSuggestion = new AdapterSuggestionSearch(context);
        recyclerSuggestion.setAdapter(mAdapterSuggestion);
        showSuggestionSearch();
        mAdapterSuggestion.setOnItemClickListener(new AdapterSuggestionSearch.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String viewModel, int pos) {
                et_search.setText(viewModel);
                ViewAnimation.collapse(lyt_suggestion);
                hideKeyboard();
                searchAction();
            }
        });

        bt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_search.setText("");
            }
        });

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_search.setText("");
                bt_back.setImageResource(R.drawable.ic_search_white_24dp);
                hideKeyboard();
                ViewAnimation.collapse(lyt_suggestion);
            }
        });

        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideKeyboard();
                    searchAction();
                    return true;
                }
                return false;
            }
        });

        et_search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                showSuggestionSearch();
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                return false;
            }
        });
    }

    private void showSuggestionSearch() {
        mAdapterSuggestion.refreshItems();
        ViewAnimation.expand(lyt_suggestion);
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence c, int i, int i1, int i2) {
            if (c.toString().trim().length() == 0) {
                bt_clear.setVisibility(View.GONE);
            } else {
                bt_clear.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence c, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    private void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void searchAction() {
        progress_bar.setVisibility(View.VISIBLE);
        ViewAnimation.collapse(lyt_suggestion);
        lyt_no_result.setVisibility(View.GONE);

        final String query = et_search.getText().toString().trim();
        if (!query.equals("")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progress_bar.setVisibility(View.GONE);
                    lyt_no_result.setVisibility(View.VISIBLE);
                }
            }, 2000);
            mAdapterSuggestion.addSearchHistory(query);
        } else {
            Toast.makeText(context, "Please fill search input", Toast.LENGTH_SHORT).show();
            progress_bar.setVisibility(View.GONE);
            lyt_no_result.setVisibility(View.VISIBLE);
        }
    }
}
