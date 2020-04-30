package com.groupname.tripmate;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class homeFragment extends Fragment {

    View view;
    Button main_activity_view_schedule, btnAdd, main_activity_btn1;
    public homeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view=inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        main_activity_view_schedule = (Button) view.findViewById(R.id.main_activity_view_schedule);
        main_activity_view_schedule.setOnTouchListener(new ButtonHighlighterOnTouchListener(main_activity_view_schedule));
        btnAdd = view.findViewById(R.id.btnSubmit);
        btnAdd.setOnTouchListener(new ButtonHighlighterOnTouchListener(btnAdd));
        main_activity_btn1 = view.findViewById(R.id.main_activity_btn1);
        main_activity_btn1.setOnTouchListener(new ButtonHighlighterOnTouchListener(main_activity_btn1));

        if (FirstClass.user.getProperty("isAdmin").equals("0")) {
            btnAdd.setVisibility(View.GONE);
        }

        // BTNsignout = findViewById(R.id.BTNsignout);
       /* BTNsignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthUI.getInstance().signOut(getApplicationContext());
            }
        });*/
        main_activity_view_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), com.groupname.tripmate.Schedule_Activity.class));
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), com.groupname.tripmate.AddBus.class));
            }
        });

        main_activity_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), com.groupname.tripmate.Schedule_Activity.class);
                intent.putExtra("activity", "homeFragment");
                startActivity(intent);
            }
        });
    }
}
