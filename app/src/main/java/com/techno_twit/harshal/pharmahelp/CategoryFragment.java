package com.techno_twit.harshal.pharmahelp;

import android.app.Activity;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class CategoryFragment extends Fragment {


   // private OnFragmentInteractionListener mListener;
    View view;

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_category, container, false);
        setUpListView();
        return view;
    }

    private void setUpListView(){
        final String[] catogery={
                "antacids",
                "baby-essentials",
                "cold-cure",
                "chronic",
                "first-aid",
                "pain-killer",
                "vitamins"
        };
        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(getActivity(), R.layout.catogery_list_rows, R.id.firstLine, catogery);
        ListView ls=(ListView)view.findViewById(R.id.catogerylistView);
        ls.setAdapter(itemsAdapter);

        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String data=catogery[position];
                Fragment fragment=new SearchFragment();
                final Bundle bundle=new Bundle();
                bundle.putString("catogery",data);
                fragment.setArguments(bundle);

                FragmentManager fragmentManager=getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame,fragment).commit();
            }
        });
    }




}
