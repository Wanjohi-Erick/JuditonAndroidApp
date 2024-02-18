package com.rickieyinnovates.juditon.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.rickieyinnovates.juditon.R;
import com.rickieyinnovates.juditon.TransactionParser;
import com.rickieyinnovates.juditon.models.Transaction;

public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        String mpesaMessage = "SBI9GH13ZR Confirmed. Ksh290.00 sent to COLLINS  MWANGI 0768691360 on 18/2/24 at 1:37 PM. New M-PESA balance is Ksh655.23. Transaction cost, Ksh7.00. Amount you can transact within the day is 499,690.00. To reverse, forward this message to 456.";
        Transaction transaction = TransactionParser.sent(mpesaMessage, root.getContext());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}