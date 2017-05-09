package com.shun.blog.ui.home.presenter;

import com.shun.blog.base.net.JsonCallback;
import com.shun.blog.base.rx.RxSchedulers;
import com.shun.blog.base.ui.BaseMvpPresenter;
import com.shun.blog.base.ui.BaseResponse;
import com.shun.blog.bean.HomeBean;
import com.shun.blog.ui.home.contract.HomeContract;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by yghysdr on 2017/04/27
 */

public class HomePresenterImpl extends BaseMvpPresenter<HomeContract.View,
        HomeContract.Model> implements HomeContract.Presenter {

    boolean haveMore;

    @Override
    public void requestData(int page, int pageSize) {
        mRxManage.addAsync(mMode
                .requestData(page, pageSize)
                .delay(2, TimeUnit.SECONDS)
                .compose(RxSchedulers.<BaseResponse<List<HomeBean>>>io_main())
                .subscribe(new JsonCallback<BaseResponse<List<HomeBean>>>() {
                    @Override
                    public void onSuccess(BaseResponse<List<HomeBean>> result) {
                        haveMore = result.haveMore;
                        mView.onSuccess(result.data);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        super.onFailure(code, msg);
                        haveMore = false;
                        mView.onFailed();
                    }
                }));
    }

    @Override
    public boolean haveMore() {
        return haveMore;
    }
}