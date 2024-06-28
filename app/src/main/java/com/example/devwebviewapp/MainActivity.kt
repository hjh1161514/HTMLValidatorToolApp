package com.example.devwebviewapp

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.devwebviewapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var filePickerLauncher: ActivityResultLauncher<String>

    companion object {
        private const val REQUEST_PERMISSION = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        checkPermission()
        // ActivityResultLauncher 초기화
        filePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                // 선택된 파일을 처리
                openSelectedFile(it)
            } ?: run {
            }
        }

        setContentView(binding.root)
    }

    private fun checkPermission() {
        // TODO x200 권한 처리
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_PERMISSION
            )
        } else {
            initClickListener()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initClickListener()
            } else {
                finish()
                Toast.makeText(this, "권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initClickListener() {
        binding.btnOpenWebview.setOnClickListener {
            startActivity(Intent(this, WebViewActivity::class.java))
        }

        binding.btnOpenLog.setOnClickListener {
            openFileChooser()
        }

        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    // 파일 선택기 열기
    private fun openFileChooser() {
        filePickerLauncher.launch("*/*") // 모든 파일 타입을 선택할 수 있도록 설정
    }

    // 선택된 파일 열기
    private fun openSelectedFile(uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, contentResolver.getType(uri))
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        try {
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "파일을 열 수 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}