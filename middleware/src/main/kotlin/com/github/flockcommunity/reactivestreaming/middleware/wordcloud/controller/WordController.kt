package com.github.flockcommunity.reactivestreaming.middleware.wordcloud.controller

import com.github.flockcommunity.reactivestreaming.middleware.wordcloud.controller.response.WordResponse
import com.github.flockcommunity.reactivestreaming.middleware.wordcloud.service.WordService
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping(path = ["/wordclouds"])
internal class WordController(
        private val wordService: WordService) {
    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping(path = ["v1/words/most-recent"])
    internal fun getMostRecentWord(): Mono<WordResponse> = wordService
            .getWords()
            .doFirst { log.info("Got most-recent word request") }
            .next()
            .map { it.externalise() }
            .doOnError { log.warn("Most recent word could not be exposed") }


    @GetMapping(path = ["v1/words"])
    internal fun getWords(): Flux<WordResponse> = wordService
            .getWords()
            .doFirst { log.info("Got words request") }
            .map { it.externalise() }
            .log()
            .doOnError { log.warn("Words can no longer be exposed", it) }
            .doOnComplete { log.info("Finished request") }
            .doOnCancel { log.info("Cancelled request") }








    @GetMapping(path = ["v2/words/most-recent"])
    internal fun getMostRecentWordV2(): Mono<WordResponse> = wordService
            .getWordsCached()
            .doFirst { log.info("Got most-recent word request") }
            .next()
            .map { it.externalise() }
            .doOnError { log.warn("Most recent word could not be exposed") }

    @GetMapping(path = ["v2/words"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    internal fun getWordsV2(): Flux<WordResponse> = wordService
            .getWordsCached()
            .doFirst { log.info("Got words request") }
            .map { it.externalise() }
            .doOnError { log.warn("Words can no longer be exposed", it) }
            .onErrorStop()
            .doOnComplete { log.info("Finished request") }
            .doOnCancel { log.info("Cancelled request") }


}

