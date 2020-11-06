package com.github.flockcommunity.reactivestreaming.middleware.wordcloud.controller.response

class WordResponse(
        val index: Long,
        val word: String
)

internal class WordCloudResponse(
        val id: String,
        val wordTotal: Long,
        val wordDistribution : Map<String, Double>
)