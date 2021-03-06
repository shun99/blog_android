package io.yghysdr.article.presenter;

import com.github.yghysdr.base.BasePresenter;
import com.github.yghysdr.http.HttpException;
import com.github.yghysdr.http.NetObserver;

import java.util.List;

import io.blog.res.BaseResponse;
import io.blog.res.bean.Article;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.yghysdr.article.contract.ArticleListContract;
import io.yghysdr.article.model.ArticleListModelImpl;
import io.yghysdr.blog.common.ICommonListView;

/**
 * Created by yghysdr on 2017/04/27
 */

public class ArticleListPresenterImpl extends BasePresenter implements
        ArticleListContract.Presenter {

    @Override
    public void requestList(ICommonListView commonListView, int page, int pageSize, int articleType) {
        new ArticleListModelImpl()
                .requestData(articleType, page, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetObserver<BaseResponse<List<Article>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                    }

                    @Override
                    public void onNext(BaseResponse<List<Article>> listBaseResponse) {
                        commonListView.onListSuccess(listBaseResponse);
                    }

                    @Override
                    public void onError(HttpException e) {
                        commonListView.onListFailed(e);
                    }
                });
    }

}