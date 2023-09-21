package com.example.servicestation;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Login extends AppCompatActivity {

    TextView nameLogin, passwordLogin, confirmPasswordLogin;
    Button confirmBtnLogin;
    private static final String PREF_JWT_TOKEN = "jwt_token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nameLogin = (TextView) findViewById(R.id.nameLogin);
        passwordLogin = (TextView) findViewById(R.id.passwordLogin);
        confirmPasswordLogin = (TextView) findViewById(R.id.confirmPasswordLogin);
        confirmBtnLogin = (Button) findViewById(R.id.confirmBtnLogin);

        confirmBtnLogin.setOnClickListener((view) -> {
            String name = nameLogin.getText().toString();
            String password = passwordLogin.getText().toString();
            String passwordCheck = confirmPasswordLogin.getText().toString();

            if (name.isEmpty() && password.isEmpty() && passwordCheck.isEmpty()) {
                Toast.makeText(Login.this, "Data is empty", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(passwordCheck)) {
                Toast.makeText(Login.this, "Password check is wrong", Toast.LENGTH_SHORT).show();
            } else {
                loginForm(name, password);
            }
        });
    }

    public void loginForm(String name, String password) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            URL urlReg = new URL("http://javaandroid07-001-site1.etempurl.com/api/Authenticate/login");
            HttpURLConnection connection = (HttpURLConnection) urlReg.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            JSONObject postData = new JSONObject();
            postData.put("userName", name);
            postData.put("password", password);

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
                String jwtToken = response.toString();

                // Сохраняем JWT токен в SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(PREF_JWT_TOKEN, jwtToken);
                editor.apply();

                Toast.makeText(this, "Successful authorization", Toast.LENGTH_SHORT).show();
                clickMenuWindow();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void clickMenuWindow() {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}
