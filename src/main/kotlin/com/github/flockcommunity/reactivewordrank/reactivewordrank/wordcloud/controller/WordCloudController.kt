package com.github.flockcommunity.reactivewordrank.reactivewordrank.wordcloud.controller

import com.github.flockcommunity.reactivewordrank.reactivewordrank.wordcloud.WordService
import com.github.flockcommunity.reactivewordrank.reactivewordrank.wordcloud.controller.response.WordCloudResponse
import com.github.flockcommunity.reactivewordrank.reactivewordrank.wordcloud.controller.response.WordResponse
import com.github.flockcommunity.reactivewordrank.reactivewordrank.wordcloud.controller.response.toResponse
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
    internal fun getWords(): Flux<WordResponse> {
        log.info("Got words request")
        return wordService.getWords()
                .map(::WordResponse)
                .onBackpressureDrop()
                .doOnError{log.warn("Words can no longer be exposed",it)}
                .onErrorStop()
                .doOnComplete { log.info("Finished request") }
                .doOnCancel { log.info("Cancelled request") }
//                .doOnDiscard { log.info("Discarded request") }
    }

    @GetMapping(path=["/words/most-recent"])
    internal fun getMostRecentWord(): Mono<WordResponse> {
        log.info("Got most-recent word request")
        return wordService.getWords()
                .next()
                .map(::WordResponse)
                .doOnError { log.warn("Most recent word could not be exposed") }
    }

    @GetMapping(path = ["/word-distributions"])
    internal fun getWordDistributions(): Flux<WordCloudResponse> {
        log.info("Got word distributions")
        return wordService.getWordDistribution()
                .map{it.toResponse()}
                .onBackpressureDrop()
                .doOnError{log.warn("Word-distribution can no longer be exposed",it)}
                .onErrorStop()
                .doOnComplete { log.info("Finished request") }
                .doOnCancel { log.info("Cancelled request") }
    }

    @GetMapping(path = ["/word-distributions/most-recent"])
    internal fun getMostRecentWordDistribution(): Mono<WordCloudResponse> {
        log.info("Got most recent word distributions")
        return wordService.getWordDistribution()
                .next()
                .map{it.toResponse()}
                .doOnError{log.warn("Most recent Word-distribution could  not be exposed",it)}
                .onErrorStop()
    }
}

