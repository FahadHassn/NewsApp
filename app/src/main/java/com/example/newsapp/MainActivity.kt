package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.newsapp.classes.News
import com.example.newsapp.interfaces.NewsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getNews()

    }

    private fun getNews() {
        val news = NewsService.newsInterface.getHeadlines("us", 1)
        news.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news1 = response.body()
                if (news1 != null) {
                    Log.d("News", news1.toString())
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("News", "Error in Fetching News", t)
            }
        })
    }
}