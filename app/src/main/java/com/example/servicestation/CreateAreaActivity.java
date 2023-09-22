package com.example.servicestation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CreateAreaActivity extends AppCompatActivity {

    TextView nameText, modelText, yearText, numberText, vinText, capacityText;
    Button createCarInfoBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_area);

        nameText = (TextView) findViewById(R.id.nameText);
        modelText = (TextView) findViewById(R.id.modelText);
        yearText = (TextView) findViewById(R.id.yearText);
        numberText = (TextView) findViewById(R.id.numberText);
        vinText = (TextView) findViewById(R.id.vinText);
        capacityText = (TextView) findViewById(R.id.capacityText);
        createCarInfoBtn = (Button) findViewById(R.id.createCarInfoBtn);

        createCarInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameText.getText().toString();
                String model = modelText.getText().toString();
                String year = yearText.getText().toString();
                String number = numberText.getText().toString();
                String vin = vinText.getText().toString();
                String capacity = capacityText.getText().toString();

                if (name.isEmpty() && model.isEmpty() && year.isEmpty() && number.isEmpty() && vin.isEmpty() && capacity.isEmpty()) {
                    Toast.makeText(CreateAreaActivity.this, "Data is empty", Toast.LENGTH_SHORT).show();
                }else {
                    registrationForm(name, model, year, number, vin, capacity);
                }
            }
        });
    }

    public void registrationForm(String name, String model, String year, String number, String vin, String capacity) {
        try {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            URL urlReg = new URL("http://javaandroid07-001-site1.etempurl.com/api/Car/Create");
            HttpURLConnection connection = (HttpURLConnection) urlReg.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            JSONObject postData = new JSONObject();
            postData.put("name", name);
            postData.put("model", model);
            postData.put("year", year);
            postData.put("number", number);
            postData.put("vin", vin);
            postData.put("capacity", capacity);

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(postData.toString().getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                Toast.makeText(this, "Succsessful create info", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}