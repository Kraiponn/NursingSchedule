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

public class MainNavigationActionMenuFragment extends Fragment {
    private LinearLayout btnExit;
    private LinearLayout btnSetting;
    private LinearLayout btnManual;


    public interface setOnNavigationMenuItemClickListener{
        void onNavigationMenuItemClicked(View view);
    }

    public static MainNavigationActionMenuFragment newInstance(int id) {
        MainNavigationActionMenuFragment fragment = new MainNavigationActionMenuFragment();
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
                R.layout.fragment_main_navigation_action_menu,
                container, false
        );

        initInstance(view);
        return view;
    }

    private void initInstance(View view) {
        btnExit = view.findViewById(R.id.layoutEXit);
        btnSetting = view.findViewById(R.id.layoutSetting);
        btnManual = view.findViewById(R.id.layoutManual);

        btnExit.setOnClickListener(btnExitActionMenuClicked);
        btnManual.setOnClickListener(btnManualClicked);
        btnSetting.setOnClickListener(btnSettingActionMenuClick);
    }

    private void onCallBackViewClicked(View v) {
        setOnNavigationMenuItemClickListener listener =
                (setOnNavigationMenuItemClickListener) getActivity();
        listener.onNavigationMenuItemClicked(v);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    /*************************************
     *  Listener Zone
     */
    View.OnClickListener btnExitActionMenuClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getFragmentManager().popBackStack();
            onCallBackViewClicked(v);
        }
    };

    View.OnClickListener btnManualClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //getFragmentManager().popBackStack();
            onCallBackViewClicked(v);
        }
    };

    View.OnClickListener btnSettingActionMenuClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //getFragmentManager().popBackStack();
            onCallBackViewClicked(v);
        }
    };


}
