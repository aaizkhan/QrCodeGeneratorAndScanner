package com.encoderbytes.qrcodeScannerAndGenerator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class CreateCode extends AppCompatActivity {
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_code);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.topbar_black));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.black2));


        }

/////////////////////////////////////////////////////////////////////


        ///////////////////////////////
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }



    public void btn_back(View view) {
        finish();
    }

    public void card_url(View view) {

        Intent i=new Intent(getApplicationContext(), CreateUrl.class);
         startActivity(i);
    }

    public void card_text(View view) {
        Intent i=new Intent(getApplicationContext(), CreateText.class);
         startActivity(i);
    }

    public void card_email(View view) {
        Intent i=new Intent(getApplicationContext(), CreateEmail.class);
         startActivity(i);
    }

    public void card_message(View view) {
        Intent i=new Intent(getApplicationContext(), InputMessage.class);
         startActivity(i);
    }
}
