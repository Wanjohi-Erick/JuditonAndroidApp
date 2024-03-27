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
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ReceiptsAdapter extends RecyclerView.Adapter<ReceiptsAdapter.MyViewHolder> {
    List<Receipt> receiptList;
    public ReceiptsAdapter(List<Receipt> receiptList) {
        this.receiptList = receiptList;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_receipt, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder myViewHolder, int i) {
        myViewHolder.rctView.setText(receiptList.get(i).getReferenceNumber());
    }

    @Override
    public int getItemCount() {
        return receiptList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView rctView;
        EditText idEdit;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            idEdit = itemView.findViewById(R.id.idView);
            rctView = itemView.findViewById(R.id.receiptView);
        }
    }
}
