package com.rickieyinnovates.juditon;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import com.rickieyinnovates.juditon.models.Transaction;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TransactionParser {
    public static final String ERROR_TRANSACTION_INSERT = "Error inserting the message";
    public static final String NATURE_VALUE_MPESA = "Mpesa";
    public static final String OUT_TYPE_VALUE_ = "Debt";
    public static final String IN_TYPE_VALUE_ = "Income";
    public static final String CURRENCY = "Ksh";
    //
    public static final String[] AGENT_SIM_CASH_FROM_CUSTOMER_KEY_WORDS= {"from", "Take"};
    public static final String[] AGENT_SIM_CASH_TO_CUSTOMER_KEY_WORDS= {"to", "Give"};
    public static final String[] PERSONAL_SIM_MPESA_RECEIVED_FROM_PERSON_KEY_WORDS= {"from", "received"};
    public static final String[] PERSONAL_SIM_MPESA_SENT_TO_PERSON_KEY_WORDS = {"to", "sent to"};
    public static final String[] PERSONAL_SIM_MPESA_BOUGHT_AIRTIME_FOR_PERSON_KEY_WORDS = {"of", "bought"};
    public static final String[] PERSONAL_SIM_MPESA_PAID_TO_TILL_KEY_WORDS= {"to", "paid to"};
    private static final String TAG = "TransactionParser";

    @SuppressLint("Range")
    public static void take(String transactionMessage, Context context) {
        // Define the regular expressions for date and amount
        ContentValues tx_data = new ContentValues();
        String firstWordRegex = "\\w+";
        String dateRegex = "\\d{1,2}/\\d{1,2}/\\d{1,2}";
        String amountRegex = CURRENCY+ "[\\d,]+\\.\\d{2}";
        String personRegex = "\\b"+ AGENT_SIM_CASH_FROM_CUSTOMER_KEY_WORDS[0] +"\\s+([^\\s]+)\\s+([^\\s]+)";
        // Create Pattern objects
        Pattern datePattern = Pattern.compile(dateRegex);
        Pattern amountPattern = Pattern.compile(amountRegex);
        Pattern personPattern = Pattern.compile(personRegex);
        // Create Matcher objects
        Matcher dateMatcher = datePattern.matcher(transactionMessage);
        Matcher amountMatcher = amountPattern.matcher(transactionMessage);
        Matcher personMatcher = personPattern.matcher(transactionMessage);
        // Find and print the date
        if (dateMatcher.find()) {
            String date = dateMatcher.group();

        }
        if (personMatcher.find()){
            String person = personMatcher.group().replaceAll(AGENT_SIM_CASH_FROM_CUSTOMER_KEY_WORDS[0], "");
            Log.d(TAG, "take: " + person);
        }
        // Find and print the amount
        if (amountMatcher.find()) {
            String amount = amountMatcher.group().replaceAll(CURRENCY + "|,", "");
            Float fi = Float.parseFloat(amount);
            Integer amt = fi.intValue();
            Log.d(TAG, "take: " + amt * -1);

        }
    }
    @SuppressLint("Range")
    public static void give(String transactionMessage, Context context) {
        // Define the regular expressions for date and amount
        ContentValues tx_data = new ContentValues();
        String firstWordRegex = "\\w+";
        String dateRegex = "\\d{1,2}/\\d{1,2}/\\d{1,2}";
        String amountRegex = CURRENCY + "[\\d,]+\\.\\d{2}";
        String personRegex = "\\b" + AGENT_SIM_CASH_TO_CUSTOMER_KEY_WORDS[0] + "\\s+([^\\s]+)\\s+([^\\s]+)";
        // Create Pattern objects
        Pattern datePattern = Pattern.compile(dateRegex);
        Pattern amountPattern = Pattern.compile(amountRegex);
        Pattern personPattern = Pattern.compile(personRegex);
        // Create Matcher objects
        Matcher dateMatcher = datePattern.matcher(transactionMessage);
        Matcher amountMatcher = amountPattern.matcher(transactionMessage);
        Matcher personMatcher = personPattern.matcher(transactionMessage);
        // Find and print the date
        if (dateMatcher.find()) {
            String date = dateMatcher.group();

        }
        if (personMatcher.find()){
            String person = personMatcher.group().replaceAll(AGENT_SIM_CASH_TO_CUSTOMER_KEY_WORDS[0], "");
            Log.d(TAG, "take: " + person);
        }
        // Find and print the amount
        if (amountMatcher.find()) {
            String amount = amountMatcher.group().replaceAll(CURRENCY + "|,", "");
            Float fi = Float.parseFloat(amount);
            Integer amt = fi.intValue();
            Log.d(TAG, "take: " + amt);

        }
    }
    @SuppressLint("Range")
    public static void received(String transactionMessage, Context context) {
        // Define the regular expressions for date and amount
        ContentValues tx_data = new ContentValues();
        String firstWordRegex = "\\w+";
        String dateRegex = "\\d{1,2}/\\d{1,2}/\\d{1,2}";
        String amountRegex = CURRENCY + "[\\d,]+\\.\\d{2}";
        String personRegex = "\\b" + PERSONAL_SIM_MPESA_RECEIVED_FROM_PERSON_KEY_WORDS[0] + "\\s+([^\\s]+)\\s+([^\\s]+)";
        // Create Pattern objects
        Pattern datePattern = Pattern.compile(dateRegex);
        Pattern amountPattern = Pattern.compile(amountRegex);
        Pattern personPattern = Pattern.compile(personRegex);
        // Create Matcher objects
        Matcher dateMatcher = datePattern.matcher(transactionMessage);
        Matcher amountMatcher = amountPattern.matcher(transactionMessage);
        Matcher personMatcher = personPattern.matcher(transactionMessage);
        // Find and print the date
        if (dateMatcher.find()) {
            String date = dateMatcher.group();

        }
        if (personMatcher.find()){
            String person = personMatcher.group().replaceAll(PERSONAL_SIM_MPESA_RECEIVED_FROM_PERSON_KEY_WORDS[0], "");
            Log.d(TAG, "take: " + person);
        }
        // Find and print the amount
        if (amountMatcher.find()) {
            String amount = amountMatcher.group().replaceAll(CURRENCY + "|,", "");
            Float fi = Float.parseFloat(amount);
            Integer amt = fi.intValue();
            Log.d(TAG, "take: " + amt);

        }
    }
    @SuppressLint("Range")
    public static Transaction sent(String transactionMessage, Context context) {
        // Define the regular expressions for date and amount
        try {
            String firstWordRegex = "\\w+";
            String dateRegex = "\\d{1,2}/\\d{1,2}/\\d{1,2}";
            String amountRegex = CURRENCY + "[\\d,]+\\.\\d{2}";
            String personRegex = "\\b" + PERSONAL_SIM_MPESA_SENT_TO_PERSON_KEY_WORDS[0] + "\\s+([^\\s]+)\\s+([^\\s]+)";
            // Create Pattern objects
            Pattern transRefPattern = Pattern.compile(firstWordRegex);
            Pattern datePattern = Pattern.compile(dateRegex);
            Pattern amountPattern = Pattern.compile(amountRegex);
            Pattern personPattern = Pattern.compile(personRegex);
            // Create Matcher objects
            Matcher transRefMatcher = transRefPattern.matcher(transactionMessage);
            Matcher dateMatcher = datePattern.matcher(transactionMessage);
            Matcher amountMatcher = amountPattern.matcher(transactionMessage);
            Matcher personMatcher = personPattern.matcher(transactionMessage);

            String transRef = null, person = null;
            String date = null;
            Double amt = null;

            if (transRefMatcher.find()) {
                transRef = transRefMatcher.group();
                Log.d(TAG, "sent: " + transRef);
            }

            // Find and print the date
            if (dateMatcher.find()) {
                date = dateMatcher.group();
                Log.d(TAG, "paid: " + date);

            }
            if (personMatcher.find()){
                person = personMatcher.group().replaceAll(PERSONAL_SIM_MPESA_SENT_TO_PERSON_KEY_WORDS[0], "");
                Log.d(TAG, "take: " + person);
            }
            // Find and print the amount
            if (amountMatcher.find()) {
                String amount = amountMatcher.group().replaceAll(CURRENCY + "|,", "");
                amt = Double.parseDouble(amount);
                Log.d(TAG, "take: " + amt * -1);
            }
            Transaction transaction = new Transaction(person, amt, amt, date, transRef);
            return transaction;
        } catch (Exception e) {
            Log.e(TAG, "sent: " + e.getLocalizedMessage(), e.fillInStackTrace());
            return new Transaction();
        }


    }
    @SuppressLint("Range")
    public static void paid(String transactionMessage, Context context) {
        // Define the regular expressions for date and amount
        ContentValues tx_data = new ContentValues();
        String firstWordRegex = "\\w+";
        String dateRegex = "\\d{1,2}/\\d{1,2}/\\d{1,2}";
        String amountRegex = CURRENCY + "[\\d,]+\\.\\d{2}";
        String personRegex = "\\b" + PERSONAL_SIM_MPESA_PAID_TO_TILL_KEY_WORDS[0] + "\\s+([^\\s]+)\\s+([^\\s]+)";
        // Create Pattern objects
        Pattern datePattern = Pattern.compile(dateRegex);
        Pattern amountPattern = Pattern.compile(amountRegex);
        Pattern personPattern = Pattern.compile(personRegex);
        // Create Matcher objects
        Matcher dateMatcher = datePattern.matcher(transactionMessage);
        Matcher amountMatcher = amountPattern.matcher(transactionMessage);
        Matcher personMatcher = personPattern.matcher(transactionMessage);
        // Find and print the date
        if (dateMatcher.find()) {
            String date = dateMatcher.group();
            Log.d(TAG, "paid: " + date);
            Log.d(TAG, "paid: " + firstWordRegex);

        }
        if (personMatcher.find()){
            String person = personMatcher.group().replaceAll(PERSONAL_SIM_MPESA_PAID_TO_TILL_KEY_WORDS[0], "");
            Log.d(TAG, "take: " + person);
        }
        // Find and print the amount
        if (amountMatcher.find()) {
            String amount = amountMatcher.group().replaceAll(CURRENCY + "|,", "");
            Float fi = Float.parseFloat(amount);
            Integer amt = fi.intValue();
            Log.d(TAG, "take: " + amt * -1);

        }
    }
    @SuppressLint("Range")
    public static void bought(String transactionMessage, Context context) {
        // Define the regular expressions for date and amount
        ContentValues tx_data = new ContentValues();
        String firstWordRegex = "\\w+";
        String dateRegex = "\\d{1,2}/\\d{1,2}/\\d{1,2}";
        String amountRegex = CURRENCY + "[\\d,]+\\.\\d{2}";
        String personRegex = "\\b" + PERSONAL_SIM_MPESA_BOUGHT_AIRTIME_FOR_PERSON_KEY_WORDS[0] + "\\s+([^\\s]+)\\s+([^\\s]+)\\s+([^\\s]+)";
        // Create Pattern objects
        Pattern datePattern = Pattern.compile(dateRegex);
        Pattern amountPattern = Pattern.compile(amountRegex);
        Pattern personPattern = Pattern.compile(personRegex);
        // Create Matcher objects
        Matcher dateMatcher = datePattern.matcher(transactionMessage);
        Matcher amountMatcher = amountPattern.matcher(transactionMessage);
        Matcher personMatcher = personPattern.matcher(transactionMessage);
        // Find and print the date
        if (dateMatcher.find()) {
            String date = dateMatcher.group();

        }
        if (personMatcher.find()) {
            String person = personMatcher.group().replaceAll(PERSONAL_SIM_MPESA_BOUGHT_AIRTIME_FOR_PERSON_KEY_WORDS[0], "");
            Log.d(TAG, "take: " + person);
        }
        // Find and print the amount
        if (amountMatcher.find()) {
            String amount = amountMatcher.group().replaceAll(CURRENCY + "|,", "");
            Float fi = Float.parseFloat(amount);
            Integer amt = fi.intValue();
            Log.d(TAG, "take: " + amt * -1);
        }
    }


}