package com.github.flockcommunity.reactivewordrank.reactivewordrank.wordcloud.controller.response

internal class WordResponse(
        val word: String
)

internal class WordCloudResponse(
        val id: String,
        val wordTotal: Long,
        val wordDistribution : Map<String, Double>

)