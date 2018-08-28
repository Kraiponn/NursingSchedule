package com.ksntechnology.nursingschedule.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.ksntechnology.nursingschedule.R;
import com.ksntechnology.nursingschedule.adapter.NursingItemAdapter;
import com.ksntechnology.nursingschedule.dao.ChildItem;
import com.ksntechnology.nursingschedule.dao.NursingItemCollectionDao;
import com.ksntechnology.nursingschedule.dao.ResultDeleteEditDao;
import com.ksntechnology.nursingschedule.dao.SectionItem;
import com.ksntechnology.nursingschedule.dialog.ConfirmDialog;
import com.ksntechnology.nursingschedule.manager.Contextor;
import com.ksntechnology.nursingschedule.manager.HttpNursingRequest;
import com.ksntechnology.nursingschedule.manager.NursingListManager;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.Calendar;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ViewScheduleRecordFragment extends Fragment {
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
    private static final int CONFIRM_DELETE = 1;
    private static final int CONFIRM_EDIT = 2;

    private String mUserWorking = "";
    private NursingItemAdapter mAdapter;
    private NursingListManager mDao;
    private Disposable mDisposable;

    private Spinner spnnMonth;
    private Spinner spnnYear;
    private RecyclerView rcv;
    private ImageView imgBack;
    private SwipeRefreshLayout swipeRefreshLayout;


    public interface onBackClickListener{
        void onBackClick();
    }

    public interface onShowDetailListener{
        void onShowDetail(int id);
    }

    public interface onCallEditOrDeleteListener{
        void onCallEditOrDelete(int id);
    }

    public static ViewScheduleRecordFragment newInstance(String userWorking) {
        ViewScheduleRecordFragment fragment = new ViewScheduleRecordFragment();
        Bundle args = new Bundle();
        args.putString("user_working", userWorking);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDao = new NursingListManager();
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
                //Toast.makeText(getContext(), "Refresh success", Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
                loadingData();
            }
        });
        imgBack.setOnClickListener(imageBackClickListener);

        //setHasOptionsMenu(true);
        setConditionView();
    }

    private void setConditionView() {
        mDao = new NursingListManager();
        rcv.setHasFixedSize(true);
        rcv.setLayoutManager(new LinearLayoutManager(
                getContext(),
                RecyclerView.VERTICAL,
                false
        ));

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
        ArrayList item = new ArrayList();


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
                item.add(new SectionItem(dateMonth));

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

            //Log.d("RecyclerView", "Result ID " + id);
            item.add(new ChildItem(
                    id,
                    iconID,
                    location,
                    shiftType
            ));
        }

        showData(item);
    }

    private void showData(ArrayList item) {
        mAdapter = new NursingItemAdapter(getActivity(), item);
        /*rcv.setHasFixedSize(true);
        rcv.setLayoutManager(new LinearLayoutManager(
                getContext(),
                RecyclerView.VERTICAL,
                false
        ));*/
        rcv.setAdapter(mAdapter);

        mAdapter.setOnItemSelectListener(new NursingItemAdapter.onItemSelectListener() {
            @Override
            public void onItemSelect(View view, int position, int id) {
                onShowDetailListener listener = (onShowDetailListener) getActivity();
                listener.onShowDetail(id);
            }
        });

        mAdapter.setOnLongItemSelectListener(new NursingItemAdapter.onLongItemSelectListener() {
            @Override
            public void onLongItemSelect(View view, final int position, final int id) {
                ConfirmDialog dialog = ConfirmDialog.newInstance(
                        "คุณต้องการแก้ไขหรือลบรายงานนี้",
                        "ลบข้อมูล","แก้ไขข้อมูล"
                );

                dialog.show(getFragmentManager(), null);
                dialog.setOnFinishDialogListener(new ConfirmDialog.onFinishDialogListener() {
                    @Override
                    public void onFinishDialog(int selectIndex) {
                        if (selectIndex == CONFIRM_DELETE) {
                            deleteItem(id, position);
                        } else if (selectIndex == CONFIRM_EDIT) {
                            onCallEditOrDeleteListener listener =
                                        (onCallEditOrDeleteListener) getActivity();
                            listener.onCallEditOrDelete(id);
                        }
                    }
                });
            }
        });
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

    private void deleteItem(int id, final int position) {
        String userWorking = mUserWorking;
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
                    //mAdapter.notifyItemRemoved(position);
                    rcv.scrollToPosition(position-1);
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
        //mUserWorking = "admin";

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
        public void onItemSelected(AdapterView<?> parent,
                                   View view, int position, long id) {
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
            onBackClickListener listener = (onBackClickListener) getActivity();
            listener.onBackClick();
        }
    };


}
