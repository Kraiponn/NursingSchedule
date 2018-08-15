package com.ksntechnology.nursingschedule.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ksntechnology.nursingschedule.R;
import com.ksntechnology.nursingschedule.dao.WorkLocationCollectionDao;
import com.ksntechnology.nursingschedule.dao.WorkMateDetailItem;

import java.util.List;

public class WorkMateDetailAdapter
        extends RecyclerView.Adapter<WorkMateDetailAdapter.WorkMateViewHolder> {
    private List<WorkLocationCollectionDao> mDao;
    private Context mContext;

    public WorkMateDetailAdapter(List<WorkLocationCollectionDao> mDao, Context mContext) {
        this.mDao = mDao;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public WorkMateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(
                R.layout.workmate_item_detail_layout,
                parent, false
        );

        return new WorkMateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkMateViewHolder holder, int position) {
        //WorkMateDetailItem item = mDao.get(position);
    }

    @Override
    public int getItemCount() {
        return mDao.size();
    }

    /*******************************
     *  View Holder
     */
    public class WorkMateViewHolder extends RecyclerView.ViewHolder {
        public TextView tvWorkingUser;
        public TextView tvRemark;

        public WorkMateViewHolder(View cv) {
            super(cv);
            tvWorkingUser = cv.findViewById(R.id.textWorkMateDetail_UserWorking);
            tvRemark = cv.findViewById(R.id.textWorkMateDetail_Remark);
        }
    }

}
