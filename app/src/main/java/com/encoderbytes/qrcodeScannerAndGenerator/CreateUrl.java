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

public class CreateUrl extends AppCompatActivity {
    private AdView mAdView;

    TextView txtUrl;
    private DBManager2 dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_url);

      txtUrl=findViewById(R.id.txt_createUrl);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.topbar_black));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.black2));
        }
        dbManager = new DBManager2(this);
        dbManager.open();

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void btn_back(View view) {
        finish();
    }

    public void btn_createUrl(View view) {

        String url=txtUrl.getText().toString().trim();

        if (url.equals("")){
            txtUrl.setError("Please enter url");
        }else {

            dbManager.insert(url,"","url");
            Intent intent=new Intent(getApplicationContext(), MyResult.class);
            intent.putExtra("result",url);
            startActivity(intent);
            finish();
        }
    }
}
