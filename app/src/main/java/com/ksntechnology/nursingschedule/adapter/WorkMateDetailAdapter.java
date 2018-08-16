package com.ksntechnology.nursingschedule.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    }

    @Override
    public int getItemCount() {
        return mDao.getData().size();
    }

    /*******************************
     *  View Holder
     */
    public class WorkMateViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUserWorking;
        public TextView tvResponsible;

        public WorkMateViewHolder(View cv) {
            super(cv);
            tvUserWorking = cv.findViewById(R.id.textUserWorking);
            tvResponsible = cv.findViewById(R.id.textResponsible);
        }
    }

}
