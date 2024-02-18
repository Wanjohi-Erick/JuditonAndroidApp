package com.rickieyinnovates.juditon.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.rickieyinnovates.juditon.R;
import com.rickieyinnovates.juditon.models.Account;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.MyViewHolder> {
    List<Account> accountList;


    public AccountsAdapter(List<Account> accountList) {
        this.accountList = accountList;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_account, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder myViewHolder, int i) {
        myViewHolder.accountView.setText(accountList.get(i).getAccount());
        //myViewHolder.idView.setText(accountList.get(i).getId());
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView accountView;
        EditText idView;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            idView = itemView.findViewById(R.id.idView);
            accountView = itemView.findViewById(R.id.accountNameView);
        }
    }
}
