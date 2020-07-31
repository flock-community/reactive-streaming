package com.github.flockcommunity.reactivestreaming.middleware.wordcloud.domain

data class WordCloud(
        val id: Long,
        val wordCounter: MutableMap<String, Long>
)