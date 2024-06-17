package com.example.appmusickotlin.test.viewmodel_livedata

import android.app.DownloadManager
import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.example.appmusickotlin.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
     val viewModel : NewViewModel by viewModels()
    private val downloadUrl = "https://static.apero.vn/techtrek/AnhKhongLamGiDauAnhThe.mp3"
    private var mediaPlayer: MediaPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val addButton = findViewById<Button>(R.id.add)
        val view = findViewById<TextView>(R.id.txt)

        addButton.setOnClickListener {
                downloadAndSaveMusic(downloadUrl)

                Toast.makeText(this, "File loaded", Toast.LENGTH_SHORT).show()


        }
    }
    private fun downloadAndSaveMusic(url: String) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                createFolderInInternalStorage(this@MainActivity, "music")
                val folder = createFolderInInternalStorage(this@MainActivity, "music")

                val file = File(folder, "AnhKhongLamGiDauAnhThe.mp3")
                // Nếu file đã tồn tại, chỉ cần phát nhạc từ file đã có
                if (file.exists()) {
                    playMusic(file)
                } else {
                    // Tải file nhạc từ URL và sau đó phát nhạc
                    val success = downloadFile(url, file)
                    if (success) {
                        playMusic(file)
                    } else {
                        // Xử lý khi tải về không thành công
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun downloadFile(url: String, file: File): Boolean {
        return try {
            val urlConnection = URL(url).openConnection()
            urlConnection.connect()
            val inputStream = urlConnection.getInputStream()

            FileOutputStream(file).use { outputStream ->

                val buffer = ByteArray(1024) // 4KB buffer
                var bytesRead: Int

                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                }
                outputStream.flush()
            }
            inputStream.close()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun playMusic(file: File) {

        mediaPlayer?.apply {
            release()
        }
        mediaPlayer = MediaPlayer().apply {
            setDataSource(file.absolutePath)
            setOnPreparedListener {
                it.start()
            }
            prepareAsync()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }

    // Hàm tạo thư mục trong bộ nhớ trong
    fun createFolderInInternalStorage(context: Context, folderName: String): File {
        val folder = File(context.filesDir, folderName)
        if (!folder.exists()) {
            folder.mkdir()
        }
        return folder
    }


}
