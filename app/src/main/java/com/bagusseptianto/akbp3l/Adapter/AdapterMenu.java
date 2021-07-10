package com.bagusseptianto.akbp3l.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bagusseptianto.akbp3l.Model.Menu;
import com.bagusseptianto.akbp3l.R;
import com.bagusseptianto.akbp3l.View.TambahPesanan;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class AdapterMenu extends RecyclerView.Adapter<AdapterMenu.adapterMenuViewHolder> {
    private List<Menu> menuList, menuListFiltered;
    private Context context;
    private View view;

    public AdapterMenu(List<Menu> menuList, Context context) {
        this.menuList = menuList;
        this.menuListFiltered = menuList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterMenu.adapterMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.activity_adapter_menu, parent, false);
        return new AdapterMenu.adapterMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMenu.adapterMenuViewHolder holder, int position) {
        final Menu menu = menuListFiltered.get(position);

        NumberFormat formatter = new DecimalFormat("#.###");
        holder.txtJenis.setText(menu.getJENIS_MENU());
        holder.txtNama.setText(menu.getNAMA_MENU());
        holder.txtHarga.setText("Rp. " + formatter.format(menu.getHARGA_MENU()));
        Glide.with(context)
            .load(menu.getGambar())
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(holder.ivGambar);

        holder.cardMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TODO
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Bundle data = new Bundle();
                data.putSerializable("menu", menu);
                data.putString("status", "pesan");
                TambahPesanan tambahPesanan = new TambahPesanan();
                tambahPesanan.setArguments(data);
                loadFragment(tambahPesanan);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (menuListFiltered != null) ? menuListFiltered.size() : 0;
    }

    public class adapterMenuViewHolder extends RecyclerView.ViewHolder {
        private TextView txtJenis, txtNama, txtHarga;
        private ImageView ivGambar;
        private CardView cardMenu;

        public adapterMenuViewHolder(@NonNull View itemView) {
            super(itemView);
            txtJenis    = itemView.findViewById(R.id.tvJenis);
            txtNama     = itemView.findViewById(R.id.tvNama);
            txtHarga    = itemView.findViewById(R.id.tvHarga);
            ivGambar    = itemView.findViewById(R.id.ivGambar);
            cardMenu    = itemView.findViewById(R.id.cardMenu);
        }
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String userInput = charSequence.toString();
                if(userInput.isEmpty()) {
                    menuListFiltered = menuList;
                } else {
                    List<Menu> filteredList = new ArrayList<>();
                    for(Menu menu : menuList) {
                        if(String.valueOf(menu.getNAMA_MENU()).toLowerCase().contains(userInput.toLowerCase()) ||
                            String.valueOf(menu.getHARGA_MENU()).toLowerCase().contains(userInput.toLowerCase()) ||
                            String.valueOf(menu.getJENIS_MENU()).toLowerCase().contains(userInput.toLowerCase())) {
                            filteredList.add(menu);
                        }
                    }
                    menuListFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = menuListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                menuListFiltered = (ArrayList<Menu>) filterResults.values;
                notifyDataSetChanged();
            }
        };
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
