package com.github.flockcommunity.reactivewordrank.reactivewordrank.wordcloud

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.server.EntityResponse
import reactor.core.publisher.Flux
import java.time.Duration


/**
 *  wordclouds: [
 *   { id : 1
 *     cloud: {  //(Map<String, Double>) (range 0 -1, or 0 - 100)
 *          dict_word_1 : 0.1,
 *          dict_word_1 : 0.6
 *     }
 *  ]
 */

@RestController
@RequestMapping(path = ["/wordclouds"])

class WordCloudController {
    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun helloWorld() : Flux<String> {
        log.info("Got hello world request")
        return Flux.fromIterable(listOf("1", "2","3")).delayElements(Duration.ofSeconds(2))
    }
}