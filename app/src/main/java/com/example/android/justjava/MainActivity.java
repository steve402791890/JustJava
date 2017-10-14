/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * <p>
 * package com.example.android.justjava;
 *
 * this is a test message for github push
 */
package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.R.attr.description;
import static android.R.attr.name;

import static java.util.logging.Logger.global;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int numberOfCoffees = 1;
    int pricePerCup = 1;
    int priceWippedCream = 1;
    int priceChocolate = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the decrement button is clicked.
     */
    public void decrement(View view) {
        if (numberOfCoffees <= 1) {
            //added a toast message
            Context context = getApplicationContext();
            CharSequence text = "The number of cups can't be less than 1.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            displayQuantity(1);
        } else {
            numberOfCoffees = numberOfCoffees - 1;
            displayQuantity(numberOfCoffees);
        }
        //displayMessage(createTempSummary(pricePerCup));
    }

    /**
     * This method is called when the increment button is clicked.
     */
    public void increment(View view) {
        if (numberOfCoffees >= 10) {
            //added a toast message
            Context context = getApplicationContext();
            CharSequence text = "Sorry, The Maximum number of cups of coffees is 100 cups.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            displayQuantity(10);
        } else {
            numberOfCoffees = numberOfCoffees + 1;
            displayQuantity(numberOfCoffees);
        }
        //displayMessage(createTempSummary(pricePerCup));
    }

    /**
     * This method is called when the decrement button is clicked.
     */
    public void decrementPricePerCup(View view) {

        if (pricePerCup <= 1) {
            //added a toast message
            Context context = getApplicationContext();
            CharSequence text = "The number of price per cup can't be less than $1.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            displayPricePerCup(1);
        } else {
            pricePerCup = pricePerCup - 1;
            displayPricePerCup(pricePerCup);
        }

    }

    /**
     * This method is called when the increment button is clicked.
     */
    public void incrementPricePerCup(View view) {

        if (pricePerCup >= 10) {
            //added a toast message
            Context context = getApplicationContext();
            CharSequence text = "Sorry, The Maximum price of cups of coffees is $10.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            displayPricePerCup(10);
        } else {
            pricePerCup = pricePerCup + 1;
            displayPricePerCup(pricePerCup);
        }

    }

    /**
     * This method displays the given quantity value on the screen.
     * @param number of the cup(s) of coffee
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

//    /** disabled
//     * This method displays the given price on the screen.
//     * @param number of cup(s) of coffee
//     */
//    private void displayPrice(int number) {
//        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
//        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
//    }

    /**
     * This method displays the price per cup on the screen.
     * @param number of cup(s) of coffee
     */
    private void displayPricePerCup(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.pricePerCup_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

//    /** disabled
//     * This method displays the given text on the screen.
//     * @param message display on the screen
//     */
//    private void displayMessage(String message) {
//        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
//        priceTextView.setText(message);
//    }

    /**
     * This method is called when the order submit button is clicked.
     */
    public void submitOrder(View view) {

        Log.v("Has wipped cream","does it?");
        boolean cream = checkWippedCream();
        boolean chocolate = checkChocolate();
        String name = checkYourName();

        String emailBody = createOrderSummary(pricePerCup, cream, chocolate, name);
//        displayMessage(createOrderSummary(pricePerCup, cream, chocolate, name));

        //adding email intent to start email app sent an order email to myself.
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(android.content.Intent.EXTRA_EMAIL,new String[] { "someone@gmail.com" });
        intent.putExtra(Intent.EXTRA_SUBJECT, "[Just JAVA App] for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, emailBody);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

//        //this is calling intent obj to handle a geo location.
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse("47.6, -122.3"));
//        //there is one app can handle this intent, if not just drop this.
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }
    }

    /**
    * This method is check if text box is entered.
    * @return a String with name input.
     */
    private String checkYourName (){

        EditText yourName = (EditText) findViewById(R.id.customer_name);
        String name = yourName.getText().toString();

        return name;

    }

    /**
     * This method is check if wipped cream is checked.
     * @return a String with detail description.
     */
    private boolean checkWippedCream (){
        CheckBox wippedCheckBox = (CheckBox) findViewById(R.id.wipped_cream_checkbox);
        boolean isWipped = wippedCheckBox.isChecked();

        if(isWipped){
            return true;
        }else{
            return false;
        }

    }

    /**
     * This method is to check if the chocolate box is checked.
     * @return a String with detail description.
     */
    private boolean checkChocolate (){
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean isChocolate = chocolateCheckBox.isChecked();

        if(isChocolate){
            return true;
        }else{
            return false;
        }

    }


    /**
     * *************this method is disabled.**********************
     * This method is called when the order need to display a message.
     *
     * @param price is unit price of each cup
     * @return the summary message
     */
    private String createTempSummary(int price) {
        //int total = calculatePrice(numberOfCoffees, price);
        String priceMessage = "Name: Ning Yang";
        priceMessage = priceMessage + "\nQuantity: " + numberOfCoffees;
        //priceMessage = priceMessage + "\nTotal： $" + total;
        priceMessage = priceMessage + "\nThank You!";
        return priceMessage;
    }

    /**
     * This method is called when the order need to display a message.
     *
     * @param price is unit price of each cup
     * @param option1 is the wipped cream on?
     * @param option2 is the chocolate on?
     * @param yourName which name will the order under.
     * @return the summary message
     */
    private String createOrderSummary(int price, Boolean option1, Boolean option2, String yourName ) {
        //Log.v("active", "ative" + option1);
        String wippedCream;
        String Chocolate;

        int total = calculatePrice(option1, option2);

        if(option1){
            wippedCream = "Yes, $" + priceWippedCream + " more with wipped cream";
        }else{
            wippedCream = "No, Cream.";
        }

        if(option2){
            Chocolate = "Yes, $" + priceChocolate + " more with chocolate";
        }else{
            Chocolate = "No, Chocolate.";
        }

        String priceMessage = getString(R.string.thank_you) + getString(R.string.order_summary_name, yourName);
        priceMessage += "\nWith Wipped Cream? " + wippedCream;
        priceMessage += "\nWith Chocolate? " +Chocolate;
        priceMessage += "\nYour order is " + numberOfCoffees + " cups of $" + pricePerCup + " coffee";
        priceMessage += "\nYour total is $" + total;
        priceMessage += "\n";

        return priceMessage;
    }

    /**
     * Calculates the price of the order.
     * @param option1 is
     * @param option2 is
     * @return the total amount of the cost
     */
    private int calculatePrice(boolean option1, boolean option2){
        int total = numberOfCoffees * pricePerCup;

        if(option1){
            //if true add priceWippedCream to total
            total += priceWippedCream;
        }

        if(option2){
            //if true add priceChocolate to total
            total += priceChocolate;
        }
        return total;
    }

}