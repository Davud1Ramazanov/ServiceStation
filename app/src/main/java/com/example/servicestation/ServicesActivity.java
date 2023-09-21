package com.example.servicestation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.List;

public class ServicesActivity extends AppCompatActivity {

    private ListView listView;
    private static final String TAG = "ServicesActivity";
    private static final String PREF_JWT_TOKEN = "jwt_token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        listView = findViewById(R.id.listService);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String jwtToken = sharedPreferences.getString(PREF_JWT_TOKEN, "");

        if (jwtToken.isEmpty()) {
            // Обработка случая, когда токен отсутствует
            // Можно перенаправить пользователя на страницу авторизации или выполнить другие действия
        } else {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.d(TAG, message);
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(chain -> {
                        // Добавляем JWT Bearer Token к заголовкам запроса
                        Request originalRequest = chain.request();
                        Request newRequest = originalRequest.newBuilder()
                                .header("Authorization", "Bearer " + jwtToken)
                                .build();
                        return chain.proceed(newRequest);
                    })
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://javaandroid07-001-site1.etempurl.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();

            ApiService apiService = retrofit.create(ApiService.class);

            Call<List<Service>> call = apiService.getServices();

            call.enqueue(new Callback<List<Service>>() {
                @Override
                public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
                    if (response.isSuccessful()) {
                        List<Service> serviceList = response.body();
                        if (serviceList != null) {
                            ServiceListAdapter adapter = new ServiceListAdapter(ServicesActivity.this, serviceList);
                            listView.setAdapter(adapter);
                        }
                    } else {
                        int statusCode = response.code();
                        Log.e(TAG, "Error executing the request, status code: " + statusCode);
                    }
                }

                @Override
                public void onFailure(Call<List<Service>> call, Throwable t) {
                    t.printStackTrace();
                    Log.e(TAG, "Error executing the request: " + t.getMessage());
                }
            });
        }
    }

    public void clickMainMenuWindow(View view) {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}