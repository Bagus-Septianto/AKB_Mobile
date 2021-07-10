package com.bagusseptianto.akbp3l.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bagusseptianto.akbp3l.Model.Pesanan;
import com.bagusseptianto.akbp3l.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class AdapterPesanan extends RecyclerView.Adapter<AdapterPesanan.adapterPesananViewHolder> {
    private List<Pesanan> pesananList;
    private Context context;
    private View view;

    public AdapterPesanan(List<Pesanan> pesananList, Context context) {
        this.pesananList = pesananList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterPesanan.adapterPesananViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.activity_adapter_pesanan, parent, false);
        return new AdapterPesanan.adapterPesananViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPesanan.adapterPesananViewHolder holder, int position) {
        final Pesanan pesanan = pesananList.get(position);
        NumberFormat formatter = new DecimalFormat("#.###");
        holder.txtJenis.setText(pesanan.getJENIS_MENU());
        holder.txtNama.setText(pesanan.getNAMA_MENU());
        holder.txtHarga.setText("Rp. " + formatter.format(Integer.valueOf(pesanan.getHARGA_MENU())));
        holder.txtJumlah.setText(" : " + String.valueOf(pesanan.getJUMLAH_PESANAN()));
        holder.txtStatus.setText(" : " + pesanan.getSTATUS_PESANAN());
    }

    @Override
    public int getItemCount() {
        return (pesananList != null) ? pesananList.size() : 0;
    }

    public class adapterPesananViewHolder extends RecyclerView.ViewHolder {
        private TextView txtJenis, txtNama, txtHarga, txtJumlah, txtStatus;

        public adapterPesananViewHolder(@NonNull View itemView) {
            super(itemView);
            txtJenis    = itemView.findViewById(R.id.tvJenis);
            txtNama     = itemView.findViewById(R.id.tvNama);
            txtHarga    = itemView.findViewById(R.id.tvHarga);
            txtJumlah   = itemView.findViewById(R.id.tvJumlahPesanan);
            txtStatus   = itemView.findViewById(R.id.tvStatusPesanan);
        }
    }
}
