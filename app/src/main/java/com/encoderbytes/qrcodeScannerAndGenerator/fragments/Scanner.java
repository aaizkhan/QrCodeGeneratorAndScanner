package com.encoderbytes.qrcodeScannerAndGenerator.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.encoderbytes.qrcodeScannerAndGenerator.DBManager;
import com.encoderbytes.qrcodeScannerAndGenerator.MyResult;
import com.encoderbytes.qrcodeScannerAndGenerator.R;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class Scanner extends Fragment  implements ZXingScannerView.ResultHandler {

    ZXingScannerView scannerView;
    private DBManager dbManager;
    Bitmap bitmap;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scanner, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        scannerView=view.findViewById(R.id.scanner);
    }

    @Override
    public void handleResult(Result result) {

        dbManager = new DBManager(getContext());
        dbManager.open();

        String resultText=result.getText();

        if (resultText.contains("https://") || resultText.contains("http://")){

            dbManager.insert(resultText,"Url");


        }else {
            dbManager.insert(resultText,"Text");

        }


        Intent intent=new Intent(getContext(), MyResult.class);
        intent.putExtra("result",resultText);
        startActivity(intent);


    }

//    @Override
//    public void onPause() {
//        super.onPause();
//
//        scannerView.stopCamera();
//    }

    @Override
    public void onResume() {
        super.onResume();

        scannerView.setResultHandler(this);
        scannerView.startCamera();

    }
}
