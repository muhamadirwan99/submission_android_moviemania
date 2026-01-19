package com.dicoding.moviemania.pages

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.moviemania.R
import com.dicoding.moviemania.databinding.ActivityProfileBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Profile"

        binding.editProfile.setOnClickListener {
            dialogUnderConstruction("Edit Profile")
        }
        binding.changePassword.setOnClickListener {
            dialogUnderConstruction("Change Password")
        }
        binding.logout.setOnClickListener {
            dialogUnderConstruction("Logout")
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun dialogUnderConstruction(message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Under Construction")
            .setMessage("This feature $message not yet available")
            .setPositiveButton("OK") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }
}