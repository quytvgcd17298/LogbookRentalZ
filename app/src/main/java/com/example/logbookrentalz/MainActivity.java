package com.example.logbookrentalz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText property, datetime, price, note, reporter;
    Spinner spinnerBedroom, spinnerFurniture;
    Button submit;
    SQLite db;


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
        arrayFurniture.add("Furniture Types");
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
                Toast.makeText(MainActivity.this, arrayFurniture.get(i), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(MainActivity.this, "Please enter furniture types !!!", Toast.LENGTH_LONG).show();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String propertyText = property.getText().toString();
                String bedroomText = spinnerBedroom.toString();
                String datetimeText = datetime.getText().toString();
                String priceText = price.getText().toString();
                String furnitureText = spinnerFurniture.toString();
                String noteText = note.getText().toString();
                String reporterText = reporter.getText().toString();


                if (!propertyText.isEmpty() & !bedroomText.isEmpty() & !datetimeText.isEmpty() &
                    !priceText.isEmpty() & !furnitureText.isEmpty() & !reporterText.isEmpty()){
                    Boolean insertedDataSuccessful = db.insertAndroidData(propertyText, bedroomText, datetimeText,priceText,furnitureText,noteText,reporterText);
                    if(insertedDataSuccessful) {
                        Toast.makeText(MainActivity.this, "Inserted Successfully !!!", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Please Insert All Inputs !!!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}