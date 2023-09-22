package com.example.servicestation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class Registration extends AppCompatActivity {

    TextView nameInput, passwordInput, emailInput;
    Button confirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        nameInput = (TextView) findViewById(R.id.nameInput);
        passwordInput = (TextView) findViewById(R.id.passwordInput);
        emailInput = (TextView) findViewById(R.id.emailInput);
        confirmBtn = (Button) findViewById(R.id.confirmBtn);

        confirmBtn.setOnClickListener((view) -> {
            String name = nameInput.getText().toString();
            String password = passwordInput.getText().toString();
            String email = emailInput.getText().toString();

            if (name.isEmpty() && password.isEmpty() && email.isEmpty()) {
                Toast.makeText(Registration.this, "Data is empty", Toast.LENGTH_SHORT).show();
            }else {
                registrationForm(name, password, email);
            }

        });
    }

    public void registrationForm(String name, String password, String email) {
        try {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            URL urlReg = new URL("http://javaandroid07-001-site1.etempurl.com/api/Authenticate/regUser");
            HttpURLConnection connection = (HttpURLConnection) urlReg.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            JSONObject postData = new JSONObject();
            postData.put("userName", name);
            postData.put("password", password);
            postData.put("email", email);

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
                Toast.makeText(this, "Succsessful registration", Toast.LENGTH_SHORT).show();
                MenuWindow();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void clickLoginWindow(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void MenuWindow() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}