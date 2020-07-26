package com.encoderbytes.qrcodeScannerAndGenerator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class CreateText extends AppCompatActivity {

    TextView txtText;
    private DBManager2 dbManager;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_text);

        Window window = this.getWindow();
         window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
         window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.topbar_black));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.black2));
        }

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        txtText=findViewById(R.id.txt_text);

        dbManager = new DBManager2(this);
        dbManager.open();

    }

    public void btn_createText(View view) {

        String txt=txtText.getText().toString().trim();

        if (txt.equals("")){
            txtText.setError("Please enter Text");
        }else {

            dbManager.insert(txt,"","text");
            Intent intent=new Intent(getApplicationContext(), MyResult.class);
            intent.putExtra("result",txt);
            startActivity(intent);
            finish();
        }
    }

    public void btn_back(View view) {
        finish();
    }
}
