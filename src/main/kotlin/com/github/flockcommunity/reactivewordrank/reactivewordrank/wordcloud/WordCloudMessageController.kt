package com.github.flockcommunity.reactivewordrank.reactivewordrank.wordcloud

import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux


@Controller
class TweetSocketController(private val service: WordService) {

    @MessageMapping("words")
    fun getWords(): Flux<WordResponse> {
        return service.getWords()
                .map(::WordResponse)
    }

}