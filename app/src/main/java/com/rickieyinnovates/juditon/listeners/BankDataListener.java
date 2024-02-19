package com.rickieyinnovates.juditon.listeners;

import android.content.Context;
import com.rickieyinnovates.juditon.models.BankAccount;

import java.util.List;

public interface BankDataListener {
    void onBankDataReceived(Context context, List<BankAccount> bankAccountList);
}
