package com.eyck.rxjavademo.module.map_1;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.eyck.rxjavademo.BaseFragment;
import com.eyck.rxjavademo.R;
import com.eyck.rxjavademo.adapter.ItemListAdapter;
import com.eyck.rxjavademo.model.Item;
import com.eyck.rxjavademo.network.Network;
import com.eyck.rxjavademo.utils.GankBeautyResultToItemsMapper;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Eyck on 2017/8/30.
 */

public class MapFragment extends BaseFragment {
    @Bind(R.id.pageTv)
    TextView pageTv;
    @Bind(R.id.tipBt)
    Button tipBt;
    @Bind(R.id.gridRv)
    RecyclerView gridRv;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.previousPageBt)
    AppCompatButton previousPageBt;
    @Bind(R.id.nextPageBt)
    AppCompatButton nextPageBt;
    private int page = 0;


    ItemListAdapter adapter = new ItemListAdapter();

    Observer<List<Item>> observer = new Observer<List<Item>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getActivity(), R.string.loading_failed, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(List<Item> images) {
            swipeRefreshLayout.setRefreshing(false);
            pageTv.setText(getString(R.string.page_with_number, page));
            adapter.setItems(images);
        }
    };


    private void loadPage(int page) {
        swipeRefreshLayout.setRefreshing(true);
        unsubscribe();
        subscription = Network.getGankApi()
                .getBeauties(10, page)
                .map(GankBeautyResultToItemsMapper.getInstance())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, view);
        gridRv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        gridRv.setAdapter(adapter);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        swipeRefreshLayout.setEnabled(false);
        return view;
    }

    @Override
    protected int getDialogRes() {
        return R.layout.dialog_map;
    }

    @Override
    protected int getTitleRes() {
        return R.string.title_map;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.previousPageBt, R.id.nextPageBt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.previousPageBt:
                loadPage(--page);
                if (page == 1) {
                    previousPageBt.setEnabled(false);
                }
                break;
            case R.id.nextPageBt:
                loadPage(++page);
                if (page == 2) {
                    previousPageBt.setEnabled(true);
                }
                break;
        }
    }
}
