package com.ksntechnology.nursingschedule.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ksntechnology.nursingschedule.R;
import com.ksntechnology.nursingschedule.adapter.model.BaseViewNursingItem;
import com.ksntechnology.nursingschedule.adapter.model.ViewNursingChildItem;
import com.ksntechnology.nursingschedule.adapter.model.ViewNursingSectionItem;
import com.ksntechnology.nursingschedule.adapter.model.ViewNursingType;
import com.ksntechnology.nursingschedule.adapter.viewholder.ViewNursingChildViewholder;
import com.ksntechnology.nursingschedule.adapter.viewholder.ViewNursingNoDataViewholder;
import com.ksntechnology.nursingschedule.adapter.viewholder.ViewNursingSectionViewholder;
import com.ksntechnology.nursingschedule.dao.NoDataItem;
import com.ksntechnology.nursingschedule.manager.Contextor;

import java.util.ArrayList;
import java.util.List;


public class ViewNursingAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<BaseViewNursingItem> mItem;
    private boolean mIsFirstChild = true;
    private Context mContext;
    private OnItemClickListener mCallBack;
    private OnItemLongClickListener mLongCallBack;


    public void setmItem(List<BaseViewNursingItem> mItem) {
        this.mItem = mItem;
    }

    public ViewNursingAdapter(Context context) {
        mItem = new ArrayList<>();
        mContext = context;
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
        } else if (viewType == ViewNursingType.TYPE_NO_DATA_ITEM) {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(
                            R.layout.no_data_item,
                            parent, false);
            return new ViewNursingNoDataViewholder(view);
        }
        
        //return null;
        throw new NullPointerException("View Type " + viewType + " doesn't match with any existing order detail type");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
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
        }else if (holder instanceof ViewNursingNoDataViewholder) {
            NoDataItem noItem = (NoDataItem) item;
            ViewNursingNoDataViewholder vh = (ViewNursingNoDataViewholder) holder;
            setupNoDataItem(vh, noItem, position);
        }
    }

    private void setupNoDataItem(ViewNursingNoDataViewholder vh,
                                 NoDataItem childItem, int position) {
    }

    @Override
    public int getItemViewType(int position) {
        return mItem.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }


    private void setupChildItem(ViewNursingChildViewholder vh,
                                final ViewNursingChildItem childItem,
                                final int position) {
        vh.imgLogoShiftType.setImageResource(childItem.getIconId());
        vh.tvChildLocation.setText(childItem.getTextLocation());
        vh.tvChildShiftType.setText(childItem.getTextShiftType());

        /*if (childItem.getIconId() == R.drawable.ic_morning) {
            vh.tvChildShiftType.setTextColor(Color.parseColor("#2962FF"));
        } else if (childItem.getIconId() == R.drawable.ic_afternoon) {
            vh.tvChildShiftType.setTextColor(Color.parseColor("#DD2C00"));
        } else {
            vh.tvChildShiftType.setTextColor(Color.parseColor("#000000"));
        }*/

        if (childItem.getIconId() == R.drawable.ic_morning) {
            vh.tvChildShiftType.setTextColor(
                    ContextCompat.getColor(mContext, R.color.text_morning));
        } else if (childItem.getIconId() == R.drawable.ic_afternoon) {
            vh.tvChildShiftType.setTextColor(
                    ContextCompat.getColor(mContext, R.color.text_afternoon));
        } else {
            vh.tvChildShiftType.setTextColor(
                    ContextCompat.getColor(mContext, R.color.text_night));
        }

        boolean isLastOfSection =
                position < mItem.size() - 1 &&
                        getItemViewType(position + 1) == ViewNursingType.TYPE_SECTION_ITEM;
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
            //mIsFirstChild = false;
        } else {
            vh.tvChildLocation.getRootView()
                    .setBackgroundResource(R.drawable.middle_item_state);
            //mIsFirstChild = false;
        }

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.onViewNursingItemDetail(childItem.getId(), position);
            }
        });

        vh.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mLongCallBack.onEditOrDeleteItem(childItem.getId(), position);
                return true;
            }
        });

    }

    private void setupSectionItem(ViewNursingSectionViewholder vh,
                                  ViewNursingSectionItem sectionItem,
                                  int position) {
        vh.tvSectionItem.setText(sectionItem.getSectionDateMonth());
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mCallBack = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mLongCallBack = listener;
    }

    public interface OnItemLongClickListener{
        void onEditOrDeleteItem(int id, int position);
    }

    public interface OnItemClickListener{
        void onViewNursingItemDetail(int id, int position);
    }
    
}
