package com.example.booksearch2024.parser

import com.example.booksearch2024.BookModel
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.regex.Pattern

class Parser {

    fun connectNaver(searchText: String): ArrayList<BookModel>{

        val list = ArrayList<BookModel>()

        try{
            // MainActivity의 search EditText에서 텍스트를 가져옴
            val myQuery = URLEncoder.encode(searchText, "utf-8")
            val urlStr = "https://openapi.naver.com/v1/search/book.xml?query=$myQuery&display=100"
            val url = URL(urlStr)
            val connection = url.openConnection() as HttpURLConnection

            // 발급받은 ID와 Secret을 서버로 전달
            connection.setRequestProperty("X-Naver-Client-Id", "yCVTyRvvU1HVB3S0UA9D")
            connection.setRequestProperty("X-Naver-Client-secret", "l2omyErI8j")

            // URL을 수행하여 받은 자원들을 파싱
            val factory = XmlPullParserFactory.newInstance()
            val parser = factory.newPullParser()

            // connection 객체가 접속 후 가지게 된 내용을 parser가 스트림으로 읽어옴
            parser.setInput(connection.inputStream, null)

            // 파서 객체를 통해 각 요소별 접근하여 태그(요소) 내부의 값들을 가져옴
            var parseEvent = parser.eventType
            var bookModel: BookModel? = null

            while(parseEvent != XmlPullParser.END_DOCUMENT){

                if(parseEvent == XmlPullParser.START_TAG) {
                    val tagName = parser.name

                    when (tagName) {
                        "title" -> {
                            bookModel = BookModel()
                            var title = parser.nextText()

                            val pattern = Pattern.compile("<.*?>")
                            val matcher = pattern.matcher(title)

                            if (matcher.find()) {
                                title = matcher.replaceAll("")
                            }
                            bookModel.title = title
                        }

                        "image" -> {
                            bookModel?.imageUrl = parser.nextText()
                        }

                        "author" -> {
                            bookModel?.author = parser.nextText()
                        }

                        "publisher" -> {
                            bookModel?.publisher = parser.nextText()
                        }

                        "description" -> {
                            bookModel?.description = parser.nextText()
                        }

                        "pubdate" -> {
                            bookModel?.let {
                                it.pubdate = parser.nextText()
                                list.add(it) //마지막 정보인 pubdate까지 찾고 난 뒤 list에 저장
                            }
                        }
                    }
                }
                parseEvent = parser.next() // 다음 요소로 이동
            }

        }catch (e : Exception){
            e.printStackTrace()
        }

        return list
    }
}