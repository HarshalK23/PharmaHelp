package com.techno_twit.harshal.pharmahelp;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techno_twit.harshal.pharmahelp.R;

public class DetailsFragment extends Fragment {

    String catogery,name,description;
    int price;
    View view;
    public DetailsFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_details, container, false);
        Bundle args=getArguments();
        if(args!=null&&args.containsKey("catogery")){
            catogery=args.getString("catogery");
            price=args.getInt("price");
            description=args.getString("description");
            name=args.getString("name");
        }

        TextView t1=(TextView) view.findViewById(R.id.textView5);
        TextView t2=(TextView) view.findViewById(R.id.textView4);
        TextView t3=(TextView) view.findViewById(R.id.textView3);
        TextView t4=(TextView) view.findViewById(R.id.textView2);

        t1.setText("Price: "+String.valueOf(price));
        t2.setText("Catogery: "+catogery);
        t3.setText("Description: "+description);
        t4.setText("Name: "+name);

        return view;
    }
}
