package com.rickieyinnovates.juditon;

import com.rickieyinnovates.juditon.models.Account;
import com.rickieyinnovates.juditon.models.BankAccount;
import com.rickieyinnovates.juditon.models.Receipt;
import com.rickieyinnovates.juditon.models.Voucher;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ApiDataManager {
    private static ApiDataManager instance;
    private String authToken;
    private String username;
    private String email;

    private List<BankAccount> bankAccounts;

    private List<Account> accountList;

    private List<Receipt> receiptList;

    private List<Voucher> voucherList;
    private ApiDataManager() {
    }

    public static ApiDataManager getInstance() {
        if (instance == null) {
            instance = new ApiDataManager();
        }
        return instance;
    }


}