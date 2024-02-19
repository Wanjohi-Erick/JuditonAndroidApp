package com.rickieyinnovates.juditon.fragments;

import android.content.Context;
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
import com.rickieyinnovates.juditon.listeners.BankDataListener;
import com.rickieyinnovates.juditon.R;
import com.rickieyinnovates.juditon.ApiDataManager;
import com.rickieyinnovates.juditon.adapters.BanksAdapter;
import com.rickieyinnovates.juditon.models.BankAccount;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BanksFragment extends Fragment implements BankDataListener {

    RecyclerView recyclerView;

    private static final String TAG = "BanksFragment";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_banks, container, false);

        recyclerView = root.findViewById(R.id.banksRecycler);

        try {
            getAllBanks(root.getContext(), this);
        } catch (Exception e) {
            Log.e(TAG, "onCreateView: ", e.fillInStackTrace());
        }


        return root;
    }

    public static void getAllBanks(Context context, BankDataListener bankDataListener) {
        List<BankAccount> bankAccountList = new ArrayList<>();
        ApiClient apiClient = new ApiClient(context);
        apiClient.makeAuthenticatedGetRequest(context, "/finance/banking/get/all", new ApiClient.Callback() {
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
                        JSONArray transactionsArray = jsonObject.getJSONArray("accounttransactions");


                        BankAccount bankAccount = new BankAccount(id, accountName, accountNumber, bankName, type, 0.00);
                        bankAccountList.add(bankAccount);
                        Log.d(TAG, "onSuccess: bank: " + bankAccount);
                    }

                    bankDataListener.onBankDataReceived(context, bankAccountList);

                    ApiDataManager apiDataManager = ApiDataManager.getInstance();
                    apiDataManager.setBankAccounts(bankAccountList);

                } catch (Exception e) {
                    Log.e(TAG, "onSuccess: " + e.getLocalizedMessage());
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.d(TAG, "onError: " + error);
                Toast.makeText(context, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onBankDataReceived(Context context, List<BankAccount> bankAccountList) {
        BanksAdapter banksAdapter = new BanksAdapter(bankAccountList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(banksAdapter);
    }
}