package com.example.devwebviewapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.devwebviewapp.databinding.ActivityBaseBinding

class BaseAcitivty : AppCompatActivity() {
    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBaseBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initClickListener()
    }

    private fun initClickListener() {
        binding.btnHtml.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.btnWebview.setOnClickListener {
            startActivity(Intent(this, InputWebUrlActivity::class.java))
        }
    }
}