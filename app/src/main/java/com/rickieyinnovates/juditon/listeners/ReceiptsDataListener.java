package com.rickieyinnovates.juditon.listeners;

import android.content.Context;
import com.rickieyinnovates.juditon.models.BankAccount;
import com.rickieyinnovates.juditon.models.Receipt;

import java.util.List;

public interface ReceiptsDataListener {
    void onReceiptsDataReceived(Context context, List<Receipt> receipts);
}
