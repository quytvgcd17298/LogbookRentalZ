package com.example.logbookrentalz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText property, datetime, price, note, reporter;
    Spinner spinnerBedroom, spinnerFurniture;
    Button submit;
    SQLite db;
    DatePickerDialog.OnDateSetListener setListener;

    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        property = findViewById(R.id.property);
        datetime = findViewById(R.id.datetime);
        price = findViewById(R.id.price);
        note = findViewById(R.id.note);
        reporter = findViewById(R.id.reporter);
        submit = findViewById(R.id.submit);

        db = new SQLite(this);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this,R.id.property,
                RegexTemplate.NOT_EMPTY,R.string.invalid_property);
        awesomeValidation.addValidation(this,R.id.datetime,
                RegexTemplate.NOT_EMPTY,R.string.invalid_datetime);
        awesomeValidation.addValidation(this,R.id.price,
                RegexTemplate.NOT_EMPTY,R.string.invalid_price);
        awesomeValidation.addValidation(this,R.id.reporter,
                RegexTemplate.NOT_EMPTY,R.string.invalid_reporter);

        spinnerBedroom = (Spinner)findViewById(R.id.spinnerBedroom);

        final ArrayList<String> arrayBedroom = new ArrayList<String>();
        arrayBedroom.add("Bedrooms");
        arrayBedroom.add("Single Room");
        arrayBedroom.add("Double Room");
        arrayBedroom.add("Triple Room");
        arrayBedroom.add("King Room");
        arrayBedroom.add("President Room");
        arrayBedroom.add("Apartment");

        ArrayAdapter arrayAdapterBed = new ArrayAdapter(this, android.R.layout.simple_spinner_item,arrayBedroom);
        arrayAdapterBed.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBedroom.setAdapter(arrayAdapterBed);

        spinnerBedroom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, arrayBedroom.get(i), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(MainActivity.this, "Please enter bedroom !!!", Toast.LENGTH_LONG).show();
            }
        });



        spinnerFurniture = (Spinner)findViewById(R.id.spinnerFurniture);

        final ArrayList<String> arrayFurniture = new ArrayList<String>();
        arrayFurniture.add(0,"Furniture Types");
        arrayFurniture.add("Classic");
        arrayFurniture.add("NeoClassic");
        arrayFurniture.add("Modern");
        arrayFurniture.add("Indochina Style");


        ArrayAdapter arrayAdapterFur = new ArrayAdapter(this, android.R.layout.simple_spinner_item,arrayFurniture);
        arrayAdapterFur.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFurniture.setAdapter(arrayAdapterFur);

        spinnerFurniture.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (adapterView.getItemAtPosition(i).equals("Furniture Types"))
            {

                Toast.makeText(MainActivity.this, "Please enter furniture types !!!", Toast.LENGTH_LONG).show();

            }
            else
            {
                Toast.makeText(MainActivity.this, arrayFurniture.get(i), Toast.LENGTH_SHORT).show();
            }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(MainActivity.this, "Please enter furniture types !!!", Toast.LENGTH_LONG).show();
            }
        });

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        datetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month +1;
                        String date = day+"/"+month+"/"+year;
                        datetime.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String propertyText = property.getText().toString();
                String bedroomText = spinnerBedroom.getSelectedItem().toString();
                String datetimeText = datetime.getText().toString();
                String priceText = price.getText().toString();
                String furnitureText = spinnerFurniture.getSelectedItem().toString();
                String noteText = note.getText().toString();
                String reporterText = reporter.getText().toString();

                Cursor cursor = db.getData();

                if (awesomeValidation.validate() && !bedroomText.equals("Bedrooms") && !furnitureText.equals("Furniture Types")){
                    Boolean insertedDataSuccessful = db.insertAndroidData(propertyText, bedroomText, datetimeText,priceText,furnitureText,noteText,reporterText);
                    Toast.makeText(MainActivity.this, "Insert Completed", Toast.LENGTH_LONG).show();

                    if(cursor.getCount() == 0){
                        Toast.makeText(MainActivity.this, "NO ENTRY", Toast.LENGTH_LONG
                        ).show();
                    }
                    else{
                        StringBuffer buffer = new StringBuffer();
                        while(cursor.moveToNext()){
                            buffer.append("Property Type: " + cursor.getString(0) + "\n");
                            buffer.append("Bedroom: " + cursor.getString(1) + "\n");
                            buffer.append("Date: " + cursor.getString(2) + "\n");
                            buffer.append("Monthly Price: " + cursor.getString(3) + "\n");
                            buffer.append("Furniture Type: " + cursor.getString(4) + "\n");
                            buffer.append("Note: " + cursor.getString(5) + "\n");
                            buffer.append("Reporter Name: " + cursor.getString(6) + "\n\n\n");
                        }
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setCancelable(true);
                        builder.setTitle("Input Details");
                        builder.setMessage(buffer.toString());
                        builder.show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Please Insert All Inputs !!!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}