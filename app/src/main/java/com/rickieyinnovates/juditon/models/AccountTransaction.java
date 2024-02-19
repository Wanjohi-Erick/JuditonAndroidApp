package com.rickieyinnovates.juditon.models;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class AccountTransaction {

    private Integer id;

    private LocalDate date;

    private String ref;

    private int account;

    private BankAccount bank;

    private String payeePayer;

    private String description;

    private String cheque;

    private Integer activity;

    private Double credit;

    private Double debit;

    private String status;
}