package com.example.devwebviewapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.example.devwebviewapp.databinding.ActivityInputWebUrlBinding

class InputWebUrlActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInputWebUrlBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInputWebUrlBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initClickListener()
        initEditText()
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
    
    private fun initEditText() {
        binding.etInputUrl.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.etInputUrl.windowToken, 0)
                true
            } else {
                false
            }
        }
    }
}