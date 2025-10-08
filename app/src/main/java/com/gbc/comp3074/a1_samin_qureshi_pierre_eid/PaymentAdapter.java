package com.gbc.comp3074.a1_samin_qureshi_pierre_eid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PaymentAdapter extends ArrayAdapter<PaymentData> {
    private Context context;
    private ArrayList<PaymentData> payments;
    private DecimalFormat df = new DecimalFormat("#,##0.00");

    public PaymentAdapter(Context context, ArrayList<PaymentData> payments) {
        super(context, R.layout.payment_list_item, payments);
        this.context = context;
        this.payments = payments;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.payment_list_item, parent, false);
            holder = new ViewHolder();
            holder.tvTimestamp = convertView.findViewById(R.id.tvTimestamp);
            holder.tvHoursRate = convertView.findViewById(R.id.tvHoursRate);
            holder.tvPayDetails = convertView.findViewById(R.id.tvPayDetails);
            holder.tvTotalAmount = convertView.findViewById(R.id.tvTotalAmount);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PaymentData payment = payments.get(position);

        holder.tvTimestamp.setText(payment.getTimestamp());
        holder.tvHoursRate.setText(String.format("%.1f hrs @ $%.2f/hr",
                payment.getHoursWorked(), payment.getHourlyRate()));

        String payDetails = String.format("Regular: $%s | Overtime: $%s | Tax: $%s",
                df.format(payment.getRegularPay()),
                df.format(payment.getOvertimePay()),
                df.format(payment.getTax()));
        holder.tvPayDetails.setText(payDetails);

        holder.tvTotalAmount.setText("$" + df.format(payment.getTotalPay()));

        return convertView;
    }

    static class ViewHolder {
        TextView tvTimestamp;
        TextView tvHoursRate;
        TextView tvPayDetails;
        TextView tvTotalAmount;
    }
}