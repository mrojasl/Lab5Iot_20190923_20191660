package com.example.lab5iot;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EmployeeApi {

    @GET("byManager/{managerId}")
    Call<List<String>> getWorkerList(@Path("managerId") String managerId);
    @GET("api/employees/byManager/{managerId}")
    Call<List<Employee>> getEmployeesByManagerId(@Path("managerId") int managerId);
}