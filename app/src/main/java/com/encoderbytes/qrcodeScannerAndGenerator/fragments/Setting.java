package com.encoderbytes.qrcodeScannerAndGenerator.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.encoderbytes.qrcodeScannerAndGenerator.R;


public class Setting extends Fragment {


    CardView cardPrivacy,cardRateUs,cardFeedback;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cardPrivacy=view.findViewById(R.id.card_privacy);
        cardFeedback=view.findViewById(R.id.card_feedback);
        cardRateUs=view.findViewById(R.id.card_ratus);


        cardRateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rateApp();
            }
        });

        cardFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                String[] emails_in_to={"encoderbytes@gmail.com"};
                intent.putExtra(Intent.EXTRA_EMAIL, emails_in_to );
                intent.putExtra(Intent.EXTRA_SUBJECT,"QR Code Scanner App (Feedback)");
                intent.putExtra(Intent.EXTRA_TEXT, "Enter your feedback here:");
                intent.putExtra(Intent.EXTRA_CC,"mailcc@gmail.com");
                intent.setType("text/html");
                intent.setPackage("com.google.android.gm");
                startActivity(intent);
            }
        });

        cardPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.privacy_policy_link)));
                startActivity(browserIntent);
            }
        });
    }

    private void rateApp()
    {
        try
        {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        }
        catch (ActivityNotFoundException e)
        {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
            startActivity(rateIntent);
        }
    }

    private Intent rateIntentForUrl(String url)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getActivity().getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21)
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        else
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        intent.addFlags(flags);
        return intent;
    }



}
