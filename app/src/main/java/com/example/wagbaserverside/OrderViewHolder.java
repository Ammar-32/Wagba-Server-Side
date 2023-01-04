package com.example.wagbaserverside;

import android.view.View;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderViewHolder extends RecyclerView.ViewHolder {
    TextView orderIdTextView;
    TextView deliveryTimeTextView;
    TextView deliveryGateTextView;
    TextView paymentMethodTextView;
    TextView userFullNameTextView;
    TextView userPhoneNumberTextView;
    Spinner orderStatusSpinner;
    ListView orderItemsListView;
    TextView orderPriceTextView;


    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);
        orderIdTextView = itemView.findViewById(R.id.order_id_text_view);
        deliveryTimeTextView = itemView.findViewById(R.id.delivery_time_text_view);
        deliveryGateTextView = itemView.findViewById(R.id.delivery_gate_text_view);
        paymentMethodTextView = itemView.findViewById(R.id.payment_method_text_view);
        userFullNameTextView = itemView.findViewById(R.id.user_full_name_text_view);
        userPhoneNumberTextView = itemView.findViewById(R.id.user_phone_number_text_view);
        orderStatusSpinner = itemView.findViewById(R.id.order_status_spinner);
        orderItemsListView = itemView.findViewById(R.id.list_view);
        orderPriceTextView = itemView.findViewById(R.id.order_price);
    }
}
