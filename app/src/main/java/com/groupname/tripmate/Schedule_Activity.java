package com.groupname.tripmate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Schedule_Activity extends AppCompatActivity implements busAdapter.ItemClicked{
    //text view
    //these are text views on busDetail_Frag
    TextView fragment_bus_detail_frag_tvBusName,fragment_bus_detail_frag_tvBusNumber,fragment_bus_detail_frag_tvTime1,
            fragment_bus_detail_frag_tvTime2,fragment_bus_detail_frag_tvFrom1,fragment_bus_detail_frag_tvFrom2,fragment_bus_detail_frag_tvTo1,
            fragment_bus_detail_frag_tvTo2;
     ImageView bus_logo;

    busList_frag list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_);
        new generateBus();
         bus_logo =findViewById(R.id.bus_logo);
        fragment_bus_detail_frag_tvBusName = findViewById(R.id.fragment_bus_detail_frag_tvBusName);
        fragment_bus_detail_frag_tvBusNumber=findViewById(R.id.fragment_bus_detail_frag_tvBusNumber);
        fragment_bus_detail_frag_tvTime1 = findViewById(R.id.fragment_bus_detail_frag_tvTime1);
        fragment_bus_detail_frag_tvTime2=findViewById(R.id.fragment_bus_detail_frag_tvTime2);
        fragment_bus_detail_frag_tvFrom1=findViewById(R.id.fragment_bus_detail_frag_tvFrom1);
        fragment_bus_detail_frag_tvFrom2=findViewById(R.id.fragment_bus_detail_frag_tvFrom2);
        fragment_bus_detail_frag_tvTo1 = findViewById(R.id.fragment_bus_detail_frag_tvTo1);
        fragment_bus_detail_frag_tvTo2=findViewById(R.id.fragment_bus_detail_frag_tvTo2);
       // fragmentManager = this.getSupportFragmentManager();
      //  list = (busList_frag)fragmentManager.findFragmentById(R.id.activity_schedule_listfrag);
        //FragmentManager manager=this.getSupportFragmentManager();//it handles fragments
        if(findViewById(R.id.schedule_landscape)!=null)//if the phone is in landscape mode
        {
            bus_logo.setVisibility(View.GONE);
            FragmentManager manager=this.getSupportFragmentManager();//it handles fragments
            //control goes to busList_frag
            manager.beginTransaction()
                    .show(manager.findFragmentById(R.id.activity_schedule_listfrag))//if phoneis in landscape//show both the fragments
                    .show(manager.findFragmentById(R.id.activity_schedule_detail_frag))
                    .commit();
        }
        if(findViewById(R.id.schedule_potrait)!=null)//if phone is in potrait
        {   //control goes to busList_frag
            FragmentManager manager=this.getSupportFragmentManager();
            manager.beginTransaction()
                    .show(manager.findFragmentById(R.id.activity_schedule_listfrag))
                    .hide(manager.findFragmentById(R.id.activity_schedule_detail_frag))

                    .commit();
        }





    }

    @Override
    public void onItemClicked(int index) {
        fragment_bus_detail_frag_tvBusName.setText(generateBus.buses.get(index).getBusName());
        fragment_bus_detail_frag_tvBusNumber.setText(generateBus.buses.get(index).getBusNumber());
        fragment_bus_detail_frag_tvTime1.setText(generateBus.buses.get(index).getTime1());
        fragment_bus_detail_frag_tvTime2.setText(generateBus.buses.get(index).getTime2());
        fragment_bus_detail_frag_tvFrom1.setText(generateBus.buses.get(index).getFrom1());
        fragment_bus_detail_frag_tvFrom2.setText(generateBus.buses.get(index).getFrom2());
        fragment_bus_detail_frag_tvTo1.setText(generateBus.buses.get(index).getTo1());
        fragment_bus_detail_frag_tvTo2.setText(generateBus.buses.get(index).getTo2());

        if(findViewById(R.id.schedule_potrait)!=null)
        {
            bus_logo.setVisibility(View.VISIBLE);
            FragmentManager manager=this.getSupportFragmentManager();//this is managing the transactions
            manager.beginTransaction()
                    .hide(manager.findFragmentById(R.id.activity_schedule_listfrag))
                    .show(manager.findFragmentById(R.id.activity_schedule_detail_frag))
                    .addToBackStack(null)//on clicking back button you will land on previous fragment
                    .commit();//this commits the changes
        }
    }
}
//to make phone works on layout as well as potrait mode we have to make another layout file ie layout-land
//click on layout->new->directory->set name as "layout-land"
//change activity_main id as "potrait_layout"
//copy activity_main -> right click on layout->paste->select directory layout-land
//change land\activity_main id as "land_layout"
//after that see what i have done here
