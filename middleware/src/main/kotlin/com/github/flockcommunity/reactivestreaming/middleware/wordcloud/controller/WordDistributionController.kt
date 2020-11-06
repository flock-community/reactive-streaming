package com.github.flockcommunity.reactivestreaming.middleware.wordcloud.controller

import com.github.flockcommunity.reactivestreaming.middleware.wordcloud.controller.response.WordCloudResponse
import com.github.flockcommunity.reactivestreaming.middleware.wordcloud.controller.response.toResponse
import com.github.flockcommunity.reactivestreaming.middleware.wordcloud.service.WordDistributionService
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping(path = ["/wordclouds"])
internal class WordDistributionController(
        private val wordDistributionService: WordDistributionService) {

    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping(path = ["v1/word-distributions/most-recent"])
    internal fun getMostRecentWordDistribution(): Mono<WordCloudResponse> = wordDistributionService
            .getWordDistribution()
            .doFirst { log.info("Got most recent word distributions") }
            .next()
            .map { it.toResponse() }
            .doOnError { log.warn("Most recent Word-distribution could  not be exposed", it) }
            .onErrorStop()


    @GetMapping(path = ["v1/word-distributions"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    internal fun getWordDistributions(): Flux<WordCloudResponse> = wordDistributionService
            .getWordDistribution()
            .doFirst { log.info("Got word distributions") }
            .map { it.toResponse() }
            .onBackpressureDrop()
            .doOnError { log.warn("Word-distribution can no longer be exposed", it) }
            .onErrorStop()
            .doOnComplete { log.info("Finished request") }
            .doOnCancel { log.info("Cancelled request") }


    @GetMapping(path = ["v2/word-distributions"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    internal fun getWordDistributionsV2(): Flux<WordCloudResponse> = wordDistributionService
            .getWordDistributionCached()
            .doFirst { log.info("Got word distributions") }
            .map { it.toResponse() }
            .onBackpressureDrop()
            .doOnError { log.warn("Word-distribution can no longer be exposed", it) }
            .onErrorStop()
            .doOnComplete { log.info("Finished request") }
            .doOnCancel { log.info("Cancelled request") }


    @GetMapping(path = ["v2/word-distributions/most-recent"])
    internal fun getMostRecentWordDistributionV2(): Mono<WordCloudResponse> = wordDistributionService
            .getWordDistributionCached()
            .doFirst { log.info("Got most recent word distributions") }
            .next()
            .map { it.toResponse() }
            .doOnError { log.warn("Most recent Word-distribution could  not be exposed", it) }
            .onErrorStop()
}

