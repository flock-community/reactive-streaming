package com.github.flockcommunity.reactivewordrank.reactivewordrank.hackernews

import com.github.flockcommunity.reactivewordrank.reactivewordrank.utils.HttpClientException
import com.github.flockcommunity.reactivewordrank.reactivewordrank.utils.HttpServerException
import com.github.flockcommunity.reactivewordrank.reactivewordrank.utils.createWebClient
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.time.Duration

@Component
class HackerNewsWebClient(
        @Value("\${httpClients.hackernews.baseUrl}") baseUrl: String,
        @Value("\${httpClients.hackernews.connectTimeout}") connectTimeout: Duration,
        @Value("\${httpClients.hackernews.readTimeout}") readTimeout: Duration
) {

    private val log = LoggerFactory.getLogger(javaClass)

    private val webClient = createWebClient(baseUrl, connectTimeout, readTimeout)

    fun getComments(): Flux<String> {
        log.info("Received getComments request")
        return webClient.get()
                .uri("/comments/stream/")
                .accept(MediaType.ALL)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError){ response -> throw HttpClientException("Something went wrong", response) }
                .onStatus(HttpStatus::is5xxServerError){ response -> throw HttpServerException("Something went wrong", response) }


                .bodyToFlux(String::class.java)
    }
}

