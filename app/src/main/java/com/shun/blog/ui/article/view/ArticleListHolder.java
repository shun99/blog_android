package com.shun.blog.ui.article.view;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shun.blog.R;
import com.shun.blog.base.ui.MyBaseHolder;
import com.shun.blog.bean.ArticleBean;
import com.shun.blog.utils.DateUtil;

import butterknife.BindView;

/**
 * Created by yghysdr on 2017/5/8.
 */

public class ArticleListHolder extends MyBaseHolder<ArticleBean> {

    @BindView(R.id.article_item_title_tv)
    TextView articleItemTitleTv;
    @BindView(R.id.article_item_content_tv)
    TextView articleItemContentTv;
    @BindView(R.id.article_item_time_tv)
    TextView articleItemTimeTv;

    public ArticleListHolder(Context context, ViewGroup root) {
        super(context, root, R.layout.item_home);
    }

    @Override
    public void initData(ArticleBean data) {
        articleItemTitleTv.setText(data.title);
        articleItemContentTv.setText(data.des);
        articleItemTimeTv.setText(DateUtil.long2Str(data.updatedAt, DateUtil.FORMAT_YMD));
    }
}