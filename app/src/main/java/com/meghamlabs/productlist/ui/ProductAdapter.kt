package com.meghamlabs.productlist.ui

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.meghamlabs.productlist.R
import com.meghamlabs.productlist.data.api.ProductApiService
import com.meghamlabs.productlist.data.model.Product
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProductAdapter(private val productList: MutableList<Product>, private val apiService: ProductApiService) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view, apiService, productList, this)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
    }

    override fun getItemCount() = productList.size

    class ViewHolder(itemView: View, private val apiService: ProductApiService, private val productList: MutableList<Product>, private val adapter: ProductAdapter) : RecyclerView.ViewHolder(itemView) {

        init {
            val deleteButton = itemView.findViewById<Button>(R.id.btnDelete)
            deleteButton.setOnClickListener {
                val product = productList[bindingAdapterPosition]
                val productId = product.id
                GlobalScope.launch {
                    val response = apiService.deleteProduct(productId)
                    if(response.isSuccessful) {
                        productList.remove(product)
                        (itemView.context as Activity).runOnUiThread {
                            adapter.notifyItemRemoved(layoutPosition)
                        }
                    }
                }
            }
        }

        fun bind(product: Product) {
            val productName = itemView.findViewById<TextView>(R.id.product_name)
            productName.text = product.brand
            val imageView = itemView.findViewById<ImageView>(R.id.product_image)
            Glide.with(itemView.context).load(product.thumbnail).into(imageView)
        }
    }
}


