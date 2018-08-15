package com.ksntechnology.nursingschedule.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ksntechnology.nursingschedule.R;

public class WorkMateDetailFragment extends Fragment {
    private String mLocation;
    private int mMonth;
    private int mYear;
    private RecyclerView rcv;

    public static WorkMateDetailFragment newInstance(
            String location, int month, int year
    ) {
        WorkMateDetailFragment fragment = new WorkMateDetailFragment();
        Bundle args = new Bundle();
        args.putString("location", location);
        args.putInt("month", month);
        args.putInt("year", year);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mLocation = getArguments().getString("location");
            mMonth = getArguments().getInt("month");
            mYear = getArguments().getInt("year");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_workmate_detail_layout,
                container, false
        );

        initInstance(view);
        return view;
    }

    private void initInstance(View view) {
        rcv = view.findViewById(R.id.recyclerView_WorkMateDetail);
    }
}
