package com.bagusseptianto.akbp3l.View;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bagusseptianto.akbp3l.API.API;
import com.bagusseptianto.akbp3l.Adapter.AdapterMenu;
import com.bagusseptianto.akbp3l.Adapter.AdapterPesanan;
import com.bagusseptianto.akbp3l.Model.Menu;
import com.bagusseptianto.akbp3l.Model.Pesanan;
import com.bagusseptianto.akbp3l.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.Request.Method.GET;

public class ViewPesanan extends Fragment {
    private RecyclerView recyclerView;
    private AdapterPesanan adapter;
    private List<Pesanan> listPesanan;
    private View view;
    private String idReservasi; //atau int idReservasi
    private TextView tvNama, tvMeja;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_pesanan, container, false);
        getActivity().setTitle("Daftar Pesanan");

        Bundle args = getArguments();
        idReservasi = args.getString("idReservasi");

        tvNama = view.findViewById(R.id.namaCust);
        tvMeja = view.findViewById(R.id.mejaCust);
        tvNama.setText(" : " + args.getString("nama"));
        tvMeja.setText(" : " + args.getString("meja"));

        listPesanan = new ArrayList<Pesanan>();
        recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new AdapterPesanan(listPesanan, view.getContext());
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        getPesanan();

        return view;
    }

    private void getPesanan() {
        //Tambahkan tampil pesanan disini
        RequestQueue queue = Volley.newRequestQueue(view.getContext());

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("Loading....");
        progressDialog.setTitle("Menampilkan data Pesanan");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        final JsonObjectRequest stringRequest = new JsonObjectRequest(GET, API.URL_GET_PESANAN + idReservasi, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Disini bagian jika response jaringan berhasil tidak dapat gangguan/error
                progressDialog.dismiss();
                try {
                    //Mengambil data response json object yang berupa data buku
                    JSONArray jsonArray = response.getJSONArray("dataDetilPesanans");

                    if (!listPesanan.isEmpty())
                        listPesanan.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        //mengubah data jsonArray tertentu mjd json Object
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        System.out.println(jsonObject);
                        int JUMLAH_PESANAN = Integer.parseInt(jsonObject.optString("JUMLAH_PESANAN"));
                        String NAMA_MENU = jsonObject.optString("NAMA_MENU");
                        String JENIS_MENU = jsonObject.optString("JENIS_MENU");
                        String HARGA_MENU = jsonObject.optString("HARGA_MENU");
                        String STATUS_PESANAN = jsonObject.optString("STATUS_PESANAN");

                        //membuat objek pesanan
                        Pesanan pesanan = new Pesanan(JUMLAH_PESANAN, NAMA_MENU, JENIS_MENU, HARGA_MENU, STATUS_PESANAN);

                        //menambahkan objek user tadi ke list user
                        listPesanan.add(pesanan);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //disini bagian jika response jaringan terdapat gangguan/error
                progressDialog.dismiss();
            }
        });
        queue.add(stringRequest);
    }
}
