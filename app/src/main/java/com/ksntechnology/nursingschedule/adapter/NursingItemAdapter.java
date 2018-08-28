package com.ksntechnology.nursingschedule.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ksntechnology.nursingschedule.R;
import com.ksntechnology.nursingschedule.dao.ChildItem;
import com.ksntechnology.nursingschedule.dao.NursingItemCollectionDao;
import com.ksntechnology.nursingschedule.dao.NursingItemDao;
import com.ksntechnology.nursingschedule.dao.SectionItem;
import com.ksntechnology.nursingschedule.manager.NursingListManager;

import java.util.List;

public class NursingItemAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List mItem;
    private static final int SECTION_ITEM = 0;
    private static final int CHILD_ITEM = 1;
    private boolean mIsFirstChild = true;
    private onItemSelectListener mCallBack;
    private onLongItemSelectListener mCallBackLongClicked;


    public interface onItemSelectListener{
        void onItemSelect(View view, int position, int id);
    }

    public interface onLongItemSelectListener{
        void onLongItemSelect(View view, int position, int id);
    }

    public NursingItemAdapter(Context mContext, List mDao) {
        this.mContext = mContext;
        this.mItem = mDao;
    }

    public void setOnItemSelectListener(onItemSelectListener listener) {
        mCallBack = listener;
    }

    public void setOnLongItemSelectListener(onLongItemSelectListener listener) {
        mCallBackLongClicked = listener;
    }

    @NonNull
    @Override

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                      int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (viewType == SECTION_ITEM) {
            View view = inflater.inflate(
                    R.layout.section_item_layout, parent, false
            );

            return new NursingSectionViewHolder(view);
        } else if (viewType == CHILD_ITEM) {
            View view = inflater.inflate(
                    R.layout.child_item_view_layout, parent, false
            );

            return new NursingChildViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,
                                 int position) {
        int viewType = getItemViewType(position);
        if (viewType == SECTION_ITEM) {
            SectionItem item = (SectionItem) mItem.get(position);
            NursingSectionViewHolder vHolder = (NursingSectionViewHolder) holder;

            mIsFirstChild = true;
            vHolder.tvSectionItem.setText(item.getSectionDateMonth());
        } else if (viewType == CHILD_ITEM) {
            ChildItem item = (ChildItem) mItem.get(position);
            NursingChildViewHolder vHolder = (NursingChildViewHolder) holder;

            vHolder.imgLogoShiftType.setImageResource(item.getIconId());
            vHolder.tvChildLocation.setText(item.getTextLocation());
            vHolder.tvChildShiftType.setText(item.getTextShiftType());

            if (item.getIconId() == R.drawable.ic_morning) {
                vHolder.tvChildShiftType.setTextColor(Color.parseColor("#2962FF"));
            } else if (item.getIconId() == R.drawable.ic_afternoon) {
                vHolder.tvChildShiftType.setTextColor(Color.parseColor("#DD2C00"));
            } else {
                vHolder.tvChildShiftType.setTextColor(Color.parseColor("#000000"));
            }

            boolean isLastOfSection =
                    position < mItem.size() - 1 && getItemViewType(position + 1) == SECTION_ITEM;
            boolean isLastOfAll = position == mItem.size() - 1;
            boolean isLastChild = isLastOfSection || isLastOfAll;

            if (mIsFirstChild && isLastChild) {
                vHolder.tvChildLocation.getRootView()
                        .setBackgroundResource(R.drawable.one_item_state);
                mIsFirstChild = false;
            } else if (mIsFirstChild || position == 0) {
                vHolder.tvChildLocation.getRootView()
                        .setBackgroundResource(R.drawable.top_item_state);
                mIsFirstChild = false;
            } else if (isLastChild) {
                vHolder.tvChildLocation.getRootView()
                        .setBackgroundResource(R.drawable.bottom_item_state);
            } else {
                vHolder.tvChildLocation.getRootView()
                        .setBackgroundResource(R.drawable.middle_item_state);
            }
        }
    }

    @Override
    public int getItemCount() {
        //return dao.getData().size();
        return mItem.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mItem.get(position) instanceof SectionItem) {
            return SECTION_ITEM;
        } else if (mItem.get(position) instanceof ChildItem) {
            return CHILD_ITEM;
        }
        return -1;
    }


    /*************************************
     *  Inner Class
     */
    public class NursingSectionViewHolder extends RecyclerView.ViewHolder {
        public TextView tvSectionItem;

        public NursingSectionViewHolder(View cv) {
            super(cv);
            tvSectionItem = cv.findViewById(R.id.textSection_topic);
        }

    }

    public class NursingChildViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgLogoShiftType;
        public TextView tvChildLocation;
        public TextView tvChildShiftType;

        public NursingChildViewHolder(View cv) {
            super(cv);
            imgLogoShiftType = cv.findViewById(R.id.imageChild_shiftType);
            tvChildLocation = cv.findViewById(R.id.textChild_location);
            tvChildShiftType = cv.findViewById(R.id.textChild_shiftType);

            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    ChildItem item = (ChildItem) mItem.get(pos);
                    mCallBack.onItemSelect(v, pos, item.getId());
                }
            });

            cv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = getAdapterPosition();
                    ChildItem item = (ChildItem) mItem.get(pos);
                    mCallBackLongClicked.onLongItemSelect(v, pos, item.getId());
                    return true;
                }
            });
        }

    }


}
