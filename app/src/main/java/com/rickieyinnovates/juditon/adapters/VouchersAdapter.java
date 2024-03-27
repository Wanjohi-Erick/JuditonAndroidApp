package com.rickieyinnovates.juditon.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.rickieyinnovates.juditon.R;
import com.rickieyinnovates.juditon.models.Receipt;
import com.rickieyinnovates.juditon.models.Voucher;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class VouchersAdapter extends RecyclerView.Adapter<VouchersAdapter.MyViewHolder> {
    List<Voucher> voucherList;
    public VouchersAdapter(List<Voucher> voucherList) {
        this.voucherList = voucherList;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_voucher, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder myViewHolder, int i) {
        myViewHolder.pvView.setText(voucherList.get(i).getVoucherNumber());
    }

    @Override
    public int getItemCount() {
        return voucherList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView pvView;
        EditText idEdit;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            idEdit = itemView.findViewById(R.id.idView);
            pvView = itemView.findViewById(R.id.voucherView);
        }
    }
}
