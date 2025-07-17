package com.example.finalprojectandroidkotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.finalprojectandroidkotlin.databinding.FragmentAddBinding

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)

        binding.buttonAddProduct.setOnClickListener {
            addProduct()
        }

        return binding.root
    }

    private fun addProduct() {
        val name = binding.editProductName.text.toString().trim()
        val priceText = binding.editProductPrice.text.toString().trim()
        val desc = binding.editProductDescription.text.toString().trim()

        if (name.isEmpty() || priceText.isEmpty() || desc.isEmpty()) {
            showToast("Please fill in all fields")
            return
        }

        val price = priceText.toDoubleOrNull()
        if (price == null || price < 0) {
            showToast("Please enter a valid price")
            return
        }

        val context = requireContext()
        val productList = FileManager.loadProducts(context)

        // Generate new ID (auto-increment)
        val newId = if (productList.isEmpty()) 1 else productList.maxOf { it.id } + 1
        val newProduct = Product(newId, name, price, desc)

        productList.add(newProduct)
        try {
            FileManager.saveProducts(context, productList)
            showToast("Product added successfully ✅")

            // Clear the form
            binding.editProductName.text?.clear()
            binding.editProductPrice.text?.clear()
            binding.editProductDescription.text?.clear()
        } catch (e: Exception) {
            e.printStackTrace()
            showToast("Failed to save product ❌")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
