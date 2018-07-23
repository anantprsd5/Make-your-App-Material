package com.example.anant.makeyourappmaterial;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anant.makeyourappmaterial.data.ArticleLoader;
import com.squareup.picasso.Picasso;

/**
 * Created by anant on 21/7/18.
 */

public class ArticleDetailFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private CoordinatorLayout coordinatorLayout;

    public static final String ARG_ITEM_ID = "item_id";
    private long mItemId;

    private View mRootView;

    public ArticleDetailFragment() {
    }

    public static ArticleDetailFragment newInstance(long itemId) {
        Bundle arguments = new Bundle();
        arguments.putLong(ARG_ITEM_ID, itemId);
        ArticleDetailFragment fragment = new ArticleDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItemId = getArguments().getLong(ARG_ITEM_ID);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mRootView = inflater.inflate(R.layout.fragment_article_detail, container, false);
        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // In support library r8, calling initLoader for a fragment in a FragmentPagerAdapter in
        // the fragment's onCreate may cause the same LoaderManager to be dealt to multiple
        // fragments because their mIndex is -1 (haven't been added to the activity yet). Thus,
        // we do this in onActivityCreated.
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return ArticleLoader.newInstanceForItemId(getActivity(), mItemId);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (data == null || data.isClosed() || !data.moveToFirst()) {
            return;
        }
        bindViews(data);
    }

    private AppCompatActivity getActivityCast(){
        return (AppCompatActivity)getActivity();
    }

    private void bindViews(Cursor mCursor) {

        ImageView imageView = mRootView.findViewById(R.id.backdrop);
        Toolbar toolbar = mRootView.findViewById(R.id.toolbar);
        TextView authorText = mRootView.findViewById(R.id.author_text_view);
        final WebView bodyText = mRootView.findViewById(R.id.body_text);
        final ProgressBar progressBar = mRootView.findViewById(R.id.indeterminateBar);
        getActivityCast().setSupportActionBar(toolbar);
        getActivityCast().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getActivityCast().getSupportActionBar().setDisplayShowHomeEnabled(true);

        String photo = mCursor.getString(ArticleLoader.Query.PHOTO_URL);
        String title = mCursor.getString(ArticleLoader.Query.TITLE);
        String author = mCursor.getString(ArticleLoader.Query.AUTHOR);
        final String body = mCursor.getString(ArticleLoader.Query.BODY);

        toolbar.setTitle(title);
        authorText.setText(author);
        bodyText.loadDataWithBaseURL("", body, "text/html", "UTF-8", "");
        bodyText.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress == 100) {
                    //do your task
                    progressBar.setVisibility(View.GONE);
                    bodyText.setVisibility(View.VISIBLE);
                }
            }
        });



        Picasso.get()
                .load(photo)
                .into(imageView);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

}
