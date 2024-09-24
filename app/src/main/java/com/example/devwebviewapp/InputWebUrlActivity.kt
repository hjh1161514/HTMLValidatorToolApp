package com.example.devwebviewapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.devwebviewapp.databinding.ActivityInputWebUrlBinding

class InputWebUrlActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInputWebUrlBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInputWebUrlBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initClickListener()
    }

    private fun initClickListener() {
        binding.btnOpenWebview.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("url", binding.etInputUrl.text.toString())
            startActivity(intent)
        }

        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}