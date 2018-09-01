package com.ksntechnology.nursingschedule.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.ksntechnology.nursingschedule.R;
import com.ksntechnology.nursingschedule.adapter.ViewNursingAdapter;
import com.ksntechnology.nursingschedule.adapter.model.BaseViewNursingItem;
import com.ksntechnology.nursingschedule.adapter.model.ViewNursingConverter;
import com.ksntechnology.nursingschedule.dao.NursingItemCollectionDao;
import com.ksntechnology.nursingschedule.dao.ResultDeleteEditDao;
import com.ksntechnology.nursingschedule.dialog.ConfirmDialog;
import com.ksntechnology.nursingschedule.manager.Contextor;
import com.ksntechnology.nursingschedule.manager.HttpNursingRequest;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ViewNursingFragment extends Fragment {
    /**********************************
     *  Variable(s)
     */
    private final String[] mThaiMonth = {
            "มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน",
            "พฤษภาคม", "มิถุนายนต์", "กรกฎาคม", "สิงหาคม",
            "กันยายน", "ตุลาคม", "พฤษจิกายน",
            "ธันวาคม"
    };
    private final String MORNING_SHIFT = "MORNING";
    private final String AFTERNOON_SHIFT = "AFTERNOON";
    private final String NIGHT_SHIFT = "NIGHT";
    private final int CONFIRM_DELETE = 1;
    private final int CONFIRM_EDIT = 2;

    private final int PREVIOUS_ACTIVITY = 0;
    private final int SHOW_ITEM_DETAIL = 1;
    private final int EDIT_OR_DELETE_ITEM = 2;

    private String mUserWorking = "";
    private ViewNursingAdapter mAdapter;
    //private NursingListManager mDao;
    private Disposable mDisposable;

    private Spinner spnnMonth;
    private Spinner spnnYear;
    private RecyclerView rcv;
    private ImageView imgBack;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<BaseViewNursingItem> mItemPacket;


    public interface OnCallItemClickListener{
        void onBackClick();
        void onShowDetail(int id);
        void onCallEditOrDelete(int id);
    }


    public static ViewNursingFragment newInstance(String userWorking) {
        ViewNursingFragment fragment = new ViewNursingFragment();
        Bundle args = new Bundle();
        args.putString("user_working", userWorking);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserWorking = getArguments().getString("user_working");
        //Log.d("DebugUserWorking", mUserWorking);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_view_schedule_record_layout,
                container, false
        );

        initInstance(view);
        return view;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /*private void onReStoreSaveInstancestate(Bundle saveInstanceState) {
        //
    }*/


    @Override
    public void onDestroy() {
        if (mDisposable != null && mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        super.onDestroy();
    }

    /**********************************
     *  Function
     */
    private void initInstance(View view) {
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        rcv = view.findViewById(R.id.recyclerViewFragment2);
        spnnMonth = view.findViewById(R.id.spinner_month);
        spnnYear = view.findViewById(R.id.spinner_year);
        imgBack = view.findViewById(R.id.imageBack);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                loadingData();
            }
        });
        imgBack.setOnClickListener(imageBackClickListener);

        //setHasOptionsMenu(true);
        setConditionView();
    }

    private void setConditionView() {
        //mDao = new NursingListManager();
        //rcv.setHasFixedSize(true);
        rcv.setLayoutManager(new LinearLayoutManager(
                getContext(),
                RecyclerView.VERTICAL,
                false
        ));
        mAdapter = new ViewNursingAdapter(getContext());
        rcv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(recyclerAdapterItemClick);
        mAdapter.setOnItemLongClickListener(recyclerAdapterLongItemClick);

        Calendar calendar = Calendar.getInstance();
        int cMonth = calendar.get(Calendar.MONTH);
        int cYear = calendar.get(Calendar.YEAR)+543;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                Contextor.getInstance().getContext(),
                R.layout.spinner_item_layout, mThaiMonth
        );
        spnnMonth.setAdapter(adapter);
        spnnMonth.setSelection(cMonth);

        ArrayList yearSelect = new ArrayList();
        for (int i=0; i<5; i++) {
            yearSelect.add(cYear-i);
        }
        adapter = new ArrayAdapter<>(
                Contextor.getInstance().getContext(),
                R.layout.spinner_item_layout,
                yearSelect
        );
        spnnYear.setAdapter(adapter);
        spnnYear.setSelection(0);

        spnnMonth.setOnItemSelectedListener(spinnerMonthItemSelect);
        spnnYear.setOnItemSelectedListener(spinnerYearItemSelect);
    }

    private void setDataToRecyclerView(NursingItemCollectionDao dao) {
        String fDate = "";
        String lastDate = "";
        String dateMonth = "";
        int iMonth = 0;
        int iDay = 0;
        String shiftType = "";
        String location = "";
        int iconID;
        int id;
        mItemPacket = new ArrayList();

        if (dao.getData().size() <= 0 || dao.getData() == null) {
            mItemPacket.add(ViewNursingConverter.createNoDataItem());
            mAdapter.setmItem(mItemPacket);
            mAdapter.notifyDataSetChanged();
            return;
        }

        for (int i=0; i<dao.getData().size(); i++) {
            id = dao.getData().get(i).getId();
            fDate = dao.getData().get(i).getDate();

            if (!fDate.equals(lastDate)) {
                //YYYY-MM-DD
                String[] arrDMY = fDate.split("-");
                iDay = Integer.valueOf(arrDMY[2]);
                iMonth = Integer.valueOf(arrDMY[1]);
                dateMonth = ((iDay < 10) ? "0" : "") + iDay;
                dateMonth += " " + mThaiMonth[iMonth-1];
                mItemPacket.add(ViewNursingConverter.createSectionItem(dateMonth));

                lastDate = fDate;
            }

            shiftType = dao.getData().get(i).getShift();
            location = dao.getData().get(i).getLocation();
            if (shiftType.equals(MORNING_SHIFT)) {
                iconID = R.drawable.ic_morning;
                shiftType = "เวรเช้า";
            } else if (shiftType.equals(AFTERNOON_SHIFT)) {
                iconID = R.drawable.ic_afternoon;
                shiftType = "เวรบ่าย";
            } else if (shiftType.equals(NIGHT_SHIFT)) {
                iconID = R.drawable.ic_night;
                shiftType = "เวรดึก";
            } else {
                iconID = R.drawable.ic_free_day;
                shiftType = "วันหยุด";
            }

            mItemPacket.add(ViewNursingConverter
                            .createChildItem(
                                    id,
                                    iconID,
                                    location,
                                    shiftType));
        }

        mAdapter.setmItem(mItemPacket);
        mAdapter.notifyDataSetChanged();
    }

    private void callBackToActivity(int callBackType, int id) {
        OnCallItemClickListener listener =
                (OnCallItemClickListener) getActivity();

        if (callBackType == PREVIOUS_ACTIVITY) {
            listener.onBackClick();
        }else if (callBackType == SHOW_ITEM_DETAIL) {
            listener.onShowDetail(id);
        }else if (callBackType == EDIT_OR_DELETE_ITEM) {
            listener.onCallEditOrDelete(id);
        }
    }


    private void showToastMsg(String text, int toastType) {
        FancyToast.makeText(
                Contextor.getInstance().getContext(),
                text,
                FancyToast.LENGTH_LONG,
                toastType,
                true
        ).show();
    }

    private void deleteItem(int id) {
        String postMode = "DELETE";
        Observable<ResultDeleteEditDao> postDelete =
                HttpNursingRequest
                        .getInstance()
                        .getApi()
                        .postDeleteOrEdit(
                                postMode,
                                id,
                                mUserWorking,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null
                        )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());

        mDisposable = postDelete.subscribe(new Consumer<ResultDeleteEditDao>() {
            @Override
            public void accept(ResultDeleteEditDao response) throws Exception {
                if (response.getErrMsg().equals("SUCCESS")) {
                    loadingData();
                    showToastMsg(
                            "ลบข้อมูลสำเร็จ",
                            FancyToast.SUCCESS
                    );
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                showToastMsg(
                        "Throw " + throwable.getMessage(),
                        FancyToast.ERROR
                );
            }
        });
    }

    private void loadingData() {
        int month = spnnMonth.getSelectedItemPosition()+1;
        int year = Integer.valueOf(spnnYear.getSelectedItem().toString())-543;

        Observable<NursingItemCollectionDao> mNursingObservalble =
                HttpNursingRequest
                        .getInstance()
                        .getApi()
                        .postObservableNursing(mUserWorking, month, year)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
        mDisposable = mNursingObservalble.subscribe(
                new Consumer<NursingItemCollectionDao>() {
                    @Override
                    public void accept(NursingItemCollectionDao dao) throws Exception {
                        //mDao.setDao(dao);
                        setDataToRecyclerView(dao);
                    }
                },
                new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showToastMsg(
                                "Throw " + throwable.getMessage(),
                                FancyToast.ERROR
                        );
                    }
                }
        );
    }


    /**********************************
     *  Listener Zone
     */
    AdapterView.OnItemSelectedListener spinnerMonthItemSelect = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            loadingData();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener spinnerYearItemSelect = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            loadingData();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    View.OnClickListener imageBackClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            callBackToActivity(PREVIOUS_ACTIVITY, 0);
        }
    };

    ViewNursingAdapter.OnItemClickListener recyclerAdapterItemClick = new ViewNursingAdapter.OnItemClickListener() {
        @Override
        public void onViewNursingItemDetail(int id, int position) {
            callBackToActivity(SHOW_ITEM_DETAIL, id);
        }
    };


    ViewNursingAdapter.OnItemLongClickListener recyclerAdapterLongItemClick = new ViewNursingAdapter.OnItemLongClickListener() {
        @Override
        public void onEditOrDeleteItem(final int id, int position) {
            ConfirmDialog dialog = ConfirmDialog.newInstance(
                    "คุณต้องการแก้ไขหรือลบรายงานนี้?",
                    "ลบข้อมูล", "แก้ไขข้อมูล"
            );

            dialog.show(getFragmentManager(), null);
            dialog.setOnFinishDialogListener(new ConfirmDialog.onFinishDialogListener() {
                @Override
                public void onFinishDialog(int selectIndex) {
                    if (selectIndex == CONFIRM_DELETE) {
                        deleteItem(id);
                    } else if (selectIndex == CONFIRM_EDIT) {
                        callBackToActivity(EDIT_OR_DELETE_ITEM, id);
                    }
                }
            });
        }
    };


}
