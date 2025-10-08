package com.gbc.comp3074.a1_samin_qureshi_pierre_eid;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PaymentData {
    private double hoursWorked;
    private double hourlyRate;
    private double regularPay;
    private double overtimePay;
    private double totalPay;
    private double tax;
    private String timestamp;

    public PaymentData(double hoursWorked, double hourlyRate, double regularPay,
                       double overtimePay, double totalPay, double tax) {
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
        this.regularPay = regularPay;
        this.overtimePay = overtimePay;
        this.totalPay = totalPay;
        this.tax = tax;
        this.timestamp = new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
                .format(new Date());
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public double getRegularPay() {
        return regularPay;
    }

    public double getOvertimePay() {
        return overtimePay;
    }

    public double getTotalPay() {
        return totalPay;
    }

    public double getTax() {
        return tax;
    }

    public String getTimestamp() {
        return timestamp;
    }
}