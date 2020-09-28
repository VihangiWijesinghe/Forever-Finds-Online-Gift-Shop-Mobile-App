package com.example.foreverfind;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class Fragmentmanage extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_fragmentmanage,container,false);
        Button btnfrg = rootView.findViewById(R.id.btnClearh);
        Button btnfrg2 = rootView.findViewById(R.id.btnDeleteA);

        btnfrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDialog();
            }
        });

        btnfrg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), ConfirmDelete.class);
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }

    public void openDialog(){


        DialogExamples example = new DialogExamples();
        example.show(getFragmentManager(),"example");
    }


}