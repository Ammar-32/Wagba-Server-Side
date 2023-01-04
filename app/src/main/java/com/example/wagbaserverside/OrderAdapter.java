package com.example.wagbaserverside;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends FirebaseRecyclerAdapter<Order, OrderViewHolder> {
    private DatabaseReference ordersRef;
    FirebaseDatabase database;
    public OrderAdapter(@NonNull FirebaseRecyclerOptions<Order> options) {
        super(options);
        database = FirebaseDatabase.getInstance("https://wagba-ed603-default-rtdb.europe-west1.firebasedatabase.app");
        ordersRef = database.getReference("Orders");
    }

    @Override
    protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull Order order) {
        holder.orderIdTextView.setText("Order ID:\n#" + order.getOrderID().toUpperCase(Locale.ROOT));
        holder.deliveryTimeTextView.setText("Delivery Time:\n" + order.getDeliveryTime());
        holder.deliveryGateTextView.setText("Delivery Gate:\n" + order.getDeliveryGate());
        holder.paymentMethodTextView.setText("Payment Method:\n" + order.getPaymentMethod());
        holder.userFullNameTextView.setText("User Full Name:\n" + order.getUserFullName());
        holder.userPhoneNumberTextView.setText("User Phone Number:\n" + order.getUserPhoneNumber());
        holder.orderPriceTextView.setText(order.getPrice() + " EGP");
        List<String> orderSummary = new ArrayList<>();
        for (OrderItemModel orderItem : order.getOrderItems()) {
            orderSummary.add("x" + orderItem.getOrderItemCount() + " " + orderItem.getOrderItemName() + "\n" + orderItem.getOrderItemTotalPrice() + "EGP");
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(holder.itemView.getContext(), android.R.layout.simple_list_item_1, orderSummary);
        holder.orderItemsListView.setAdapter(adapter);

        ArrayAdapter<String> orderStatusAdapter = new ArrayAdapter<>(holder.itemView.getContext(),
                android.R.layout.simple_spinner_item, new String[]{"Order Requested", "Order Confirmed", "Order Delivered", "Order Cancelled"});
        orderStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.orderStatusSpinner.setAdapter(orderStatusAdapter);

        holder.orderStatusSpinner.setSelection(orderStatusAdapter.getPosition(order.getOrderStatus()));
        holder.orderStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedStatus = (String) parent.getItemAtPosition(position);
                ordersRef.child(order.getOrderID()).child("orderStatus").setValue(selectedStatus);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
        Calendar currentTime = Calendar.getInstance();
        Calendar cancelTime12 = Calendar.getInstance();
        cancelTime12.set(Calendar.HOUR_OF_DAY, 10);
        cancelTime12.set(Calendar.MINUTE, 30);
        Calendar cancelTime15 = Calendar.getInstance();
        cancelTime15.set(Calendar.HOUR_OF_DAY, 13);
        cancelTime15.set(Calendar.MINUTE, 30);

        if (currentTime.after(cancelTime12) && "Order Requested".equals(order.getOrderStatus()) && order.getDeliveryTime().equals("12:00")) {
            ordersRef.child(order.getOrderID()).child("orderStatus").setValue("Order Cancelled");
        }

        if (currentTime.after(cancelTime15) && "Order Requested".equals(order.getOrderStatus()) && order.getDeliveryTime().equals("15:00")) {
            ordersRef.child(order.getOrderID()).child("orderStatus").setValue("Order Cancelled");
        }

    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new OrderViewHolder(view);
    }
}
