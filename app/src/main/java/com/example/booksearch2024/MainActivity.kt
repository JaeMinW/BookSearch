package com.example.booksearch2024

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.booksearch2024.databinding.ActivityMainBinding
import com.example.booksearch2024.parser.Parser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        binding.searchBtn.setOnClickListener {
            performSearch()
        }

        binding.search.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || event != null && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                performSearch()
                true
            } else {
                false
            }
        }

        binding.listView.setOnItemClickListener { parent, view, position, id ->
            val selectedBook = parent.getItemAtPosition(position) as BookModel

            val intent = Intent(this, BookDetail::class.java).apply {
                putExtra("title", selectedBook.title)
                putExtra("author", selectedBook.author)
                putExtra("publisher", selectedBook.publisher)
                putExtra("pubdate", selectedBook.pubdate)
                putExtra("imageUrl", selectedBook.imageUrl)
                putExtra("description", selectedBook.description)
            }
            startActivity(intent)
        }

    }
    private fun performSearch() {
        val searchText = binding.search.text.toString()

        // 로딩 인디케이터 표시
        binding.progressBar.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.IO).launch {

            try {
                val parser = Parser()
                val booklist = parser.connectNaver(searchText)


                withContext(Dispatchers.Main) {
                    val adapter = ListViewAdapter(booklist)
                    binding.listView.adapter = adapter

                    // 로딩 인디케이터 숨기기
                    binding.progressBar.visibility = View.GONE
                }
            }catch (e : Exception){
                withContext(Dispatchers.Main){
                    //에러처리
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this@MainActivity, "검색 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}