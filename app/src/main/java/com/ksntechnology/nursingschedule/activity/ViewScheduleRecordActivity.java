package com.ksntechnology.nursingschedule.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.ksntechnology.nursingschedule.R;
import com.ksntechnology.nursingschedule.adapter.NursingItemAdapter;
import com.ksntechnology.nursingschedule.dao.ChildItem;
import com.ksntechnology.nursingschedule.dao.NursingItemCollectionDao;
import com.ksntechnology.nursingschedule.dao.SectionItem;
import com.ksntechnology.nursingschedule.manager.HttpNursingRequest;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewScheduleRecordActivity extends AppCompatActivity {
    private final String[] mThaiMonth = {
            "มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน",
            "พฤษภาคม", "มิถุนายนต์", "กรกฎาคม", "สิงหาคม",
            "กันยายน", "ตุลาคม", "พฤษจิกายน",
            "ธันวาคม"
    };
    private final String MORNING_SHIFT = "MORNING";
    private final String AFTERNOON_SHIFT = "AFTERNOON";
    private final String NIGHT_SHIFT = "NIGHT";
    private String mUserWorking = "";
    private NursingItemAdapter mAdapter;

    private Spinner spnnMonth;
    private Spinner spnnYear;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private RecyclerView rcv;
    //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_schedule_record);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            //
        }

        initInstance();
    }

    private void initInstance() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        rcv = findViewById(R.id.recyclerView);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        spnnMonth = toolbar.findViewById(R.id.spinner_month);
        spnnYear = toolbar.findViewById(R.id.spinner_year);
        //getDataTest();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setConditionView();
        Intent intent = getIntent();
        mUserWorking = intent.getStringExtra("user_working");
    }

    private void setConditionView() {
        rcv.setHasFixedSize(true);
        rcv.setLayoutManager(new LinearLayoutManager(
                this, RecyclerView.VERTICAL, false
        ));

        Calendar calendar = Calendar.getInstance();
        int cMonth = calendar.get(Calendar.MONTH);
        int cYear = calendar.get(Calendar.YEAR)+543;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, R.layout.spinner_item_layout, mThaiMonth
        );
        spnnMonth.setAdapter(adapter);
        spnnMonth.setSelection(cMonth);

        ArrayList yearSelect = new ArrayList();
        for (int i=0; i<5; i++) {
            yearSelect.add(cYear-i);
        }
        adapter = new ArrayAdapter<>(
                this, R.layout.spinner_item_layout,
                yearSelect
        );
        spnnYear.setAdapter(adapter);
        spnnYear.setSelection(0);


        spnnMonth.setOnItemSelectedListener(spinnerMonthItemSelect);
        spnnYear.setOnItemSelectedListener(spinnerYearItemSelect);

        //getDataTest();
        //loadingData();
    }

    private void loadingData() {
        int month = spnnMonth.getSelectedItemPosition()+1;
        int year = Integer.valueOf(spnnYear.getSelectedItem().toString())-543;
        //Log.d("SPINNER", month + " " + year);

        Call<NursingItemCollectionDao> call =
                HttpNursingRequest.getInstance().getApi().postNursingSchedule(
                        mUserWorking, month, year
                );
        call.enqueue(new Callback<NursingItemCollectionDao>() {
            @Override
            public void onResponse(Call<NursingItemCollectionDao> call,
                                   Response<NursingItemCollectionDao> response) {
                if (response.isSuccessful()) {
                    NursingItemCollectionDao dao = response.body();
                    setDataToRecyclerView(dao);
                } else {
                    FancyToast.makeText(
                            getApplicationContext(),
                            "Error " + response.errorBody().toString(),
                            FancyToast.LENGTH_LONG,
                            FancyToast.ERROR,
                            true
                    ).show();
                }
            }

            @Override
            public void onFailure(Call<NursingItemCollectionDao> call,
                                  Throwable t) {
                FancyToast.makeText(
                        getApplicationContext(),
                        "Throw " + t.getMessage(),
                        FancyToast.LENGTH_LONG,
                        FancyToast.ERROR,
                        true
                ).show();
            }
        });
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

                Log.d("RecyclerView", "Result " + dateMonth);
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
            } else {
                iconID = R.drawable.ic_night;
                shiftType = "เวรดึก";
            }

            Log.d("RecyclerView", "Result ID " + id);
            item.add(new ChildItem(
                    id,
                    iconID,
                    location,
                    shiftType
            ));
        }

        mAdapter = new NursingItemAdapter(this, item);
        rcv.setAdapter(mAdapter);
        mAdapter.setOnItemSelectListener(new NursingItemAdapter.onItemSelectListener() {
            @Override
            public void onItemSelect(View view, int position, int id) {
                Toast.makeText(ViewScheduleRecordActivity.this,
                        "Select " + id, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDataTest() {
        Call<NursingItemCollectionDao> call =
                HttpNursingRequest.getInstance().getApi().postTest();
        call.enqueue(new Callback<NursingItemCollectionDao>() {
            @Override
            public void onResponse(Call<NursingItemCollectionDao> call,
                                   Response<NursingItemCollectionDao> response) {
                if (response.isSuccessful()) {
                    NursingItemCollectionDao dao = response.body();
                    String str = String.valueOf(dao.getData().get(1).getId());
                    str += "\n" + dao.getData().get(1).getDate();
                    str += "\n" + dao.getData().get(1).getJobType();
                    str += "\n" + dao.getData().get(1).getShift();
                    str += "\n" + dao.getData().get(1).getLocation();
                    str += "\n" + dao.getData().get(1).getRemark();


                    Log.d("DAO", str);
                    FancyToast.makeText(
                            getApplicationContext(),
                            str,
                            FancyToast.LENGTH_LONG,
                            FancyToast.SUCCESS,
                            true
                    ).show();
                } else {
                    Log.d("DAO", response.errorBody().toString());
                    FancyToast.makeText(
                            getApplicationContext(),
                            response.errorBody().toString(),
                            FancyToast.LENGTH_LONG,
                            FancyToast.ERROR,
                            true
                    ).show();
                }
            }

            @Override
            public void onFailure(Call<NursingItemCollectionDao> call,
                                  Throwable t) {
                Log.d("DAO", t.getMessage());
                FancyToast.makeText(
                        getApplicationContext(),
                        t.getMessage(),
                        FancyToast.LENGTH_LONG,
                        FancyToast.ERROR,
                        true
                ).show();
            }
        });
    }


    /**********************************
     *  Listener Zone
     */
    AdapterView.OnItemSelectedListener spinnerMonthItemSelect = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent,
                                   View view, int position, long id) {
            Log.d("SPINNER", "Result spinner month");
            loadingData();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener spinnerYearItemSelect = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Log.d("SPINNER", "Result spinner year");
            loadingData();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };



}
