package com.example.justjava;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    int quantity=0;
    int priceOfOneCup=5;
    int priceFinal;
    boolean hasWhippedCream, hasChocolateTopping;
    String customerName;
    //  method called when order button clicked
    @SuppressLint("QueryPermissionsNeeded")
    public void submitOrder(View view){
        priceFinal=calculatePrice(quantity,priceOfOneCup);
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateToppingCheckBox = findViewById(R.id.chocolate_topping_checkbox);
        hasChocolateTopping = chocolateToppingCheckBox.isChecked();
        EditText nameEditText= findViewById(R.id.name_view);
        customerName = nameEditText.getText().toString();
        String priceMessage=createOrderSummary();
        String addresses = getString(R.string.targetMail);
        String subject =  getString(R.string.subject)+ customerName;
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    //method to create order summary
    public String createOrderSummary(){
        String name= getString(R.string.name) + customerName;
        String message=name + getString(R.string.quantity) + quantity + "\n" + " Total: $ "+ priceFinal;
        message+= getString(R.string.whipped) + hasWhippedCream;
        message+= getString(R.string.chocolate) + hasWhippedCream;
        message+= getString(R.string.thankYou);
        return message;
    }

    // calculate price method
    private int calculatePrice(int quantity, int priceOfOneCup){
        int basePrice=priceOfOneCup;
        if(hasWhippedCream){
            basePrice+=1;
        }
        if(hasChocolateTopping){
            basePrice+=2;
        }
        return basePrice*quantity;
    }
    //method called when + button clicked
    public void incrementQuantity(View view){
        if(quantity==100) {
            Context context = getApplicationContext();
            CharSequence text = getString(R.string.tooSmallOrder);
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        else
        {
            quantity++;
        }
        display(quantity);
    }
    //method called when - button clicked
    public void decrementQuantity(View view){
        if(quantity==1) {
            Context context = getApplicationContext();
            CharSequence text = getString(R.string.tooLargeOrder);
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        }
        else{
            quantity--;
        }
        display(quantity);
    }
    //method to display quantity of coffees on UI
    private void display(int number){
        TextView quantityTextView= findViewById(R.id.quantity_text_view);
        quantityTextView.setText(number);
    }


}