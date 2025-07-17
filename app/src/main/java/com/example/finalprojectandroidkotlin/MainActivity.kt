package com.example.finalprojectandroidkotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.finalprojectandroidkotlin.databinding.ActivityMainBinding
import com.example.yourapp.RemoveFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    private val homeFragment = HomeFragment()
    private val addFragment = AddFragment()    // Placeholder
    private val removeFragment = RemoveFragment() // Placeholder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set default selected fragment
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, homeFragment)
            .commit()

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    loadFragment(homeFragment)
                    true
                }
                R.id.nav_add -> {
                    loadFragment(addFragment)
                    true
                }
                R.id.nav_remove -> {
                    loadFragment(removeFragment)
                    true
                }
                else -> false
            }
        }


//        initializeDataFileIfNeeded() // temporary
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
    }





    // <temporary>
    private fun initializeDataFileIfNeeded() {
        val existing = FileManager.loadProducts(this)
        if (existing.isEmpty()) {
            val initialProducts = listOf(
                Product(1, "Phone", 299.99, "Smartphone with 6GB RAM"),
                Product(2, "Laptop", 899.99, "Gaming laptop with RTX 3060"),
                Product(3, "Headphones", 49.99, "Noise-cancelling headphones")
            )
            FileManager.saveProducts(this, initialProducts)
        }
    }
    // </temporary>
}
