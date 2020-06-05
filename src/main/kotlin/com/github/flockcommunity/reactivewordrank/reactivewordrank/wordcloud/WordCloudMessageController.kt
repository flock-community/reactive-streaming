package com.github.flockcommunity.reactivewordrank.reactivewordrank.wordcloud

import org.slf4j.LoggerFactory.getLogger
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux


@Controller
class TweetSocketController(private val service: WordService) {

    private val log = getLogger(javaClass)

    @MessageMapping("words")
    fun getWords(): Flux<WordResponse> {
        log.info("Messaging words request")
        return service.getWords()
                .doOnNext{ log.info("Exposing '$it' through with Rsocket")}
                .map(::WordResponse)
    }

}