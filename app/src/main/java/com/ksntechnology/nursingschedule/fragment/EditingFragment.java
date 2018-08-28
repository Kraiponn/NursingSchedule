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
import com.ksntechnology.nursingschedule.dao.NursingItemCollectionDao;
import com.ksntechnology.nursingschedule.dao.ResultDeleteEditDao;
import com.ksntechnology.nursingschedule.dao.WorkLocationCollectionDao;
import com.ksntechnology.nursingschedule.dialog.MyAlertDialog;
import com.ksntechnology.nursingschedule.dialog.MyTimePickerDialog;
import com.ksntechnology.nursingschedule.dialog.SingleChoiceDialog;
import com.ksntechnology.nursingschedule.manager.Contextor;
import com.ksntechnology.nursingschedule.manager.HttpNursingRequest;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class EditingFragment extends Fragment {
    private RadioGroup radioGroupJobType;
    private RadioButton radRealJob;
    private RadioButton radOtJob;

    private ImageButton btnFromTime;
    private ImageButton btnToTime;
    private ImageButton btnLocation;
    private ImageButton btnRemark;
    private EditText edtFromTime;
    private EditText edtToTime;
    private EditText edtLocation;
    private EditText edtRemark;
    private Button btnUpdateItem;
    private Button btnBack;

    private int mId = 0;
    private ArrayList mArrLocation;
    private String[] mLocationItem;
    private static final String[] arrRemark = {
            "Medical Nurse", "InCharge",
            "Day", "Night",
            "Other"
    };

    private Disposable mDisposable;
    private Observable<NursingItemCollectionDao> callData;
    private Observable<WorkLocationCollectionDao> mLocationObservalble;

    public interface setOnEditItemClickListener{
        void onEditItemClicked();
    }

    public interface setOnReloadUpdateDataListener{
        void onReloadUpdateData();
    }

    public static EditingFragment newInstance(int id) {
        EditingFragment fragment = new EditingFragment();
        Bundle args = new Bundle();
        args.putInt("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mId = getArguments().getInt("id");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_edit_item,
                container, false
        );

        initInstance(view, savedInstanceState);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initDefaultLocation();
    }

    private void initInstance(View view, Bundle savedInstanceState) {
        btnUpdateItem = view.findViewById(R.id.button_UpdateItem);
        btnBack = view.findViewById(R.id.button_BackItem);
        radioGroupJobType = view.findViewById(R.id.radioGroup_jobType);
        radRealJob = view.findViewById(R.id.radio_realJob);
        radOtJob = view.findViewById(R.id.radio_otJob);

        edtFromTime = view.findViewById(R.id.edit_fromTime);
        edtToTime = view.findViewById(R.id.edit_toTime);
        edtLocation = view.findViewById(R.id.edit_location);
        edtRemark = view.findViewById(R.id.edit_remark);
        btnFromTime = view.findViewById(R.id.imageButton_fromTime);
        btnToTime = view.findViewById(R.id.imageButton_toTime);
        btnLocation = view.findViewById(R.id.imageButton_location);
        btnRemark = view.findViewById(R.id.imageButton_Remark);

        btnLocation.setOnClickListener(btnLocationClicked);
        btnFromTime.setOnClickListener(btnFromTimeClicked);
        btnToTime.setOnClickListener(btnToTimeClicked);
        btnRemark.setOnClickListener(btnRemarkClicked);
        btnUpdateItem.setOnClickListener(btnUpdateItemClicked);
        btnBack.setOnClickListener(btnBackClicked);

        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        String selectMode = "SELECT_DETAIL_MODE";
        callData =
                HttpNursingRequest
                        .getInstance()
                        .getApi()
                        .postObservableNursingByCondition(
                                mId,
                                selectMode,
                                null, null, null
                        )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
        mDisposable = callData.subscribe(
                new Consumer<NursingItemCollectionDao>() {
                    @Override
                    public void accept(NursingItemCollectionDao dao) throws Exception {
                        setDataToView(dao);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        FancyToast.makeText(
                                Contextor.getInstance().getContext(),
                                "Throw " + throwable.getMessage(),
                                FancyToast.LENGTH_LONG,
                                FancyToast.ERROR,
                                true
                        ).show();
                    }
                }
        );
    }

    private void initDefaultLocation() {
        mArrLocation = new ArrayList();
        mLocationObservalble =
                HttpNursingRequest
                        .getInstance()
                        .getApi()
                        .getWorkLocation()
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
                    edtLocation.setText(locationItem[selectIndex]);
                }
            }
        });
    }

    private void setDataToView(NursingItemCollectionDao dao) {
        edtFromTime.setText(dao.getData().get(0).getFromTime());
        edtToTime.setText(dao.getData().get(0).getToTime());
        edtLocation.setText(dao.getData().get(0).getLocation());
        edtRemark.setText(dao.getData().get(0).getRemark());

        String jobType = dao.getData().get(0).getJobType();
        if (jobType.equals("REAL")) {
            radRealJob.setChecked(true);
        } else {
            radOtJob.setChecked(true);
        }
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

    private void getCurrentTime(final View view) {
        MyTimePickerDialog dialog = MyTimePickerDialog.newInstance();
        dialog.show(getFragmentManager(), null);

        dialog.setOnFinishDialogListener(
                new MyTimePickerDialog.onFinishDialogListener() {
                    @Override
                    public void onFinishDialog(int[] intArrTime) {
                        String arrTime = "";
                        arrTime += ((intArrTime[0]<10) ? "0" : "") + intArrTime[0] + ":";
                        arrTime += ((intArrTime[1]<10) ? "0" : "") + intArrTime[1];

                        switch (view.getId()) {
                            case R.id.imageButton_fromTime:
                                edtFromTime.setText(arrTime + ":00");
                                break;
                            case R.id.imageButton_toTime:
                                edtToTime.setText(arrTime + ":00");
                                break;
                        }
                    }
                });
    }

    private void getDefaultRemark(final View view) {
        SingleChoiceDialog dialog = SingleChoiceDialog.newInstance(
                "กรุณาเลือกประเภทตำแหน่งในทีม", arrRemark, "ตกลง"
        );
        dialog.show(getFragmentManager(), null);
        dialog.setOnFinishDialogListener(
                new SingleChoiceDialog.onFinishDialogListener() {
                    @Override
                    public void onFinishDialog(int selectIndex) {
                        if (selectIndex != -1) {
                            edtRemark.setText(arrRemark[selectIndex]);
                        }
                    }
                });
    }

    private void checkReadyView() {
        if (edtFromTime.getText().toString().equals("")) {
            setAlertEditView(
                    "กรุณาระบุเวลาเข้างาน",
                    edtFromTime
            );
            return;
        }else if (edtToTime.getText().toString().equals("")) {
            setAlertEditView(
                    "กรุณาระบุเวลาออกงาน",
                    edtToTime
            );
            return;
        }else if (edtLocation.getText().toString().equals("")) {
            setAlertEditView(
                    "กรุณาระบุสถานที่เข้างาน",
                    edtLocation
            );
            return;
        }else if (edtRemark.getText().toString().equals("")) {
            setAlertEditView(
                    "คุณยังไม่ได้ระบุตำแหน่งงาน ในทีม",
                    edtRemark
            );
            return;
        }


        if (!radRealJob.isChecked() && !radOtJob.isChecked()) {
            setAlertRadioButtonView(
                    "คุณยังไม่ได้เลือกประเภทเวร",
                    radRealJob
            );
            return;
        }

        updateItem();

    }

    private void updateItem() {
        String fromTime = edtFromTime.getText().toString().trim();
        String toTime = edtToTime.getText().toString().trim();
        String location = edtLocation.getText().toString().trim();
        String remark = edtRemark.getText().toString().trim();
        String jobType = "";

        if (radRealJob.isChecked()) {
            jobType = "REAL";
        } else {
            jobType = "OT";
        }

        Observable<ResultDeleteEditDao> mUpdate =
                HttpNursingRequest.getInstance().getApi()
                        .postDeleteOrEdit(
                                "UPDATE",
                                mId,
                                "",
                                null,
                                fromTime,
                                toTime,
                                null,
                                jobType,
                                location,
                                remark
                        )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());

        mDisposable = mUpdate.subscribe(
                new Consumer<ResultDeleteEditDao>() {
                    @Override
                    public void accept(ResultDeleteEditDao dao) throws Exception {
                        if (dao.getErrMsg().equals("SUCCESS")) {
                            showToastMsg(
                                    "แก้ไขข้อมูลสำเร็จ",
                                    FancyToast.SUCCESS
                            );
                            setDefaultView();
                            backToReloadCurrentListData();
                        } else {
                            showToastMsg(
                                    dao.getErrMsg(),
                                    FancyToast.WARNING
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
                        /*FancyToast.makeText(
                                Contextor.getInstance().getContext(),
                                "Throw " + throwable.getMessage(),
                                FancyToast.LENGTH_LONG,
                                FancyToast.ERROR,
                                true
                        ).show();*/
                    }
                }
        );
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

    private void backToReloadCurrentListData() {
        setOnReloadUpdateDataListener listener =
                (setOnReloadUpdateDataListener) getActivity();
        listener.onReloadUpdateData();
    }

    private void setDefaultView() {
        edtFromTime.setText("");
        edtToTime.setText("");
        edtLocation.setText("");
        edtRemark.setText("");
        radRealJob.setChecked(false);
        radOtJob.setChecked(false);
    }

    @Override
    public void onDestroy() {
        if (mDisposable != null && mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        super.onDestroy();
    }


    /*********************************
     *  Listener Zone
     */
    View.OnClickListener btnUpdateItemClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            checkReadyView();
        }
    };

    View.OnClickListener btnBackClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setOnEditItemClickListener listener =
                    (setOnEditItemClickListener) getActivity();
            listener.onEditItemClicked();
        }
    };

    View.OnClickListener btnLocationClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showLocationDialog(mLocationItem);
        }
    };

    View.OnClickListener btnFromTimeClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getCurrentTime(v);
        }
    };

    View.OnClickListener btnToTimeClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getCurrentTime(v);
        }
    };

    View.OnClickListener btnRemarkClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getDefaultRemark(v);
        }
    };

}
