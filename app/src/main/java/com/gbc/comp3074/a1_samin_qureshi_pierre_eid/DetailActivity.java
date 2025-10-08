package com.gbc.comp3074.a1_samin_qureshi_pierre_eid;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailActivity extends AppCompatActivity {

    private ListView lvPayments;
    private TextView tvEmptyState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Enable back button in ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Payment History");
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.detail_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeViews();
        setupListView();
    }

    private void initializeViews() {
        lvPayments = findViewById(R.id.lvPayments);
        tvEmptyState = findViewById(R.id.tvEmptyState);
    }

    private void setupListView() {
        if (MainActivity.paymentList.isEmpty()) {
            lvPayments.setVisibility(ListView.GONE);
            tvEmptyState.setVisibility(TextView.VISIBLE);
        } else {
            lvPayments.setVisibility(ListView.VISIBLE);
            tvEmptyState.setVisibility(TextView.GONE);

            PaymentAdapter adapter = new PaymentAdapter(this, MainActivity.paymentList);
            lvPayments.setAdapter(adapter);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}