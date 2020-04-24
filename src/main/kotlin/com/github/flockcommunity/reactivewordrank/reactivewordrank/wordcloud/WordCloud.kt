package com.github.flockcommunity.reactivewordrank.reactivewordrank.wordcloud

data class WordCloud(
        val id: Long,
        val wordCounter: MutableMap<String, Long>
)