package com.github.yghysdr.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;

/**
 * Fragment基础类
 */

public abstract class BaseFragment extends LazyLoadFragment {
    protected Activity mActivity;
    protected Unbinder unbind;
    private PresenterManager mPresenterManager;
    private DisposableManager mDisposableManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        afterCreate(savedInstanceState);
        mDisposableManager = new DisposableManager();
        mPresenterManager = new PresenterManager();
        addPresenter(mPresenterManager.mPresenterList);
        mPresenterManager.attach();
    }

    public void addPresenter(List<BasePresenter> basePresenters) {

    }

    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        unbind = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    /**
     * 不建议使用
     */
    public void initData() {

    }

    /**
     * fragment 建议使用懒加载，减少没进入该fragment却由于该Fragment而造成的异常崩溃，减少资源集中加载
     */
    protected abstract void LazyLoad();

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Object event) {

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    protected void onFragmentFirstVisible() {
        //当第一次可见的时候，加载数据
        LazyLoad();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbind != null) {
            unbind.unbind();
        }
        EventBus.getDefault().unregister(this);
        mPresenterManager.detach();
        mDisposableManager.clear();
    }


    public void addDisposable(Disposable disposable) {
        mDisposableManager.addDisposable(disposable);
    }
}
