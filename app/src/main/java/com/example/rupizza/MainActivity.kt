package com.example.rupizza

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.rupizza.databinding.ActivityMainBinding
import com.example.rupizza.ui.theme.RUPizzaTheme

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use View Binding to inflate layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up button click listeners for navigation
        binding.chicagoButton.setOnClickListener {
            val intent = Intent(this, ChicagoActivity::class.java)
            startActivity(intent)
        }

        binding.nyButton.setOnClickListener {
            val intent = Intent(this, NYActivity::class.java)
            startActivity(intent)
        }

        binding.currentOrderButton.setOnClickListener {
            val intent = Intent(this, OrderActivity::class.java)
            startActivity(intent)
        }

        binding.placedOrdersButton.setOnClickListener {
            val intent = Intent(this, PlacedOrdersActivity::class.java)
            startActivity(intent)
        }
    }
}