package com.techno_twit.harshal.pharmahelp;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;


public class FeedbackFragment extends Fragment {
       private OnFragmentInteractionListener mListener;

    public static FeedbackFragment newInstance() {
        FeedbackFragment fragmentDemo = new FeedbackFragment();
        return fragmentDemo;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_feedback, container, false);
        final EditText feedback=(EditText)view.findViewById(R.id.feedback);
        final RatingBar ratingBar=(RatingBar)view.findViewById(R.id.ratingBar);
        Button send=(Button)view.findViewById(R.id.sendbutton);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedback.setText("");
                ratingBar.setNumStars(0);
                View defaultView = getActivity().findViewById(android.R.id.content);
                Snackbar.make(defaultView,"Feedback sent",Snackbar.LENGTH_SHORT).show();
            }
        });
        return view;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {

        public void onFragmentInteraction(Uri uri);
    }

}
