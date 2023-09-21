package com.example.servicestation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("Category/Select")
    Call<List<Service>> getServices();
}
