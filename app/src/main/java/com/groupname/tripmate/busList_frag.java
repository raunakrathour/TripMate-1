package com.groupname.tripmate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class busList_frag extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter myadapter;
    View view;

    public busList_frag() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_bus_list_frag, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = view.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        //From here we can control the listing style on buslist_Frag
        //we can make any layout as needed
        layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);

        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.addGroupBy("time");

        Backendless.Persistence.of(bus.class).find(queryBuilder, new AsyncCallback<List<bus>>() {
            @Override
            public void handleResponse(List<bus> response) {
                FirstClass.busses = (ArrayList<bus>) response;
                myadapter = new busAdapter(getActivity(), FirstClass.busses);
                recyclerView.setAdapter(myadapter);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(getContext(), "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

  //  public void notifyItem()
  //  {
       // myadapter.notifyDataSetChanged();
   // }
}
