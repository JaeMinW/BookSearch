package com.example.booksearch2024

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.example.booksearch2024.databinding.LvItemBinding

class ListViewAdapter(public val list : List<BookModel>) : BaseAdapter() {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var binding : LvItemBinding

        if(convertView == null){
            // 새 항목 뷰를 생성하고 바인딩 객체를 초기화
            binding = LvItemBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
            // convertView를 바인딩의 루트 뷰로 설정
            val view = binding.root
            // convertView에 바인딩 객체를 태그로 저장
            view.tag = binding
            return view
        }else{
            // convertView가 null이 아닐 때 태그에서 바인딩 객체를 가져옴
            binding = convertView.tag as LvItemBinding
        }

        // BookModel 객체를 가져옴
        val bookModel = getItem(position) as BookModel

        // 데이터 바인딩을 통해 뷰에 데이터 설정
        binding.bookTitle.text = bookModel.title
        binding.bookPublisher.text = bookModel.publisher
        binding.bookAuthor.text = bookModel.author
        binding.bookPubDate.text = bookModel.pubdate

        // 이미지 로딩
        Glide.with(parent?.context!!)
            .load(bookModel.imageUrl)
            .placeholder(R.drawable.loading)
            .into(binding.bookImg)

        return convertView

    }

}