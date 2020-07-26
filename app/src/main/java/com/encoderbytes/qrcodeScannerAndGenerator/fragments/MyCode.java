package com.encoderbytes.qrcodeScannerAndGenerator.fragments;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.TextView;

import com.encoderbytes.qrcodeScannerAndGenerator.CreateCode;
import com.encoderbytes.qrcodeScannerAndGenerator.DBManager2;
import com.encoderbytes.qrcodeScannerAndGenerator.R;
import com.encoderbytes.qrcodeScannerAndGenerator.adapters.MyCodes_adapter;
import com.encoderbytes.qrcodeScannerAndGenerator.models.History_model;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.ArrayList;


public class MyCode extends Fragment implements RewardedVideoAdListener {

    Button btnCreateCode;

    RecyclerView recyclerViewCodes;
    ArrayList<History_model> codes_models;
    MyCodes_adapter myCodes_adapter;
    TextView txtEmpty;
    private RewardedVideoAd mRewardedVideoAd;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_code, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(getContext());
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();


        btnCreateCode = view.findViewById(R.id.btn_createCode);
        recyclerViewCodes = view.findViewById(R.id.recyclerCodes);
        codes_models = new ArrayList<>();
        txtEmpty = view.findViewById(R.id.txt_noData);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true);
        linearLayoutManager.setStackFromEnd(true);

        recyclerViewCodes.setLayoutManager(linearLayoutManager);
        myCodes_adapter = new MyCodes_adapter(getContext(), codes_models);

        retriveData();

        btnCreateCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                }else {
                    startActivity(new Intent(getContext(), CreateCode.class));
                }


            }
        });
    }
    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd(getString(R.string.admob_rewardedVideoAds),
                new AdRequest.Builder().build());
    }
    private void retriveData() {
        DBManager2 dbManager = new DBManager2(getContext());
        dbManager.open();
        final Cursor cursor = dbManager.fetch();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String desc = cursor.getString(2);
            String type = cursor.getString(3);

            History_model myCodes = new History_model();
            myCodes.setId(id);
            myCodes.setTitle(title);
            myCodes.setDesc(desc);
            myCodes.setType(type);

            codes_models.add(myCodes);

        }
        if (!(codes_models.size() < 1)) {
            recyclerViewCodes.setAdapter(myCodes_adapter);

        } else {
            txtEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

        startActivity(new Intent(getContext(), CreateCode.class));

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {
        startActivity(new Intent(getContext(), CreateCode.class));

    }
}
