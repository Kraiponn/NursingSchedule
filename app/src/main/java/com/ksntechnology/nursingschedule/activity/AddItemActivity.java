package com.ksntechnology.nursingschedule.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ksntechnology.nursingschedule.R;
import com.ksntechnology.nursingschedule.dao.AddNursingItemDao;
import com.ksntechnology.nursingschedule.dao.SectionCollectionDao;
import com.ksntechnology.nursingschedule.dao.WorkLocationCollectionDao;
import com.ksntechnology.nursingschedule.dialog.MyAlertDialog;
import com.ksntechnology.nursingschedule.dialog.MyDatePickerDialog;
import com.ksntechnology.nursingschedule.dialog.MyTimePickerDialog;
import com.ksntechnology.nursingschedule.dialog.SingleChoiceDialog;
import com.ksntechnology.nursingschedule.manager.HttpNursingRequest;
import com.shashank.sony.fancytoastlib.FancyToast;


import java.util.ArrayList;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddItemActivity extends AppCompatActivity {
    private EditText edtDate;
    private ImageButton btnDate;
    private RadioButton radFreeDay;
    private RadioButton radWorkingDay;
    private LinearLayout layoutMorning;
    private LinearLayout layoutAfternoon;
    private LinearLayout layoutNight;

    private CheckBox chkShiftMor;
    private RadioGroup radioGroupMor;
    private RadioButton radRealJobMor;
    private RadioButton radOtJobMor;
    private RadioButton radMenSectionMor;
    private RadioButton radWomenbMor;
    private ImageButton btnFromTimeMor;
    private ImageButton btnToTimeMor;
    private ImageButton btnLocationMor;
    private ImageButton btnRemarkMor;
    private ImageButton btnSectionMor;
    private EditText edtFromTimeMor;
    private EditText edtToTimeMor;
    private EditText edtLocationMor;
    private EditText edtRemarkMor;
    private EditText edtSectionMor;

    private CheckBox chkShiftAft;
    private RadioGroup radioGroupAft;
    private RadioButton radRealJobAft;
    private RadioButton radOtJobAft;
    private RadioButton radMenSectionAft;
    private RadioButton radWomenbAft;
    private ImageButton btnFromTimeAft;
    private ImageButton btnToTimeAft;
    private ImageButton btnLocationAft;
    private ImageButton btnRemarkAft;
    private ImageButton btnSectionAft;
    private EditText edtFromTimeAft;
    private EditText edtToTimeAft;
    private EditText edtLocationAft;
    private EditText edtRemarkAft;
    private EditText edtSectionAft;

    private CheckBox chkShiftNig;
    private RadioGroup radioGroupNig;
    private RadioButton radRealJobNig;
    private RadioButton radOtJobNig;
    private RadioButton radMenSectionNig;
    private RadioButton radWomenbNig;
    private ImageButton btnFromTimeNig;
    private ImageButton btnToTimeNig;
    private ImageButton btnLocationNig;
    private ImageButton btnRemarkNig;
    private ImageButton btnSectionNig;
    private EditText edtFromTimeNig;
    private EditText edtToTimeNig;
    private EditText edtLocationNig;
    private EditText edtRemarkNig;
    private EditText edtSectionNig;

    private Button btnAddItem;
    private Button btnClearItem;
    private String mUser;
    private final String[] arrRemark = {
            "Medical Nurse", "InCharge",
            "Day", "Night",
            "Other"
    };

    private final int TOAST_TYPE_SUCCESS = 1;
    private final int TOAST_TYPE_WARNING = 2;
    private final int TOAST_TYPE_ERROR = 3;

    private final int DISPLAY_SHIFT_MORNING_TYPE = 1;
    private final int DISPLAY_SHIFT_AFTERNOON_TYPE = 2;
    private final int DISPLAY_SHIFT_NIGHT_TYPE = 3;
    private final String RESPONSE_DUPLICATE_FREE_DAY = "DuplicateFreeDay"; //-- FREE DAY
    private final String RESPONSE_DUPLICATE_WORK_DAY = "DuplicateWorkDay"; //-- WORKING DAY
    private final boolean WORK_FREE_DAY = false;
    private final boolean WORK_NORMAL_DAY = true;

    private Disposable mDisposable;
    //private ArrayList mArrLocation;
    private Observable<WorkLocationCollectionDao> mLocationObservalble;
    private Observable<SectionCollectionDao> mSectionObservalble;
    private String[] mLocationItem;
    private String[] mSectionItem;


    /***********************************
     *  Method Zone
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            //
        }

        initInstance();
        if (savedInstanceState == null) {
            radFreeDay.setChecked(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //initAllView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();

        if (radFreeDay.isChecked()) {
            setWorkingDayType(WORK_FREE_DAY);
        } else {
            setWorkingDayType(WORK_NORMAL_DAY);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(
                R.anim.from_top, R.anim.to_bottom
        );
    }

    @Override
    protected void onDestroy() {
        if (mDisposable != null && mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    /*************************************
     *  Custom Method
     *
     */
    private void initInstance() {
        btnDate = findViewById(R.id.imageButton_date);
        edtDate = findViewById(R.id.edit_date);
        radFreeDay = findViewById(R.id.radioFreeDay);
        radWorkingDay = findViewById(R.id.radioWorkingDay);
        btnAddItem = findViewById(R.id.button_addItem);
        btnClearItem = findViewById(R.id.button_ClearItem);
        layoutMorning = findViewById(R.id.layoutMorning);
        layoutAfternoon = findViewById(R.id.layoutAfternoon);
        layoutNight = findViewById(R.id.layoutNight);

        chkShiftMor = findViewById(R.id.checkbox_morShift);
        radioGroupMor = findViewById(R.id.radioGroup_jobTypeMor);
        radRealJobMor = findViewById(R.id.radio_realJobMor);
        radOtJobMor = findViewById(R.id.radio_otJobMor);
        edtFromTimeMor = findViewById(R.id.edit_fromTimeMor);
        edtToTimeMor = findViewById(R.id.edit_toTimeMor);
        edtLocationMor = findViewById(R.id.edit_locationMor);
        edtRemarkMor = findViewById(R.id.edit_remarkMor);
        btnFromTimeMor = findViewById(R.id.imageButton_fromTimeMor);
        btnToTimeMor = findViewById(R.id.imageButton_toTimeMor);
        btnLocationMor = findViewById(R.id.imageButton_locationMor);
        btnRemarkMor = findViewById(R.id.imageButton_RemarkMor);
        radMenSectionMor = findViewById(R.id.radio_sectionManMor);
        radWomenbMor = findViewById(R.id.radio_sectionWomanMor);
        edtSectionMor = findViewById(R.id.edit_sectionMor);
        btnSectionMor = findViewById(R.id.imageButton_sectionMor);

        chkShiftAft = findViewById(R.id.checkbox_aftShift);
        radioGroupAft = findViewById(R.id.radioGroup_jobTypeAft);
        radRealJobAft = findViewById(R.id.radio_realJobAft);
        radOtJobAft = findViewById(R.id.radio_otJobAft);
        edtFromTimeAft = findViewById(R.id.edit_fromTimeAft);
        edtToTimeAft = findViewById(R.id.edit_toTimeAft);
        edtLocationAft = findViewById(R.id.edit_locationAft);
        edtRemarkAft = findViewById(R.id.edit_remarkAft);
        btnFromTimeAft = findViewById(R.id.imageButton_fromTimeAft);
        btnToTimeAft = findViewById(R.id.imageButton_toTimeAft);
        btnLocationAft = findViewById(R.id.imageButton_locationAft);
        btnRemarkAft = findViewById(R.id.imageButton_RemarkAft);
        radMenSectionAft = findViewById(R.id.radio_sectionManAft);
        radWomenbAft = findViewById(R.id.radio_sectionWomanAft);
        edtSectionAft = findViewById(R.id.edit_sectionAft);
        btnSectionAft = findViewById(R.id.imageButton_sectionAft);

        chkShiftNig = findViewById(R.id.checkbox_nigShift);
        radioGroupNig = findViewById(R.id.radioGroup_jobTypeNig);
        radRealJobNig = findViewById(R.id.radio_realJobNig);
        radOtJobNig = findViewById(R.id.radio_otJobNig);
        edtFromTimeNig = findViewById(R.id.edit_fromTimeNig);
        edtToTimeNig = findViewById(R.id.edit_toTimeNig);
        edtLocationNig = findViewById(R.id.edit_locationNig);
        edtRemarkNig = findViewById(R.id.edit_remarkNig);
        btnFromTimeNig = findViewById(R.id.imageButton_fromTimeNig);
        btnToTimeNig = findViewById(R.id.imageButton_toTimeNig);
        btnLocationNig = findViewById(R.id.imageButton_locationNig);
        btnRemarkNig= findViewById(R.id.imageButton_RemarkNig);
        radMenSectionNig = findViewById(R.id.radio_sectionManNig);
        radWomenbNig = findViewById(R.id.radio_sectionWomanNig);
        edtSectionNig= findViewById(R.id.edit_sectionNig);
        btnSectionNig = findViewById(R.id.imageButton_sectionNig);

        btnDate.setOnClickListener(btnDateClickListener);
        radFreeDay.setOnClickListener(radFreeDayClick);
        radWorkingDay.setOnClickListener(radWorkingDayClick);
        btnAddItem.setOnClickListener(btnAddItemClickListener);
        btnClearItem.setOnClickListener(btnClearItemClickListener);

        chkShiftMor.setOnCheckedChangeListener(chkMorShiftCheckChanged);
        btnFromTimeMor.setOnClickListener(btnFromTimeMorClickListener);
        btnLocationMor.setOnClickListener(btnLocationMorClickListener);
        btnToTimeMor.setOnClickListener(btnToTimeMorClickListener);
        btnRemarkMor.setOnClickListener(btnRemarkClick);
        btnSectionMor.setOnClickListener(btnSectionMorClicked);

        chkShiftAft.setOnCheckedChangeListener(chkAftShiftCheckChanged);
        btnFromTimeAft.setOnClickListener(btnFromTimeAftClickListener);
        btnLocationAft.setOnClickListener(btnLocationAftClickListener);
        btnToTimeAft.setOnClickListener(btnToTimeAftClickListener);
        btnRemarkAft.setOnClickListener(btnRemarkAftClick);
        btnSectionAft.setOnClickListener(btnSectionAftClicked);

        chkShiftNig.setOnCheckedChangeListener(chkNigShiftCheckChanged);
        btnFromTimeNig.setOnClickListener(btnFromTimeNigClickListener);
        btnLocationNig.setOnClickListener(btnLocationNigClickListener);
        btnToTimeNig.setOnClickListener(btnToTimeNigClickListener);
        btnRemarkNig.setOnClickListener(btnRemarkNigClick);
        btnSectionNig.setOnClickListener(btnSectionNigClicked);
    }

    private void init() {
        Intent intent = getIntent();
        mUser = intent.getStringExtra("user_working");

        //mArrLocation = new ArrayList();
        setWorkLocation();
        setWorkSection();
    }

    private void setWorkLocation() {
        mLocationObservalble =
                HttpNursingRequest
                        .getInstance()
                        .getApi()
                        .getWorkLocation("REQUEST_LOCATION")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
        mDisposable = mLocationObservalble.subscribe(
                new Consumer<WorkLocationCollectionDao>() {
                    @Override
                    public void accept(WorkLocationCollectionDao dao) throws Exception {
                        mLocationItem = new String[dao.getData().size()];
                        for (int i=0; i<dao.getData().size(); i++) {
                            mLocationItem[i] = dao.getData().get(i).getLocationName();
                        }
                    }
                },
                new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        MyAlertDialog dialog = MyAlertDialog.newInstance(
                                "เกิดข้อผิดพลาด",
                                "การเชื่อมต่อล้มเหลว โปรดลองอีกครั้ง",
                                true
                        );
                        dialog.show(getSupportFragmentManager(), null);
                    }
                }
        );
    }

    private void setWorkSection() {
        mSectionObservalble =
                HttpNursingRequest
                        .getInstance()
                        .getApi()
                        .getWorkSection("REQUEST_SECTION")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());

        mDisposable = mSectionObservalble.subscribe(
                new Consumer<SectionCollectionDao>() {
                    @Override
                    public void accept(SectionCollectionDao dao) throws Exception {
                        mSectionItem = new String[dao.getData().size()];
                        for (int i=0; i<dao.getData().size(); i++) {
                            mSectionItem[i] = dao.getData().get(i).getSection();
                        }
                    }
                },
                new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        MyAlertDialog dialog = MyAlertDialog.newInstance(
                                "เกิดข้อผิดพลาด",
                                "การเชื่อมต่อล้มเหลว โปรดลองอีกครั้ง",
                                true
                        );
                        dialog.show(getSupportFragmentManager(), null);
                    }
                }
        );
    }

    private void showLocationDialog(final String[] locationItem, final View view) {
        SingleChoiceDialog dialog = SingleChoiceDialog.newInstance(
                "กรุณาเลือกสถานที่ทำงาน",
                locationItem,
                "ตกลง"
        );
        dialog.show(getSupportFragmentManager(), null);
        dialog.setOnFinishDialogListener(new SingleChoiceDialog.onFinishDialogListener() {
            @Override
            public void onFinishDialog(int selectIndex) {
                if (selectIndex != -1) {
                    switch (view.getId()) {
                        case R.id.imageButton_locationMor:
                            edtLocationMor.setText(locationItem[selectIndex]);
                            break;
                        case R.id.imageButton_locationAft:
                            edtLocationAft.setText(locationItem[selectIndex]);
                            break;
                        case R.id.imageButton_locationNig:
                            edtLocationNig.setText(locationItem[selectIndex]);
                            break;
                    }
                }
            }
        });
    }

    private void showSectionDialog(final String[] sectionItem, final View view) {
        SingleChoiceDialog dialog = SingleChoiceDialog.newInstance(
                "กรุณาเลือกฝ่ายหรือแผนกในสถานที่ทำงาน",
                sectionItem,
                "ตกลง"
        );
        dialog.show(getSupportFragmentManager(), null);
        dialog.setOnFinishDialogListener(new SingleChoiceDialog.onFinishDialogListener() {
            @Override
            public void onFinishDialog(int selectIndex) {
                if (selectIndex != -1) {
                    switch (view.getId()) {
                        case R.id.imageButton_sectionMor:
                            edtSectionMor.setText(sectionItem[selectIndex]);
                            break;
                        case R.id.imageButton_sectionAft:
                            edtSectionAft.setText(sectionItem[selectIndex]);
                            break;
                        case R.id.imageButton_sectionNig:
                            edtSectionNig.setText(sectionItem[selectIndex]);
                            break;
                    }
                }
            }
        });
    }

    private void setWorkingDayType(boolean workingDayType) {
        if (workingDayType == WORK_FREE_DAY) {
            chkShiftMor.setVisibility(View.GONE);
            chkShiftAft.setVisibility(View.GONE);
            chkShiftNig.setVisibility(View.GONE);
            setDisplayAllShift(false);
            //Log.d("WORKTYPE", "FREE");
        } else {
            chkShiftMor.setVisibility(View.VISIBLE);
            chkShiftAft.setVisibility(View.VISIBLE);
            chkShiftNig.setVisibility(View.VISIBLE);

            setDisplayAllShift(true);
            //Log.d("WORKTYPE", "WORKING");
        }
    }

    private void setDisplayAllShift(boolean on_off) {
        if (on_off) {
            if (chkShiftMor.isChecked()) {
                setDisplayShiftDetails(true, DISPLAY_SHIFT_MORNING_TYPE);
            } else {
                setDisplayShiftDetails(false, DISPLAY_SHIFT_MORNING_TYPE);
            }

            if (chkShiftAft.isChecked()) {
                setDisplayShiftDetails(true, DISPLAY_SHIFT_AFTERNOON_TYPE);
            } else {
                setDisplayShiftDetails(false, DISPLAY_SHIFT_AFTERNOON_TYPE);
            }

            if (chkShiftNig.isChecked()) {
                setDisplayShiftDetails(true, DISPLAY_SHIFT_NIGHT_TYPE);
            } else {
                setDisplayShiftDetails(false, DISPLAY_SHIFT_NIGHT_TYPE);
            }
        }else {
            setDisplayShiftDetails(false, DISPLAY_SHIFT_MORNING_TYPE);
            setDisplayShiftDetails(false, DISPLAY_SHIFT_AFTERNOON_TYPE);
            setDisplayShiftDetails(false, DISPLAY_SHIFT_NIGHT_TYPE);
        }


    }

    private void setDisplayShiftDetails(boolean on_off, int displayShiftType) {
        switch (displayShiftType) {
            case DISPLAY_SHIFT_MORNING_TYPE:
                if (on_off) {
                    layoutMorning.setVisibility(View.VISIBLE);
                } else {
                    layoutMorning.setVisibility(View.GONE);
                }
                break;
            case DISPLAY_SHIFT_AFTERNOON_TYPE:
                if (on_off) {
                    layoutAfternoon.setVisibility(View.VISIBLE);
                } else {
                    layoutAfternoon.setVisibility(View.GONE);
                }
                break;
            case DISPLAY_SHIFT_NIGHT_TYPE:
                if (on_off) {
                    layoutNight.setVisibility(View.VISIBLE);
                } else {
                    layoutNight.setVisibility(View.GONE);
                }
                break;
        }
    }

    private void setDefaultCheckBoxAllShiftType() {
        setEmptyView();
        chkShiftMor.setChecked(false);
        chkShiftAft.setChecked(false);
        chkShiftNig.setChecked(false);
        if (radFreeDay.isChecked()) {
            setWorkingDayType(WORK_FREE_DAY);
        } else {
            setWorkingDayType(WORK_NORMAL_DAY);
        }
    }

    private boolean checkMorningShift() {
        boolean result = false;
        if (chkShiftMor.isChecked()) {
            if (!radRealJobMor.isChecked() && !radOtJobMor.isChecked()) {
                setAlertRadioButtonView(
                        "คุณยังไม่ได้เลือกประเภทงาน กะเช้า",
                        radRealJobMor);
                result = false;
                return result;
            }

            if (edtFromTimeMor.getText().toString().trim().equals("")) {
                setAlertEditView(
                        "คุณยังไม่ได้ระบุ เวลาเริ่มต้นเข้างาน",
                        edtFromTimeMor);
                result = false;
                return result;
            }else if (edtToTimeMor.getText().toString().trim().equals("")) {
                setAlertEditView(
                        "คุณยังไม่ได้ระบุ เวลาสิ้นสุดเข้างาน",
                        edtToTimeMor);
                result = false;
                return result;
            }else if (edtLocationMor.getText().toString().trim().equals("")) {
                setAlertEditView(
                        "คุณยังไม่ได้ระบุสถานที่ ที่เข้างาน",
                        edtLocationMor);
                result = false;
                return result;
            }else if (edtRemarkMor.getText().toString().trim().equals("")) {
                setAlertEditView(
                        "คุณยังไม่ได้ระบุหน้าที่(ตำแหน่ง)ในทีม",
                        edtRemarkMor);
                result = false;
                return result;
            }else if (edtSectionMor.getText().toString().trim().equals("")) {
                setAlertEditView(
                        "คุณยังไม่ได้ระบุแผนกที่เข้างาน",
                        edtSectionMor);
                result = false;
                return result;
            }

            if (!radMenSectionMor.isChecked() && !radWomenbMor.isChecked()) {
                setAlertRadioButtonView(
                        "กรุณาระบุฝั่งผู้ป่วย(หญิง-ชาย) ที่คุณรับผิดชอบ",
                        radMenSectionMor);
                result = false;
                return result;
            }
        }

        result = true;
        return result;
    }

    private boolean checkAfternoonShift() {
        boolean result = false;
        if (chkShiftAft.isChecked()) {
            if (!radRealJobAft.isChecked() && !radOtJobAft.isChecked()) {
                setAlertRadioButtonView(
                        "คุณยังไม่ได้เลือกประเภทงาน กะบ่าย",
                        radRealJobAft);
                result = false;
                return result;
            }

            if (edtFromTimeAft.getText().toString().trim().equals("")) {
                setAlertEditView(
                        "คุณยังไม่ได้ระบุ เวลาเริ่มต้นเข้างาน",
                        edtFromTimeAft);
                result = false;
                return result;
            }else if (edtToTimeAft.getText().toString().trim().equals("")) {
                setAlertEditView(
                        "คุณยังไม่ได้ระบุ เวลาสิ้นสุดเข้างาน",
                        edtToTimeAft);
                result = false;
                return result;
            }else if (edtLocationAft.getText().toString().trim().equals("")) {
                setAlertEditView(
                        "คุณยังไม่ได้ระบุสถานที่ ที่เข้างาน",
                        edtLocationAft);
                result = false;
                return result;
            }else if (edtRemarkAft.getText().toString().trim().equals("")) {
                setAlertEditView(
                        "คุณยังไม่ได้ระบุหน้าที่(ตำแหน่ง)ในทีม",
                        edtRemarkAft);
                result = false;
                return result;
            }else if (edtSectionAft.getText().toString().trim().equals("")) {
                setAlertEditView(
                        "คุณยังไม่ได้ระบุแผนกที่เข้างาน",
                        edtSectionAft);
                result = false;
                return result;
            }

            if (!radMenSectionAft.isChecked() && !radWomenbAft.isChecked()) {
                setAlertRadioButtonView(
                        "กรุณาระบุฝั่งผู้ป่วย(หญิง-ชาย) ที่คุณรับผิดชอบ",
                        radMenSectionAft);
                result = false;
                return result;
            }
        }

        result = true;
        return result;
    }

    private boolean checkNightShift() {
        boolean result = false;
        if (chkShiftNig.isChecked()) {
            if (!radRealJobNig.isChecked() && !radOtJobNig.isChecked()) {
                setAlertRadioButtonView(
                        "คุณยังไม่ได้เลือกประเภทงาน กะเย็น",
                        radRealJobNig);
                result = false;
                return result;
            }

            if (edtFromTimeNig.getText().toString().trim().equals("")) {
                setAlertEditView(
                        "คุณยังไม่ได้ระบุ เวลาเริ่มต้นเข้างาน",
                        edtFromTimeNig);
                result = false;
                return result;
            }else if (edtToTimeNig.getText().toString().trim().equals("")) {
                setAlertEditView(
                        "คุณยังไม่ได้ระบุ เวลาสิ้นสุดเข้างาน",
                        edtToTimeNig);
                result = false;
                return result;
            }else if (edtLocationNig.getText().toString().trim().equals("")) {
                setAlertEditView(
                        "คุณยังไม่ได้ระบุสถานที่ ที่เข้างาน",
                        edtLocationNig);
                result = false;
                return result;
            }else if (edtRemarkNig.getText().toString().trim().equals("")) {
                setAlertEditView(
                        "คุณยังไม่ได้ระบุหน้าที่(ตำแหน่ง)ในทีม",
                        edtRemarkNig);
                result = false;
                return result;
            }else if (edtSectionNig.getText().toString().trim().equals("")) {
                setAlertEditView(
                        "คุณยังไม่ได้ระบุแผนกที่เข้างาน",
                        edtSectionNig);
                result = false;
                return result;
            }

            if (!radMenSectionNig.isChecked() && !radWomenbNig.isChecked()) {
                setAlertRadioButtonView(
                        "กรุณาระบุฝั่งผู้ป่วย(หญิง-ชาย) ที่คุณรับผิดชอบ",
                        radMenSectionNig);
                result = false;
                return result;
            }
        }

        result = true;
        return result;
    }

    private void InsertItem() {
        if (edtDate.getText().toString().trim().equals("")) {
            setAlertEditView(
                    "คุณยังไม่ได้ระบุ วัน-เดือน-ปี ในการเข้างาน",
                    edtDate);
        } else {
            if (radFreeDay.isChecked()) {
                confirmAddNewItem();
            } else {
                if (checkMorningShift()) {
                    if (checkAfternoonShift()) {
                        if (checkNightShift()) {
                            confirmAddNewItem();
                        }
                    }
                }
            }
        }
    }

    private void confirmAddNewItem() {
        String userWorking = "";
        String date = "";
        String fromTime = "";
        String toTime = "";
        String shift = "";
        String jobType = "";
        String location = "";
        String remark = "";
        String section = "";
        String section_sex = "";

        userWorking = mUser;
        date = edtDate.getText().toString().trim();

        if (!radFreeDay.isChecked() && !radWorkingDay.isChecked()) {
            showAlertDialog(
                    "คำเตือน",
                    "กรุณาเลือกประเภทวัน(วันหยุด หรือ วันทำงาน)",
                    true
            );
            return;
        }else {
            if (radFreeDay.isChecked()) {
                fromTime = "00:00:00";
                toTime = "00:00:00";
                shift = "FREE";
                location = "FREE";
                remark = "FREE";
                jobType = "FREE";
                section = "FREE";
                section_sex = "FREE";

                addItem(
                        userWorking, date, fromTime, toTime,
                        shift, jobType, location, remark,
                        section, section_sex
                );

                return;
            }
        }



        //------ Insert Morning Shift
        if (chkShiftMor.isChecked()) {
            fromTime = edtFromTimeMor.getText().toString().trim();
            toTime = edtToTimeMor.getText().toString().trim();
            shift = "MORNING";
            location = edtLocationMor.getText().toString().trim();
            remark = edtRemarkMor.getText().toString().trim();
            section = edtSectionMor.getText().toString().trim();
            if (radMenSectionMor.isChecked()) {
                section_sex = "MEN";
            }else{
                section_sex = "WOMEN";
            }

            if (radRealJobMor.isChecked()) {
                jobType = "REAL";
            } else {
                jobType = "OT";
            }

            addItem(
                    userWorking, date, fromTime, toTime,
                    shift, jobType, location, remark,
                    section, section_sex
            );

        }

        //------ Insert Afternoon Shift
        if (chkShiftAft.isChecked()) {
            fromTime = edtFromTimeAft.getText().toString().trim();
            toTime = edtToTimeAft.getText().toString().trim();
            shift = "AFTERNOON";
            location = edtLocationAft.getText().toString().trim();
            remark = edtRemarkAft.getText().toString().trim();
            section = edtSectionAft.getText().toString().trim();
            if (radMenSectionAft.isChecked()) {
                section_sex = "MEN";
            }else{
                section_sex = "WOMEN";
            }

            if (radRealJobAft.isChecked()) {
                jobType = "REAL";
            } else {
                jobType = "OT";
            }

            addItem(
                    userWorking, date, fromTime, toTime,
                    shift, jobType, location, remark,
                    section, section_sex
            );
        }

        //------ Insert Night Shift
        if (chkShiftNig.isChecked()) {
            fromTime = edtFromTimeNig.getText().toString().trim();
            toTime = edtToTimeNig.getText().toString().trim();
            shift = "NIGHT";
            location = edtLocationNig.getText().toString().trim();
            remark = edtRemarkNig.getText().toString().trim();
            section = edtSectionNig.getText().toString().trim();
            if (radMenSectionNig.isChecked()) {
                section_sex = "MEN";
            }else{
                section_sex = "WOMEN";
            }

            if (radRealJobNig.isChecked()) {
                jobType = "REAL";
            } else {
                jobType = "OT";
            }

            addItem(
                    userWorking, date, fromTime, toTime,
                    shift, jobType, location, remark,
                    section, section_sex
            );
        }


        if (!chkShiftMor.isChecked() && !chkShiftAft.isChecked()
                && !chkShiftNig.isChecked()) {
            showAlertDialog(
                    "คำเตือน",
                    "คุณยังไม่ได้เลือกช่วงเวลา(กะ) ในการเข้างาน",
                    true
            );
        }

    }

    private void addItem(String userWorking, String date, String fromTime,
                         String toTime, String shift, String jobType,
                         String location, String remark,
                         String section, String section_sex) {

        Call<AddNursingItemDao> call =
                HttpNursingRequest.getInstance().getApi().feedToServer(
                        userWorking, date, fromTime, toTime, shift,
                        jobType, location, remark, section, section_sex
                );
        call.enqueue(new Callback<AddNursingItemDao>() {
            @Override
            public void onResponse(Call<AddNursingItemDao> call,
                                   Response<AddNursingItemDao> response) {
                if (response.isSuccessful()) {
                    if (response.body().getErrMsg().equals(RESPONSE_DUPLICATE_WORK_DAY)) {
                        showAlertDialog(
                                "บันทึกข้อมูลซ้ำ!",
                                "มีข้อมูลของช่วงเวลาเข้างานนี้อยู่แล้ว ถ้าต้องการแก้ไขให้ไปที่หน้าเปลี่ยนแปลงแก้ไข",
                                true
                        );
                    }else if (response.body().getErrMsg().equals(RESPONSE_DUPLICATE_FREE_DAY)) {
                        showAlertDialog(
                                "บันทึกข้อมูลซ้ำ!",
                                "มีข้อมูลของวันนี้อยู่แล้ว(วันหยุด) ถ้าต้องการแก้ไขให้ไปที่หน้าเปลี่ยนแปลงแก้ไข",
                                true
                        );
                    }else {
                        toastMessage("บันทึกข้อมูลใหม่สำเร็จ",
                                TOAST_TYPE_SUCCESS);
                        setEmptyView();
                    }
                } else {
                    /*toastMessage("Error " + response.errorBody(),
                            TOAST_TYPE_ERROR);*/
                    showAlertDialog(
                            "เกิดข้อผิดพลาด!",
                            "การบันทึกข้อมูลล้มเหลว โปรดลองอีกครั้ง",
                            false
                    );
                }
            }

            @Override
            public void onFailure(Call<AddNursingItemDao> call,
                                  Throwable t) {
                /*toastMessage("Throw " + t.getMessage(),
                        TOAST_TYPE_ERROR);*/
                showAlertDialog(
                        "เกิดข้อผิดพลาด!",
                        "การบันทึกข้อมูลล้มเหลว " + t.getMessage(),
                        false
                );
            }
        });

    }


    private void toastMessage(String text, int alertType) {
        switch (alertType) {
            case 1:
                FancyToast.makeText(
                        this,
                        text,
                        FancyToast.LENGTH_LONG,
                        FancyToast.SUCCESS,
                        true
                ).show();
                break;
            case 2:
                FancyToast.makeText(
                        this,
                        text,
                        FancyToast.LENGTH_LONG,
                        FancyToast.WARNING,
                        true
                ).show();
                break;
            case 3:
                FancyToast.makeText(
                        this,
                        text,
                        FancyToast.LENGTH_LONG,
                        FancyToast.ERROR,
                        true
                ).show();
                break;
        }
    }

    private void getCurrentDate(final View view) {
        setDefaultCheckBoxAllShiftType();

        MyDatePickerDialog dialog = MyDatePickerDialog.newInstance();
        dialog.show(getSupportFragmentManager(), null);
        dialog.setOnFinishDialogListener(
                new MyDatePickerDialog.onFinishDialogListener() {
                    @Override
                    public void onFinishDialog(int[] strDate) {
                        String date;
                        date = strDate[2] + "-";
                        date += ((strDate[1] < 10) ? "0" : "") + strDate[1] + "-";
                        date += ((strDate[0] < 10) ? "0" : "") + strDate[0];
                        switch (view.getId()) {
                            case R.id.imageButton_date:
                                edtDate.setText(date);
                                break;
                        }
                    }
                });
    }

    private void getCurrentTime(final View view) {
        MyTimePickerDialog dialog = MyTimePickerDialog.newInstance();
        dialog.show(getSupportFragmentManager(), null);

        dialog.setOnFinishDialogListener(
                new MyTimePickerDialog.onFinishDialogListener() {
                    @Override
                    public void onFinishDialog(int[] intArrTime) {
                        String arrTime = "";
                        arrTime += ((intArrTime[0]<10) ? "0" : "") + intArrTime[0] + ":";
                        arrTime += ((intArrTime[1]<10) ? "0" : "") + intArrTime[1];

                        switch (view.getId()) {
                            case R.id.imageButton_fromTimeMor:
                                edtFromTimeMor.setText(arrTime + ":00");
                                break;
                            case R.id.imageButton_toTimeMor:
                                edtToTimeMor.setText(arrTime + ":00");
                                break;
                            case R.id.imageButton_fromTimeAft:
                                edtFromTimeAft.setText(arrTime + ":00");
                                break;
                            case R.id.imageButton_toTimeAft:
                                edtToTimeAft.setText(arrTime + ":00");
                                break;
                            case R.id.imageButton_fromTimeNig:
                                edtFromTimeNig.setText(arrTime + ":00");
                                break;
                            case R.id.imageButton_toTimeNig:
                                edtToTimeNig.setText(arrTime + ":00");
                                break;
                        }
                    }
                });
    }

    private void getSimpleRemark(final View view) {
        SingleChoiceDialog dialog = SingleChoiceDialog.newInstance(
                "กรุณาเลือกประเภทตำแหน่งในทีม", arrRemark, "ตกลง"
        );
        dialog.show(getSupportFragmentManager(), null);
        dialog.setOnFinishDialogListener(
                new SingleChoiceDialog.onFinishDialogListener() {
                    @Override
                    public void onFinishDialog(int selectIndex) {
                        if (selectIndex != -1) {
                            switch (view.getId()) {
                                case R.id.imageButton_RemarkMor:
                                    edtRemarkMor.setText(arrRemark[selectIndex]);
                                    break;
                                case R.id.imageButton_RemarkAft:
                                    edtRemarkAft.setText(arrRemark[selectIndex]);
                                    break;
                                case R.id.imageButton_RemarkNig:
                                    edtRemarkNig.setText(arrRemark[selectIndex]);
                                    break;
                            }
                        }
                    }
                });
    }

    private void showAlertDialog(String title,String msg, boolean alertType) {
        MyAlertDialog dialog = MyAlertDialog.newInstance(
                title, msg, alertType
        );

        dialog.show(getSupportFragmentManager(), null);
    }

    private void setEmptyView() {
        edtFromTimeMor.setText("");
        edtToTimeMor.setText("");
        edtLocationMor.setText("");
        edtRemarkMor.setText("");
        edtSectionMor.setText("");
        radOtJobMor.setChecked(false);
        radRealJobMor.setChecked(false);
        radMenSectionMor.setChecked(false);
        radWomenbMor.setChecked(false);

        edtFromTimeAft.setText("");
        edtToTimeAft.setText("");
        edtLocationAft.setText("");
        edtRemarkAft.setText("");
        edtSectionAft.setText("");
        radOtJobAft.setChecked(false);
        radRealJobAft.setChecked(false);
        radMenSectionAft.setChecked(false);
        radWomenbAft.setChecked(false);

        edtFromTimeNig.setText("");
        edtToTimeNig.setText("");
        edtLocationNig.setText("");
        edtRemarkNig.setText("");
        edtSectionNig.setText("");
        radOtJobNig.setChecked(false);
        radRealJobNig.setChecked(false);
        radMenSectionNig.setChecked(false);
        radWomenbNig.setChecked(false);
    }

    private void setAlertRadioButtonView(String text, RadioButton rad) {
        new SimpleTooltip.Builder(this)
                .anchorView(rad)
                .text(text)
                .gravity(Gravity.BOTTOM)
                .animated(true)
                .transparentOverlay(false)
                .build()
                .show();
    }

    private void setAlertEditView(String text, EditText edt) {
        new SimpleTooltip.Builder(this)
                .anchorView(edt)
                .text(text)
                .gravity(Gravity.BOTTOM)
                .animated(true)
                .transparentOverlay(false)
                .build()
                .show();
    }



    /*************************************
     *  Listener Zone
     */
    View.OnClickListener btnAddItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            InsertItem();
        }
    };

    View.OnClickListener btnClearItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setEmptyView();
        }
    };

    View.OnClickListener btnDateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getCurrentDate(v);
        }
    };

    View.OnClickListener radFreeDayClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setWorkingDayType(WORK_FREE_DAY);
        }
    };

    View.OnClickListener radWorkingDayClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setWorkingDayType(WORK_NORMAL_DAY);
        }
    };


    //------------ Morning
    CompoundButton.OnCheckedChangeListener chkMorShiftCheckChanged = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            if (isChecked) {
                setDisplayShiftDetails(true, DISPLAY_SHIFT_MORNING_TYPE);
            } else {
                setDisplayShiftDetails(false, DISPLAY_SHIFT_MORNING_TYPE);
            }
        }
    };

    View.OnClickListener btnFromTimeMorClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getCurrentTime(v);
        }
    };

    View.OnClickListener btnToTimeMorClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getCurrentTime(v);
        }
    };

    View.OnClickListener btnLocationMorClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showLocationDialog(mLocationItem, v);
        }
    };

    View.OnClickListener btnRemarkClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getSimpleRemark(v);
        }
    };

    View.OnClickListener btnSectionMorClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showSectionDialog(mSectionItem, v);
        }
    };


    //------------ AfterNoon
    CompoundButton.OnCheckedChangeListener chkAftShiftCheckChanged = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            if (isChecked) {
                setDisplayShiftDetails(true, DISPLAY_SHIFT_AFTERNOON_TYPE);
            } else {
                setDisplayShiftDetails(false, DISPLAY_SHIFT_AFTERNOON_TYPE);
            }
        }
    };

    View.OnClickListener btnFromTimeAftClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getCurrentTime(v);
        }
    };

    View.OnClickListener btnToTimeAftClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getCurrentTime(v);
        }
    };

    View.OnClickListener btnLocationAftClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showLocationDialog(mLocationItem, v);
        }
    };

    View.OnClickListener btnRemarkAftClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getSimpleRemark(v);
        }
    };

    View.OnClickListener btnSectionAftClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showSectionDialog(mSectionItem, v);
        }
    };


    //----- Night
    CompoundButton.OnCheckedChangeListener chkNigShiftCheckChanged = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            if (isChecked) {
                setDisplayShiftDetails(true, DISPLAY_SHIFT_NIGHT_TYPE);
            } else {
                setDisplayShiftDetails(false, DISPLAY_SHIFT_NIGHT_TYPE);
            }
        }
    };

    View.OnClickListener btnFromTimeNigClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getCurrentTime(v);
        }
    };

    View.OnClickListener btnToTimeNigClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getCurrentTime(v);
        }
    };

    View.OnClickListener btnLocationNigClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showLocationDialog(mLocationItem, v);
        }
    };

    View.OnClickListener btnRemarkNigClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getSimpleRemark(v);
        }
    };

    View.OnClickListener btnSectionNigClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showSectionDialog(mSectionItem, v);
        }
    };



}
