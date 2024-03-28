package com.rickieyinnovates.juditon;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.VolleyError;
import com.rickieyinnovates.juditon.adapters.CustomBanksAdapter;
import com.rickieyinnovates.juditon.fragments.BanksFragment;
import com.rickieyinnovates.juditon.listeners.BankDataListener;
import com.rickieyinnovates.juditon.models.BankAccount;
import com.rickieyinnovates.juditon.models.Transaction;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AddTransaction extends AppCompatActivity implements BankDataListener {
    private static final String TAG = "AddTransaction";
    Spinner paidvia;
    EditText mpesa, date, received, details, trans;

    ProgressDialog progressDialog;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait ...");
        builder = new AlertDialog.Builder(this);

        mpesa = findViewById(R.id.mpesa);
        date = findViewById(R.id.receipt_date);
        received = findViewById(R.id.receivedFrom);
        details = findViewById(R.id.receiptDetails);
        trans = findViewById(R.id.transRef);
        paidvia = findViewById(R.id.bankSpinner);
        Button add = findViewById(R.id.addReceipt);

        try {
            List<BankAccount> bankAccountList = ApiDataManager.getInstance().getBankAccounts();
            if (bankAccountList == null) {
                BanksFragment.getAllBanks(this, this);
            } else {
                populateBanksSpinner(this, bankAccountList);
            }
        } catch (Exception e) {
            Log.e(TAG, "onCreate: ", e.fillInStackTrace());
        }

        mpesa.setOnEditorActionListener((v, actionId, event) -> {

            String message = v.getText().toString();
            Transaction transaction = TransactionParser.sent(message, v.getContext());
            System.out.println(transaction);

            date.setText(transaction.getDate().toString());
            received.setText(transaction.getPayerOrPayee());
            trans.setText(transaction.getTransactionReferenece());

            return false;
        });

        add.setOnClickListener(v->{
            String dateString, receivedFrom, detailsString, transRef;
            int bank;
            dateString = date.getText().toString();
            receivedFrom = received.getText().toString();
            detailsString = details.getText().toString();
            transRef = trans.getText().toString();
            BankAccount bankAccount = (BankAccount) paidvia.getSelectedItem();
            Log.d(TAG, "onCreate: bank: " + bankAccount);
            bank = bankAccount.getId();
            if (validInputs(dateString, receivedFrom, detailsString, transRef)) {
                try {
                    addReceipt(dateString, receivedFrom, detailsString, transRef, bank);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    private boolean validInputs(String dateString, String receivedFrom, String detailsString, String transRef) {
        if (TextUtils.isEmpty(dateString)) {
            String error = "Field cannot be empty!";
            date.setError(error);
            date.requestFocus();

            return false;
        }

        if (TextUtils.isEmpty(receivedFrom)) {
            String error = "Field cannot be empty!";
            received.setError(error);
            received.requestFocus();

            return false;
        }

        if (TextUtils.isEmpty(detailsString)) {
            String error = "Field cannot be empty!";
            details.setError(error);
            details.requestFocus();

            return false;
        }

        if (TextUtils.isEmpty(transRef)) {
            String error = "Field cannot be empty!";
            trans.setError(error);
            trans.requestFocus();

            return false;
        }

        return true;
    }

    private void addReceipt(String dateString, String receivedFrom, String detailsString, String transRef, int bank) throws JSONException {
        progressDialog.show();

        JSONObject acAm = new JSONObject();
        acAm.put("particulars", 1);
        acAm.put("amount", 1000);

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(acAm);


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("date", dateString);
        jsonObject.put("receivedFrom", receivedFrom);
        jsonObject.put("details", detailsString);
        jsonObject.put("transRef", transRef);
        jsonObject.put("bank", bank);
        jsonObject.put("receiptTableRowList", jsonArray);

        Log.d(TAG, "addReceipt: " + jsonObject);

        ApiClient apiClient = new ApiClient(this);
        apiClient.makeAuthenticatedPostRequest(jsonObject, "/finance/receipt/add", new ApiClient.Callback() {
            @Override
            public void onSuccess(String response) {
                progressDialog.dismiss();
                Log.i(TAG, "onSuccess: " + response);
                try {
                    JSONObject responseObject = new JSONObject(response);
                    builder.setTitle("Server Response");
                    builder.setMessage(responseObject.getString("response"));
                    builder.setPositiveButton("Ok", (dialog, which) -> dialog.dismiss());

                    builder.create().show();
                } catch (Exception e) {
                    throw new RuntimeException();
                }
            }

            @Override
            public void onError(VolleyError error) {
                progressDialog.dismiss();
                Log.e(TAG, "onError: ", error);
            }
        });
    }

    @Override
    public void onBankDataReceived(Context context, List<BankAccount> bankAccountList) {
        Log.d(TAG, "onCreate: banks: " + bankAccountList);
        populateBanksSpinner(context, bankAccountList);
    }

    private void populateBanksSpinner(Context context, List<BankAccount> bankAccountList) {
        CustomBanksAdapter arrayAdapter = new CustomBanksAdapter(context, bankAccountList);
        paidvia.setAdapter(arrayAdapter);
    }
}