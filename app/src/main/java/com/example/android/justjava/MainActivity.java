package com.example.android.justjava;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.text.NumberFormat;

/*

    This app displays an order form to order coffee.
    */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream_id);
        boolean hasWhippedCream = whippedCream.isChecked();

        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_id);
        boolean hasChocolate = chocolate.isChecked();

        EditText nameText = (EditText) findViewById(R.id.name_text_view);
        String name = nameText.getText().toString();

        String x = createOrderSummary(name, calculatePrice(hasWhippedCream, hasChocolate), hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.title_for_order, name));
        intent.putExtra(Intent.EXTRA_TEXT, x);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
            Toast.makeText(getApplicationContext(), getString(R.string.success), Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(getApplicationContext(), getString(R.string.failure), Toast.LENGTH_SHORT).show();


    }

    /**
     * Calculates the price of the order.
     *
     * @param hasWhippedCream is whether or not the user wants whipped cream topping
     * @param hasChocolate    is whether or not the user wants chocolate topping
     * @return a price
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int priceOfOneCoffee = 5;

        if (hasWhippedCream) {
            priceOfOneCoffee = priceOfOneCoffee + 2;
        }
        if (hasChocolate) {
            priceOfOneCoffee = priceOfOneCoffee + 1;
        }

        return quantity * priceOfOneCoffee;
    }

    /**
     * Create summary of the order.
     *
     * @param price Price of coffees.
     */
    private String createOrderSummary(String name, int price, boolean hasWhippedCream, boolean hasChocolate) {
        return getString(R.string.name, name) +
                "\n" + getString(R.string.add_whipped_cream, hasWhippedCream) +
                "\n" + getString(R.string.add_chocolate, hasChocolate) +
                "\n" + getString(R.string.quantity_price, quantity) +
                "\n" + getString(R.string.total_currency_mark,
                            NumberFormat.getCurrencyInstance().format(price)) +
                "\n" + getString(R.string.thank_you);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity < 100) {
            quantity++;
            display(quantity);
        } else {
            Toast.makeText(getApplicationContext(), "You can't order more than 100 coffees.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity > 1) {
            quantity--;
            display(quantity);
        } else {
            Toast.makeText(getApplicationContext(), "You can't order more than 100 coffees.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}