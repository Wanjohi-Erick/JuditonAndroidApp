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
import com.rickieyinnovates.juditon.adapters.BanksAdapter;
import com.rickieyinnovates.juditon.adapters.ReceiptsAdapter;
import com.rickieyinnovates.juditon.listeners.BankDataListener;
import com.rickieyinnovates.juditon.listeners.ReceiptsDataListener;
import com.rickieyinnovates.juditon.models.BankAccount;
import com.rickieyinnovates.juditon.models.Receipt;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReceiptsFragment extends Fragment implements ReceiptsDataListener {

    RecyclerView recyclerView;
    private static final String TAG = "ReceiptsFragment";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_receipts, container, false);
        recyclerView = root.findViewById(R.id.receiptsRecycler);

        try {
            getAllReceipts(root.getContext(), this);
        } catch (Exception e) {
            Log.e(TAG, "onCreateView: ", e.fillInStackTrace());
        }
        return root;
    }

    public static void getAllReceipts(Context context, ReceiptsDataListener receiptsDataListener) {
        List<Receipt> receiptList = new ArrayList<>();
        ApiClient apiClient = new ApiClient(context);
        apiClient.makeAuthenticatedGetRequest(context, "/finance/api/getAllReceipts", new ApiClient.Callback() {
            @Override
            public void onSuccess(String response) {
                Log.d(TAG, "onSuccess: response: " + response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int transactionId = jsonObject.getInt("transactionId");
                        String referenceNumber = jsonObject.getString("referenceNumber");
                        String date = jsonObject.getString("date");
                        String receivedFrom = jsonObject.getString("receivedFrom");
                        String description = jsonObject.getString("description");
                        String details = jsonObject.getString("details");
                        double amount = jsonObject.getDouble("amount");

                        Receipt receipt = new Receipt(transactionId, referenceNumber, date, receivedFrom, description, details, amount);
                        receiptList.add(receipt);
                        Log.d(TAG, "onSuccess: receipt: " + receipt);
                    }

                    receiptsDataListener.onReceiptsDataReceived(context, receiptList);

                    ApiDataManager apiDataManager = ApiDataManager.getInstance();
                    apiDataManager.setReceiptList(receiptList);

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
    public void onReceiptsDataReceived(Context context, List<Receipt> receiptList) {
        ReceiptsAdapter receiptsAdapter = new ReceiptsAdapter(receiptList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(receiptsAdapter);
    }
}