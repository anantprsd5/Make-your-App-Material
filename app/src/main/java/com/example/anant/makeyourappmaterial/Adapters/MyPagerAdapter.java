package com.example.anant.makeyourappmaterial.Adapters;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.example.anant.makeyourappmaterial.ArticleDetailFragment;
import com.example.anant.makeyourappmaterial.data.ArticleLoader;

/**
 * Created by anant on 21/7/18.
 */

public class MyPagerAdapter extends FragmentStatePagerAdapter {

    private Cursor mCursor;

    public MyPagerAdapter(FragmentManager fm, Cursor cursor) {
        super(fm);
        mCursor = cursor;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        mCursor.moveToPosition(position);
        return ArticleDetailFragment.newInstance(mCursor.getLong(ArticleLoader.Query._ID));
    }

    @Override
    public int getCount() {
        return (mCursor != null) ? mCursor.getCount() : 0;
    }
}
