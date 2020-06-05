package com.github.flockcommunity.reactivewordrank.reactivewordrank.wordcloud.domain

data class WordCloud(
        val id: Long,
        val wordCounter: MutableMap<String, Long>
)