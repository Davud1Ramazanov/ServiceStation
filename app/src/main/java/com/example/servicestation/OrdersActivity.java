package com.example.servicestation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.servicestation.ApiService.ApiService;
import com.example.servicestation.ListAdapter.OrdersListAdapter; // Change the import here
import com.example.servicestation.Models.Order; // Change the import here

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrdersActivity extends AppCompatActivity {

    private ListView listView;
    private static final String TAG = "OrdersActivity";
    private static final String PREF_JWT_TOKEN = "jwt_token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        listView = findViewById(R.id.listOrders);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String jwtToken = sharedPreferences.getString(PREF_JWT_TOKEN, "");

        if (jwtToken.isEmpty()) {
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

            Call<List<Order>> call = apiService.getOrders();

            call.enqueue(new Callback<List<Order>>() {
                @Override
                public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                    if (response.isSuccessful()) {
                        List<Order> orderList = response.body();
                        if (orderList != null) {
                            OrdersListAdapter ordersListAdapter = new OrdersListAdapter(OrdersActivity.this, orderList);
                            listView.setAdapter(ordersListAdapter);
                        }
                    } else {
                        int statusCode = response.code();
                        Log.e(TAG, "Error executing the request, status code: " + statusCode);
                    }
                }

                @Override
                public void onFailure(Call<List<Order>> call, Throwable t) {
                    t.printStackTrace();
                    Log.e(TAG, "Error executing the request: " + t.getMessage());
                }
            });
        }
    }

    public void clickMainMenuWin(View view) {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    public void clickOrderWin(View view) {
        Intent intent = new Intent(this, OrdersActivity.class);
        startActivity(intent);
    }

    public void clickServiceWin(View view) {
        Intent intent = new Intent(this, ServicesActivity.class);
        startActivity(intent);
    }

    public void clickAreaWin(View view) {
        Intent intent = new Intent(this, AreaActivity.class);
        startActivity(intent);
    }
}
