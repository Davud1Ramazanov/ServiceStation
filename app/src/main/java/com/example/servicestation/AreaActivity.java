package com.example.servicestation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.servicestation.ApiService.ApiService;
import com.example.servicestation.ListAdapter.AreaListAdapter;
import com.example.servicestation.Models.Area;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AreaActivity extends AppCompatActivity {

    private ListView listView;
    private static final String TAG = "AreaActivity";
    private static final String PREF_JWT_TOKEN = "jwt_token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);

        listView = findViewById(R.id.listService);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String jwtToken = sharedPreferences.getString(PREF_JWT_TOKEN, "");

        if (jwtToken.isEmpty()) {
            // Handle the case where JWT token is empty or missing.
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

            Call<List<Area>> call = apiService.getCars();

            call.enqueue(new Callback<List<Area>>() {
                @Override
                public void onResponse(Call<List<Area>> call, Response<List<Area>> response) {
                    if (response.isSuccessful()) {
                        List<Area> areaList = response.body();
                        if (areaList != null) {
                            AreaListAdapter areaListAdapter = new AreaListAdapter(AreaActivity.this, areaList);
                            listView.setAdapter(areaListAdapter);
                        }
                    } else {
                        int statusCode = response.code();
                        Log.e(TAG, "Error executing the request, status code: " + statusCode);
                    }
                }

                @Override
                public void onFailure(Call<List<Area>> call, Throwable t) {
                    t.printStackTrace();
                    Log.e(TAG, "Error executing the request: " + t.getMessage());
                }
            });
        }
    }
}
