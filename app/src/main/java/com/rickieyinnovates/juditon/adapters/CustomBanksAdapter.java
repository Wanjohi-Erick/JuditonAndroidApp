package com.rickieyinnovates.juditon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.rickieyinnovates.juditon.models.BankAccount;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CustomBanksAdapter extends ArrayAdapter<BankAccount> {

    public CustomBanksAdapter(@NonNull Context context, List<BankAccount> bankAccounts) {
        super(context, 0, bankAccounts);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createView(position, convertView, parent);
    }

    private View createView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        BankAccount bankAccount = getItem(position);

        if (bankAccount != null) {
            textView.setText(bankAccount.getAccountName());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable @org.jetbrains.annotations.Nullable View convertView, @NonNull @NotNull ViewGroup parent) {
        return createView(position, convertView, parent);
    }
}
