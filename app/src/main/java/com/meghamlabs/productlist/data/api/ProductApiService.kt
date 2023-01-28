package com.meghamlabs.productlist.data.api

import com.meghamlabs.productlist.data.model.APIResponse
import com.meghamlabs.productlist.data.model.Product
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApiService{

    @GET("/products")
    suspend fun getProducts(): Response<APIResponse>

    @DELETE("/products/{id}")
    suspend fun deleteProduct(@Path("id") id: Int): Response<APIResponse>
}