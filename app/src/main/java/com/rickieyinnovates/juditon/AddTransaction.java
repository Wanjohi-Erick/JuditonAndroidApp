package com.rickieyinnovates.juditon;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.VolleyError;
import com.rickieyinnovates.juditon.adapters.CustomBanksAdapter;
import com.rickieyinnovates.juditon.adapters.MyAdapter;
import com.rickieyinnovates.juditon.fragments.AccountsFragment;
import com.rickieyinnovates.juditon.fragments.BanksFragment;
import com.rickieyinnovates.juditon.fragments.ReceiptsFragment;
import com.rickieyinnovates.juditon.listeners.AccountsDataListener;
import com.rickieyinnovates.juditon.listeners.BankDataListener;
import com.rickieyinnovates.juditon.listeners.ItemClickListener;
import com.rickieyinnovates.juditon.models.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddTransaction extends AppCompatActivity implements BankDataListener, AccountsDataListener, ItemClickListener {
    private static final String TAG = "AddTransaction";
    Spinner paidvia;
    EditText mpesa, date, received, details, trans;


    Button add, btn_update, btn_cancel;
    RecyclerView recyclerView;
    MyAdapter adapter;

    List<UserData> list = new ArrayList<>();
    List<Account> accountList = new ArrayList<>();

    AlertDialog.Builder builder;

    String name, email;

    ProgressDialog progressDialog;

    List<Row> rows = new ArrayList<>();

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
        Button addRow = findViewById(R.id.addRowBtn);



        recyclerView = findViewById(R.id.addReceiptRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));




        addRow.setOnClickListener(view -> {

            UserData userData = new UserData();

            userData.setActivity(name);
            userData.setAmount(email);

            list.add(userData);
            adapter.notifyItemInserted(list.size() - 1);

        });

        try {
            List<BankAccount> bankAccountList = ApiDataManager.getInstance().getBankAccounts();
            List<Account> accounts = ApiDataManager.getInstance().getAccountList();
            if (bankAccountList == null) {
                BanksFragment.getAllBanks(this, this);
            } else {
                populateBanksSpinner(this, bankAccountList);
            }

            if (accounts == null) {
                AccountsFragment.getAllAccounts(this, this);
            } else {
                populateAccountsSpinner(this, accounts);
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

        JSONArray jsonArray = new JSONArray();
        for (Row row : rows) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("activity", row.getParticulars());
                jsonObject.put("amount", row.getAmount());

                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


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
                    builder.setPositiveButton("Ok", (dialog, which) -> {
                        dialog.dismiss();

                        // Get the FragmentManager instance
                        FragmentManager fragmentManager = getSupportFragmentManager();

                        // Begin a FragmentTransaction
                        FragmentTransaction transaction = fragmentManager.beginTransaction();

                        // Replace the container with YourFragment
                        Fragment yourFragment = new ReceiptsFragment();
                        transaction.replace(R.id.fragment_container, yourFragment);

                        // You can also add the transaction to the back stack if you want to support back navigation
                        // transaction.addToBackStack(null);

                        // Commit the transaction
                        transaction.commit();
                    });

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

    private void populateAccountsSpinner(Context context, List<Account> accounts) {
        accountList = accounts;
        adapter = new MyAdapter(this, list, accountList, this);
        recyclerView.setAdapter(adapter);
        Log.i(TAG, "populateAccountsSpinner: " + accountList.size());
    }

    @Override
    public void onAccountsDataReceived(Context context, List<Account> accountList) {
        populateAccountsSpinner(context, accountList);
    }

    @Override
    public void updateRow(Row row) {
        rows.add(row);
        System.out.println("row: " + row.toString());
        System.out.println("rows: " + rows.toString());

        double total = rows.stream().mapToDouble(Row::getAmount).sum();

        TextView tv = findViewById(R.id.totalReceiptView);
        tv.setText("Total Payable: " + total);
    }
}