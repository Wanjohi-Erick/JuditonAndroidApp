package com.rickieyinnovates.juditon.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.VolleyError;
import com.rickieyinnovates.juditon.ApiClient;
import com.rickieyinnovates.juditon.R;
import com.rickieyinnovates.juditon.adapters.AccountsAdapter;
import com.rickieyinnovates.juditon.adapters.BanksAdapter;
import com.rickieyinnovates.juditon.models.Account;
import com.rickieyinnovates.juditon.models.BankAccount;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BanksFragment extends Fragment {

    private static final String TAG = "BanksFragment";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_banks, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.banksRecycler);
        List<BankAccount> bankAccountList = new ArrayList<>();

        ApiClient apiClient = new ApiClient(root.getContext());
        apiClient.makeAuthenticatedGetRequest(root.getContext(), "/finance/banking/get/all", new ApiClient.Callback() {
            @Override
            public void onSuccess(String response) {
                Log.d(TAG, "onSuccess: response: " + response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String accountName = jsonObject.getString("accountName");
                        String accountNumber = jsonObject.getString("account");
                        String bankName = jsonObject.getString("bankName");
                        String type = jsonObject.getString("type");

                        BankAccount bankAccount = new BankAccount(id, accountName, accountNumber, bankName, type, 0.00);
                        bankAccountList.add(bankAccount);
                    }

                    BanksAdapter banksAdapter = new BanksAdapter(bankAccountList);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
                    recyclerView.setAdapter(banksAdapter);

                } catch (Exception e) {
                    Log.e(TAG, "onSuccess: " + e.getLocalizedMessage());
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.d(TAG, "onError: " + error);
                Toast.makeText(root.getContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}