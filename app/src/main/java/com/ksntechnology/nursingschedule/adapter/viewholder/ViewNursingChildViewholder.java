package com.ksntechnology.nursingschedule.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ksntechnology.nursingschedule.R;

public class ViewNursingChildViewholder extends RecyclerView.ViewHolder {
    public ImageView imgLogoShiftType;
    public TextView tvChildLocation;
    public TextView tvChildShiftType;

    public ViewNursingChildViewholder(View cv) {
        super(cv);
        imgLogoShiftType = cv.findViewById(R.id.imageChild_shiftType);
        tvChildLocation = cv.findViewById(R.id.textChild_location);
        tvChildShiftType = cv.findViewById(R.id.textChild_shiftType);
    }

}
