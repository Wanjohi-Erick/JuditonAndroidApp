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
import com.rickieyinnovates.juditon.adapters.VouchersAdapter;
import com.rickieyinnovates.juditon.listeners.ReceiptsDataListener;
import com.rickieyinnovates.juditon.listeners.VouchersDataListener;
import com.rickieyinnovates.juditon.models.Receipt;
import com.rickieyinnovates.juditon.models.Voucher;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VouchersFragment extends Fragment implements VouchersDataListener {

    RecyclerView recyclerView;

    private static final String TAG = "VouchersFragment";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_vouchers, container, false);
        recyclerView = root.findViewById(R.id.vouchersRecycler);
        try {
            getAllVouchers(root.getContext(), this);
        } catch (Exception e) {
            Log.e(TAG, "onCreateView: ", e.fillInStackTrace());
        }
        return root;
    }

    public static void getAllVouchers(Context context, VouchersDataListener vouchersDataListener) {
        List<Voucher> voucherList = new ArrayList<>();
        ApiClient apiClient = new ApiClient(context);
        apiClient.makeAuthenticatedGetRequest(context, "/finance/api/getAllVouchers", new ApiClient.Callback() {
            @Override
            public void onSuccess(String response) {
                Log.d(TAG, "onSuccess: response: " + response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int pvId = jsonObject.getInt("pvId");
                        String voucherNumber = jsonObject.getString("voucherNumber");
                        String date = jsonObject.getString("date");
                        String status = jsonObject.getString("status");
                        String payeeName = jsonObject.getString("payeeName");
                        String account = jsonObject.getString("account");
                        String details = jsonObject.getString("details");
                        double amount = jsonObject.getDouble("amount");

                        Voucher voucher = new Voucher(pvId, voucherNumber, status, date, payeeName, account, details, amount);
                        voucherList.add(voucher);
                    }

                    vouchersDataListener.onReceiptsDataReceived(context, voucherList);

                    ApiDataManager apiDataManager = ApiDataManager.getInstance();
                    apiDataManager.setVoucherList(voucherList);

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
    public void onReceiptsDataReceived(Context context, List<Voucher> vouchers) {
        VouchersAdapter receiptsAdapter = new VouchersAdapter(vouchers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(receiptsAdapter);
    }
}