package com.ksntechnology.nursingschedule.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ksntechnology.nursingschedule.R;
import com.ksntechnology.nursingschedule.dao.SectionCollectionDao;
import com.ksntechnology.nursingschedule.dao.WorkLocationCollectionDao;
import com.ksntechnology.nursingschedule.dialog.MyAlertDialog;
import com.ksntechnology.nursingschedule.dialog.MyDatePickerDialog;
import com.ksntechnology.nursingschedule.dialog.SingleChoiceDialog;
import com.ksntechnology.nursingschedule.manager.HttpNursingRequest;

import java.util.ArrayList;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class WorkMateMainFragment extends Fragment {
    private Button btnWorkMate;
    private Button btnClearItem;
    private EditText edtDate;
    private Button btnDate;
    private EditText edtLocation;
    private EditText edtSection;
    private Button btnLocation;
    private ImageButton btnSection;
    private RadioButton radMenSection;
    private RadioButton radWomenSection;
    private RadioGroup radioGroup;
    private RadioButton radMorning;
    private RadioButton radAfternoon;
    private RadioButton radNight;

    private static final String MORNING_SHIFT = "MORNING";
    private static final String AFTERNOON_SHIFT = "AFTERNOON";
    private static final String NIGHT_SHIFT = "NIGHT";
    private Disposable mDisposable;
    private ArrayList mArrLocation;
    private Observable<WorkLocationCollectionDao> mLocationObservalble;
    private Observable<SectionCollectionDao> mSectionObservalble;
    private String[] mLocationItem;
    private String[] mSectionItem;

    public interface onCallWorkMateDetailListener{
        void setOnCallWorkMateDetail(
                String location,
                String shift,
                String date,
                String section,
                String section_sex);
    }

    public static WorkMateMainFragment newInstance() {
        WorkMateMainFragment fragment = new WorkMateMainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_workmate_main_layout,
                container, false
        );

        initInstance(view, savedInstanceState);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setLocationItems();
        setWorkSection();
    }

    @Override
    public void onDestroyView() {
        if (mDisposable != null && mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        super.onDestroyView();
    }

    private void initInstance(View view, Bundle savedInstanceState) {
        edtDate = view.findViewById(R.id.editFind_Date);
        btnDate = view.findViewById(R.id.buttonFind_Date);
        edtLocation = view.findViewById(R.id.editFind_Location);
        btnLocation = view.findViewById(R.id.buttonFind_Location);
        radioGroup = view.findViewById(R.id.radioGroupFind_Shift);
        radMorning = view.findViewById(R.id.radioFind_Morning);
        radAfternoon = view.findViewById(R.id.radioFind_Afternoon);
        radNight = view.findViewById(R.id.radioFind_Night);
        btnWorkMate = view.findViewById(R.id.buttonFind_WorkMate);
        btnClearItem = view.findViewById(R.id.buttonFind_ClearItem);
        radMenSection = view.findViewById(R.id.radioFindWorkMate_sectionMan);
        radWomenSection = view.findViewById(R.id.radioFindWorkMate_sectionWoman);
        edtSection = view.findViewById(R.id.editFindWorkMate_section);
        btnSection = view.findViewById(R.id.imageButtonFindWorkMate_section);

        btnDate.setOnClickListener(btnDateClick);
        btnLocation.setOnClickListener(btnLocationClickListener);
        btnWorkMate.setOnClickListener(btnWorkMateClickListener);
        btnClearItem.setOnClickListener(btnClearClick);
        btnSection.setOnClickListener(btnSectionClicked);
    }

    private void setLocationItems() {
        mArrLocation = new ArrayList();
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
                        String str = "";
                        mLocationItem = new String[dao.getData().size()];
                        for (int i=0; i<dao.getData().size(); i++) {
                            str += dao.getData().get(i).getLocationName();
                            mLocationItem[i] = dao.getData().get(i).getLocationName();
                        }

                        //Log.d("Location Response", str);
                        //showLocationDialog(locationItem);
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
                        dialog.show(getFragmentManager(), null);
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
                        dialog.show(getFragmentManager(), null);
                    }
                }
        );
    }

    private void showLocationDialog(final String[] locationItem) {
        SingleChoiceDialog dialog = SingleChoiceDialog.newInstance(
                "กรุณาเลือกสถานที่ทำงาน",
                locationItem,
                "ตกลง"
        );
        dialog.show(getFragmentManager(), null);
        dialog.setOnFinishDialogListener(new SingleChoiceDialog.onFinishDialogListener() {
            @Override
            public void onFinishDialog(int selectIndex) {
                if (selectIndex != -1) {
                    /*Toast.makeText(getContext(),
                            "Select " + locationItem[selectIndex],
                            Toast.LENGTH_SHORT).show();*/
                    edtLocation.setText(locationItem[selectIndex]);
                }
            }
        });
    }

    private void getCurrentDate(View view) {
        MyDatePickerDialog dialog = MyDatePickerDialog.newInstance();
        dialog.show(getFragmentManager(), null);
        dialog.setOnFinishDialogListener(
                new MyDatePickerDialog.onFinishDialogListener() {
                    @Override
                    public void onFinishDialog(int[] strDate) {
                        String date;
                        date = strDate[2] + "-";
                        date += ((strDate[1] < 10) ? "0" : "") + strDate[1] + "-";
                        date += ((strDate[0] < 10) ? "0" : "") + strDate[0];
                        edtDate.setText(date);
                    }
                });
        //Log.d("GetDate", "Hello GetDate");
    }

    private void setAlertRadioButtonView(String text, RadioButton rad) {
        new SimpleTooltip.Builder(getContext())
                .anchorView(rad)
                .text(text)
                .gravity(Gravity.BOTTOM)
                .animated(true)
                .transparentOverlay(false)
                .build()
                .show();
    }

    private void setAlertEditView(String text, EditText edt) {
        new SimpleTooltip.Builder(getContext())
                .anchorView(edt)
                .text(text)
                .gravity(Gravity.BOTTOM)
                .animated(true)
                .transparentOverlay(false)
                .build()
                .show();
    }

    private void requestWorkMateDetail() {
        if (edtDate.getText().toString().equals("")) {
            setAlertEditView(
                    "กรุณาระบุวันในการค้นหา",
                    edtDate);
            return;
        } else if (edtLocation.getText().toString().equals("")) {
            setAlertEditView(
                    "กรุณาระบุสถานที่ในการค้นหา",
                    edtLocation);
            return;
        }else if (edtSection.getText().toString().trim().equals("")) {
            setAlertEditView(
                    "กรุณาระบุฝ่ายหรือแผนกที่คุณต้องการค้นหา",
                    edtSection);
            return;
        }else if (!radMenSection.isChecked() && !radWomenSection.isChecked()) {
            setAlertRadioButtonView(
                    "กรุณาเลือกฝั่งผู้ป่วยหญิงหรือชายของแผนกที่คุณต้องการค้นหา",
                    radMenSection);
            return;
        }else if (!radMorning.isChecked() && !radAfternoon.isChecked()
                && !radNight.isChecked()) {
            setAlertRadioButtonView(
                    "กรุณาเลือกช่วงเวลา(กะ) ในการค้นหา",
                    radMorning);
            return;
        }


        String shift = "";
        if (radMorning.isChecked()) {
            shift = MORNING_SHIFT;
        } else if (radMorning.isChecked()) {
            shift = AFTERNOON_SHIFT;
        } else {
            shift = NIGHT_SHIFT;
        }

        String section = edtSection.getText().toString().trim();
        String section_sex;
        if (radMenSection.isChecked()) {
            section_sex = "MEN";
        } else {
            section_sex = "WOMEN";
        }

        String location = edtLocation.getText().toString().trim();
        String date = edtDate.getText().toString().trim();
        /*String[] arrDate = edtDate.getText().toString().trim().split("-");
        int month = Integer.valueOf(arrDate[1]);
        int year = Integer.valueOf(arrDate[0]);*/

        //Log.d("RESULT", month + "-" + year + " Location " + location);
        onCallWorkMateDetailListener listener =
                (onCallWorkMateDetailListener) getActivity();
        listener.setOnCallWorkMateDetail(
                location,
                shift,
                date,
                section,
                section_sex
        );
    }

    private void showSectionDialog(final String[] sectionItem, final View view) {
        SingleChoiceDialog dialog = SingleChoiceDialog.newInstance(
                "กรุณาเลือกฝ่ายหรือแผนกในสถานที่ทำงาน",
                sectionItem,
                "ตกลง"
        );
        dialog.show(getFragmentManager(), null);
        dialog.setOnFinishDialogListener(new SingleChoiceDialog.onFinishDialogListener() {
            @Override
            public void onFinishDialog(int selectIndex) {
                if (selectIndex != -1) {
                    edtSection.setText(sectionItem[selectIndex]);
                }
            }
        });
    }


    /*********************************
     * Listener Zone
     */
    View.OnClickListener btnWorkMateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Log.d("ButtonClick", "Hello BTN");
            requestWorkMateDetail();
        }
    };

    View.OnClickListener btnLocationClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showLocationDialog(mLocationItem);
        }
    };

    View.OnClickListener btnDateClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Log.d("GetDate", "Hello GetDate");
            getCurrentDate(v);
        }
    };

    View.OnClickListener btnClearClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            edtDate.setText("");
            edtLocation.setText("");
            edtSection.setText("");
            radMorning.setChecked(false);
            radAfternoon.setChecked(false);
            radNight.setChecked(false);
            radMenSection.setChecked(false);
            radWomenSection.setChecked(false);
        }
    };

    View.OnClickListener btnSectionClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showSectionDialog(mSectionItem, v);
        }
    };

}
