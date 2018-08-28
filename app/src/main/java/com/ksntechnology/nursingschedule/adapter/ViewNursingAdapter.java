package com.ksntechnology.nursingschedule.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ksntechnology.nursingschedule.R;
import com.ksntechnology.nursingschedule.adapter.model.BaseViewNursingItem;
import com.ksntechnology.nursingschedule.adapter.model.ViewNursingChildItem;
import com.ksntechnology.nursingschedule.adapter.model.ViewNursingSectionItem;
import com.ksntechnology.nursingschedule.adapter.model.ViewNursingType;
import com.ksntechnology.nursingschedule.adapter.viewholder.ViewNursingChildViewholder;
import com.ksntechnology.nursingschedule.adapter.viewholder.ViewNursingSectionViewholder;

import java.util.ArrayList;
import java.util.List;


public class ViewNursingAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<BaseViewNursingItem> mItem;
    private boolean mIsFirstChild = true;

    public void setmItem(List<BaseViewNursingItem> mItem) {
        this.mItem = mItem;
    }

    public ViewNursingAdapter() {
        mItem = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                      int viewType) {
        if (viewType == ViewNursingType.TYPE_SECTION_ITEM) {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(
                            R.layout.section_item_layout,
                            parent, false);
            return new ViewNursingSectionViewholder(view);
        }else if (viewType == ViewNursingType.TYPE_CHILD_ITEM) {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(
                            R.layout.child_item_view_layout,
                            parent, false);
            return new ViewNursingChildViewholder(view);
        }
        
        //return null;
        throw new NullPointerException("View Type " + viewType + " doesn't match with any existing order detail type");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,
                                 int position) {
        BaseViewNursingItem item = mItem.get(position);
        if (holder instanceof ViewNursingSectionViewholder) {
            ViewNursingSectionItem sectionItem = (ViewNursingSectionItem) item;
            ViewNursingSectionViewholder vh = (ViewNursingSectionViewholder) holder;

            mIsFirstChild = true;
            setupSectionItem(vh, sectionItem, position);
        }else if (holder instanceof ViewNursingChildViewholder) {
            ViewNursingChildItem childItem = (ViewNursingChildItem) item;
            ViewNursingChildViewholder vh = (ViewNursingChildViewholder) holder;
            setupChildItem(vh, childItem, position);
        }
    }

    private void setupChildItem(ViewNursingChildViewholder vh, ViewNursingChildItem childItem, int position) {
        vh.imgLogoShiftType.setImageResource(childItem.getIconId());
        vh.tvChildLocation.setText(childItem.getTextLocation());
        vh.tvChildShiftType.setText(childItem.getTextShiftType());

        if (childItem.getIconId() == R.drawable.ic_morning) {
            vh.tvChildShiftType.setTextColor(Color.parseColor("#2962FF"));
        } else if (childItem.getIconId() == R.drawable.ic_afternoon) {
            vh.tvChildShiftType.setTextColor(Color.parseColor("#DD2C00"));
        } else {
            vh.tvChildShiftType.setTextColor(Color.parseColor("#000000"));
        }

        boolean isLastOfSection =
                position < mItem.size() - 1 && getItemViewType(position + 1) == ViewNursingType.TYPE_SECTION_ITEM;
        boolean isLastOfAll = position == mItem.size() - 1;
        boolean isLastChild = isLastOfSection || isLastOfAll;

        if (mIsFirstChild && isLastChild) {
            vh.tvChildLocation.getRootView()
                    .setBackgroundResource(R.drawable.one_item_state);
            mIsFirstChild = false;
        } else if (mIsFirstChild || position == 0) {
            vh.tvChildLocation.getRootView()
                    .setBackgroundResource(R.drawable.top_item_state);
            mIsFirstChild = false;
        } else if (isLastChild) {
            vh.tvChildLocation.getRootView()
                    .setBackgroundResource(R.drawable.bottom_item_state);
        } else {
            vh.tvChildLocation.getRootView()
                    .setBackgroundResource(R.drawable.middle_item_state);
        }
    }

    private void setupSectionItem(ViewNursingSectionViewholder vh,
                                  ViewNursingSectionItem sectionItem,
                                  int position) {
        vh.tvSectionItem.setText(sectionItem.getSectionDateMonth());
    }

    @Override
    public int getItemViewType(int position) {
        return mItem.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }
    
}
