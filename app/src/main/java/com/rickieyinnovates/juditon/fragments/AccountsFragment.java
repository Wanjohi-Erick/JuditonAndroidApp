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
import com.rickieyinnovates.juditon.ApiDataManager;
import com.rickieyinnovates.juditon.R;
import com.rickieyinnovates.juditon.adapters.AccountsAdapter;
import com.rickieyinnovates.juditon.listeners.AccountsDataListener;
import com.rickieyinnovates.juditon.models.Account;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AccountsFragment extends Fragment implements AccountsDataListener {
    RecyclerView recyclerView;

    private static final String TAG = "AccountsFragment";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_accounts, container, false);
        recyclerView = root.findViewById(R.id.accountsRecycler);

        try {
            getAllAccounts(root.getContext(), this);
        } catch (Exception e) {
            Log.e(TAG, "onCreateView: ", e.fillInStackTrace());
        }

        return root;
    }

    public static void getAllAccounts(Context context, AccountsDataListener listener) {
        List<Account> accountList = new ArrayList<>();

        ApiClient apiClient = new ApiClient(context);
        apiClient.makeAuthenticatedGetRequest(context, "/finance/chart/get/all", new ApiClient.Callback() {
            @Override
            public void onSuccess(String response) {
                Log.d(TAG, "onSuccess: response: " + response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String name = jsonObject.getString("account");

                        Account account = new Account(id, name);
                        accountList.add(account);
                    }

                    listener.onAccountsDataReceived(context, accountList);

                    ApiDataManager apiDataManager = ApiDataManager.getInstance();
                    apiDataManager.setAccountList(accountList);

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
    public void onAccountsDataReceived(Context context, List<Account> accountList) {
        AccountsAdapter accountsAdapter = new AccountsAdapter(accountList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(accountsAdapter);
    }
}