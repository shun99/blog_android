package com.shun.blog.ui.article.presenter;

import android.content.Context;
import android.view.ViewGroup;

import com.shun.blog.ui.article.view.ArchiveHolder;
import com.shun.blog.ui.article.view.ArchiveHolderYear;
import com.yghysdr.srecycleview.BaseHolder;
import com.yghysdr.srecycleview.BaseRVAdapter;

/**
 * Created by yghysdr on 2017/6/30.
 */

public class ArchiveListAdapter extends BaseRVAdapter {
    private static final int TYPE_YEAR = 1;

    public ArchiveListAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    protected BaseHolder myOnCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_YEAR) {
            return new ArchiveHolderYear(mContext, parent);
        }
        return new ArchiveHolder(mContext, parent);
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataList.get(position) instanceof Long) {
            return TYPE_YEAR;
        }
        return super.getItemViewType(position);
    }
}