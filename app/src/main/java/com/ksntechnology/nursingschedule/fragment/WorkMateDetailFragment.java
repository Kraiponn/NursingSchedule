package com.ksntechnology.nursingschedule.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ksntechnology.nursingschedule.R;
import com.ksntechnology.nursingschedule.adapter.WorkMateDetailAdapter;
import com.ksntechnology.nursingschedule.dao.NursingItemCollectionDao;
import com.ksntechnology.nursingschedule.dialog.MyAlertDialog;
import com.ksntechnology.nursingschedule.manager.HttpNursingRequest;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class WorkMateDetailFragment extends Fragment {
    private String mLocation;
    private String mShift;
    private String mDate;
    private String mSection;
    private String mSectionSex;
    private RecyclerView rcv;
    private TextView tvResult;
    private Disposable mDisposable;
    private WorkMateDetailAdapter mAdapter;
    //private WorkMateDetailItemManager mDao;
    private Observable<NursingItemCollectionDao> callDao;

    public static WorkMateDetailFragment newInstance(
            String location, String myDate,
            String shift, String section, String section_sex
    ) {
        WorkMateDetailFragment fragment = new WorkMateDetailFragment();
        Bundle args = new Bundle();
        args.putString("location", location);
        args.putString("shift", shift);
        args.putString("date", myDate);
        args.putString("section", section);
        args.putString("section_sex", section_sex);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mLocation = getArguments().getString("location");
            mShift = getArguments().getString("shift");
            mDate = getArguments().getString("date");
            mSection = getArguments().getString("section");
            mSectionSex = getArguments().getString("section_sex");
            //Log.d("ResponsesXYZ", mLocation + " " + mDate);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_workmate_detail_layout,
                container, false
        );

        initInstance(view, savedInstanceState);
        return view;
    }

    @Override
    public void onDestroy() {
        cancelRequestData();
        super.onDestroy();
    }

    @Override
    public void onStop() {
        cancelRequestData();
        super.onStop();
    }

    private void cancelRequestData() {
        if (mDisposable != null && mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    private void initInstance(View view, Bundle savedInstanceState) {
        rcv = view.findViewById(R.id.recyclerView_WorkMateDetail);
        tvResult = view.findViewById(R.id.textResultState);
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            rcv.setHasFixedSize(true);
            rcv.setLayoutManager(new GridLayoutManager(
                    getContext(),
                    2,
                    GridLayoutManager.VERTICAL,
                    false
            ));

            //mDao = new WorkMateDetailItemManager();
        }

        callDao =
                HttpNursingRequest
                        .getInstance()
                        .getApi()
                        .postObservableNursingByCondition(
                                9,
                                "SELECT_WORKMATE_DETAIL_MODE",
                                mDate,
                                mLocation,
                                mShift,
                                mSection,
                                mSectionSex
                        )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
        mDisposable = callDao.subscribe(
                new Consumer<NursingItemCollectionDao>() {
                    @Override
                    public void accept(NursingItemCollectionDao dao) throws Exception {
                        /*mDao.setDao(dao);
                        Toast.makeText(getContext(),
                                dao.getData().size()+"",
                                Toast.LENGTH_SHORT).show();*/
                        setResponseToView(dao);
                    }
                },
                new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        /*Toast.makeText(getContext(),
                                "Throw " + throwable.getMessage(),
                                Toast.LENGTH_SHORT).show();*/
                        showAlertDialog(
                                "TimeOut Or Throwable!",
                                "TimeOut: Throwable at " +
                                        throwable.getMessage(),
                                true
                        );
                    }
                }
        );
    }

    private void setResponseToView(NursingItemCollectionDao dao) {
        if (dao.getData().size() > 0) {
            tvResult.setVisibility(View.GONE);
            mAdapter =
                    new WorkMateDetailAdapter(getContext(), dao);
            rcv.setAdapter(mAdapter);
        } else {
            tvResult.setVisibility(View.VISIBLE);
        }
    }


    private void showAlertDialog(String title, String msg, boolean alertType) {
        MyAlertDialog dialog = MyAlertDialog.newInstance(
                title, msg, alertType
        );

        dialog.show(getFragmentManager(), null);
    }


}
