package com.encoderbytes.qrcodeScannerAndGenerator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.encoderbytes.qrcodeScannerAndGenerator.fragments.History;
import com.encoderbytes.qrcodeScannerAndGenerator.fragments.MyCode;
import com.encoderbytes.qrcodeScannerAndGenerator.fragments.Scanner;
import com.encoderbytes.qrcodeScannerAndGenerator.fragments.Setting;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    FrameLayout fragment_container;
    private InterstitialAd mInterstitialAd;
    int click=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.nav_view);
        fragment_container = findViewById(R.id.fragment_container);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }



        loadFragment(new Scanner());


        Window window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.topbar_black));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.black2));

        }

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.admob_interstitalAds));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Fragment fragment = null;


                switch (menuItem.getItemId()) {

                    case R.id.navigation_scanner:
                        click ++;
                        if (mInterstitialAd.isLoaded() && click==4 ) {
                            mInterstitialAd.show();
                            click=0;
                        }else {
                            fragment = new Scanner();
                        }

                        break;
                    case R.id.navigation_history:
                        click ++;

                        if (mInterstitialAd.isLoaded() && click==4 ) {
                            mInterstitialAd.show();
                            click=0;

                        }else {
                            fragment = new History();
                        }
                        break;


                    case R.id.navigation_myCode:
                        click ++;

                        if (mInterstitialAd.isLoaded() && click==4 ) {
                            mInterstitialAd.show();
                            click=0;
                        }else {
                            fragment = new MyCode();
                        }

                        break;

                    case R.id.navigation_setting:

                        fragment = new Setting();
                        break;

                }

                return loadFragment(fragment);
            }
        });

    }

    private boolean loadFragment(Fragment fragment) {

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
