package com.rickieyinnovates.juditon;

import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.rickieyinnovates.juditon.models.Transaction;

public class AddTransaction extends AppCompatActivity {
    private static final String TAG = "AddTransaction";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        EditText mpesa, date, received, details, trans;
        Spinner paidvia;

        mpesa = findViewById(R.id.mpesa);
        date = findViewById(R.id.receipt_date);
        received = findViewById(R.id.receivedFrom);
        details = findViewById(R.id.receiptDetails);
        trans = findViewById(R.id.transRef);
        paidvia = findViewById(R.id.bankSpinner);
        Button add = findViewById(R.id.addReceipt);

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
}