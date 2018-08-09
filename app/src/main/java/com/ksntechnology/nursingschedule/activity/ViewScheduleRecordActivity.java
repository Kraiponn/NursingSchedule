package com.ksntechnology.nursingschedule.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ksntechnology.nursingschedule.R;

import java.util.ArrayList;
import java.util.Calendar;

public class ViewScheduleRecordActivity extends AppCompatActivity {
    private final String[] mThaiMonth = {
            "มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน",
            "พฤษภาคม", "มิถุนายนต์", "กรกฎาคม", "สิงหาคม",
            "กันยายน", "ตุลาคม", "พฤษจิกายน",
            "ธันวาคม"
    };

    private Spinner spnnMonth;
    private Spinner spnnYear;
    private Toolbar toolbar;
    private FloatingActionButton fab;


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
    }

    @Override
    protected void onStart() {
        super.onStart();
        setConditionView();
    }

    private void setConditionView() {
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
    }
}
