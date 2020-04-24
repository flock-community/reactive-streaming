package com.github.flockcommunity.reactivewordrank.reactivewordrank.wordcloud

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


/**
 *  wordClouds: [
 *   { id : 1
 *     wordCounters: {  //(Map<String, Double>) (range 0 -1, or 0 - 100)
 *          dict_word_1 : 0.1,
 *          dict_word_1 : 0.6
 *     }
 *  ]
 */

@RestController
@RequestMapping(path = ["/wordclouds"])
class WordCloudController(private val wordService: WordService) {
    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping(path = ["/words"])
    fun getWords(): Flux<WordResponse> {
        log.info("Got words request")
        return wordService.getWords()
                .map(::WordResponse)
                .doOnError{log.warn("Words can no longer be exposed",it)}
                .onErrorStop()
                .doOnComplete { log.info("Finished request") }
    }

    @GetMapping(path=["/words/most-recent"])
    fun getMostRecentWord(): Mono<WordResponse> {
        log.info("Got most-recent word request")
        return wordService.getWords()
                .next()
                .map(::WordResponse)
                .doOnError { log.warn("Most recent word could not be exposed") }
    }

    @GetMapping(path = ["/word-distributions"])
    fun getWordDistributions(): Flux<WordCloud> {
        log.info("Got word distributions")
        return wordService.getWordDistribution()
                .doOnError{log.warn("Word-distribution can no longer be exposed",it)}
                .onErrorStop()
                .doOnComplete { log.info("Finished request") }
    }

    @GetMapping(path = ["/word-distributions/most-recent"])
    fun getMostRecentWordDistribution(): Mono<WordCloud> {
        log.info("Got most recent word distributions")
        return wordService.getWordDistribution()
                .next()
                .doOnError{log.warn("Most recent Word-distribution could  not be exposed",it)}
                .onErrorStop()
    }
}

class WordResponse(
        val word: String
)

