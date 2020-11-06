package com.github.flockcommunity.reactivestreaming.middleware.wordcloud.domain

import com.github.flockcommunity.reactivestreaming.middleware.wordcloud.controller.response.WordResponse

data class Word(
        val idx: Long,
        val word: String
) {
    fun externalise(): WordResponse = WordResponse(idx,word)
}

data class WordCloud(
        val id: Long,
        val wordCounter: MutableMap<String, Long>
)