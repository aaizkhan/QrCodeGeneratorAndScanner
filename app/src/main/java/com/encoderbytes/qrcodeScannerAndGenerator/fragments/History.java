package com.encoderbytes.qrcodeScannerAndGenerator.fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.encoderbytes.qrcodeScannerAndGenerator.DBManager;
import com.encoderbytes.qrcodeScannerAndGenerator.R;
import com.encoderbytes.qrcodeScannerAndGenerator.adapters.History_adapter;
import com.encoderbytes.qrcodeScannerAndGenerator.models.History_model;

import java.util.ArrayList;

public class History extends Fragment {


    RecyclerView recyclerViewHistory;
    ArrayList<History_model> history_models;
    History_adapter history_adapter;

    TextView txtEmpty;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewHistory=view.findViewById(R.id.recyclerHistory);
         recyclerViewHistory.hasFixedSize();
        txtEmpty=view.findViewById(R.id.txt_empty);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,true);
        linearLayoutManager.setStackFromEnd(true);

        recyclerViewHistory.setLayoutManager(linearLayoutManager);

        history_models=new ArrayList<>();
        history_adapter=new History_adapter(getContext(),history_models);

        retriveData();

    }

    private void retriveData() {

      //  history_models.clear();

        DBManager dbManager  = new DBManager(getContext());
        dbManager.open();
        final Cursor cursor = dbManager.fetch();

        while (cursor.moveToNext()){

            int id=cursor.getInt(0);
            String title=cursor.getString(1);
            String type=cursor.getString(2);

            History_model model=new History_model(id,title,type);

            history_models.add(model);


        }
        if (!(history_models.size()<1)){
            recyclerViewHistory.setAdapter(history_adapter);
        }else {
            txtEmpty.setVisibility(View.VISIBLE);
        }

       // dbManager.close();
    }
}
