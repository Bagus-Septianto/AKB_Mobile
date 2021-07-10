package com.bagusseptianto.akbp3l.View;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bagusseptianto.akbp3l.API.API;
import com.bagusseptianto.akbp3l.Adapter.AdapterMenu;
import com.bagusseptianto.akbp3l.Model.Menu;
import com.bagusseptianto.akbp3l.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.Request.Method.GET;

public class ViewMenu extends Fragment {
    private RecyclerView recyclerView;
    private AdapterMenu adapter;
    private List<Menu> listMenu;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_menu, container, false);

        loadDaftarMenu();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(android.view.Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_bar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem searchItem = menu.findItem(R.id.btnSearch);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
    }

    @Override
    public void onPrepareOptionsMenu(android.view.Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.btnSearch).setVisible(true);
        getActivity().setTitle("Daftar Menu");
    }

    private void loadDaftarMenu() {
        setAdapter();
        getMenu();
    }

    public void setAdapter(){
        listMenu = new ArrayList<Menu>();
        recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new AdapterMenu(listMenu, view.getContext());

        /*Buat tampilan untuk adapter jika potrait menampilkan 2 data dalam 1 baris,
        sedangakan untuk landscape 4 data dalam 1 baris*/
        RecyclerView.LayoutManager layoutManager;
        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            layoutManager = new GridLayoutManager(view.getContext(), 2);
        } else {
            layoutManager = new GridLayoutManager(view.getContext(), 4);
        }
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void getMenu() {
        //Tambahkan tampil menu disini
        RequestQueue queue = Volley.newRequestQueue(view.getContext());

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("Loading....");
        progressDialog.setTitle("Menampilkan data Menu");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        final JsonObjectRequest stringRequest = new JsonObjectRequest(GET, API.URL_GET_MENU, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Disini bagian jika response jaringan berhasil tidak dapat gangguan/error
                progressDialog.dismiss();
                try {
                    //Mengambil data response json object yang berupa data buku
                    JSONArray jsonArray = response.getJSONArray("data");

                    if (!listMenu.isEmpty())
                        listMenu.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        //mengubah data jsonArray tertentu mjd json Object
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                        int id = Integer.parseInt(jsonObject.optString("ID_MENU"));
                        int idbahan = Integer.parseInt(jsonObject.optString("ID_BAHAN"));
                        String bahan = jsonObject.optString("NAMA_BAHAN");
                        int harga = Integer.parseInt(jsonObject.optString("HARGA_MENU"));
                        String nama = jsonObject.optString("NAMA_MENU");
                        String jenis = jsonObject.optString("JENIS_MENU");
                        String deskripsi = jsonObject.optString("DESKRIPSI_MENU");
                        String unit = jsonObject.optString("UNIT_MENU");
                        String gambar = jsonObject.optString("gambar");

                        //membuat objek user
                        Menu menu = new Menu(id, idbahan, harga, nama, jenis, deskripsi, unit, gambar);

                        //menambahkan objek user tadi ke list user
                        listMenu.add(menu);
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
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }
}
