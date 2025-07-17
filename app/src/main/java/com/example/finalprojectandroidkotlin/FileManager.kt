package com.example.finalprojectandroidkotlin

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object FileManager {
    private const val FILE_NAME = "products.json"
    private val gson = Gson()

    fun loadProducts(context: Context): MutableList<Product> {
        return try {
            val fileInput = context.openFileInput(FILE_NAME)
            val json = fileInput.bufferedReader().use { it.readText() }
            val type = object : TypeToken<MutableList<Product>>() {}.type
            gson.fromJson(json, type) ?: mutableListOf()
        } catch (e: Exception) {
            mutableListOf()
        }
    }

    fun saveProducts(context: Context, products: List<Product>) {
        val json = gson.toJson(products)
        context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use {
            it.write(json.toByteArray())
        }
    }
}
