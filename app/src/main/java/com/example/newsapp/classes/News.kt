package com.example.newsapp.classes

data class News(
    val totalResults: Int,
    val articles: List<Article>
)