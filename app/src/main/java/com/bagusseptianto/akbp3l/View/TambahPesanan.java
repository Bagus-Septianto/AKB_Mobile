package com.bagusseptianto.akbp3l.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bagusseptianto.akbp3l.API.API;
import com.bagusseptianto.akbp3l.Model.Menu;
import com.bagusseptianto.akbp3l.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;
import static com.bagusseptianto.akbp3l.MainActivity.ID_RESERVASI;

public class TambahPesanan extends Fragment {
    private TextView tvNamaMenu, tvDeskripsiMenu, tvHargaMenu;
    private TextInputEditText txtJumlahPesanan;
    private ImageView ivGambar;
    private Button btnPesan, btnKembali;
    private int idMenu;
    private Menu menu;
    private View view;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tambah_pesanan, container, false);
        menu = (Menu) getArguments().getSerializable("menu");
        tvNamaMenu          = view.findViewById(R.id.tvNamaMenu);
        tvDeskripsiMenu     = view.findViewById(R.id.tvDeskripsiMenu);
        tvHargaMenu         = view.findViewById(R.id.tvHargaMenu);
        txtJumlahPesanan    = view.findViewById(R.id.txtJumlahPesanan);
        ivGambar            = view.findViewById(R.id.ivGambar);
        btnKembali          = view.findViewById(R.id.btnBatal);
        sharedPreferences = getActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        btnPesan            = view.findViewById(R.id.btnPesan);
        btnPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                // data-data pesanan yang perlu di post

                if(txtJumlahPesanan.getText().toString().isEmpty())
                    Toast.makeText(getContext(), "Masukkan Jumlah Pesanan", Toast.LENGTH_SHORT).show();
                else {
                    int jumlahPesanan = Integer.parseInt(txtJumlahPesanan.getText().toString());
                    //something = new Something(jumlahPesanan, idReservasi);
                    pesan(jumlahPesanan);
                }
            }
        });

        tvNamaMenu.setText(menu.getNAMA_MENU());
        tvDeskripsiMenu.setText(menu.getDESKRIPSI_MENU());
        NumberFormat formatter = new DecimalFormat("#.###");
        tvHargaMenu.setText("Rp. " + formatter.format(menu.getHARGA_MENU()));
        Glide.with(view.getContext())
            .load(menu.getGambar())
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(ivGambar);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(android.view.Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(android.view.Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if(menu.findItem(R.id.btnSearch) != null)
            menu.findItem(R.id.btnSearch).setVisible(false);
        getActivity().setTitle("Pesan");
    }

    public void pesan(int jumlahPesanan) {
        RequestQueue queue = Volley.newRequestQueue(getContext());

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(POST, API.URL_PESAN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.getString("message").equals("Berhasil Menambahkan Pesanan")) {
                        loadFragment(new ViewMenu());
                    }
                    Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=utf-8");
                params.put("ID_MENU", String.valueOf(menu.getID_MENU()));
                params.put("ID_RESERVASI", sharedPreferences.getString(ID_RESERVASI, ""));
                params.put("JUMLAH_PESANAN", String.valueOf(jumlahPesanan));
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void loadFragment(Fragment fragment) {
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_view_menu, fragment)
            .addToBackStack(null)
            .commit();
    }
}
