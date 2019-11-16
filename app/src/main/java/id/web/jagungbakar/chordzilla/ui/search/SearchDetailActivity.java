package id.web.jagungbakar.chordzilla.ui.search;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONObject;

import id.web.jagungbakar.chordzilla.R;

public class SearchDetailActivity extends AppCompatActivity {

    private SharedPreferences sharedpreferences;

    private WebView chord_webview;
    private LinearLayout chord_content_container;
    private int screen_width = 0;
    private SerializableChord chord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);

        Intent intent = getIntent();
        if (intent.hasExtra("chord_intent") && intent.hasExtra("chord_intent")) {
            try {
                chord = (SerializableChord)intent.getExtras().get("chord_intent");
            } catch (Exception e){e.printStackTrace();}
        }

        initToolbar();
        initView();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.green_50), PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(chord.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#546E7A")));
        getSupportActionBar().setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#37474F")));

        TextView toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText(chord.getTitle());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        chord_content_container = (LinearLayout) findViewById(R.id.chord_content_container);

        chord_webview = (WebView) findViewById(R.id.chord_webview);
        chord_webview.setVerticalScrollBarEnabled(false);
        chord_webview.setHorizontalScrollBarEnabled(false);
        chord_webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        chord_webview.getSettings().setJavaScriptEnabled(true);
        chord_webview.getSettings().setAppCacheEnabled(false);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screen_width = size.x;

        /*Log.e(getClass().getSimpleName(), "screen_width : "+ screen_width);
        if (screen_width > 900) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(640, LinearLayout.LayoutParams.WRAP_CONTENT);
            chord_webview.setLayoutParams(params);
        }*/

        try {
            String style = "<style>";
            style += "body{font-size: 15px;}";
            style += ".ft-14{font-size:10px !important;}";
            style += ".ft-16{font-size:16px !important;}";
            style += ".ft-17{font-size:17px !important;}";
            style += ".ft-18{font-size:18px !important;}";
            style += ".ft-20{font-size:20px !important;}";
            style += ".ft-22{font-size:22px !important;}";
            style += ".ft-24{font-size:24px !important;}";
            style += ".ft-26{font-size:26px !important;}";
            style += "a {color: #00ca9b;text-decoration:none;}";
            style += "p{line-height: 2;}";
            style += "</style>";

            style += "<script type='text/javascript' src='jquery.min.js'></script>";
            style += "<script type='text/javascript' src='chord.min.js'></script>";
            String chord_content = chord.getContent();
            // parse the data
            chord_content = chord_content.replaceAll("\\{", "<a href=\"#\" class=\"chord\">");
            chord_content = chord_content.replaceAll("\\}", "</a>");
            chord_content = chord_content.replaceAll("\\[", "<sup><a href=\"#\" class=\"chord\">");
            chord_content = chord_content.replaceAll("\\]", "</a></sup>");
            String html_data = "<html>"+ style +"<body>" + chord_content + "</body></html>";

            chord_webview.loadDataWithBaseURL("file:///android_asset/", html_data, "text/html", "utf-8", null);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
