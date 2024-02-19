package com.rickieyinnovates.juditon.listeners;

import android.content.Context;
import com.rickieyinnovates.juditon.models.Account;

import java.util.List;

public interface AccountsDataListener {
    void onAccountsDataReceived(Context context, List<Account> accountList);
}
