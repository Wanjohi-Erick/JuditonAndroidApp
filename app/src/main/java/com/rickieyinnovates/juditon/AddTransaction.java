package com.rickieyinnovates.juditon;

import android.content.Context;
import android.util.Log;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.rickieyinnovates.juditon.fragments.BanksFragment;
import com.rickieyinnovates.juditon.listeners.BankDataListener;
import com.rickieyinnovates.juditon.models.BankAccount;
import com.rickieyinnovates.juditon.models.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class AddTransaction extends AppCompatActivity implements BankDataListener {
    private static final String TAG = "AddTransaction";
    Spinner paidvia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        EditText mpesa, date, received, details, trans;

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

            Log.d(TAG, "onEditorAction: " + message);
            Log.d(TAG, "onEditorAction: " + transaction);

            return false;
        });

        add.setOnClickListener(v->{

        });


    }

    @Override
    public void onBankDataReceived(Context context, List<BankAccount> bankAccountList) {
        Log.d(TAG, "onCreate: banks: " + bankAccountList);
        populateBanksSpinner(context, bankAccountList);
    }

    private void populateBanksSpinner(Context context, List<BankAccount> bankAccountList) {
        List<String> accountNames = bankAccountList.stream().map(BankAccount::getAccountName).collect(Collectors.toList());
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, accountNames);
        paidvia.setAdapter(arrayAdapter);
    }
}