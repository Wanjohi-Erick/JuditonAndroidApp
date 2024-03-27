package com.rickieyinnovates.juditon.listeners;

import android.content.Context;
import com.rickieyinnovates.juditon.models.Receipt;
import com.rickieyinnovates.juditon.models.Voucher;

import java.util.List;

public interface VouchersDataListener {
    void onReceiptsDataReceived(Context context, List<Voucher> vouchers);
}
