package com.bagusseptianto.akbp3l;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bagusseptianto.akbp3l.View.ViewMenu;
import com.bagusseptianto.akbp3l.View.ViewPesanan;

public class MainActivity extends AppCompatActivity {

    Button button, button2, buttonCamera;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    public static final String SHARED_PREFS = "sharedPrefs" ;
    public static final String ID_RESERVASI = "2" ;
    public static final String NAMA_CUSTOMER = "Susi" ;
    public static final String NAMA_MEJA = "Table 5" ;
//    public static final String time = "040121 1455" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonCamera = findViewById(R.id.buttonCamera);
        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CameraActivity.class);
                startActivity(i);
                finish();
            }
        });

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ViewMenu());
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String scannedIdReservasi = sharedPreferences.getString(ID_RESERVASI, "");
        String scannedNamaCust = sharedPreferences.getString(NAMA_CUSTOMER, "");
        String scannedMejaCust = sharedPreferences.getString(NAMA_MEJA, "");
        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPesanan fragment = new ViewPesanan();
                Bundle args = new Bundle();
                args.putString("idReservasi", scannedIdReservasi);
                args.putString("nama", scannedNamaCust);
                args.putString("meja", scannedMejaCust);
                fragment.setArguments(args);

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction
                    .replace(R.id.mainFragment, fragment)
                    .addToBackStack(null)
                    .commit();
            }
        });
    }

    public void loadFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction
            .replace(R.id.mainFragment, fragment)
            .addToBackStack(null)
            .commit();
    }
}
