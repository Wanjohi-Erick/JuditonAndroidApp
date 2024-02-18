package com.rickieyinnovates.juditon.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.rickieyinnovates.juditon.R;
import com.rickieyinnovates.juditon.models.BankAccount;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BanksAdapter extends RecyclerView.Adapter<BanksAdapter.MyViewHolder> {
    List<BankAccount> accountList;


    public BanksAdapter(List<BankAccount> accountList) {
        this.accountList = accountList;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_banks, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder myViewHolder, int i) {
        myViewHolder.bankNameView.setText(accountList.get(i).getAccountName());
        //myViewHolder.idView.setText(accountList.get(i).getId());
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView bankNameView;
        EditText idView;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            idView = itemView.findViewById(R.id.idView);
            bankNameView = itemView.findViewById(R.id.bankNameView);
        }
    }
}
