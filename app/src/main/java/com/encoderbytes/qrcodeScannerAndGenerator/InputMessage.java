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

public class InputMessage extends AppCompatActivity {

    TextView txtPhoneNumber, txtMessage;
    private DBManager2 dbManager;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_message);

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

        txtPhoneNumber = findViewById(R.id.txt_msgPhone);
        txtMessage = findViewById(R.id.txt_message);

        dbManager = new DBManager2(this);
        dbManager.open();


    }

    public void create_Qr(View view) {
        String phone = txtPhoneNumber.getText().toString().trim();
        String msg = txtMessage.getText().toString().trim();

        if (phone.equals("")) {
            txtPhoneNumber.setError("Please enter phone number");
        }
        if (msg.equals("")) {
            txtMessage.setError("Please enter message");
        } else {

            dbManager.insert(phone,msg,"message");

            String txtResult="Phone :"+phone+ "\nMessage : "+msg;

            Intent intent=new Intent(getApplicationContext(), MyResult.class);
            intent.putExtra("result",txtResult);
            startActivity(intent);
            finish();
         }
    }

    public void btn_back(View view) {
        finish();
    }
}
