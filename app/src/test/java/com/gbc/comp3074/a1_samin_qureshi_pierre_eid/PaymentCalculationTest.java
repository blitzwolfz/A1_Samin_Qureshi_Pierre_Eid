package com.gbc.comp3074.a1_samin_qureshi_pierre_eid;

import org.junit.Test;
import static org.junit.Assert.*;

public class PaymentCalculationTest {

    private static final double DELTA = 0.01;

    @Test
    public void testRegularPay_LessThan40Hours() {
        double hours = 35;
        double rate = 20.0;
        double expectedPay = 700.0;

        double actualPay = hours * rate;

        assertEquals(expectedPay, actualPay, DELTA);
    }

    @Test
    public void testRegularPay_Exactly40Hours() {
        double hours = 40;
        double rate = 25.0;
        double expectedPay = 1000.0;

        double actualPay = hours * rate;

        assertEquals(expectedPay, actualPay, DELTA);
    }

    @Test
    public void testOvertimePay_MoreThan40Hours() {
        double hours = 45;
        double rate = 20.0;
        double expectedRegularPay = 800.0; // 40 * 20
        double expectedOvertimePay = 150.0; // 5 * 20 * 1.5
        double expectedTotalPay = 950.0;

        double regularPay = 40 * rate;
        double overtimePay = (hours - 40) * rate * 1.5;
        double totalPay = regularPay + overtimePay;

        assertEquals(expectedRegularPay, regularPay, DELTA);
        assertEquals(expectedOvertimePay, overtimePay, DELTA);
        assertEquals(expectedTotalPay, totalPay, DELTA);
    }

    @Test
    public void testTaxCalculation() {
        double totalPay = 1000.0;
        double expectedTax = 180.0; // 18% of 1000

        double actualTax = totalPay * 0.18;

        assertEquals(expectedTax, actualTax, DELTA);
    }

    @Test
    public void testCompletePaymentScenario_NoOvertime() {
        double hours = 30;
        double rate = 15.0;

        double regularPay = hours * rate;
        double overtimePay = 0;
        double totalPay = regularPay + overtimePay;
        double tax = totalPay * 0.18;

        assertEquals(450.0, regularPay, DELTA);
        assertEquals(0.0, overtimePay, DELTA);
        assertEquals(450.0, totalPay, DELTA);
        assertEquals(81.0, tax, DELTA);
    }

    @Test
    public void testCompletePaymentScenario_WithOvertime() {
        double hours = 50;
        double rate = 25.0;

        double regularPay = 40 * rate;
        double overtimePay = (hours - 40) * rate * 1.5;
        double totalPay = regularPay + overtimePay;
        double tax = totalPay * 0.18;

        assertEquals(1000.0, regularPay, DELTA);
        assertEquals(375.0, overtimePay, DELTA);
        assertEquals(1375.0, totalPay, DELTA);
        assertEquals(247.5, tax, DELTA);
    }

    @Test
    public void testPaymentData_Creation() {
        double hours = 45;
        double rate = 20.0;
        double regularPay = 800.0;
        double overtimePay = 150.0;
        double totalPay = 950.0;
        double tax = 171.0;

        PaymentData payment = new PaymentData(hours, rate, regularPay, overtimePay, totalPay, tax);

        assertEquals(hours, payment.getHoursWorked(), DELTA);
        assertEquals(rate, payment.getHourlyRate(), DELTA);
        assertEquals(regularPay, payment.getRegularPay(), DELTA);
        assertEquals(overtimePay, payment.getOvertimePay(), DELTA);
        assertEquals(totalPay, payment.getTotalPay(), DELTA);
        assertEquals(tax, payment.getTax(), DELTA);
        assertNotNull(payment.getTimestamp());
    }

    @Test
    public void testEdgeCase_OneHourOvertime() {
        double hours = 41;
        double rate = 30.0;

        double regularPay = 40 * rate;
        double overtimePay = 1 * rate * 1.5;
        double totalPay = regularPay + overtimePay;

        assertEquals(1200.0, regularPay, DELTA);
        assertEquals(45.0, overtimePay, DELTA);
        assertEquals(1245.0, totalPay, DELTA);
    }

    @Test
    public void testDecimalHours() {
        double hours = 42.5;
        double rate = 20.0;

        double regularPay = 40 * rate;
        double overtimePay = (hours - 40) * rate * 1.5;
        double totalPay = regularPay + overtimePay;

        assertEquals(800.0, regularPay, DELTA);
        assertEquals(75.0, overtimePay, DELTA);
        assertEquals(875.0, totalPay, DELTA);
    }

    @Test
    public void testDecimalRate() {
        double hours = 40;
        double rate = 15.75;

        double regularPay = hours * rate;

        assertEquals(630.0, regularPay, DELTA);
    }
}