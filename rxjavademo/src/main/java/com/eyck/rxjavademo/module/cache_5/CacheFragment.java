package com.eyck.rxjavademo.module.cache_5;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eyck.rxjavademo.BaseFragment;
import com.eyck.rxjavademo.R;
import com.eyck.rxjavademo.adapter.ItemListAdapter;
import com.eyck.rxjavademo.model.Item;
import com.eyck.rxjavademo.module.cache_5.data.Data;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/**
 * Created by Eyck on 2017/8/30.
 */

public class CacheFragment extends BaseFragment {


    @Bind(R.id.loadingTimeTv)
    AppCompatTextView loadingTimeTv;
    @Bind(R.id.cacheRv)
    RecyclerView cacheRv;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    ItemListAdapter adapter = new ItemListAdapter();
    private long startingTime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cache, container, false);
        ButterKnife.bind(this, view);
        cacheRv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        cacheRv.setAdapter(adapter);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        swipeRefreshLayout.setEnabled(false);
        return view;
    }

    @Override
    protected int getDialogRes() {
        return 0;
    }

    @Override
    protected int getTitleRes() {
        return 0;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.loadBt, R.id.clearMemoryCacheBt, R.id.clearMemoryAndDiskCacheBt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.loadBt:
                load();
                break;
            case R.id.clearMemoryCacheBt:
                clearMemoryCache();
                break;
            case R.id.clearMemoryAndDiskCacheBt:
                clearMemoryAndDiskCache();
                break;
        }
    }

    private void clearMemoryAndDiskCache() {
        Data.getInstance().clearMemoryAndDiskCache();
        adapter.setItems(null);
        Toast.makeText(getActivity(), R.string.memory_and_disk_cache_cleared, Toast.LENGTH_SHORT).show();
    }

    private void clearMemoryCache() {
        Data.getInstance().clearMemoryCache();
        adapter.setItems(null);
        Toast.makeText(getActivity(), R.string.memory_cache_cleared, Toast.LENGTH_SHORT).show();
    }

    private void load() {
        swipeRefreshLayout.setRefreshing(true);
        startingTime = System.currentTimeMillis();
        unsubscribe();
        Data.getInstance().subscribeData(new Observer<List<Item>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), R.string.loading_failed, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(List<Item> items) {
                swipeRefreshLayout.setRefreshing(false);
                int loadingTime = (int) (System.currentTimeMillis() - startingTime);
                loadingTimeTv.setText(getString(R.string.loading_time_and_source, loadingTimeTv, Data.getInstance().getDataSourceText()));
                adapter.setItems(items);
            }
        });
    }
}
