package com.github.flockcommunity.reactivestreaming.middleware.wordcloud.controller

import com.github.flockcommunity.reactivestreaming.middleware.wordcloud.controller.response.WordCloudResponse
import com.github.flockcommunity.reactivestreaming.middleware.wordcloud.controller.response.WordResponse
import com.github.flockcommunity.reactivestreaming.middleware.wordcloud.controller.response.toResponse
import com.github.flockcommunity.reactivestreaming.middleware.wordcloud.service.WordDistributionService
import com.github.flockcommunity.reactivestreaming.middleware.wordcloud.service.WordService
import org.reactivestreams.Subscription
import org.slf4j.LoggerFactory.getLogger
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import reactor.core.Scannable
import reactor.core.publisher.BufferOverflowStrategy
import reactor.core.publisher.Flux


@Controller
internal class WordCloudMessageController(
        private val wordService: WordService,
        private val wordDistributionService: WordDistributionService) {

    @MessageMapping("words")
    internal fun getWords(): Flux<WordResponse> {
        var subscription: Subscription? = null;

        return wordService.getWords()
                .doFirst { log.info("Messaging words request") }
                .doOnNext {
                    log.info("QueueSize: ${Scannable.from(subscription).scan(Scannable.Attr.BUFFERED)}")
                }
//                .onBackpressureLatest()
//                .onBackpressureBuffer(1000, BufferOverflowStrategy.DROP_OLDEST)
                .doOnSubscribe { subscription = it }
                .log()
                .doOnNext { log.info("Exposing '$it' through with rSocket") }
                .map { it.externalise() }
    }


    @MessageMapping("word-distributions")
    internal fun getWordDistributions(): Flux<WordCloudResponse> {
        log.info("Messaging word-distributions request")
        return wordDistributionService.getWordDistributionCached()
                .onBackpressureLatest()
                .doOnNext { log.info("Exposing '$it' through with rSocket") }
                .map { it.toResponse() }
    }

    private val log = getLogger(javaClass)

    init {
        log.warn("Hello I'm $javaClass")
    }
}