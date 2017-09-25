package com.example.apple.coffeeorderingapp;

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


public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    int basePrice = 5;
    int finalPrice = 0;
    int chocolatePrice = 2, whippedCreamPrice = 1;
    boolean whippedCreamcheckbox = false;
    boolean chocolateCheckbox = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayQuantity(quantity);
    }

    public void increaseQuantity(View view) {
        quantity += 1;
        finalPrice = getFinalPrice();
        displayQuantity(quantity);
        displayPrice(finalPrice);
    }

    public void decreaseQuantity(View view) {
        quantity -= 1;
        if (quantity <= 0) {
            quantity = 0;
        }
        finalPrice = getFinalPrice();
        displayQuantity(quantity);
        displayPrice(finalPrice);
    }

    public void displayQuantity(int x) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(x));
    }

    public int getFinalPrice() {
        return quantity * basePrice;
    }

    public void displayPrice(int x) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText("Total : $" + x);

    }

    public void checkboxChecked(View view) {
        TextView whippedCreamTextView = (TextView) findViewById(R.id.whipped_cream_text_view);
        TextView chocolateTextView = (TextView) findViewById(R.id.chocolate_text_view);

        CheckBox creamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocoCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);

        whippedCreamcheckbox = creamCheckbox.isChecked();
        if (whippedCreamcheckbox) {
            whippedCreamTextView.setTextColor(Color.parseColor("#65c1d8"));
            creamCheckbox.setTextColor(Color.parseColor("#65c1d8"));
        } else {
            whippedCreamTextView.setTextColor(Color.parseColor("#000000"));
            creamCheckbox.setTextColor(Color.parseColor("#ffffff"));
        }

        chocolateCheckbox = chocoCheckbox.isChecked();
        if (chocolateCheckbox) {
            chocolateTextView.setTextColor(Color.parseColor("#65c1d8"));
            creamCheckbox.setTextColor(Color.parseColor("#65c1d8"));
        } else {
            chocolateTextView.setTextColor(Color.parseColor("#000000"));
            creamCheckbox.setTextColor(Color.parseColor("#ffffff"));
        }
    }

    public int calculateFinalPrice(int quantity) {
        int price = 0;
        if (chocolateCheckbox) {
            price += chocolatePrice;
        }
        if (whippedCreamcheckbox) {
            price += whippedCreamPrice;
        }
        return (basePrice + price) * quantity;
    }

    public void displaySummary() {
        EditText nameEditText = (EditText) findViewById(R.id.name_edittext_view);
        String name = nameEditText.getText().toString();
        TextView summaryTextView = (TextView) findViewById(R.id.summary_text_view);
        Log.i("Main Acitivity", "Name : " + name);
        // Calculate the final price based on toppings and no of orders
        int finalPrice = calculateFinalPrice(quantity);

        String text = "****** SUMMARY ****** \n";
        text += "Name : " + name + "\n";
        text += "No of Coffee : " + String.valueOf(quantity) + "\n";
        text += "Add Whipped Cream toppings ? :" + whippedCreamcheckbox + "\n";
        text += "Add Chocolate toppings ? :" + chocolateCheckbox + "\n";
        text += "Total is : $" + String.valueOf(finalPrice) + "\n";
        text += R.string.thankYou + R.string.visitAgain + "\n";

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee Ordering app for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        summaryTextView.setText(text);
    }

    public void orderSummary(View view) {
        displaySummary();
        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
    }

}
