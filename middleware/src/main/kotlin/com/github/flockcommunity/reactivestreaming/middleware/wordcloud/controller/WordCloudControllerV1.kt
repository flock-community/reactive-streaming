package com.github.flockcommunity.reactivestreaming.middleware.wordcloud.controller

import com.github.flockcommunity.reactivestreaming.middleware.wordcloud.controller.response.WordCloudResponse
import com.github.flockcommunity.reactivestreaming.middleware.wordcloud.controller.response.WordResponse
import com.github.flockcommunity.reactivestreaming.middleware.wordcloud.controller.response.toResponse
import com.github.flockcommunity.reactivestreaming.middleware.wordcloud.service.SimpleWordService
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping(path = ["/wordclouds/v1"])
internal class WordCloudControllerV1(private val wordService: SimpleWordService) {
    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping(path = ["/words/most-recent"])
    internal fun getMostRecentWord(): Mono<WordResponse> = wordService
            .getWords()
            .doFirst { log.info("Got most-recent word request") }
            .next()
            .map { it.externalise() }
            .doOnError { log.warn("Most recent word could not be exposed") }

    @GetMapping(path = ["/words"])
    internal fun getWords(): Flux<WordResponse> = wordService
            .getWords()
            .doFirst { log.info("Got words request") }
            .map { it.externalise() }
            .log()
            .doOnError { log.warn("Words can no longer be exposed", it) }
            .doOnComplete { log.info("Finished request") }
            .doOnCancel { log.info("Cancelled request") }

    @GetMapping(path = ["/word-distributions"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    internal fun getWordDistributions(): Flux<WordCloudResponse> {
        log.info("Got word distributions")
        return wordService.getWordDistribution()
                .map { it.toResponse() }
                .onBackpressureDrop()
                .doOnError { log.warn("Word-distribution can no longer be exposed", it) }
                .onErrorStop()
                .doOnComplete { log.info("Finished request") }
                .doOnCancel { log.info("Cancelled request") }
    }

    @GetMapping(path = ["/word-distributions/most-recent"])
    internal fun getMostRecentWordDistribution(): Mono<WordCloudResponse> {
        log.info("Got most recent word distributions")
        return wordService.getWordDistribution()
                .next()
                .map { it.toResponse() }
                .doOnError { log.warn("Most recent Word-distribution could  not be exposed", it) }
                .onErrorStop()
    }
}

