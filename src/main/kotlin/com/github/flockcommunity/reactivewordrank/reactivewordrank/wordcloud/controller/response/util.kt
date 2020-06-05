package com.github.flockcommunity.reactivewordrank.reactivewordrank.wordcloud.controller.response

import com.github.flockcommunity.reactivewordrank.reactivewordrank.wordcloud.domain.WordCloud

internal fun WordCloud.toResponse(): WordCloudResponse {
    return WordCloudResponse(
            id = id.toString(),
            wordTotal = wordCounter.values.sum(),
            wordDistribution = wordCounter.mapValues { (_, count) -> count.toDouble() }
    )
}