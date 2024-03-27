package com.rickieyinnovates.juditon.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Receipt {

    private Integer transactionId;
    private String referenceNumber;
    private String date;
    private String receivedFrom;
    private String description;
    private String details;
    private Double amount;
}