package com.example.myapplication.com.example.myapplication

import com.example.myapplication.ProductResponse
import retrofit2.Call
import retrofit2.http.GET

interface ProductService {
    @GET("b/MX0A")
    fun getProducts(): Call<ProductResponse>
}