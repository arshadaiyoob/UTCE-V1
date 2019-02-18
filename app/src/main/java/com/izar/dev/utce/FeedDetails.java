package com.izar.dev.utce;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.w3c.dom.Document;

public class FeedDetails extends AppCompatActivity {

    TextView author,fdate,ftitle;
    ImageView fthumb;
    WebView fweb;
    boolean loadingFinished = true;
    boolean redirect = false;
    private ShimmerFrameLayout mShimmerViewContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_details);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar2);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        author = (TextView) findViewById(R.id.detail_Author);
        fweb = (WebView) findViewById(R.id.detail_webview);
        fthumb = (ImageView) findViewById(R.id.detail_image);
        fdate = (TextView)findViewById(R.id.detail_date);
        ftitle=(TextView) findViewById(R.id.detail_title);
        final Bundle bundle = getIntent().getExtras();
        String description_doc = bundle.getString("desc");
        fweb.loadData(description_doc, "text/html; charset=UTF-8", null);
        fweb.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String urlNewString) {
                if (!loadingFinished) {
                    redirect = true;
                }

                loadingFinished = false;
                view.loadUrl(urlNewString);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap facIcon) {
                loadingFinished = false;
                mShimmerViewContainer.startShimmer();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if(!redirect){
                    loadingFinished = true;
                }

                if(loadingFinished && !redirect){
                    mShimmerViewContainer.stopShimmer();
                    mShimmerViewContainer.setVisibility(View.GONE);
                } else{

                }

            }
        });
        author.setText(bundle.getString("author"));
        fdate.setText(bundle.getString("date"));
        ftitle.setText(bundle.getString("title"));
        Glide.with(this).load(bundle.getString("thumb")).into(fthumb);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container5);
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#FFFFFF"));




        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(bundle.getString("title"));
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });



    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmer();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmer();
        super.onPause();
    }
}
