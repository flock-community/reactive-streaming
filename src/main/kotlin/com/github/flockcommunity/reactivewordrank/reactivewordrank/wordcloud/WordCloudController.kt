package com.github.flockcommunity.reactivewordrank.reactivewordrank.wordcloud

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux


/**
 *  wordClouds: [
 *   { id : 1
 *     cloud: {  //(Map<String, Double>) (range 0 -1, or 0 - 100)
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
    fun getWords(): Flux<String> {
        log.info("Got words request")
        return wordService.getWords()
                .doOnError{log.warn("Words can no longer be exposed",it)}
                .onErrorStop()
    }

    @GetMapping(path = ["/word-distributions"])
    fun getWordDistributions(): Flux<WordCloud> {
        log.info("Got word distributions")
        return wordService.getWordDistribution()
                .doOnError{log.warn("Word-distribution can no longer be exposed",it)}
                .onErrorStop()
    }
}

