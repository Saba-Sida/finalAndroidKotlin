package com.example.yourapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.finalprojectandroidkotlin.FileManager
import com.example.finalprojectandroidkotlin.databinding.FragmentRemoveBinding

class RemoveFragment : Fragment() {

    private var _binding: FragmentRemoveBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRemoveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.buttonRemoveProduct.setOnClickListener {
            val nameToRemove = binding.editProductNameToRemove.text.toString().trim()

            if (nameToRemove.isBlank()) {
                showToast("Please enter a product name.")
                return@setOnClickListener
            }

            val productList = FileManager.loadProducts(requireContext())
            val updatedList = productList.toMutableList()

            val removedProduct = updatedList.find {
                it.name.equals(nameToRemove, ignoreCase = true)
            }

            if (removedProduct != null) {
                updatedList.remove(removedProduct)
                FileManager.saveProducts(requireContext(), updatedList)
                showToast("Successfully removed product: ${removedProduct.name}")
                binding.editProductNameToRemove.text.clear()
            } else {
                showToast("Product '$nameToRemove' not found.")
            }
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
