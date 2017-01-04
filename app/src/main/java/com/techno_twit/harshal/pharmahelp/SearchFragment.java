package com.techno_twit.harshal.pharmahelp;

import android.app.FragmentManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class SearchFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    View view;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ListView mListView;
    View defaultView;
    String link;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_search, container, false);
        link="http://"+Connectivity.getIpPort()+"/psr/getmedicine.php";

        Bundle args=getArguments();
        if(args!=null&&args.containsKey("catogery")){
            link="http://"+Connectivity.getIpPort()+"/psr/getcatogerymedicine.php"+"?catogery="+args.getString("catogery");
        }

        setupWindow(view);




        return view;
    }

    public void refresh() {

        getMedicines med=new getMedicines(mListView);
        med.execute();
    }

    private void setupWindow(View view) {
        defaultView = view.findViewById(android.R.id.content);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();

            }

        });
        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);

        //List View
        mListView = (ListView) view.findViewById(R.id.list);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO finish here
            }
        });

        refresh();

    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {

        public void onFragmentInteraction(Uri uri);

    }

    private class getMedicines extends AsyncTask<Void, Void,Boolean >{

        ListView listView;
        String[] name;
        String[] catogery;
        String[] description;
        int[] price;
        String[] photo;
        public getMedicines(ListView mListview){
            this.listView=mListview;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            HttpURLConnection con= null;
            try {
                URL url=new URL(link);
                con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("POST");

                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                con.setConnectTimeout(15000);
                con.setDoInput(true);
                con.setDoOutput(true);

                BufferedReader buff=new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder result=new StringBuilder();

                String line;
                while((line=buff.readLine())!=null){
                    result.append(line);
                }

                if(result==null){
                    return false;
                }
                Log.e("error",result.toString());
                JSONArray json1=new JSONArray(result.toString());
                name = new String[json1.length()];
                catogery= new String[json1.length()];
                 description = new String[json1.length()];
                price = new int[json1.length()];
                photo=new String[json1.length()];
                for(int i=0;i<json1.length();i++) {
                    JSONArray json = json1.getJSONArray(i);
                    name[i]=json.getString(0);
                    description[i]=json.getString(1);
                    catogery[i]=json.getString(2);
                    price[i]=json.getInt(3);
                    photo[i]=json.getString(4);
                }

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } catch (JSONException e) {
                e.printStackTrace();
                return false;

            }

            return true;
        }
        @Override
        public void onPostExecute(Boolean param){
            if(param==false){
                //Snackbar.make(defaultView,"No results",Snackbar.LENGTH_SHORT).show();
                Log.i("Search","No return");
                return;
            }

            ArrayAdapter<String> itemsAdapter =
                    new ArrayAdapter<String>(getActivity(), R.layout.catogery_list_rows, R.id.firstLine, name);
            listView.setAdapter(itemsAdapter);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Fragment fragment = new DetailsFragment();
                    final Bundle bundle = new Bundle();
                    bundle.putString("name", name[position]);
                    bundle.putString("description", description[position]);
                    bundle.putString("catogery", catogery[position]);
                    bundle.putInt("price", price[position]);
                    bundle.putString("photo",photo[position]);
                    fragment.setArguments(bundle);

                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();
                }
            });

        }
    }

}