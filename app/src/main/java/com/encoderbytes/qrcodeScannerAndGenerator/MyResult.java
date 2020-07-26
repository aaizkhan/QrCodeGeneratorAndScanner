package com.encoderbytes.qrcodeScannerAndGenerator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class MyResult extends AppCompatActivity {


    TextView txtResultText,txtResultLabel1,txtResultLabel2;
    ImageView imgLabel,imageView;

    Bitmap bitmap;
    String resultText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_result);

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

        imageView=findViewById(R.id.img_qrCode);
        txtResultText=findViewById(R.id.txt_result);
        txtResultLabel1=findViewById(R.id.txt_labelResult);
        txtResultLabel2=findViewById(R.id.txt_labelResult2);
        imgLabel=findViewById(R.id.img_label);



          resultText=getIntent().getStringExtra("result");

        if (resultText.contains("https://") || resultText.contains("http://")){

            txtResultLabel1.setText("Url");
            txtResultLabel2.setText("Url");
            imgLabel.setImageResource(R.drawable.ic_link);


        }else {
            txtResultLabel1.setText("Text");
            txtResultLabel2.setText("Text");

            imgLabel.setImageResource(R.drawable.ic_letter_a);

        }

        String text=resultText; // Whatever you need to encode in the QR code
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE,450,450);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        txtResultText.setText(resultText);

    }

    public void btn_browser(View view) {

        if (resultText.contains("http://") || resultText.contains("https://")){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(resultText)));
        }else {

            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/#q=" + resultText)));

        }

    }

    public void btn_copy(View view) {

        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("text copied", resultText);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Copied", Toast.LENGTH_SHORT).show();
    }

    public void btn_share(View view) {

        Bitmap bm = ((android.graphics.drawable.BitmapDrawable) imageView.getDrawable()).getBitmap();
        try {
            java.io.File file = new java.io.File(getExternalCacheDir() + "/image.jpg");
            java.io.OutputStream out = new java.io.FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {

            }
        Intent iten = new Intent(android.content.Intent.ACTION_SEND);
        iten.setType("*/*");
        iten.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new java.io.File(getExternalCacheDir() + "/image.jpg")));
        startActivity(Intent.createChooser(iten, "Share QR Code"));

    }

    public void btn_back(View view) {
        finish();
    }
}
