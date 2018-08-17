package com.ksntechnology.nursingschedule.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.ksntechnology.nursingschedule.R;
import com.ksntechnology.nursingschedule.dao.NursingItemCollectionDao;
import com.ksntechnology.nursingschedule.dao.NursingItemDao;
import com.ksntechnology.nursingschedule.dao.WorkLocationCollectionDao;
import com.ksntechnology.nursingschedule.dao.WorkLocationItemDao;
import com.ksntechnology.nursingschedule.dao.WorkMateDetailItem;

import java.util.List;

public class WorkMateDetailAdapter
        extends RecyclerView.Adapter<WorkMateDetailAdapter.WorkMateViewHolder> {
    private NursingItemCollectionDao mDao;
    private Context mContext;

    public WorkMateDetailAdapter(Context mContext, NursingItemCollectionDao mDao) {
        this.mDao = mDao;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public WorkMateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(
                R.layout.child_item_workmate_detail,
                parent, false
        );

        return new WorkMateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkMateViewHolder holder, int position) {
        NursingItemDao item = mDao.getData().get(position);
        holder.tvUserWorking.setText(item.getUserWorking());
        holder.tvResponsible.setText(item.getRemark());

        if (item.getRemark().equals("InCharge")) {
            setImageForNursingResponsible(
                    R.drawable.bg_incharge,
                    holder.imgLog);
        } else if (item.getRemark().equals("Medical Nurse")) {
            setImageForNursingResponsible(
                    R.drawable.bg_mednurse,
                    holder.imgLog);
        }else if (item.getRemark().equals("Day")) {
            setImageForNursingResponsible(
                    R.drawable.ic_afternoon,
                    holder.imgLog);
        }else if (item.getRemark().equals("Night")) {
            setImageForNursingResponsible(
                    R.drawable.ic_night,
                    holder.imgLog);
        } else {
            setImageForNursingResponsible(
                    R.drawable.bg_other,
                    holder.imgLog);
        }
    }

    @Override
    public int getItemCount() {
        return mDao.getData().size();
    }

    private void setImageForNursingResponsible(int imgRes, ImageView imgLog) {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.loading);
        options =
                options.transforms(
                        new CenterCrop(),
                        new RoundedCorners(60));

        Glide.with(mContext)
                .load(imgRes)
                .apply(options)
                .into(imgLog);
    }

    /*******************************
     *  View Holder
     */
    public class WorkMateViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgLog;
        public TextView tvUserWorking;
        public TextView tvResponsible;

        public WorkMateViewHolder(View cv) {
            super(cv);
            imgLog = cv.findViewById(R.id.imageLogo);
            tvUserWorking = cv.findViewById(R.id.textUserWorking);
            tvResponsible = cv.findViewById(R.id.textResponsible);
        }
    }

}
