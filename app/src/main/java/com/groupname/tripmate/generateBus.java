package com.groupname.tripmate;

import android.app.Application;

import java.util.ArrayList;

public class generateBus {
    public static ArrayList<bus> buses;

    public generateBus() {
        buses = new ArrayList<bus>();
        buses.add(new bus("IIITA_BUS_1","UP6969",
                "12 pm","IIITA","Civil Lines","4 pm","Civil Lines","Hajipur"));
        buses.add(new bus("IIITA_BUS_2","UP169",
                "1 pm","Gurgao","Chandigarh","5 pm","Chandni Chowk","China"));
        buses.add(new bus("IIITA_BUS_3","UP199",
                "12 pm","Saam","Raat","7 pm","Astha_home","Bhopal"));
        buses.add(new bus("IIITA_BUS_4","UP196",
                "12 pm","Kichen","Bathroom","8 pm","Bihar","Agra"));
        buses.add(new bus("IIITA_BUS_5","UP189",
                "12 pm","IIITA","Civil Lines","10 pm","Pakistan","Deoghar"));
        buses.add(new bus("IIITA_BUS_6","UP190",
                "12 pm","Mirjapur","Johnpur","1 am","Civil Lines","Patna"));
        buses.add(new bus("IIITA_BUS_7","UP5659",
                "12 pm","Wuhan","New York","3 am","Jhalwa","America"));
        buses.add(new bus("IIITA_BUS_8","UP78439",
                "1 am","Kitchen","Bedroom","7 am","Prateek's heart","Astha's heart"));
        buses.add(new bus("IIITA_BUS_1","UP6969",
                "12 pm","IIITA","Civil Lines","4 pm","Civil Lines","Hajipur"));
        buses.add(new bus("IIITA_BUS_2","UP169",
                "1 pm","Gurgao","Chandigarh","5 pm","Chandni Chowk","China"));
        buses.add(new bus("IIITA_BUS_3","UP199",
                "12 pm","Saam","Raat","7 pm","Astha_home","Bhopal"));
        buses.add(new bus("IIITA_BUS_4","UP196",
                "12 pm","Kichen","Bathroom","8 pm","Bihar","Agra"));
        buses.add(new bus("IIITA_BUS_5","UP189",
                "12 pm","IIITA","Civil Lines","10 pm","Pakistan","Deoghar"));
    }
}

