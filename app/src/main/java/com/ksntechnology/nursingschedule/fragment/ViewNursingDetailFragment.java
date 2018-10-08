package com.ksntechnology.nursingschedule.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ksntechnology.nursingschedule.R;
import com.ksntechnology.nursingschedule.dao.NursingItemCollectionDao;
import com.ksntechnology.nursingschedule.manager.HttpNursingRequest;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ViewNursingDetailFragment extends Fragment {
    private ImageView imgShift;
    private TextView tvUserWorking;
    private TextView tvDate;
    private TextView tvFromTime;
    private TextView tvToTime;
    private TextView tvShiftType;
    private TextView tvJobType;
    private TextView tvLocation;
    private TextView tvRemark;
    private TextView tvSection;
    private TextView tvSectionSex;
    private int mId = 0;
    private static final String POST_MODE = "SELECT_DETAIL_MODE";
    private static final String REAL_JOB = "REAL";
    private static final String OT_JOB = "OT";
    private static final String MORNING_SHIFT = "MORNING";
    private static final String AFTERNOON_SHIFT = "AFTERNOON";
    private static final String NIGHT_SHIFT = "NIGHT";
    private final String[] mThaiMonth = {
            "มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน",
            "พฤษภาคม", "มิถุนายนต์", "กรกฎาคม", "สิงหาคม",
            "กันยายน", "ตุลาคม", "พฤษจิกายน",
            "ธันวาคม"
    };

    private Disposable mDisposable;
    private Observable<NursingItemCollectionDao> mNursingObservalble;

    public static ViewNursingDetailFragment newInstance(int id) {
        ViewNursingDetailFragment fragment = new ViewNursingDetailFragment();
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
                R.layout.fragment_view_nursing_detail_layout,
                container, false
        );

        initInstance(view, savedInstanceState);
        return view;
    }

    private void initInstance(View view, Bundle savedInstanceState) {
        imgShift = view.findViewById(R.id.imageShift);
        tvUserWorking = view.findViewById(R.id.textUserWorking);
        tvDate = view.findViewById(R.id.textDate);
        tvFromTime = view.findViewById(R.id.textFromTime);
        tvToTime = view.findViewById(R.id.textToTime);
        tvShiftType = view.findViewById(R.id.textShift);
        tvJobType = view.findViewById(R.id.textJobType);
        tvLocation = view.findViewById(R.id.textLocation);
        tvRemark = view.findViewById(R.id.textRemark);
        tvSection = view.findViewById(R.id.textSection);
        tvSectionSex = view.findViewById(R.id.textSectionSex);

        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {
        mNursingObservalble =
                HttpNursingRequest
                        .getInstance()
                        .getApi()
                        .postObservableNursingByCondition(
                                mId,
                                POST_MODE,
                                null,
                                null,
                                null,
                                null,
                                null)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());

        mDisposable = mNursingObservalble.subscribe(
                new Consumer<NursingItemCollectionDao>() {
                    @Override
                    public void accept(NursingItemCollectionDao dao) throws Exception {
                        showDetailOnView(dao);
                    }
                },
                new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getContext(),
                                "Throwable " + throwable.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void showDetailOnView(NursingItemCollectionDao dao) {
        tvUserWorking.setText("ชื่อพนักงาน : " + dao.getData().get(0).getUserWorking());
        String strDate = "";
        String[] arrDate = dao.getData().get(0).getDate().split("-");
        int date = Integer.valueOf(arrDate[2]);
        int month = Integer.valueOf(arrDate[1]);
        int year = Integer.valueOf(arrDate[0]);
        strDate = date + " ";
        strDate += mThaiMonth[month - 1] + " ";
        strDate += year + 543;

        tvDate.setText("วันทำงาน: " + strDate);
        tvFromTime.setText("เข้างาน: " + dao.getData().get(0).getFromTime());
        tvToTime.setText("เลิกงาน: " + dao.getData().get(0).getToTime());
        tvLocation.setText("สถานที่ทำงาน: " + dao.getData().get(0).getLocation());


        if (dao.getData().get(0).getShift().equals(MORNING_SHIFT)) {
            tvShiftType.setText("เวรเช้า");
            imgShift.setImageResource(R.drawable.ic_morning);
        }else if (dao.getData().get(0).getShift().equals(AFTERNOON_SHIFT)) {
            tvShiftType.setText("เวรบ่าย");
            imgShift.setImageResource(R.drawable.ic_afternoon);
        }else if (dao.getData().get(0).getShift().equals(NIGHT_SHIFT)) {
            tvShiftType.setText("เวรดึก");
            imgShift.setImageResource(R.drawable.ic_night);
        }else {
            tvShiftType.setText("วันหยุด");
            imgShift.setImageResource(R.drawable.ic_free_day);
        }

        if (dao.getData().get(0).getJobType().equals(REAL_JOB)) {
            tvJobType.setText("ประเภทเวร: " + "เวรจริง");
        }else if (dao.getData().get(0).getJobType().equals(OT_JOB)) {
            tvJobType.setText("ประเภทเวร: " + "เวรพิเศษ(OT)");
        }else {
            tvJobType.setText("ประเภทเวร: " + "วันหยุด");
        }

        if (!dao.getData().get(0).getRemark().equals("")) {
            tvRemark.setText("หน้าที่ในทีม: " + dao.getData().get(0).getRemark());
        } else {
            tvRemark.setText("");
        }

        tvSection.setText("ฝ่าย(แผนก): " + dao.getData().get(0).getSection());
        //tvSectionSex.setText("ผู้ป่วยฝั่ง: " + dao.getData().get(0).getSection_sex());
        if (dao.getData().get(0).getSection_sex().equals("MEN")) {
            tvSectionSex.setText("ฝั่ง: ชาย");
        } else {
            tvSectionSex.setText("ฝั่ง: หญิง");
        }
    }

    @Override
    public void onDestroy() {
        if (mDisposable != null && mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        super.onDestroy();
    }

}
