package com.meghamlabs.productlist.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.meghamlabs.productlist.R
import com.meghamlabs.productlist.data.api.ProductApiService
import com.meghamlabs.productlist.data.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var apiService: ProductApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ProductApiService::class.java)

        lifecycleScope.launch(Dispatchers.Main) {

            val response = apiService.getProducts()
            val productList = (response.body()?.products!!)
            val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
            val adapter = ProductAdapter(productList as MutableList<Product>,apiService)
            recyclerView.adapter = adapter
            val layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerView.layoutManager = layoutManager


        }

    }
}