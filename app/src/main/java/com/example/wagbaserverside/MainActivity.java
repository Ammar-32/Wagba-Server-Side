package com.example.wagbaserverside;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference ordersRef;
    OrderAdapter orderAdapter;
    FirebaseRecyclerOptions<Order> options;
    Spinner filterSpinner;

    @Override
    protected void onStart() {
        super.onStart();
        orderAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        orderAdapter.stopListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        filterSpinner = findViewById(R.id.filter_order_status_spinner);

        ImageButton refreshBtn = (ImageButton)findViewById(R.id.refresh_btn);



        String[] orderStatusFilters = new String[] { "Show All", "Order Requested", "Order Confirmed", "Order Delivered", "Order Cancelled" };
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, orderStatusFilters);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(spinnerAdapter);

        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedStatus = parent.getItemAtPosition(position).toString();
                Query ordersQuery;
                if (selectedStatus.equals("Show All")) {
                    ordersQuery = ordersRef;
                } else {
                    ordersQuery = ordersRef.orderByChild("orderStatus").equalTo(selectedStatus);
                }
                FirebaseRecyclerOptions<Order> options = new FirebaseRecyclerOptions.Builder<Order>()
                        .setQuery(ordersQuery, Order.class)
                        .build();
                orderAdapter.updateOptions(options);
                orderAdapter.startListening();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        database = FirebaseDatabase.getInstance("https://wagba-ed603-default-rtdb.europe-west1.firebasedatabase.app");
        ordersRef = database.getReference("Orders");

        options = new FirebaseRecyclerOptions.Builder<Order>().setQuery(ordersRef, Order.class).build();
        orderAdapter = new OrderAdapter(options);

        recyclerView = (RecyclerView) findViewById(R.id.order_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(orderAdapter);

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderAdapter.notifyDataSetChanged();
            }
        });

    }
}