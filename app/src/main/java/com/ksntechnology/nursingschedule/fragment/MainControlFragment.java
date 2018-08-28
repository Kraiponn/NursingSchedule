package com.ksntechnology.nursingschedule.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ksntechnology.nursingschedule.R;

public class MainControlFragment extends Fragment {
    private LinearLayout btnViewData;
    private LinearLayout btnAddItem;
    private LinearLayout btnViewTeam;
    private LinearLayout btnViewStatistic;
    private ImageView imgActionMenuSetting;


    public interface setOnItemClickListener{
        void onItemClicked(View view);
    }

    public static MainControlFragment newInstance(int id) {
        MainControlFragment fragment = new MainControlFragment();
        Bundle args = new Bundle();
        args.putInt("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_main_content,
                container, false
        );

        initInstance(view, savedInstanceState);
        return view;
    }

    private void initInstance(View view, Bundle savedInstanceState) {
        btnViewData = view.findViewById(R.id.button_viewData);
        btnAddItem = view.findViewById(R.id.button_AddItem);
        btnViewTeam = view.findViewById(R.id.button_viewFriends);
        btnViewStatistic = view.findViewById(R.id.button_viewStatistic);
        imgActionMenuSetting = view.findViewById(R.id.image_action_menu_setting);

        imgActionMenuSetting.setOnClickListener(imgActionMenuSettingClicked);
        btnViewData.setOnClickListener(btnViewDataClickListener);
        btnAddItem.setOnClickListener(btnAddItemClickListener);
        btnViewTeam.setOnClickListener(btnViewTeamClickListener);
        btnViewStatistic.setOnClickListener(btnViewStatisticClickListener);
    }

    private void onCallBackViewClicked(View v) {
        setOnItemClickListener listener =
                (setOnItemClickListener) getActivity();
        listener.onItemClicked(v);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    /*************************************
     *  Listener Zone
     */
    View.OnClickListener imgActionMenuSettingClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onCallBackViewClicked(v);
        }
    };

    View.OnClickListener btnViewDataClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onCallBackViewClicked(v);
        }
    };

    View.OnClickListener btnAddItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onCallBackViewClicked(v);
        }
    };

    View.OnClickListener btnViewTeamClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onCallBackViewClicked(v);
        }
    };

    View.OnClickListener btnViewStatisticClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onCallBackViewClicked(v);
        }
    };

}
