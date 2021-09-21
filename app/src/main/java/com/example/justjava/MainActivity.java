package com.example.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.net.Uri;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    int quantity=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    // when we click + button
    public void increment(View view)
    {
        if(quantity==100)
        {

            Toast.makeText(this,"You can't have more than 100 Coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity++;
        displayQuantity(quantity);
    }
    // when we click - button
    public void decrement(View view)
    {
        if(quantity==0)
        {
            Toast.makeText(this,"You can't have less than 1 Coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity--;
        displayQuantity(quantity);
    }
    // when we click order button
    public void submitOrder(View view)
    {
        EditText nameField=(EditText)findViewById(R.id.name_field);
        String name=nameField.getText().toString();

        CheckBox WhippedCreamCheckBox=(CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream= WhippedCreamCheckBox.isChecked();

        CheckBox ChocolateCheckBox=(CheckBox)findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate=ChocolateCheckBox.isChecked();

        int price=calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage=createOrderSummery(name,price,hasWhippedCream,hasChocolate);

        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(intent.EXTRA_SUBJECT, "Just Java Order for"+name);
        intent.putExtra(intent.EXTRA_TEXT, priceMessage);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }

    }
    private String createOrderSummery(String name, int price,boolean addWhippedCream,boolean addChocolateBox){
        String priceMessage=getString(R.string.order_summary_name);
        priceMessage+="\nQuantity: "+quantity;
        priceMessage+="\nAdd Whipped Cream? : "+addWhippedCream;
        priceMessage+="\nAdd Chocolate? : "+addChocolateBox;
        priceMessage+="\nTotal: $"+price;
        priceMessage+="\nThank You";
        return priceMessage;
    }

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate)
    {
        int basePrice=5;
        if(addWhippedCream)
            basePrice+=1;
        if(addChocolate)
            basePrice+=2;
        return quantity*basePrice;
    }

    private void displayQuantity(int numbers)
    {
        TextView quantityTextView=(TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("   "+numbers);
    }

}
