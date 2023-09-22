package com.example.servicestation.ApiService;

import com.example.servicestation.Models.Area;
import com.example.servicestation.Models.Order;
import com.example.servicestation.Models.Service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("Category/Select")
    Call<List<Service>> getServices();
    @GET("Order/SelectOrderUser")
    Call<List<Order>> getOrders();
    @GET("Car/SelectUser")
    Call<List<Area>> getCars();
}
