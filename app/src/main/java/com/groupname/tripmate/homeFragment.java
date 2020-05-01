package com.groupname.tripmate;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class homeFragment extends Fragment {

    View view;
    Button BTNsignout, main_activity_view_schedule,btnTrackLocation,main_activity_btn1;
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
        main_activity_btn1 = view.findViewById(R.id.main_activity_btn1);
        main_activity_view_schedule.setOnTouchListener(new ButtonHighlighterOnTouchListener(main_activity_view_schedule));
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

        btnTrackLocation = (Button)view.findViewById(R.id.btnTrackLocation);
        if(FirstClass.user.getProperty("isDriver").equals("1"))
        {
            main_activity_btn1.setVisibility(View.GONE);
            btnTrackLocation.setText("Set Location");
            Toast.makeText(this.getActivity(), "Welcome Driver Set Your Location", Toast.LENGTH_LONG).show();
        }
        else
        {
            main_activity_btn1.setVisibility(View.VISIBLE);
            btnTrackLocation.setText("Track Location");
        }
        btnTrackLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),com.groupname.tripmate.TrackLocation.class));
            }
        });
    }
}
