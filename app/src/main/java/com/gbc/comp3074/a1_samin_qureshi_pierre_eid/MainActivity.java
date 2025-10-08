package com.gbc.comp3074.a1_samin_qureshi_pierre_eid;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText etHoursWorked, etHourlyRate;
    private TextView tvPay, tvOvertimePay, tvTotalPay, tvTax;
    private Button btnCalculate;

    public static ArrayList<PaymentData> paymentList = new ArrayList<>();
    private DecimalFormat df = new DecimalFormat("#,##0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeViews();
        setupCalculateButton();
    }

    private void initializeViews() {
        etHoursWorked = findViewById(R.id.etHoursWorked);
        etHourlyRate = findViewById(R.id.etHourlyRate);
        tvPay = findViewById(R.id.tvPay);
        tvOvertimePay = findViewById(R.id.tvOvertimePay);
        tvTotalPay = findViewById(R.id.tvTotalPay);
        tvTax = findViewById(R.id.tvTax);
        btnCalculate = findViewById(R.id.btnCalculate);
    }

    private void setupCalculateButton() {
        btnCalculate.setOnClickListener(v -> calculatePayment());
    }

    private void calculatePayment() {
        String hoursStr = etHoursWorked.getText().toString().trim();
        String rateStr = etHourlyRate.getText().toString().trim();

        if (!validateInput(hoursStr, rateStr)) {
            return;
        }

        double hours = Double.parseDouble(hoursStr);
        double rate = Double.parseDouble(rateStr);

        double regularPay;
        double overtimePay;
        double totalPay;
        double tax;

        if (hours <= 40) {
            regularPay = hours * rate;
            overtimePay = 0;
        } else {
            regularPay = 40 * rate;
            overtimePay = (hours - 40) * rate * 1.5;
        }

        totalPay = regularPay + overtimePay;
        tax = totalPay * 0.18;

        displayResults(regularPay, overtimePay, totalPay, tax);
        savePaymentData(hours, rate, regularPay, overtimePay, totalPay, tax);
        showSuccessMessage();
    }

    private boolean validateInput(String hoursStr, String rateStr) {
        if (hoursStr.isEmpty() || rateStr.isEmpty()) {
            showErrorDialog("Input Error", "Please fill in all fields");
            return false;
        }

        try {
            double hours = Double.parseDouble(hoursStr);
            double rate = Double.parseDouble(rateStr);

            if (hours <= 0) {
                showErrorDialog("Invalid Hours", "Hours worked must be greater than 0");
                return false;
            }

            if (rate <= 0) {
                showErrorDialog("Invalid Rate", "Hourly rate must be greater than 0");
                return false;
            }

            if (hours > 168) {
                showErrorDialog("Invalid Hours", "Hours cannot exceed 168 (hours in a week)");
                return false;
            }

            return true;
        } catch (NumberFormatException e) {
            showErrorDialog("Invalid Input", "Please enter valid numbers");
            return false;
        }
    }

    private void displayResults(double pay, double overtimePay, double totalPay, double tax) {
        tvPay.setText("$" + df.format(pay));
        tvOvertimePay.setText("$" + df.format(overtimePay));
        tvTotalPay.setText("$" + df.format(totalPay));
        tvTax.setText("$" + df.format(tax));
    }

    private void savePaymentData(double hours, double rate, double pay, double overtimePay,
                                 double totalPay, double tax) {
        PaymentData payment = new PaymentData(hours, rate, pay, overtimePay, totalPay, tax);
        paymentList.add(payment);
    }

    private void showSuccessMessage() {
        Toast.makeText(this, "Payment calculated successfully!", Toast.LENGTH_SHORT).show();
    }

    private void showErrorDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_view_payments) {
            Intent intent = new Intent(this, DetailActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}