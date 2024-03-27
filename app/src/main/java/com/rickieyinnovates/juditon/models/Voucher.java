package com.rickieyinnovates.juditon.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
public class Voucher {
    private int pvId;
    private String voucherNumber, status;
    private String date;
    private String payeeName;
    private String account;
    private String details;
    private double amount;
}

