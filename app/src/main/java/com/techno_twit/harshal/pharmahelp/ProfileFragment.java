package com.techno_twit.harshal.pharmahelp;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static android.R.id.input;
import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment{

    Button b;
    ImageView i;
    TextView t;
    private static int RESULT_LOAD_IMAGE = 1;

    SharedPreferences sp;
    String input;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        b = (Button) v.findViewById(R.id.button);
        i= (ImageView) v.findViewById(R.id.profile);
        t = (TextView) v.findViewById(R.id.text);

       /* sp=this.getActivity().getSharedPreferences("shared",MODE_PRIVATE);
        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        t.setText(sp.getString("name", ""));

        input = sp.getString("image","");

        byte[] decodedByte = Base64.decode(input, 0);
        i.setImageBitmap( BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length)); */


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);

            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }
}