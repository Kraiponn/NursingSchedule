package com.ksntechnology.nursingschedule.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ksntechnology.nursingschedule.R;

public class ViewNursingSectionViewholder extends RecyclerView.ViewHolder {
    public TextView tvSectionItem;

    public ViewNursingSectionViewholder(View cv) {
        super(cv);
        tvSectionItem = cv.findViewById(R.id.textSection_topic);
    }
}
