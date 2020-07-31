package com.github.flockcommunity.reactivestreaming.middleware.wordcloud.controller.response

import com.github.flockcommunity.reactivestreaming.middleware.wordcloud.domain.WordCloud

internal fun WordCloud.toResponse(): WordCloudResponse {
    return WordCloudResponse(
            id = id.toString(),
            wordTotal = wordCounter.values.sum(),
            wordDistribution = wordCounter.mapValues { (_, count) -> count.toDouble() }
    )
}