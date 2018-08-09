package com.ksntechnology.nursingschedule.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ksntechnology.nursingschedule.R;
import com.ksntechnology.nursingschedule.dao.AddNursingItemDao;
import com.ksntechnology.nursingschedule.dao.SignInRegisterResultDao;
import com.ksntechnology.nursingschedule.dialog.MyAlertDialog;
import com.ksntechnology.nursingschedule.dialog.MyDatePickerDialog;
import com.ksntechnology.nursingschedule.dialog.MyTimePickerDialog;
import com.ksntechnology.nursingschedule.dialog.SingleChoiceDialog;
import com.ksntechnology.nursingschedule.manager.HttpNursingRequest;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddItemActivity extends AppCompatActivity {
    private EditText edtDate;
    private ImageButton btnDate;
    private LinearLayout layoutMorning;
    private LinearLayout layoutAfternoon;
    private LinearLayout layoutNight;

    private CheckBox chkShiftMor;
    private RadioGroup radioGroupMor;
    private RadioButton radRealJobMor;
    private RadioButton radOtJobMor;
    private ImageButton btnFromTimeMor;
    private ImageButton btnToTimeMor;
    private ImageButton btnLocationMor;
    private EditText edtFromTimeMor;
    private EditText edtToTimeMor;
    private EditText edtLocationMor;
    private EditText edtRemarkMor;

    private CheckBox chkShiftAft;
    private RadioGroup radioGroupAft;
    private RadioButton radRealJobAft;
    private RadioButton radOtJobAft;
    private ImageButton btnFromTimeAft;
    private ImageButton btnToTimeAft;
    private ImageButton btnLocationAft;
    private EditText edtFromTimeAft;
    private EditText edtToTimeAft;
    private EditText edtLocationAft;
    private EditText edtRemarkAft;

    private CheckBox chkShiftNig;
    private RadioGroup radioGroupNig;
    private RadioButton radRealJobNig;
    private RadioButton radOtJobNig;
    private ImageButton btnFromTimeNig;
    private ImageButton btnToTimeNig;
    private ImageButton btnLocationNig;
    private EditText edtFromTimeNig;
    private EditText edtToTimeNig;
    private EditText edtLocationNig;
    private EditText edtRemarkNig;

    private Button btnAddItem;
    private Button btnClearItem;
    private String mUser;
    private final String[] arrLocation = {
            "โรงพยาบาลราชวิถี", "โรงพยาบาลพระมงกุฏเกล้า", "โรงพยาบาลการุญเวช",
            "บริษัท NXP", "บริษัท Fabrinet"
    };

    private final int TOAST_TYPE_SUCCESS = 1;
    private final int TOAST_TYPE_WARNING = 2;
    private final int TOAST_TYPE_ERROR = 3;

    private final int DISPLAY_SHIFT_MORNING_TYPE = 1;
    private final int DISPLAY_SHIFT_AFTERNOON_TYPE = 2;
    private final int DISPLAY_SHIFT_NIGHT_TYPE = 3;
    private final String RESPONSE_RESULT = "Duplicate data";


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
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(
                R.anim.from_top, R.anim.to_bottom
        );
    }

    /*************************************
     *  Custom Method
     */
    private void initInstance() {
        btnDate = findViewById(R.id.imageButton_date);
        edtDate = findViewById(R.id.edit_date);
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

        btnDate.setOnClickListener(btnDateClickListener);
        btnAddItem.setOnClickListener(btnAddItemClickListener);
        btnClearItem.setOnClickListener(btnClearItemClickListener);

        chkShiftMor.setOnCheckedChangeListener(chkMorShiftCheckChanged);
        btnFromTimeMor.setOnClickListener(btnFromTimeMorClickListener);
        btnLocationMor.setOnClickListener(btnLocationMorClickListener);
        btnToTimeMor.setOnClickListener(btnToTimeMorClickListener);

        chkShiftAft.setOnCheckedChangeListener(chkAftShiftCheckChanged);
        btnFromTimeAft.setOnClickListener(btnFromTimeAftClickListener);
        btnLocationAft.setOnClickListener(btnLocationAftClickListener);
        btnToTimeAft.setOnClickListener(btnToTimeAftClickListener);

        chkShiftNig.setOnCheckedChangeListener(chkNigShiftCheckChanged);
        btnFromTimeNig.setOnClickListener(btnFromTimeNigClickListener);
        btnLocationNig.setOnClickListener(btnLocationNigClickListener);
        btnToTimeNig.setOnClickListener(btnToTimeNigClickListener);

        initAllView();
    }

    private void initAllView() {
        setDisableMorningView();
        setDisableAfteNoonView();
        setDisableNightView();

        layoutMorning.setVisibility(View.GONE);
        layoutAfternoon.setVisibility(View.GONE);
        layoutNight.setVisibility(View.GONE);
        Intent intent = getIntent();
        mUser = intent.getStringExtra("user_working");
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

    private void getLocation(final View view) {
        SingleChoiceDialog dialog = SingleChoiceDialog.newInstance(
                "เลือกสถานที่ทำงาน", arrLocation, "ตกลง"
        );
        dialog.show(getSupportFragmentManager(), null);
        dialog.setOnFinishDialogListener(
                new SingleChoiceDialog.onFinishDialogListener() {
                    @Override
                    public void onFinishDialog(int selectIndex) {
                        if (selectIndex != -1) {
                            switch (view.getId()) {
                                case R.id.imageButton_locationMor:
                                    edtLocationMor.setText(arrLocation[selectIndex]);
                                    break;
                                case R.id.imageButton_locationAft:
                                    edtLocationAft.setText(arrLocation[selectIndex]);
                                    break;
                                case R.id.imageButton_locationNig:
                                    edtLocationNig.setText(arrLocation[selectIndex]);
                                    break;
                            }
                        }
                    }
                });
    }

    private void setEnableMorningView() {
        radioGroupMor.setEnabled(true);
        radRealJobMor.setEnabled(true);
        radOtJobMor.setEnabled(true);
        edtFromTimeMor.setEnabled(true);
        edtToTimeMor.setEnabled(true);
        btnFromTimeMor.setEnabled(true);
        btnToTimeMor.setEnabled(true);
        edtLocationMor.setEnabled(true);
        btnLocationMor.setEnabled(true);
        edtRemarkMor.setEnabled(true);
    }

    private void setDisableMorningView() {
        radioGroupMor.setEnabled(false);
        radRealJobMor.setEnabled(false);
        radOtJobMor.setEnabled(false);
        edtFromTimeMor.setEnabled(false);
        edtToTimeMor.setEnabled(false);
        btnFromTimeMor.setEnabled(false);
        btnToTimeMor.setEnabled(false);
        edtLocationMor.setEnabled(false);
        btnLocationMor.setEnabled(false);
        edtRemarkMor.setEnabled(false);
    }

    private void setEnableAfterNoonView() {
        radioGroupAft.setEnabled(true);
        radRealJobAft.setEnabled(true);
        radOtJobAft.setEnabled(true);
        edtFromTimeAft.setEnabled(true);
        edtToTimeAft.setEnabled(true);
        btnFromTimeAft.setEnabled(true);
        btnToTimeAft.setEnabled(true);
        edtLocationAft.setEnabled(true);
        btnLocationAft.setEnabled(true);
        edtRemarkAft.setEnabled(true);
    }

    private void setDisableAfteNoonView() {
        radioGroupAft.setEnabled(false);
        radRealJobAft.setEnabled(false);
        radOtJobAft.setEnabled(false);
        edtFromTimeAft.setEnabled(false);
        edtToTimeAft.setEnabled(false);
        btnFromTimeAft.setEnabled(false);
        btnToTimeAft.setEnabled(false);
        edtLocationAft.setEnabled(false);
        btnLocationAft.setEnabled(false);
        edtRemarkAft.setEnabled(false);
    }

    private void setEnableNightView() {
        radioGroupNig.setEnabled(true);
        radRealJobNig.setEnabled(true);
        radOtJobNig.setEnabled(true);
        edtFromTimeNig.setEnabled(true);
        edtToTimeNig.setEnabled(true);
        btnFromTimeNig.setEnabled(true);
        btnToTimeNig.setEnabled(true);
        edtLocationNig.setEnabled(true);
        btnLocationNig.setEnabled(true);
        edtRemarkNig.setEnabled(true);
    }

    private void setDisableNightView() {
        radioGroupNig.setEnabled(false);
        radRealJobNig.setEnabled(false);
        radOtJobNig.setEnabled(false);
        edtFromTimeNig.setEnabled(false);
        edtToTimeNig.setEnabled(false);
        btnFromTimeNig.setEnabled(false);
        btnToTimeNig.setEnabled(false);
        edtLocationNig.setEnabled(false);
        btnLocationNig.setEnabled(false);
        edtRemarkNig.setEnabled(false);
    }

    private void InsertItem() {
        if (edtDate.getText().toString().trim().equals("")) {
            setAlertEditView(
                    "คุณยังไม่ได้เระบุ วัน-เดือน-ปี ในการเข้างาน",
                    edtDate);
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

    private void confirmAddNewItem() {
        String userWorking;
        String date;
        String fromTime;
        String toTime;
        String shift;
        String jobType;
        String location;
        String remark;

        userWorking = mUser;
        date = edtDate.getText().toString().trim();

        if (chkShiftMor.isChecked()) {
            fromTime = edtFromTimeMor.getText().toString().trim();
            toTime = edtToTimeMor.getText().toString().trim();
            shift = "MORNING";
            location = edtLocationMor.getText().toString().trim();
            remark = edtRemarkMor.getText().toString().trim();
            if (radRealJobMor.isChecked()) {
                jobType = "REAL";
            } else {
                jobType = "OT";
            }

            addItem(
                    userWorking, date, fromTime, toTime,
                    shift, jobType, location, remark
            );

        }

        if (chkShiftAft.isChecked()) {
            fromTime = edtFromTimeAft.getText().toString().trim();
            toTime = edtToTimeAft.getText().toString().trim();
            shift = "AFTERNOON";
            location = edtLocationAft.getText().toString().trim();
            remark = edtRemarkAft.getText().toString().trim();
            if (radRealJobAft.isChecked()) {
                jobType = "REAL";
            } else {
                jobType = "OT";
            }

            addItem(
                    userWorking, date, fromTime, toTime,
                    shift, jobType, location, remark
            );
        }

        if (chkShiftNig.isChecked()) {
            fromTime = edtFromTimeNig.getText().toString().trim();
            toTime = edtToTimeNig.getText().toString().trim();
            shift = "NIGHT";
            location = edtLocationNig.getText().toString().trim();
            remark = edtRemarkNig.getText().toString().trim();
            if (radRealJobNig.isChecked()) {
                jobType = "REAL";
            } else {
                jobType = "OT";
            }

            addItem(
                    userWorking, date, fromTime, toTime,
                    shift, jobType, location, remark
            );
        }


        if (!chkShiftMor.isChecked() && !chkShiftAft.isChecked()
                && !chkShiftNig.isChecked()) {
            showAlertDialog(
                    "คำเตือน",
                    "คุณยังไม่ได้เลือกช่วงเวลาในการเข้างาน",
                    true
            );
        }

    }


    private void addItem(String userWorking, String date, String fromTime,
                            String toTime, String shift, String jobType,
                            String location, String remark) {

        Call<AddNursingItemDao> call =
                HttpNursingRequest.getInstance().getApi().feedToServer(
                    userWorking, date, fromTime, toTime, shift,
                    jobType, location, remark
                );
        call.enqueue(new Callback<AddNursingItemDao>() {
            @Override
            public void onResponse(Call<AddNursingItemDao> call,
                                   Response<AddNursingItemDao> response) {
                if (response.isSuccessful()) {
                    if (response.body().getErrMsg().equals(RESPONSE_RESULT)) {
                        /*toastMessage("Duplicate data",
                                TOAST_TYPE_WARNING);*/
                        showAlertDialog(
                                "บันทึกข้อมูลซ้ำ!",
                                "มีข้อมูลของช่วงเวลาเข้างานนี้อยู่แล้ว ถ้าต้องการแก้ไขให้ไปที่หน้าเปลี่ยนแปลงแก้ไข",
                                true
                        );
                    } else {
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
        radOtJobMor.setChecked(false);
        radRealJobMor.setChecked(false);

        edtFromTimeAft.setText("");
        edtToTimeAft.setText("");
        edtLocationAft.setText("");
        edtRemarkAft.setText("");
        radOtJobAft.setChecked(false);
        radRealJobAft.setChecked(false);

        edtFromTimeNig.setText("");
        edtToTimeNig.setText("");
        edtLocationNig.setText("");
        edtRemarkNig.setText("");
        radOtJobNig.setChecked(false);
        radRealJobNig.setChecked(false);
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
            }
        }

        result = true;
        return result;
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


    //------------ Morning
    CompoundButton.OnCheckedChangeListener chkMorShiftCheckChanged = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            if (isChecked) {
                setEnableMorningView();
                setDisplayShiftDetails(true, DISPLAY_SHIFT_MORNING_TYPE);
            } else {
                setDisableMorningView();
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
            getLocation(v);
        }
    };

    //------------ AfterNoon
    CompoundButton.OnCheckedChangeListener chkAftShiftCheckChanged = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            if (isChecked) {
                setEnableAfterNoonView();
                setDisplayShiftDetails(true, DISPLAY_SHIFT_AFTERNOON_TYPE);
            } else {
                setDisableAfteNoonView();
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
            getLocation(v);
        }
    };


    //----- Night
    CompoundButton.OnCheckedChangeListener chkNigShiftCheckChanged = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            if (isChecked) {
                setEnableNightView();
                setDisplayShiftDetails(true, DISPLAY_SHIFT_NIGHT_TYPE);
            } else {
                setDisableNightView();
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
            getLocation(v);
        }
    };


}
