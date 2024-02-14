package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.classes.News
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.interfaces.NewsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var newsAdapter: NewsAdapter
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getNews()

    }

    private fun getNews() {
        val news = NewsService.newsInterface.getHeadlines("us", 1)
        news.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val newsResponse = response.body()
                if (newsResponse?.articles != null && newsResponse.articles.isNotEmpty()) {
                    Log.d("News", newsResponse.toString())
                    binding.apply {
                        mainProgress.visibility = View.GONE
                        newsAdapter = NewsAdapter(this@MainActivity, newsResponse.articles)
                        newsRecyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
                        newsRecyclerview.adapter = newsAdapter
                    }
                } else {
                    Toast.makeText(this@MainActivity, "News not available", Toast.LENGTH_SHORT)
                        .show()
                    binding.mainProgress.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("News", "Error in Fetching News", t)
                Toast.makeText(this@MainActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
                binding.mainProgress.visibility = View.GONE
            }
        })
    }
}