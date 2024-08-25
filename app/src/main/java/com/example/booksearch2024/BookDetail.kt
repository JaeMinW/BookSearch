package com.example.booksearch2024

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.booksearch2024.databinding.ActivityBookDetailBinding

class BookDetail : AppCompatActivity() {

    private lateinit var binding : ActivityBookDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_detail)

        val title = intent.getStringExtra("title")
        val author = intent.getStringExtra("author")
        val publisher = intent.getStringExtra("publisher")
        val pubdate = intent.getStringExtra("pubdate")
        val imageUrl = intent.getStringExtra("imageUrl")
        val description = intent.getStringExtra("description")

        binding.bookTitle.text = title
        binding.bookAuthor.text = String.format("저자 : %s", author)
        binding.bookPublisher.text = String.format("출판사 : %s", publisher)
        binding.bookPubdate.text = String.format("출간일 : %s", pubdate)
        binding.bookDescription.text = description

        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.loading)
            .into(binding.bookImg)


    }
}